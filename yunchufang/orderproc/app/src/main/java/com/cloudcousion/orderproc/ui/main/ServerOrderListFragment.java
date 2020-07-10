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
import com.cloudcousion.orderserver.OrderServerI;
import com.cloudcousion.orderserver.OrderServerStateListenerI;

import java.util.Locale;

public class ServerOrderListFragment extends Fragment implements OrderServerStateListenerI {

    private OrderServerI orderServer;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {

        }
    };
    private TextView orderCountTV;
    private RecyclerView orderListRV;
    private OrderListRVAdapter adapter;

    public ServerOrderListFragment(OrderServerI orderServer) {
        super();
        this.orderServer = orderServer;
        this.orderServer.registerStateListener(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater
            , ViewGroup container
            , Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_server_order, container, false);
        orderCountTV = root.findViewById(R.id.tv_order_count);
        updateOrderCount();
        orderListRV = root.findViewById(R.id.rv_order_list);
        initOrderListRV();
        return root;
    }

    private void initOrderListRV() {
        adapter = new OrderListRVAdapter(getContext(), orderServer);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        orderListRV.setLayoutManager(layoutManager);
        orderListRV.setAdapter(adapter);
        orderListRV.setItemAnimator(new DefaultItemAnimator());
    }

    private void updateOrderCount() {
        String fmt = getResources().getString(R.string.fmt_total_order_count);
        String msg = String.format(Locale.US, fmt, orderServer.ordersInQueue().size());
        orderCountTV.setText(msg);
    }

    /**
     * This method is called from order server thread!
     */
    @Override
    public void serverStateChanged() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                updateOrderCount();
                adapter.notifyServerStateChanged();
                adapter.notifyDataSetChanged();
            }
        });
    }
}
