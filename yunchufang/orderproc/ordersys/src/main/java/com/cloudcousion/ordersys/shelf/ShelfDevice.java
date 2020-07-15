package com.cloudcousion.ordersys.shelf;

import com.cloudcousion.orderserver.model.OrderTemperature;
import com.cloudcousion.orderserver.utils.ConsoleLogger;
import com.cloudcousion.orderserver.utils.OrderLoggerI;
import com.cloudcousion.ordersys.kitchen.CookedOrder;
import com.cloudcousion.ordersys.utils.OrderValueCalculatorI;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.UUID;

public abstract class ShelfDevice {
    protected OrderLoggerI logger = ConsoleLogger.getInstance();

    protected int capacity;   /* Device capacity */

    public ShelfDevice(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Put order on shelf
     * @param order
     * @return
     */

    public abstract boolean putOrder(CookedOrder order);

    /**
     * Free place in shelf
     *
     * @return free place in shelf
     */
    public abstract int getFree();

    /**
     * For courier to take out the order and deliver to customer!
     *
     * @param id
     * @param temperature
     * @return Cooked order or null is not found
     */
    public abstract CookedOrder takeOrder(UUID id, OrderTemperature temperature);

    /**
     *Order size in shelf
     *
     * @return order size
     */
    public abstract int orderSize();

    /**
     * All order list
     *
     * @return Cloned order list
     */
    public abstract List<CookedOrder> allOrders();

    /**
     * Peek the the order with lowest value
     *
     * @param orderId
     * @param temperature
     * @return order with lowest value
     */
    public abstract CookedOrder peekOrder(UUID orderId, OrderTemperature temperature);

    /**
     * Evaluate the order value and drop the order with value < 0
     *
     * @param evaluator
     * @return All orders with value < 0
     */
    public abstract List<CookedOrder> evaluate(OrderValueCalculatorI evaluator);
}
