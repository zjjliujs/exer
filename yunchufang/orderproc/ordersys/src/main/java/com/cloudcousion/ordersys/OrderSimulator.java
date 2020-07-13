package com.cloudcousion.ordersys;

import com.cloudcousion.orderserver.OrderServerI;
import com.cloudcousion.orderserver.mockup.MockUpOrderServer;
import com.cloudcousion.orderserver.model.Order;
import com.cloudcousion.ordersys.config.SimulatorConfig;
import com.cloudcousion.ordersys.courier.CourierManager;
import com.cloudcousion.ordersys.kitchen.Kitchen;
import com.cloudcousion.ordersys.shelf.ShelfManager;
import com.cloudcousion.ordersys.utils.OrderValueCalculatorI;
import com.cloudcousion.ordersys.utils.SimpleOrderValueCalculator;

import java.util.ArrayList;
import java.util.List;

public class OrderSimulator {
    public final MockUpOrderServer orderServer;
    public final SimpleOrderValueCalculator valueCalculator;
    public final ShelfManager shelfManager;
    public final Kitchen kitchen;
    public final CourierManager courierManager;

    private final SimulatorConfig config;

    public OrderSimulator(SimulatorConfig config) {
        this.config = config;
        orderServer = MockUpOrderServer.getInstance();
        valueCalculator = SimpleOrderValueCalculator.getInstance();
        shelfManager = new ShelfManager(config, valueCalculator);
        kitchen = new Kitchen(valueCalculator, orderServer, shelfManager);
        courierManager = new CourierManager(config, shelfManager, orderServer);
    }

    public void start(List<Order> orders) {
        orderServer.setDispatchRate(config.defaultDispatchRate);
        orderServer.putOrders(orders);
        orderServer.startDispatch();
        shelfManager.start();
        kitchen.start();
        courierManager.start();
    }

    public void stop() {
        orderServer.stopService();
        kitchen.close();
        shelfManager.close();
        courierManager.close();
    }
}
