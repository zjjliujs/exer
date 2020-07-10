package com.cloudcousion.ordersys.kitchen;

import com.cloudcousion.orderserver.OrderConsumerI;
import com.cloudcousion.orderserver.OrderServerI;
import com.cloudcousion.orderserver.model.Order;
import com.cloudcousion.orderserver.utils.ConsoleLogger;
import com.cloudcousion.orderserver.utils.OrderLoggerI;
import com.cloudcousion.ordersys.shelf.ShelfManagerI;
import com.cloudcousion.ordersys.utils.OrderValueCalculatorI;

import java.util.LinkedList;
import java.util.Queue;

public class Kitchen extends Thread implements OrderConsumerI {
    private OrderLoggerI logger = ConsoleLogger.getInstance();

    private final OrderValueCalculatorI valueCalculator;
    private OrderServerI orderServer;
    private ShelfManagerI shelf;
    private Queue<CookedOrder> cookedOrders;
    private boolean exit;

    public Kitchen(OrderValueCalculatorI valueCalculator, OrderServerI orderServer, ShelfManagerI shelf) {
        cookedOrders = new LinkedList<>();
        this.valueCalculator = valueCalculator;
        this.orderServer = orderServer;
        this.shelf = shelf;
        this.exit = false;
    }

    @Override
    public void run() {
        while (!exit) {
            synchronized (this) {
                while (cookedOrders.size() == 0) {
                    try {
                        logger.logDebug("Kitchen run: to wait!");
                        wait();
                    } catch (InterruptedException e) {
                        logger.logError("InterruptedException, msg:" + e.getMessage());
                    }
                }

                logger.logDebug("Kitchen run: cookedOrders size:" + cookedOrders.size());
                for (CookedOrder order : cookedOrders) {
                    shelf.shelfOrder(order);
                }

                cookedOrders.clear();
            }
        }
    }

    /**
     * Close kitchen. Stop kitchen thread, unregister server consumer.
     */
    public synchronized void close() {
        orderServer.unregisterOrderConsumer(this);
        exit = true;
        notifyAll();
    }

    @Override
    public synchronized boolean dispatchOrder(Order order) {
        //Cooked instantly :)
        cookedOrders.add(new CookedOrder(order));
        notifyAll();
        return true;
    }
}
