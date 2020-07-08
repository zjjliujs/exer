package com.cloudcousion.orderserver;

import com.cloudcousion.orderserver.model.Order;

import java.util.List;

public interface OrderConsumerI {
    /**
     * Listener for order server to dispatch order to client (order process system)
     * @param order
     * @return true if dispatch succeed, false if dispatch failed
     */
    boolean dispatchOrder(Order order);
}
