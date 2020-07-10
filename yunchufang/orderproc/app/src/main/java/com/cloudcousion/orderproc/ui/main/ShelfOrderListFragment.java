package com.cloudcousion.orderproc.ui.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cloudcousion.orderproc.R;
import com.cloudcousion.orderserver.model.OrderTemperature;
import com.cloudcousion.ordersys.kitchen.CookedOrder;
import com.cloudcousion.ordersys.shelf.ShelfManagerI;
import com.cloudcousion.ordersys.shelf.ShelfStateListenerI;

import java.util.List;
import java.util.Locale;

public class ShelfOrderListFragment extends Fragment implements ShelfStateListenerI {
    private final ShelfManagerI shelfManager;
    private final List<CookedOrder> hotOrderList;
    private final List<CookedOrder> coldOrderList;
    private final List<CookedOrder> frozenOrderList;
    private final List<CookedOrder> overflowOrderList;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {

        }
    };
    private TextView orderCountTV;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater
            , @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_shelf_order, container, false);
        orderCountTV = root.findViewById(R.id.tv_order_count);
        updateOrderCount();
        //orderListRV = root.findViewById(R.id.rv_order_list);
        //initOrderListRV();
        return root;
    }

    private void updateOrderCount() {
        String fmt = getResources().getString(R.string.fmt_total_order_count);
        String msg = String.format(Locale.US, fmt, orderTotal());
        orderCountTV.setText(msg);
    }

    private int orderTotal() {
        return hotOrderList.size() + coldOrderList.size() + frozenOrderList.size() + overflowOrderList.size();
    }

    public ShelfOrderListFragment(ShelfManagerI shelfManager) {
        this.shelfManager = shelfManager;
        hotOrderList = shelfManager.deviceOrderList(OrderTemperature.Hot);
        coldOrderList = shelfManager.deviceOrderList(OrderTemperature.Cold);
        frozenOrderList = shelfManager.deviceOrderList(OrderTemperature.Frozen);
        overflowOrderList = shelfManager.deviceOrderList(OrderTemperature.None);
    }

    //This method will be called from shelf manager or courier manager or order server thread!
    @Override
    public synchronized void stateChanged() {
        //TODO stateChanged
    }
}
