package com.cloudcousion.orderproc.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cloudcousion.orderproc.R;
import com.cloudcousion.orderserver.OrderServerI;
import com.cloudcousion.orderserver.model.Order;

import java.util.ArrayList;
import java.util.List;

public class ServerOrderListRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final OrderServerI server;
    private List<Order> orderList;

    public ServerOrderListRVAdapter(Context context, OrderServerI server) {
        this.context = context;
        this.server = server;
        /**
         * If we use server list directly , there would have race condition between main thread and
         * order server thread.
         */
        this.orderList = new ArrayList<>(server.ordersInQueue());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderHolder(LayoutInflater.from(context).inflate(R.layout.item_order, null));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Order order = orderList.get(position);
        OrderHolder h = (OrderHolder) holder;
        h.tvName.setText(order.getName());
        h.tvTemperature.setText(order.getTemp().toString());
        h.tvShelfLife.setText(String.valueOf(order.getShelfLife()));
        h.tvDecayRate.setText(String.valueOf(order.getDecayRate()));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public void notifyServerStateChanged() {
        this.orderList = new ArrayList<>(server.ordersInQueue());
        notifyDataSetChanged();
    }
}
