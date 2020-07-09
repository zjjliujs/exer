package com.cloudcousion.ordersys.shelf;

import com.cloudcousion.orderserver.model.OrderTemperature;
import com.cloudcousion.ordersys.kitchen.CookedOrder;

import java.util.List;
import java.util.UUID;

public interface ShelfI {
    /**
     * Put cooked order on into shelf
     *
     * @param order
     */
    void shelfOrder(CookedOrder order);

    /**
     * For courier to take out of the order on the shelf
     *
     * @Param orderId  order id to be taken out
     */
    CookedOrder takeOrder(UUID orderId, OrderTemperature temperature);

    /**
     * Count order size in shelf device
     *
     * @param temperature None meas overflow shelf device
     * @return order size in one special shelf device
     */
    int shelfDeviceOrderSize(OrderTemperature temperature);

    /**
     * Get wasted orders
     *
     * @return
     */
    List<CookedOrder> getWasteOrders();

    /**
     * Stop thread
     */
    void close();
}
