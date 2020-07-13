package com.cloudcousion.orderproc.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cloudcousion.orderproc.R;
import com.cloudcousion.orderserver.model.Order;
import com.cloudcousion.orderserver.model.OrderTemperature;
import com.cloudcousion.ordersys.kitchen.CookedOrder;
import com.cloudcousion.ordersys.shelf.ShelfManagerI;

import java.util.List;

public class ShelfOrderListRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final OrderTemperature orderTemperature;
    private List<CookedOrder> orders;

    public ShelfOrderListRVAdapter(Context context, ShelfManagerI shelfManager, OrderTemperature orderTemp) {
        this.context = context;
        this.orderTemperature = orderTemp;
        //deviceOrderList return a cloned list!
        this.orders = shelfManager.deviceOrderList(orderTemp);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ValuedOrderHolder(LayoutInflater.from(context).inflate(R.layout.item_value_order, null));
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
