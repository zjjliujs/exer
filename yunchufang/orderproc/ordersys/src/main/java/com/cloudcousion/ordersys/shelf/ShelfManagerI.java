package com.cloudcousion.ordersys.shelf;

import com.cloudcousion.orderserver.model.OrderTemperature;
import com.cloudcousion.ordersys.kitchen.CookedOrder;

import java.util.List;
import java.util.UUID;

public interface ShelfManagerI {
    /**
     * Put cooked order on into shelf
     *
     * @param order
     */
    void shelfOrder(CookedOrder order);

    /**
     * For courier to take out of the order on the shelf
     *
     * @param temperature
     * @return Order on shelf or null if not found
     * @Param orderId  order id to be taken out
     */
    CookedOrder takeOrder(UUID orderId, OrderTemperature temperature);

    /**
     * Get the order ,but not take if out from the shelf
     *
     * @param orderId
     * @param temperature
     * @return Order on shelf or null if not found
     */
    CookedOrder peekOrder(UUID orderId, OrderTemperature temperature);

    /**
     * Count order size in shelf device
     *
     * @param temperature None meas overflow shelf device
     * @return order size in one special shelf device
     */
    int deviceOrderSize(OrderTemperature temperature);

    /**
     * Get wasted orders
     *
     * @return
     */
    List<CookedOrder> getWasteOrders();

    /**
     * Add Shelf state change listener
     *
     * @param stateListener
     */
    void registerStateListener(ShelfStateListenerI stateListener);

    /**
     * Unregister shelf state change listener
     *
     * @param stateListener
     */
    void unregisterStateListener(ShelfStateListenerI stateListener);

    /**
     * Stop thread
     */
    void close();

    /**
     * Get order list form device
     *
     * @param temperature None means overflow shelf
     * @return order list clone
     */
    List<CookedOrder> deviceOrderList(OrderTemperature temperature);

    /**
     * Order size in hot, cold, frozen, overflow totally
     *
     * @return
     */
    int totalOrderSize();
}
