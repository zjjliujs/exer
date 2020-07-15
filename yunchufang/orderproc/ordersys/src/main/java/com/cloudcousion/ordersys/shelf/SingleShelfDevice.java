package com.cloudcousion.ordersys.shelf;

import com.cloudcousion.orderserver.model.OrderTemperature;
import com.cloudcousion.ordersys.kitchen.CookedOrder;
import com.cloudcousion.ordersys.utils.OrderValueCalculatorI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.UUID;

public abstract class SingleShelfDevice extends ShelfDevice {

    protected Queue<CookedOrder> orders;

    public SingleShelfDevice(int capacity) {
        super(capacity);
        this.orders = new PriorityQueue<>();
    }

    public int getFree() {
        return capacity - orders.size();
    }

    @Override
    public CookedOrder takeOrder(UUID id, OrderTemperature temperature) {
        Iterator<CookedOrder> it = orders.iterator();
        while (it.hasNext()) {
            CookedOrder order = it.next();
            if (order.getId().equals(id)) {
                it.remove();
                return order;
            }
        }
        return null;
    }

    @Override
    public CookedOrder peekOrder(UUID id, OrderTemperature temperature) {
        Iterator<CookedOrder> it = orders.iterator();
        while (it.hasNext()) {
            CookedOrder order = it.next();
            if (order.getId().equals(id)) {
                return order;
            }
        }
        return null;
    }

    @Override
    public int orderSize() {
        return orders.size();
    }

    @Override
    public List<CookedOrder> allOrders() {
        return new ArrayList<>(orders);
    }

    @Override
    public List<CookedOrder> evaluate(OrderValueCalculatorI evaluator) {
        List<CookedOrder> wasted = new ArrayList<>();
        Iterator<CookedOrder> it = orders.iterator();
        while (it.hasNext()) {
            CookedOrder order = it.next();
            float v = evaluator.calculateOrderValue(order);
            order.setValue(v);
            if (v <= 0) {
                wasted.add(order);
                it.remove();
            }
            logger.logDebug("EvaluateOrders value:" + v + ", order id:" + order.getId());
        }
        return wasted;
    }
}
