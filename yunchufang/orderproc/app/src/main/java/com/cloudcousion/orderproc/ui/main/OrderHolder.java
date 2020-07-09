package com.cloudcousion.orderproc.ui.main;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cloudcousion.orderproc.R;

public class OrderHolder extends RecyclerView.ViewHolder {
    TextView tvName;
    TextView tvTemperature;
    TextView tvShelfLife;
    TextView tvDecayRate;


    public OrderHolder(@NonNull View itemView) {
        super(itemView);
        tvName = itemView.findViewById(R.id.name);
        tvTemperature = itemView.findViewById(R.id.temperature);
        tvShelfLife = itemView.findViewById(R.id.shelf_life);
        tvDecayRate = itemView.findViewById(R.id.decay_rate);
    }
}
