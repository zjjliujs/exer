package com.cloudcousion.ordersys.utils;

import com.cloudcousion.ordersys.kitchen.CookedOrder;
import com.cloudcousion.ordersys.shelf.ShelfType;

import java.util.Calendar;

public class SimpleOrderValueCalculator implements OrderValueCalculatorI {
    private static SimpleOrderValueCalculator instance = null;

    private SimpleOrderValueCalculator() {

    }

    public static synchronized SimpleOrderValueCalculator getInstance() {
        if (instance == null) {
            instance = new SimpleOrderValueCalculator();
        }
        return instance;
    }

    @Override
    public float calculateOrderValue(CookedOrder order) {
        Calendar calendar = Calendar.getInstance();
        long now = calendar.getTimeInMillis();
        /* seconds */
        int orderAge = (int) ((now - order.getCookTime()) / 1000.);
        int shelfDecayModifier = order.getShelfType() == ShelfType.SINGLE ? 1 : 2;
        float value = (order.getShelfLife() - orderAge - order.getDecayRate() * orderAge * shelfDecayModifier) / order.getShelfLife();
        return value;
    }
}
