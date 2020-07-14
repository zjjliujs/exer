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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cloudcousion.orderproc.R;
import com.cloudcousion.ordersys.kitchen.CookedOrder;

import java.util.List;
import java.util.Locale;

public abstract class ValuedOrderFragment extends Fragment {

    @SuppressLint("HandlerLeak")
    protected Handler handler = new Handler() {
        public void handleMessage(Message msg) {

        }
    };
    protected TextView orderCountTV;
    protected RecyclerView orderListRV;
    protected ValuedOrderRVAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater
            , ViewGroup container
            , Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_valued_order, container, false);
        orderCountTV = root.findViewById(R.id.tv_order_count);
        orderListRV = root.findViewById(R.id.rv_order_list);

        List<CookedOrder> orders = getOrderList();
        updateOrderCount(orders.size());
        initOrderListRV(orders);
        return root;
    }

    private void updateOrderCount(int size) {
        String fmt = getResources().getString(R.string.fmt_total_order_count);
        String msg = String.format(Locale.US, fmt, size);
        orderCountTV.setText(msg);
    }

    private void initOrderListRV(List<CookedOrder> orders) {
        adapter = new ValuedOrderRVAdapter(getContext(), orders);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        orderListRV.setLayoutManager(layoutManager);
        orderListRV.setAdapter(adapter);
        orderListRV.setItemAnimator(new DefaultItemAnimator());
    }

    protected abstract List<CookedOrder> getOrderList();

    public void stateChanged() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                List<CookedOrder> orderList = getOrderList();
                updateOrderCount(orderList.size());
                adapter.setOrders(orderList);
            }
        });
    }
}
