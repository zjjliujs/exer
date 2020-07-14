package com.cloudcousion.ordersys;

import com.alibaba.fastjson.JSON;
import com.cloudcousion.orderserver.model.Order;
import com.cloudcousion.ordersys.config.SimulatorConfig;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class OrderSimulatorTest {
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
    public void isRunningTest() throws InterruptedException {
        SimulatorConfig config = new SimulatorConfig();
        config.courierDelayMin = 2;
        config.courierDelayMax = 6;
        OrderSimulator simulator = new OrderSimulator(config);
        simulator.start(orders);
        Assert.assertTrue(simulator.isRunning());
        Thread.sleep(1000);
        Assert.assertTrue(simulator.isRunning());
        Thread.sleep(6100);
        Assert.assertFalse(simulator.isRunning());
    }
}
