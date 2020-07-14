package com.cloudcousion.orderproc.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cloudcousion.orderproc.R;
import com.cloudcousion.ordersys.kitchen.CookedOrder;

import java.util.List;

public class ValuedOrderRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private List<CookedOrder> orders;

    public ValuedOrderRVAdapter(Context context, List<CookedOrder> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ValuedOrderHolder(LayoutInflater.from(context).inflate(R.layout.item_valued_order, null));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CookedOrder order = orders.get(position);
        ValuedOrderHolder h = (ValuedOrderHolder) holder;
        h.tvName.setText(order.getName());
        h.tvTemperature.setText(order.getTemp().toString());
        h.tvShelfLife.setText(String.valueOf(order.getShelfLife()));
        h.tvDecayRate.setText(String.valueOf(order.getDecayRate()));
        h.tvValue.setText(String.valueOf(order.getValue()));
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void setOrders(List<CookedOrder> orders) {
        this.orders = orders;
        notifyDataSetChanged();
    }
}
