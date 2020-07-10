package com.cloudcousion.ordersys;

import com.alibaba.fastjson.JSON;
import com.cloudcousion.orderserver.config.OrderServerConfig;
import com.cloudcousion.orderserver.mockup.MockUpOrderServer;
import com.cloudcousion.orderserver.model.Order;
import com.cloudcousion.orderserver.model.OrderTemperature;
import com.cloudcousion.ordersys.kitchen.CookedOrder;
import com.cloudcousion.ordersys.kitchen.Kitchen;
import com.cloudcousion.ordersys.shelf.ShelfManagerI;
import com.cloudcousion.ordersys.shelf.ShelfStateListenerI;
import com.cloudcousion.ordersys.utils.SimpleOrderValueCalculator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class KitchenTest {
    private MockUpOrderServer orderServer;
    private Kitchen kitchen;
    private TestShelf shelf;

    @Before
    public void init() {
        OrderServerConfig.setDispatchRate(2);
        orderServer = MockUpOrderServer.getInstance();
        shelf = new TestShelf();
        kitchen = new Kitchen(SimpleOrderValueCalculator.getInstance(), orderServer, shelf);
        kitchen.start();

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
        orderServer.putOrders(orders);
        orderServer.startDispatch();
    }

    @After
    public void cleanUp() {
        orderServer.stopService();
    }

    @Test
    public void kitchenShelfTst() throws InterruptedException {
        assertEquals(0, shelf.orders.size());
        int delay = 1000 / OrderServerConfig.getDispatchRate();
        Thread.sleep(delay);
        assertEquals(1, shelf.orders.size());
        Thread.sleep(delay);
        assertEquals(2, shelf.orders.size());
        Thread.sleep(delay);
        assertEquals(3, shelf.orders.size());
        Thread.sleep(delay);
        assertEquals(4, shelf.orders.size());
        //All order consumed
        Thread.sleep(delay);
        assertEquals(4, shelf.orders.size());
    }

    @Test
    public void kitchenCloseTst() throws InterruptedException {
        kitchen.close();
        assertEquals(0, shelf.orders.size());
        int delay = 1000 / OrderServerConfig.getDispatchRate();
        Thread.sleep(delay);
        assertEquals(0, shelf.orders.size());
        Thread.sleep(delay);
        assertEquals(0, shelf.orders.size());
        Thread.sleep(delay);
        assertEquals(0, shelf.orders.size());

        assertEquals(4, orderServer.ordersInQueue().size());
    }

    private class TestShelf implements ShelfManagerI {
        List<CookedOrder> orders = new ArrayList<>();

        @Override
        public synchronized void shelfOrder(CookedOrder order) {
            orders.add(order);
        }

        @Override
        public synchronized CookedOrder takeOrder(UUID orderId, OrderTemperature temperature) {
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
            //Do nothing!
        }

        @Override
        public CookedOrder peekOrder(UUID orderId, OrderTemperature temperature) {
            return null;
        }

        @Override
        public void registerStateListener(ShelfStateListenerI stateListener) {

        }

        @Override
        public void unregisterStateListener(ShelfStateListenerI stateListener) {

        }

        @Override
        public List<CookedOrder> deviceOrderList(OrderTemperature temperature) {
            return null;
        }
    }
}
