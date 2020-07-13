package com.cloudcousion.orderproc;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
import java.util.Arrays;
import java.util.List;

public class OrderSimulatorActivity extends AppCompatActivity {
    private Spinner spinner;
    private SimulatorConfig simulatorConfig;
    private OrderSimulator orderSimulator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        simulatorConfig = new SimulatorConfig();
        Integer defaultRate = PreferenceUtils.getDispatchRatePreference(this, simulatorConfig.defaultDispatchRate);
        simulatorConfig.defaultDispatchRate = defaultRate;
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
                            int defaultDispatchRatePos = spinner.getSelectedItemPosition();
                            int rate = simulatorConfig.dispatchRates[defaultDispatchRatePos];
                            PreferenceUtils.saveDispatchRatePreference(OrderSimulatorActivity.this, rate);
                            simulatorConfig.defaultDispatchRate = rate;

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

        spinner = dialog.findViewById(R.id.spinner_dispatch_rate);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, simulatorConfig.dispatchRates);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        int defaultDispatchRatePos = Arrays.binarySearch(simulatorConfig.dispatchRates, simulatorConfig.defaultDispatchRate);
        spinner.setSelection(defaultDispatchRatePos);
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