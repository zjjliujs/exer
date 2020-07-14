package com.cloudcousion.ordersys.shelf;

import com.cloudcousion.orderserver.model.OrderTemperature;
import com.cloudcousion.orderserver.utils.ConsoleLogger;
import com.cloudcousion.orderserver.utils.OrderLoggerI;
import com.cloudcousion.ordersys.config.SimulatorConfig;
import com.cloudcousion.ordersys.kitchen.CookedOrder;
import com.cloudcousion.ordersys.utils.OrderValueCalculatorI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.UUID;

public class ShelfManager extends Thread implements ShelfManagerI {
    private OrderLoggerI logger = ConsoleLogger.getInstance();

    private final OrderValueCalculatorI evaluator;
    private boolean exit;
    private TempShelfDevice hotShelfDev;
    private TempShelfDevice coldShelfDev;
    private TempShelfDevice frozenShelfDev;
    private OverflowShelfDevice overflowShelfDev;
    private List<CookedOrder> wastedOrders;
    private List<ShelfStateListenerI> stateListeners;

    public ShelfManager(SimulatorConfig config, OrderValueCalculatorI evaluator) {
        wastedOrders = new ArrayList<>();
        initShelfDevices(config);
        this.evaluator = evaluator;
        stateListeners = new ArrayList<>();
        exit = false;
    }

    private void initShelfDevices(SimulatorConfig config) {
        hotShelfDev = new TempShelfDevice(OrderTemperature.Hot, config.hotShelfCapacity);
        coldShelfDev = new TempShelfDevice(OrderTemperature.Cold, config.coldShelfCapacity);
        frozenShelfDev = new TempShelfDevice(OrderTemperature.Frozen, config.frozenShelfCapacity);
        overflowShelfDev = new OverflowShelfDevice(config.overflowShelfCapacity);
    }

    @Override
    public synchronized void setConfig(SimulatorConfig config) {
        initShelfDevices(config);
    }

    @Override
    public void run() {
        while (!exit) {
            synchronized (this) {
                EvaluateOrders(hotShelfDev);
                EvaluateOrders(coldShelfDev);
                EvaluateOrders(frozenShelfDev);
                EvaluateOrders(overflowShelfDev);

                //Value change event and remove event need all be notified!
                notifyStateListeners();

                //Evaluate one time in one seconds
                try {
                    wait(1000);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }

    /**
     * @param shelfDevice
     * @return true if order moved to waste
     */
    private void EvaluateOrders(ShelfDevice shelfDevice) {
        Iterator<CookedOrder> it = shelfDevice.orders.iterator();
        while (it.hasNext()) {
            CookedOrder order = it.next();
            float v = evaluator.calculateOrderValue(order);
            order.setValue(v);
            if (v <= 0) {
                setAsWasted(order);
                it.remove();
            }
            logger.logDebug("EvaluateOrders value:" + v + ", order id:" + order.getId());
        }
    }

    @Override
    public synchronized void close() {
        exit = true;
        notifyAll();
    }

    @Override
    public synchronized CookedOrder peekOrder(UUID orderId, OrderTemperature temperature) {
        Queue<CookedOrder> orders;
        switch (temperature) {
            case Hot:
                orders = hotShelfDev.orders;
                break;
            case Cold:
                orders = coldShelfDev.orders;
                break;
            case Frozen:
                orders = frozenShelfDev.orders;
                break;
            default:
                orders = overflowShelfDev.orders;
        }
        for (CookedOrder order : orders) {
            if (order.getId().equals(orderId))
                return order;
        }
        return null;
    }

    @Override
    public synchronized CookedOrder takeOrder(UUID orderId, OrderTemperature temperature) {
        CookedOrder order;
        switch (temperature) {
            case Hot:
                order = hotShelfDev.takeOrder(orderId);
                break;
            case Cold:
                order = coldShelfDev.takeOrder(orderId);
                break;
            case Frozen:
                order = frozenShelfDev.takeOrder(orderId);
                break;
            default:
                //Impossible
                throw new RuntimeException("takeOrder, error temperature:" + temperature);
        }
        if (order == null) {
            order = overflowShelfDev.takeOrder(orderId);
        }
        if (order != null) {
            notifyStateListeners();
        }
        return order;
    }

    @Override
    public synchronized List<CookedOrder> deviceOrderList(OrderTemperature temperature) {
        switch (temperature) {
            case Cold: {
                return new ArrayList<>(coldShelfDev.orders);
            }
            case Hot: {
                return new ArrayList<>(hotShelfDev.orders);
            }
            case Frozen: {
                return new ArrayList<>(frozenShelfDev.orders);
            }
            case None: {
                return new ArrayList<>(overflowShelfDev.orders);
            }
            default:
                //Impossible!
                throw new RuntimeException("deviceOrderList, wrong params!");
        }
    }

    @Override
    public synchronized void shelfOrder(CookedOrder order) {
        doShelfOrder(order, false);
        notifyStateListeners();
    }

    private void notifyStateListeners() {
        for (ShelfStateListenerI listener : stateListeners) {
            listener.stateChanged();
        }
    }

    private void doShelfOrder(CookedOrder order, boolean fromOverflow) {
        switch (order.getTemp()) {
            case Hot:
                putToDevice(hotShelfDev, order, fromOverflow);
                break;
            case Cold:
                putToDevice(coldShelfDev, order, fromOverflow);
                break;
            case Frozen:
                putToDevice(frozenShelfDev, order, fromOverflow);
                break;
            default:
                //Impossible!
                throw new RuntimeException("doShelfOrder, wrong order temp! order id:" + order.getId());
        }
    }

    private void putToDevice(TempShelfDevice tempDev, CookedOrder order, boolean fromOverflow) {
        boolean ok = tempDev.putOrder(order);
        if (!ok) {
            //temp device is full
            if (fromOverflow) {
                //wasted!
                setAsWasted(order);
            } else {
                ok = overflowShelfDev.putOrder(order);
                if (!ok) {
                    //overflow device is full aussi!
                    CookedOrder o = overflowShelfDev.takeOne();
                    doShelfOrder(o, true);
                    //Should have place now!
                    ok = overflowShelfDev.putOrder(order);
                    if (!ok) {
                        //Impossible!
                        throw new RuntimeException("putToDevice, overflow shelf devce still full!");
                    }
                }
            }
        }
    }

    @Override
    public int deviceOrderSize(OrderTemperature temperature) {
        switch (temperature) {
            case Cold:
                return coldShelfDev.orders.size();
            case Frozen:
                return frozenShelfDev.orders.size();
            case Hot:
                return hotShelfDev.orders.size();
            case None:
                return overflowShelfDev.orders.size();
            default:
                //Impossible
                throw new RuntimeException("Error temperature:" + temperature);
        }
    }

    @Override
    public synchronized int totalOrderSize() {
        return hotShelfDev.orders.size()
                + coldShelfDev.orders.size()
                + frozenShelfDev.orders.size()
                + overflowShelfDev.orders.size();
    }

    private void setAsWasted(CookedOrder order) {
        wastedOrders.add(order);
    }

    @Override
    public synchronized List<CookedOrder> getWasteOrders() {
        return new ArrayList<>(wastedOrders);
    }

    @Override
    public synchronized void registerStateListener(ShelfStateListenerI stateListener) {
        stateListeners.add(stateListener);
    }

    @Override
    public synchronized void unregisterStateListener(ShelfStateListenerI stateListener) {
        stateListeners.remove(stateListener);
    }
}
