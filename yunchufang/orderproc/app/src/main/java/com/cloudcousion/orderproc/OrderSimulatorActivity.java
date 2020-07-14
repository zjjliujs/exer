package com.cloudcousion.orderproc;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.cloudcousion.orderproc.utils.PreferenceUtils;
import com.cloudcousion.orderserver.model.Order;
import com.cloudcousion.ordersys.OrderSimulator;
import com.cloudcousion.ordersys.config.SimulatorConfig;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.cloudcousion.orderproc.ui.main.TabPageAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class OrderSimulatorActivity extends AppCompatActivity {
    private SimulatorConfig simulatorConfig;
    private OrderSimulator orderSimulator;
    private EditText etDispatchRate;
    private EditText etHotShelfCap;
    private EditText etColdShelfCap;
    private EditText etFrozenShelfCap;
    private EditText etOverflowShelfCap;
    private EditText etCourierMinDelay;
    private EditText etCourierMaxDelay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        simulatorConfig = new SimulatorConfig();
        Integer v = PreferenceUtils.getDispatchRatePref(this, simulatorConfig.dispatchRate);
        simulatorConfig.dispatchRate = v;
        v = PreferenceUtils.getHotShelfCapPref(this, simulatorConfig.hotShelfCapacity);
        simulatorConfig.hotShelfCapacity = v;
        v = PreferenceUtils.getColdShelfCapPref(this, simulatorConfig.coldShelfCapacity);
        simulatorConfig.coldShelfCapacity = v;
        v = PreferenceUtils.getFrozenShelfCapPref(this, simulatorConfig.frozenShelfCapacity);
        simulatorConfig.frozenShelfCapacity = v;
        v = PreferenceUtils.getOverflowShelfCapPref(this, simulatorConfig.overflowShelfCapacity);
        simulatorConfig.overflowShelfCapacity = v;
        v = PreferenceUtils.getCourierMinDelayPref(this, simulatorConfig.courierDelayMin);
        simulatorConfig.courierDelayMin = v;
        v = PreferenceUtils.getCourierMaxDelayPref(this, simulatorConfig.courierDelayMax);
        simulatorConfig.courierDelayMax = v;

        showConfigDialog();

        setContentView(R.layout.activity_order_simulator);
        orderSimulator = new OrderSimulator(simulatorConfig);
        //Create pager adapter
        TabPageAdapter sectionsPagerAdapter = new TabPageAdapter(this
                , getSupportFragmentManager()
                , orderSimulator);
        //Set up View Pager
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        //Set up tabs
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        View menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfigDialog();
            }
        });
    }

    private void showConfigDialog() {
        Dialog dialog = new AlertDialog.Builder(this)
                .setView(R.layout.dialog_config)
                .setPositiveButton(R.string.alert_dialog_start_simulator, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        try {
                            int rate = Integer.parseInt(etDispatchRate.getText().toString());
                            PreferenceUtils.saveDispatchRatePreference(getApplicationContext(), rate);
                            simulatorConfig.dispatchRate = rate;

                            int value = Integer.parseInt(etHotShelfCap.getText().toString());
                            PreferenceUtils.saveHotShelfCapPref(getApplicationContext(), value);
                            simulatorConfig.hotShelfCapacity = value;

                            value = Integer.parseInt(etColdShelfCap.getText().toString());
                            PreferenceUtils.saveColdShelfCapPref(getApplicationContext(), value);
                            simulatorConfig.coldShelfCapacity = value;

                            value = Integer.parseInt(etFrozenShelfCap.getText().toString());
                            PreferenceUtils.saveFrozenShelfCapPref(getApplicationContext(), value);
                            simulatorConfig.frozenShelfCapacity = value;

                            value = Integer.parseInt(etOverflowShelfCap.getText().toString());
                            PreferenceUtils.saveOverflowShelfCapPref(getApplicationContext(), value);
                            simulatorConfig.overflowShelfCapacity = value;

                            int min = Integer.parseInt(etCourierMinDelay.getText().toString());
                            int max = Integer.parseInt(etCourierMaxDelay.getText().toString());
                            value = Math.min(min, max);
                            PreferenceUtils.saveCourierMinValue(getApplicationContext(), value);
                            simulatorConfig.courierDelayMin = value;

                            value = Math.max(min, max);
                            PreferenceUtils.saveCourierMaxValue(getApplicationContext(), value);
                            simulatorConfig.courierDelayMax = value;

                            List<Order> orders = readOrdersFromRes(R.raw.orders);
                            logDebug("showConfigDialog, order size:" + orders.size());

                            orderSimulator.start(orders);
                        } catch (IOException e) {
                            String fmt = getString(R.string.error_order_file_read);
                            String msg = String.format(fmt, e.getMessage());
                            Toast.makeText(OrderSimulatorActivity.this, msg, Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        finish();
                    }
                })
                .create();
        dialog.show();

        //Server order dispatch rate
        etDispatchRate = dialog.findViewById(R.id.et_dispatch_rate);
        etDispatchRate.setText(String.valueOf(simulatorConfig.dispatchRate));

        etHotShelfCap = dialog.findViewById(R.id.et_hot_shelf_capacity);
        etHotShelfCap.setText(String.valueOf(simulatorConfig.hotShelfCapacity));

        etColdShelfCap = dialog.findViewById(R.id.et_cold_shelf_capacity);
        etColdShelfCap.setText(String.valueOf(simulatorConfig.coldShelfCapacity));

        etFrozenShelfCap = dialog.findViewById(R.id.et_frozen_shelf_capacity);
        etFrozenShelfCap.setText(String.valueOf(simulatorConfig.frozenShelfCapacity));

        etOverflowShelfCap = dialog.findViewById(R.id.et_overflow_shelf_capacity);
        etOverflowShelfCap.setText(String.valueOf(simulatorConfig.overflowShelfCapacity));

        etCourierMinDelay = dialog.findViewById(R.id.et_courier_min_delay);
        etCourierMinDelay.setText(String.valueOf(simulatorConfig.courierDelayMin));

        etCourierMaxDelay = dialog.findViewById(R.id.et_courier_max_delay);
        etCourierMaxDelay.setText(String.valueOf(simulatorConfig.courierDelayMax));
    }

    private List<Order> readOrdersFromRes(int orderResId) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(getResources().openRawResource(orderResId)));
        StringBuffer orderBuffer = new StringBuffer();
        char[] buffer = new char[1025];
        int n;
        while ((n = br.read(buffer, 0, 1024)) > 0) {
            orderBuffer.append(buffer, 0, n);
        }
        br.close();
        List<Order> orders = JSON.parseArray(orderBuffer.toString(), Order.class);
        return orders;
    }

    private void logDebug(String msg) {
        Log.d(getClass().getSimpleName(), msg);
    }
}