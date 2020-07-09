package com.cloudcousion.ordersys.shelf;

import com.cloudcousion.orderserver.model.OrderTemperature;
import com.cloudcousion.ordersys.kitchen.CookedOrder;

public class TempShelfDevice extends ShelfDevice {
    /*
     * Overflow shelf device should have None temperature
     */
    private OrderTemperature temperature;

    public TempShelfDevice(OrderTemperature temperature, int capacity) {
        super(capacity);
        this.temperature = temperature;
    }

    @Override
    public boolean putOrder(CookedOrder order) {
        if (order.getTemp() != temperature)
            return false;
        if (getFree() == 0) {
            return false;
        }
        order.setShelfType(ShelfType.SINGLE);
        orders.add(order);
        logger.logDebug("TempShelfDevice, putOrder, added order:" + order.getId());
        return true;
    }

    public OrderTemperature getTemperature() {
        return temperature;
    }
}
