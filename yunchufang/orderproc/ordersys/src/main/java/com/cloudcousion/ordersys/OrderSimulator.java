package com.cloudcousion.ordersys;

import com.cloudcousion.orderserver.mockup.MockUpOrderServer;
import com.cloudcousion.orderserver.model.Order;
import com.cloudcousion.ordersys.config.SimulatorConfig;
import com.cloudcousion.ordersys.courier.CourierManager;
import com.cloudcousion.ordersys.kitchen.Kitchen;
import com.cloudcousion.ordersys.shelf.ShelfManager;
import com.cloudcousion.ordersys.shelf.ShelfStateListenerI;
import com.cloudcousion.ordersys.utils.SimpleOrderValueCalculator;

import java.util.List;

public class OrderSimulator implements ShelfStateListenerI {
    public final MockUpOrderServer orderServer;
    public final SimpleOrderValueCalculator valueCalculator;
    public final ShelfManager shelfManager;
    public final Kitchen kitchen;
    public final CourierManager courierManager;

    private final SimulatorConfig config;
    private boolean running;

    public OrderSimulator(SimulatorConfig config) {
        this.config = config;
        orderServer = MockUpOrderServer.getInstance();
        valueCalculator = SimpleOrderValueCalculator.getInstance();
        shelfManager = new ShelfManager(this.config, valueCalculator);
        shelfManager.registerStateListener(this);
        kitchen = new Kitchen(valueCalculator, orderServer, shelfManager);
        courierManager = new CourierManager(this.config, shelfManager, orderServer);
        running = false;
    }

    public synchronized void start(List<Order> orders) {
        orderServer.setDispatchRate(config.dispatchRate);
        orderServer.putOrders(orders);
        orderServer.startDispatch();
        shelfManager.start();
        kitchen.start();
        courierManager.start();
        running = true;
    }

    public synchronized void stop() {
        orderServer.stopService();
        kitchen.close();
        shelfManager.close();
        shelfManager.unregisterStateListener(this);
        courierManager.close();
    }

    public synchronized boolean isRunning() {
        return running;
    }

    /*
        The method called by shelf manager thread
     */
    @Override
    public synchronized void stateChanged() {
        if (shelfManager.totalOrderSize() == 0 && orderServer.orderQueueSize() == 0) {
            running = false;
        }
    }

    public synchronized void setConfig(SimulatorConfig config) {
        if (isRunning())
            return;
        orderServer.setDispatchRate(config.dispatchRate);
        shelfManager.setConfig(config);
        courierManager.setConfig(config);
    }
}
