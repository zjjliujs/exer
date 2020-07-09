package com.cloudcousion.ordersys.utils;

import com.cloudcousion.ordersys.kitchen.CookedOrder;

public interface OrderValueCalculatorI {
    float calculateOrderValue(CookedOrder order);
}
