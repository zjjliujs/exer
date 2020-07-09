package com.cloudcousion.ordersys.shelf;

import com.cloudcousion.orderserver.utils.ConsoleLogger;
import com.cloudcousion.orderserver.utils.OrderLoggerI;
import com.cloudcousion.ordersys.kitchen.CookedOrder;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

public abstract class ShelfDevice {
    protected OrderLoggerI logger = ConsoleLogger.getInstance();

    protected int capacity;   /* Device capacity */
    protected Queue<CookedOrder> orders;

    public ShelfDevice(int capacity) {
        this.orders = new LinkedList<>();
        this.capacity = capacity;
    }

    public abstract boolean putOrder(CookedOrder order);

    public int getFree() {
        return capacity - orders.size();
    }

    public CookedOrder takeOrder(UUID id) {
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

    public int getCapacity() {
        return capacity;
    }
}
