package com.cloudcousion.orderproc.ui.main;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.cloudcousion.orderproc.R;

public class ValuedOrderHolder extends OrderHolder {
    TextView tvValue;

    public ValuedOrderHolder(@NonNull View itemView) {
        super(itemView);

        tvValue = itemView.findViewById(R.id.value);
    }
}
