package com.cloudcousion.ordersys.courier;

import com.cloudcousion.orderserver.OrderListenerI;
import com.cloudcousion.orderserver.OrderServerI;
import com.cloudcousion.orderserver.model.Order;
import com.cloudcousion.orderserver.utils.ConsoleLogger;
import com.cloudcousion.orderserver.utils.OrderLoggerI;
import com.cloudcousion.ordersys.config.SimulatorConfig;
import com.cloudcousion.ordersys.kitchen.CookedOrder;
import com.cloudcousion.ordersys.shelf.ShelfManagerI;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

public class CourierManager extends Thread implements OrderListenerI {
    private final OrderLoggerI logger = ConsoleLogger.getInstance();
    private final SimulatorConfig simulatorConfig;
    private boolean exit;

    private ShelfManagerI shelfManager;
    private PriorityQueue<OrderInfo> orderInfos;
    private List<CookedOrder> fullFillOrders;
    private List<CourierMgrStateListenerI> stateListener;

    public CourierManager(SimulatorConfig simulatorConfig
            , ShelfManagerI shelfManager
            , OrderServerI orderServer) {
        this.shelfManager = shelfManager;
        this.simulatorConfig = simulatorConfig;
        orderServer.registerOrderDeliveryListener(this);
        fullFillOrders = new ArrayList<>();
        orderInfos = new PriorityQueue<>();
        stateListener = new ArrayList<>();
        exit = false;
    }

    @Override
    public void run() {
        while (!exit) {
            synchronized (this) {
                while (orderInfos.size() == 0) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        logger.logWarning("InterruptedException, msg:" + e.getMessage());
                    }
                }

                Calendar calendar = Calendar.getInstance();
                long now = calendar.getTimeInMillis();
                OrderInfo orderInfo = orderInfos.peek();
                while (orderInfo != null && orderInfo.getPickTime() <= now) {
                    orderInfos.poll();
                    notifyStateListeners();
                    CookedOrder order = shelfManager.takeOrder(orderInfo.getOrderId(), orderInfo.getTemperature());
                    //Maybe the order is wasted!
                    if (order != null) {
                        logger.logInfo("CourierManager, deliver order:" + order.getId());
                        deliverOrder(order);
                    }
                    orderInfo = orderInfos.peek();
                }

                long delay = 1000;
                if (orderInfo != null && orderInfo.getPickTime() > now) {
                    delay = orderInfo.getPickTime() - now;
                }
                try {
                    wait(delay);
                } catch (InterruptedException e) {
                    logger.logWarning("InterruptedException, msg:" + e.getMessage());
                }
            }
        }
    }

    private void notifyStateListeners() {
        for (CourierMgrStateListenerI listener : stateListener) {
            listener.stateChanged();
        }
    }

    private void deliverOrder(CookedOrder order) {
        fullFillOrders.add(order);
    }

    public synchronized int orderQueueSize() {
        return orderInfos.size();
    }

    //This method will be call in order server thread!
    @Override
    public synchronized void orderSendToKitchen(Order order) {
        OrderInfo orderInfo = new OrderInfo(order);

        //Courier will arrive at rand time from 2-6 seconds
        Calendar calendar = Calendar.getInstance();
        long now = calendar.getTimeInMillis();
        Random random = new Random();
        float r = random.nextFloat();
        long f = (long) (simulatorConfig.courierDelayMin + (simulatorConfig.courierDelayMax - simulatorConfig.courierDelayMin) * r);
        //Set the pick up time!
        long v = now + f;
        //logger.logDebug("orderSendToKitchen, r:" + r + ", f:" + f + ", now:" + now + ", v:" + v);
        orderInfo.setPickTime(v);

        orderInfos.offer(orderInfo);
        notifyAll();
    }

    public synchronized OrderInfo poolOrder() {
        return orderInfos.poll();
    }

    public List<CookedOrder> getFullFillOrders() {
        return new ArrayList<>(fullFillOrders);
    }

    public void registerStateListener(CourierMgrStateListenerI listener) {
        stateListener.add(listener);
    }

    public void unregisterStateListener(CourierMgrStateListenerI listener) {
        stateListener.remove(listener);
    }

    public synchronized void close() {
        exit = true;
        notifyAll();
    }
}
