package com.cloudcousion.orderserver;

import com.cloudcousion.orderserver.model.Order;

import java.util.Collection;
import java.util.List;

public interface OrderServerI {
    /**
     * Set order dispatch rate
     *
     * @param dispatchRate
     */
    void setDispatchRate(int dispatchRate);

    /**
     * For system to start order server.
     *
     * @return true if succeed, false if failed
     */
    boolean startDispatch();

    /**
     * To stop order service
     *
     * @return
     */
    void stopService();

    /**
     * For client to put order into order server
     *
     * @param order -- new order
     * @return true if succeed, false if failed
     */
    boolean putOrder(Order order);

    /**
     * For client to put order lists into order server
     *
     * @param orders -- new order list
     * @return true if succeed, false if failed
     */
    boolean putOrders(List<Order> orders);

    /**
     * For order process system to register listener on order server
     *
     * @param orderListener
     */
    void registerOrderConsumer(OrderConsumerI orderListener);

    /**
     * For order process system to unregister listener on order server
     *
     * @param orderListenerI
     */
    void unregisterOrderConsumer(OrderConsumerI orderListenerI);

    /**
     * Register order server state changed message listener
     * @param listener
     */
    void registerStateListener(OrderServerListenerI listener);

    /**
     * Unregister order server state changed message listener
     * @param listener
     */
    void unregisterStateListener(OrderServerListenerI listener);

    /**
     * Order list on server queue
     *
     * @return
     */
    List<Order> ordersInQueue();
}
