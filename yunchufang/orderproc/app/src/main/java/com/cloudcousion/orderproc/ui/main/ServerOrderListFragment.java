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

import com.cloudcousion.orderproc.R;
import com.cloudcousion.orderserver.OrderServerI;
import com.cloudcousion.orderserver.OrderServerListenerI;

import java.util.Locale;

public class ServerOrderListFragment extends Fragment implements OrderServerListenerI {

    private OrderServerI orderServer;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {

        }
    };
    private TextView orderCountTV;

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
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_order_simulator, container, false);
        orderCountTV = root.findViewById(R.id.tv_order_count);
        updateOrderCount();
        return root;
    }

    private void updateOrderCount() {
        String fmt = getResources().getString(R.string.fmt_server_order_queue);
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
            }
        });
    }
}
