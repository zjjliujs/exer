package com.cloudcousion.ordersys.shelf;

import com.cloudcousion.orderserver.model.OrderTemperature;
import com.cloudcousion.orderserver.utils.AndroidLogger;
import com.cloudcousion.orderserver.utils.ConsoleLogger;
import com.cloudcousion.orderserver.utils.OrderLoggerI;
import com.cloudcousion.ordersys.config.SimulatorConfig;
import com.cloudcousion.ordersys.kitchen.CookedOrder;
import com.cloudcousion.ordersys.utils.OrderValueCalculatorI;

import java.util.ArrayList;
import java.util.List;
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
        List<CookedOrder> ords = shelfDevice.evaluate(evaluator);
        wastedOrders.addAll(ords);
    }

    @Override
    public synchronized void close() {
        exit = true;
        notifyAll();
    }

    @Override
    public synchronized CookedOrder peekOrder(UUID orderId, OrderTemperature temperature) {
        switch (temperature) {
            case Hot:
                return hotShelfDev.peekOrder(orderId, temperature);
            case Cold:
                return coldShelfDev.peekOrder(orderId, temperature);
            case Frozen:
                return frozenShelfDev.peekOrder(orderId, temperature);
            case None:
                return overflowShelfDev.peekOrder(orderId, temperature);
            default:
                //Impossible!
                throw new RuntimeException("Error temperature:" + temperature);
        }
    }

    @Override
    public synchronized CookedOrder takeOrder(UUID orderId, OrderTemperature temperature) {
        CookedOrder order;
        switch (temperature) {
            case Hot:
                order = hotShelfDev.takeOrder(orderId, temperature);
                break;
            case Cold:
                order = coldShelfDev.takeOrder(orderId, temperature);
                break;
            case Frozen:
                order = frozenShelfDev.takeOrder(orderId, temperature);
                break;
            default:
                //Impossible
                throw new RuntimeException("takeOrder, error temperature:" + temperature);
        }
        if (order == null) {
            order = overflowShelfDev.takeOrder(orderId, temperature);
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
                return new ArrayList<>(coldShelfDev.allOrders());
            }
            case Hot: {
                return new ArrayList<>(hotShelfDev.allOrders());
            }
            case Frozen: {
                return new ArrayList<>(frozenShelfDev.allOrders());
            }
            case None: {
                return new ArrayList<>(overflowShelfDev.allOrders());
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
                putToDevice(hotShelfDev, order);
                break;
            case Cold:
                putToDevice(coldShelfDev, order);
                break;
            case Frozen:
                putToDevice(frozenShelfDev, order);
                break;
            default:
                //Impossible!
                throw new RuntimeException("doShelfOrder, wrong order temp! order id:" + order.getId());
        }
    }

    private void putToDevice(TempShelfDevice tempDev, CookedOrder order) {
        boolean ok = tempDev.putOrder(order);
        if (!ok) {
            ok = overflowShelfDev.putOrder(order);
            if (!ok) {
                //overflow device is full aussi!
                boolean shiftOk;
                switch (order.getTemp()) {
                    case Hot:
                        //Hot shelf already full
                        shiftOk = shiftOrder(coldShelfDev);
                        if (!shiftOk)
                            shiftOk = shiftOrder(frozenShelfDev);
                        break;
                    case Cold:
                        //Cold shelf already full
                        shiftOk = shiftOrder(hotShelfDev);
                        if (!shiftOk)
                            shiftOk = shiftOrder(frozenShelfDev);
                        break;
                    case Frozen:
                        //Frozen shelf alread full
                        shiftOk = shiftOrder(hotShelfDev);
                        if (!shiftOk)
                            shiftOk = shiftOrder(coldShelfDev);
                        break;
                    default:
                        //Impossible
                        throw new RuntimeException("Error temperature!");
                }
                logger.logDebug("putToDevice, shiftOK:" + shiftOk);
                if (!shiftOk) {
                    //All shelf is full, move order with lowest value to wast list
                    CookedOrder lowVOrd = overflowShelfDev.pollOrder();
                    wastedOrders.add(lowVOrd);
                }
                //Should have place now!
                logger.logDebug("putToDevice, overflowShelfDev size:" + overflowShelfDev.orderSize());
                ok = overflowShelfDev.putOrder(order);
                if (!ok) {
                    //Impossible!
                    throw new RuntimeException("putToDevice, overflow shelf devce still full!");
                }
            }
        }
    }

    /**
     * Shift order from overflow shelf to a temp shelf!
     *
     * @param targetShelfDev
     * @return true if shift ok， false is can't shift (shelf is full)
     */
    private boolean shiftOrder(TempShelfDevice targetShelfDev) {
        OrderTemperature temperature;
        if (targetShelfDev == hotShelfDev) {
            temperature = OrderTemperature.Hot;
        } else if (targetShelfDev == coldShelfDev) {
            temperature = OrderTemperature.Cold;
        } else if (targetShelfDev == frozenShelfDev) {
            temperature = OrderTemperature.Frozen;
        } else {
            //impossible!
            throw new RuntimeException("Wrong shelf dev!");
        }
        if (targetShelfDev.getFree() > 0) {
            if (overflowShelfDev.haveOrder(temperature)) {
                CookedOrder o = overflowShelfDev.pollOrder(temperature);
                //o should not be null!
                targetShelfDev.putOrder(o);
                return true;
            }
        }
        return false;
    }

    @Override
    public int deviceOrderSize(OrderTemperature temperature) {
        switch (temperature) {
            case Cold:
                return coldShelfDev.orderSize();
            case Frozen:
                return frozenShelfDev.orderSize();
            case Hot:
                return hotShelfDev.orderSize();
            case None:
                return overflowShelfDev.orderSize();
            default:
                //Impossible
                throw new RuntimeException("Error temperature:" + temperature);
        }
    }

    @Override
    public synchronized int totalOrderSize() {
        return hotShelfDev.orderSize()
                + coldShelfDev.orderSize()
                + frozenShelfDev.orderSize()
                + overflowShelfDev.orderSize();
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
