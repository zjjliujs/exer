package com.cloudcousion.ordersys.shelf;

import com.cloudcousion.ordersys.kitchen.CookedOrder;

public class OverflowShelfDevice extends ShelfDevice {

    public OverflowShelfDevice(int capacity) {
        super(capacity);
    }

    @Override
    public boolean putOrder(CookedOrder order) {
        if (getFree() == 0) {
            return false;
        }
        order.setShelfType(ShelfType.OVERFLOW);
        orders.add(order);
        logger.logDebug("OverflowShelfDevice, putOrder, added order:" + order.getId());
        return true;
    }

    /**
     * Take one order to put to temp device
     *
     * @return
     */
    public CookedOrder takeOne() {
        if (orders.size() == 0)
            return null;
        return orders.poll();
    }
}
