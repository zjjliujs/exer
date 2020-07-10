package com.cloudcousion.orderserver;

import com.cloudcousion.orderserver.model.Order;

public interface OrderListenerI {
    void orderSendToKitchen(Order order);
}
