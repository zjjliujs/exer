package com.cloudcousion.orderserver.model;

import java.util.Objects;
import java.util.UUID;

public class Order {
    private UUID id;
    private String name;
    private OrderTemp temp;
    private int shelfLife;
    private float decayRate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return getId().equals(order.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OrderTemp getTemp() {
        return temp;
    }

    public void setTemp(OrderTemp temp) {
        this.temp = temp;
    }

    public int getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(int shelfLife) {
        this.shelfLife = shelfLife;
    }

    public float getDecayRate() {
        return decayRate;
    }

    public void setDecayRate(float decayRate) {
        this.decayRate = decayRate;
    }
}
