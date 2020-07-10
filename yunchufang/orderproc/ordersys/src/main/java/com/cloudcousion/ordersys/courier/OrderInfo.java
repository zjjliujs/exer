package com.cloudcousion.ordersys.courier;

import com.cloudcousion.orderserver.model.Order;
import com.cloudcousion.orderserver.model.OrderTemperature;

import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

public class OrderInfo implements Comparable<OrderInfo> {
    private UUID orderId;
    private OrderTemperature temperature;
    //Random time for courier to pick up order from shelf
    private long pickTime;

    public OrderInfo(Order order) {
        this.orderId = order.getId();
        this.temperature = order.getTemp();
    }

    @Override
    public int compareTo(OrderInfo o) {
        if (pickTime < o.pickTime)
            return -1;
        else if (pickTime == o.pickTime)
            return 0;
        else
            return 1;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public OrderTemperature getTemperature() {
        return temperature;
    }

    public long getPickTime() {
        return pickTime;
    }

    public void setPickTime(long pickTime) {
        this.pickTime = pickTime;
    }
}
