package com.cloudcousion.orderserver;

import com.alibaba.fastjson.JSON;
import com.cloudcousion.orderserver.config.OrderServerConfig;
import com.cloudcousion.orderserver.mockup.MockUpOrderServer;
import com.cloudcousion.orderserver.model.Order;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class MockUpOrderServerTest {
    private MockUpOrderServer orderServer;
    private TestOrderConsumer testConsumer;

    @Before
    public void init() {
        OrderServerConfig.setDispatchRate(2);
        orderServer = MockUpOrderServer.getInstance();
        testConsumer = new TestOrderConsumer();
        orderServer.registerOrderConsumer(testConsumer);
    }

    @After
    public void cleanUp() {
        orderServer.stopService();
    }

    @Test
    public void emptyOrderTst() throws InterruptedException {
        orderServer.startDispatch();
        Thread.sleep(3000);
        assertEquals(testConsumer.orders.size(), 0);
    }

    @Test
    public void orderDispatchTst() throws InterruptedException {
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
        assertEquals(0, testConsumer.orders.size());
        int delay = 1000 / OrderServerConfig.getDispatchRate();
        Thread.sleep(delay);
        assertEquals(1, testConsumer.orders.size());
        Thread.sleep(delay);
        assertEquals(2, testConsumer.orders.size());
        Thread.sleep(delay);
        assertEquals(3, testConsumer.orders.size());
        //All order consumed
        Thread.sleep(delay);
        assertEquals(4, testConsumer.orders.size());
    }

    static class TestOrderConsumer implements OrderConsumerI {
        List<Order> orders;

        public TestOrderConsumer() {
            this.orders = new ArrayList<>();
        }

        @Override
        public boolean dispatchOrder(Order order) {
            orders.add(order);
            return true;
        }
    }
}
