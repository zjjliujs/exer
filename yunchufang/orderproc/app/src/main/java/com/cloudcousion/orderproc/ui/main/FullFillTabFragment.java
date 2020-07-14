package com.cloudcousion.orderproc.ui.main;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import com.cloudcousion.ordersys.courier.CourierManager;
import com.cloudcousion.ordersys.courier.CourierMgrStateListenerI;
import com.cloudcousion.ordersys.kitchen.CookedOrder;

import java.util.List;

public class FullFillTabFragment extends ValuedOrderFragment implements CourierMgrStateListenerI {
    private final CourierManager courierManager;

    public FullFillTabFragment(CourierManager courierManager) {
        super();
        this.courierManager = courierManager;
        courierManager.registerStateListener(this);
    }

    @Override
    protected List<CookedOrder> getOrderList() {
        return courierManager.getFullFillOrders();
    }
}
