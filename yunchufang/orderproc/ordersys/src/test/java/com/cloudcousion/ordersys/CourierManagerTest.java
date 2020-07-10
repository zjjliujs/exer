package com.cloudcousion.ordersys;

import com.alibaba.fastjson.JSON;
import com.cloudcousion.orderserver.model.Order;
import com.cloudcousion.orderserver.model.OrderTemperature;
import com.cloudcousion.orderserver.utils.ConsoleLogger;
import com.cloudcousion.orderserver.utils.OrderLoggerI;
import com.cloudcousion.ordersys.config.SystemConfig;
import com.cloudcousion.ordersys.courier.CourierManager;
import com.cloudcousion.ordersys.courier.CourierMgrStateListenerI;
import com.cloudcousion.ordersys.courier.OrderInfo;
import com.cloudcousion.ordersys.kitchen.CookedOrder;
import com.cloudcousion.ordersys.shelf.ShelfManagerI;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class CourierManagerTest {
    private final OrderLoggerI logger = ConsoleLogger.getInstance();
    private List<Order> orders;

    @Before
    public void init() {
        orders = JSON.parseArray("[\n" +
                "  {\n" +
                "    \"id\": \"a8cfcb76-7f24-4420-a5ba-d46dd77bdffd\",\n" +
                "    \"name\": \"Banana Split\",\n" +
                "    \"temp\": \"frozen\",\n" +
                "    \"shelfLife\": 20,\n" +
                "    \"decayRate\": 0.63\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"58e9b5fe-3fde-4a27-8e98-682e58a4a65d\",\n" +
                "    \"name\": \"McFlury\",\n" +
                "    \"temp\": \"frozen\",\n" +
                "    \"shelfLife\": 375,\n" +
                "    \"decayRate\": 0.4\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"2ec069e3-576f-48eb-869f-74a540ef840c\",\n" +
                "    \"name\": \"Acai Bowl\",\n" +
                "    \"temp\": \"cold\",\n" +
                "    \"shelfLife\": 249,\n" +
                "    \"decayRate\": 0.3\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"690b85f7-8c7d-4337-bd02-04e04454c826\",\n" +
                "    \"name\": \"Yogurt\",\n" +
                "    \"temp\": \"cold\",\n" +
                "    \"shelfLife\": 263,\n" +
                "    \"decayRate\": 0.37\n" +
                "  },\n" +
                "]\n", Order.class);
    }

    @Test
    public void stateNotifyTest() throws InterruptedException {
        TestShelfManager shelfManager = new TestShelfManager();
        SystemConfig config = new SystemConfig();
        config.courierDelayMin = 2000;
        config.courierDelayMax = 6000;
        CourierManager cm = new CourierManager(shelfManager, config);
        TestStateListener stateListener = new TestStateListener();
        cm.registerStateListener(stateListener);

        for (Order order : orders) {
            shelfManager.shelfOrder(new CookedOrder(order));
            cm.orderSendToKitchen(order);
        }
        cm.start();

        Thread.sleep(1000);
        Assert.assertTrue(0 == stateListener.count);
        Thread.sleep(6000);
        Assert.assertTrue(4 == stateListener.count);
    }

    @Test
    public void orderThreadTest() throws InterruptedException {
        TestShelfManager shelfManager = new TestShelfManager();
        SystemConfig config = new SystemConfig();
        config.courierDelayMin = 2000;
        config.courierDelayMax = 6000;
        CourierManager cm = new CourierManager(shelfManager, config);

        for (Order order : orders) {
            shelfManager.shelfOrder(new CookedOrder(order));
            cm.orderSendToKitchen(order);
        }

        cm.start();
        Assert.assertTrue(4 == cm.orderQueueSize());
        Assert.assertTrue(4 == shelfManager.orders.size());

        Thread.sleep(1000);
        Assert.assertTrue(4 == cm.orderQueueSize());
        Assert.assertTrue(4 == shelfManager.orders.size());

        //Total sleep 7 seconds, all order should be delivered
        Thread.sleep(6000);
        Assert.assertTrue(0 == cm.orderQueueSize());
        Assert.assertTrue(0 == shelfManager.orders.size());
    }

    @Test
    public void orderSendToKitchen() {
        CourierManager cm = new CourierManager(new TestShelfManager(), new SystemConfig());

        for (Order order : orders) {
            cm.orderSendToKitchen(order);
        }

        Assert.assertTrue(4 == cm.orderQueueSize());
        OrderInfo info1 = cm.poolOrder();
        logger.logDebug("orderSendToKitchen, pick time:" + info1.getPickTime() + ", id:" + info1.getOrderId());
        OrderInfo info2 = cm.poolOrder();
        logger.logDebug("orderSendToKitchen, pick time:" + info2.getPickTime() + ", id:" + info2.getOrderId());
        OrderInfo info3 = cm.poolOrder();
        logger.logDebug("orderSendToKitchen, pick time:" + info3.getPickTime() + ", id:" + info3.getOrderId());
        OrderInfo info4 = cm.poolOrder();
        logger.logDebug("orderSendToKitchen, pick time:" + info4.getPickTime() + ", id:" + info4.getOrderId());
        Assert.assertTrue(info1.getPickTime() < info2.getPickTime());
        Assert.assertTrue(info2.getPickTime() < info3.getPickTime());
        Assert.assertTrue(info3.getPickTime() < info4.getPickTime());

        Assert.assertTrue(0 == cm.orderQueueSize());
    }

    private class TestShelfManager implements ShelfManagerI {
        List<CookedOrder> orders;

        public TestShelfManager() {
            orders = new ArrayList<>();
        }

        @Override
        public void shelfOrder(CookedOrder order) {
            orders.add(order);
        }

        @Override
        public CookedOrder takeOrder(UUID orderId, OrderTemperature temperature) {
            Iterator<CookedOrder> it = orders.iterator();
            while (it.hasNext()) {
                CookedOrder order = it.next();
                if (order.getId().equals(orderId)) {
                    it.remove();
                    return order;
                }
            }
            return null;
        }

        @Override
        public CookedOrder peekOrder(UUID orderId, OrderTemperature temperature) {
            return null;
        }

        @Override
        public int deviceOrderSize(OrderTemperature temperature) {
            return 0;
        }

        @Override
        public List<CookedOrder> getWasteOrders() {
            return null;
        }

        @Override
        public void close() {

        }
    }

    private class TestStateListener implements CourierMgrStateListenerI {
        int count = 0;

        @Override
        public void stateChanged() {
            count++;
        }
    }
}
