package com.cloudcousion.ordersys;

import com.alibaba.fastjson.JSON;
import com.cloudcousion.orderserver.model.Order;
import com.cloudcousion.orderserver.model.OrderTemperature;
import com.cloudcousion.ordersys.config.SimulatorConfig;
import com.cloudcousion.ordersys.kitchen.CookedOrder;
import com.cloudcousion.ordersys.shelf.ShelfManager;
import com.cloudcousion.ordersys.shelf.ShelfStateListenerI;
import com.cloudcousion.ordersys.shelf.ShelfType;
import com.cloudcousion.ordersys.utils.SimpleOrderValueCalculator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShelfManagerTest {
    private ShelfManager shelfMgr;
    private List<CookedOrder> cookedOrders;

    @Before
    public void init() {
        SimulatorConfig kc = new SimulatorConfig();
        kc.overflowShelfCapacity = 1;
        kc.hotShelfCapacity = 1;
        shelfMgr = new ShelfManager(kc, SimpleOrderValueCalculator.getInstance());

        List<Order> orders = JSON.parseArray("[\n" +
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
        cookedOrders = new ArrayList<>();
        for (Order order : orders) {
            cookedOrders.add(new CookedOrder(order));
        }
    }

    @Test
    public void testStateListenerShelfOrder() {
        shelfMgr.start();
        TestStateListener listener = new TestStateListener();
        shelfMgr.registerStateListener(listener);

        Assert.assertTrue(0 == listener.count);
        CookedOrder order = cookedOrders.get(0);
        shelfMgr.shelfOrder(order);
        Assert.assertTrue(1 == listener.count);
    }

    @Test
    public void testStateListenerTakeOrder() {
        CookedOrder order = cookedOrders.get(0);
        shelfMgr.shelfOrder(order);
        shelfMgr.start();
        TestStateListener listener = new TestStateListener();
        shelfMgr.registerStateListener(listener);

        Assert.assertTrue(0 == listener.count);
        shelfMgr.takeOrder(order.getId(), order.getTemp());
        Assert.assertTrue(1 == listener.count);
    }

    @Test
    public void testStateListener() throws InterruptedException {
        shelfMgr.shelfOrder(cookedOrders.get(0));
        shelfMgr.start();
        TestStateListener listener = new TestStateListener();
        shelfMgr.registerStateListener(listener);

        Thread.sleep(1100);
        Assert.assertTrue(0 == listener.count);

        Thread.sleep(13000);
        Assert.assertTrue(1 == listener.count);
    }

    @Test
    public void testShelfThread() throws InterruptedException {
        for (CookedOrder cookedOrder : cookedOrders) {
            shelfMgr.shelfOrder(cookedOrder);
        }
        shelfMgr.start();
        Thread.sleep(1100);
        CookedOrder order = shelfMgr.peekOrder(UUID.fromString("a8cfcb76-7f24-4420-a5ba-d46dd77bdffd")
                , OrderTemperature.Frozen);
        Assert.assertNotNull(order);
        Assert.assertTrue(Math.abs(order.getValue() - 0.9185) < 0.00001);

        Thread.sleep(1000);
        Assert.assertTrue(Math.abs(order.getValue() - 0.837) < 0.00001);

        Thread.sleep(13000);
        Assert.assertTrue(order.getValue() <= 0);
        order = shelfMgr.peekOrder(UUID.fromString("a8cfcb76-7f24-4420-a5ba-d46dd77bdffd")
                , OrderTemperature.Frozen);
        Assert.assertNull(order);
    }

    @Test
    public void testShelfOrder() {
        for (CookedOrder cookedOrder : cookedOrders) {
            shelfMgr.shelfOrder(cookedOrder);
        }
        Assert.assertEquals(1, shelfMgr.deviceOrderSize(OrderTemperature.Cold));
        Assert.assertEquals(1, shelfMgr.deviceOrderSize(OrderTemperature.Frozen));
        Assert.assertEquals(1, shelfMgr.deviceOrderSize(OrderTemperature.None));
        Assert.assertEquals(0, shelfMgr.deviceOrderSize(OrderTemperature.Hot));
        Assert.assertEquals(1, shelfMgr.getWasteOrders().size());

        UUID id = UUID.fromString("a8cfcb76-7f24-4420-a5ba-d46dd77bdffd");
        CookedOrder order = shelfMgr.takeOrder(id, OrderTemperature.Frozen);
        Assert.assertNotNull(order);
        Assert.assertEquals(id, order.getId());
        Assert.assertEquals(ShelfType.SINGLE, order.getShelfType());

        id = UUID.fromString("58e9b5fe-3fde-4a27-8e98-682e58a4a65d");
        order = shelfMgr.takeOrder(id, OrderTemperature.Frozen);
        Assert.assertNull(order);

        id = UUID.fromString("2ec069e3-576f-48eb-869f-74a540ef840c");
        order = shelfMgr.takeOrder(id, OrderTemperature.Cold);
        Assert.assertNotNull(order);
        Assert.assertEquals(id, order.getId());
        Assert.assertEquals(ShelfType.SINGLE, order.getShelfType());

        id = UUID.fromString("690b85f7-8c7d-4337-bd02-04e04454c826");
        order = shelfMgr.takeOrder(id, OrderTemperature.Cold);
        Assert.assertNotNull(order);
        Assert.assertEquals(id, order.getId());
        Assert.assertEquals(ShelfType.OVERFLOW, order.getShelfType());
    }

    private class TestStateListener implements ShelfStateListenerI {
        int count = 0;

        @Override
        public void stateChanged() {
            count++;
        }
    }
}
