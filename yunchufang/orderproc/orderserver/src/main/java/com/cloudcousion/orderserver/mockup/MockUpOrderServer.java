package com.cloudcousion.orderserver.mockup;

import com.cloudcousion.orderserver.OrderConsumerI;
import com.cloudcousion.orderserver.OrderListenerI;
import com.cloudcousion.orderserver.OrderServerI;
import com.cloudcousion.orderserver.OrderServerStateListenerI;
import com.cloudcousion.orderserver.config.OrderServerConfig;
import com.cloudcousion.orderserver.model.Order;
import com.cloudcousion.orderserver.utils.ConsoleLogger;
import com.cloudcousion.orderserver.utils.OrderLoggerI;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MockUpOrderServer implements OrderServerI, Runnable {
    //private OrderLoggerI logger = AndroidLogger.getInstance();
    private OrderLoggerI logger = ConsoleLogger.getInstance();

    private static MockUpOrderServer instance = null;

    private boolean exit;
    private Queue<Order> orders;
    private List<OrderConsumerI> orderConsumers;
    private List<OrderServerStateListenerI> stateListeners;
    private int dispatchRate;   //orders per second
    private Thread thread;
    private List<OrderListenerI> orderListeners;

    public synchronized static MockUpOrderServer getInstance() {
        if (instance == null) {
            instance = new MockUpOrderServer();
        }
        return instance;
    }

    private MockUpOrderServer() {
        orders = new LinkedList<>();
        orderConsumers = new ArrayList<>();
        stateListeners = new ArrayList<>();
        orderListeners = new ArrayList<>();
        this.dispatchRate = OrderServerConfig.getDispatchRate();
        this.thread = null;
        this.exit = false;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                /*If no order or listener, we have nothing to do */
                logger.logDebug("run: orderConsumers size: " + orderConsumers.size());
                logger.logDebug("run: order size: " + orders.size());
                while (orderConsumers.size() == 0 || orders.size() == 0) {
                    try {
                        if (exit) {
                            logger.logDebug("run: to exit!");
                            return;
                        }
                        wait();
                    } catch (InterruptedException e) {
                        logger.logWarning("InterruptedException, " + e.getMessage());
                    }
                }

                for (OrderConsumerI consumer : orderConsumers) {
                    Order order = orders.poll();
                    if (order == null) {
                        try {
                            if (exit) {
                                logger.logDebug("run: to exit!");
                                return;
                            }
                            wait();
                        } catch (InterruptedException e) {
                            logger.logWarning("InterruptedException, " + e.getMessage());
                        }
                    } else {
                        logger.logDebug("run: to dispatch order! order id:" + order.getId());
                        consumer.dispatchOrder(order);
                        notifyOrderListener(order);
                        notifyServerStateChanged();
                    }
                }

                try {
                    if (exit) {
                        logger.logDebug("run: to exit!");
                        return;
                    }
                    wait((long) (1000f / dispatchRate));
                } catch (InterruptedException e) {
                    logger.logWarning("InterruptedException, " + e.getMessage());
                }
            }
        }
    }

    private void notifyOrderListener(Order order) {
        for (OrderListenerI listener : orderListeners) {
            listener.orderSendToKitchen(order);
        }
    }

    private void notifyServerStateChanged() {
        for (OrderServerStateListenerI listener : stateListeners) {
            listener.serverStateChanged();
        }
    }

    @Override
    public List<Order> ordersInQueue() {
        return new ArrayList<>(orders);
    }

    @Override
    public synchronized void setDispatchRate(int dispatchRate) {
        this.dispatchRate = dispatchRate;
    }

    @Override
    public synchronized boolean startDispatch() {
        if (thread == null) {
            thread = new Thread(this);
        }
        thread.start();
        return true;
    }

    @Override
    public synchronized void stopService() {
        exit = true;
        notifyAll();
        instance = null;
    }

    @Override
    public synchronized boolean putOrder(Order order) {
        orders.add(order);
        notifyAll();
        notifyServerStateChanged();
        return true;
    }

    @Override
    public synchronized boolean putOrders(List<Order> newOrders) {
        this.orders.addAll(newOrders);
        notifyAll();
        notifyServerStateChanged();
        return true;
    }

    @Override
    public synchronized void registerOrderConsumer(OrderConsumerI orderConsumer) {
        orderConsumers.add(orderConsumer);
        notifyAll();
    }

    @Override
    public synchronized void unregisterOrderConsumer(OrderConsumerI orderConsumer) {
        orderConsumers.remove(orderConsumer);
    }

    @Override
    public synchronized void registerStateListener(OrderServerStateListenerI listener) {
        stateListeners.add(listener);
    }

    @Override
    public synchronized void unregisterStateListener(OrderServerStateListenerI listener) {
        stateListeners.remove(listener);
    }

    @Override
    public void registerOrderDeliveryListener(OrderListenerI listener) {
        orderListeners.add(listener);
    }

    @Override
    public void unregisterOrderDeliveryListener(OrderListenerI listener) {

    }
}
