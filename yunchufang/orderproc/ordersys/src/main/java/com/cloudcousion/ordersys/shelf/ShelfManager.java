package com.cloudcousion.ordersys.shelf;

import com.cloudcousion.orderserver.model.OrderTemperature;
import com.cloudcousion.ordersys.config.KitchenConfig;
import com.cloudcousion.ordersys.kitchen.CookedOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShelfManager extends Thread implements ShelfI {
    private TempShelfDevice hotShelfDev;
    private TempShelfDevice coldShelfDev;
    private TempShelfDevice frozenShelfDev;
    private OverflowShelfDevice overflowShelfDev;
    private List<CookedOrder> wastedOrders;

    public ShelfManager(KitchenConfig config) {
        wastedOrders = new ArrayList<>();
        hotShelfDev = new TempShelfDevice(OrderTemperature.Hot, config.tempShelfCapacity);
        coldShelfDev = new TempShelfDevice(OrderTemperature.Cold, config.tempShelfCapacity);
        frozenShelfDev = new TempShelfDevice(OrderTemperature.Frozen, config.tempShelfCapacity);
        overflowShelfDev = new OverflowShelfDevice(config.overflowShelfCapacity);
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
        return order;
    }

    @Override
    public synchronized void shelfOrder(CookedOrder order) {
        doShelfOrder(order, false);
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
                    CookedOrder ord = overflowShelfDev.takeOne();
                    doShelfOrder(ord, true);
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
    public int shelfDeviceOrderSize(OrderTemperature temperature) {
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

    private void setAsWasted(CookedOrder order) {
        wastedOrders.add(order);
    }

    @Override
    public List<CookedOrder> getWasteOrders() {
        return wastedOrders;
    }
}
