package com.cloudcousion.ordersys.kitchen;

import com.cloudcousion.orderserver.model.Order;
import com.cloudcousion.orderserver.model.OrderTemperature;
import com.cloudcousion.ordersys.shelf.ShelfType;

import java.util.Calendar;
import java.util.UUID;

public class CookedOrder extends Order {
    private Order order;
    private long cookTime;
    private ShelfType shelfType;
    private float value;

    public CookedOrder(Order order) {
        this.order = order;
        //Ignore cooking time
        Calendar calendar = Calendar.getInstance();
        cookTime = calendar.getTimeInMillis();
    }

    public ShelfType getShelfType() {
        return shelfType;
    }

    public void setShelfType(ShelfType shelfType) {
        this.shelfType = shelfType;
    }

    public long getCookTime() {
        return cookTime;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public UUID getId() {
        return order.getId();
    }

    @Override
    public void setId(UUID id) {
        order.setId(id);
    }

    @Override
    public String getName() {
        return order.getName();
    }

    @Override
    public void setName(String name) {
        order.setName(name);
    }

    @Override
    public OrderTemperature getTemp() {
        return order.getTemp();
    }

    @Override
    public void setTemp(OrderTemperature temp) {
        order.setTemp(temp);
    }

    @Override
    public int getShelfLife() {
        return order.getShelfLife();
    }

    @Override
    public void setShelfLife(int shelfLife) {
        order.setShelfLife(shelfLife);
    }

    @Override
    public float getDecayRate() {
        return order.getDecayRate();
    }

    @Override
    public void setDecayRate(float decayRate) {
        order.setDecayRate(decayRate);
    }
}
