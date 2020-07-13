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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cloudcousion.orderproc.R;
import com.cloudcousion.orderserver.model.OrderTemperature;
import com.cloudcousion.orderserver.utils.AndroidLogger;
import com.cloudcousion.orderserver.utils.OrderLoggerI;
import com.cloudcousion.ordersys.shelf.ShelfManagerI;
import com.cloudcousion.ordersys.shelf.ShelfStateListenerI;

import java.util.Locale;

public class ShelfTabFragment extends Fragment implements ShelfStateListenerI {
    private OrderLoggerI logger = AndroidLogger.getInstance();

    private final ShelfManagerI shelfManager;
    /*
    private final List<CookedOrder> hotOrderList;
    private final List<CookedOrder> coldOrderList;
    private final List<CookedOrder> frozenOrderList;
    private final List<CookedOrder> overflowOrderList;
    */

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {

        }
    };
    private TextView orderCountTV;
    private int currentTabId = R.id.tv_hot_btn;
    private View hotBtn;
    private View coldBtn;
    private View frozenBtn;
    private View overflowBtn;
    private TextView devOrderSizeTV;
    private OrderTemperature currentOrderTemp;
    private RecyclerView orderListRV;
    private ShelfOrderListRVAdapter orderRVAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater
            , @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_shelf_tab, container, false);
        orderCountTV = root.findViewById(R.id.tv_order_count);
        updateTotalOrderCount();

        currentOrderTemp = OrderTemperature.Hot;
        initViews(root);
        currentTabId = R.id.tv_hot_btn;

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        shelfManager.registerStateListener(this);
        updateTabUI();
    }

    @Override
    public void onPause() {
        super.onPause();
        shelfManager.unregisterStateListener(this);
    }

    private void initViews(View root) {
        devOrderSizeTV = root.findViewById(R.id.tv_dev_order_size);
        orderListRV = root.findViewById(R.id.rv_shelf_order_list);
        initOrderListRV();

        hotBtn = root.findViewById(R.id.tv_hot_btn);
        hotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentTabId = R.id.tv_hot_btn;
                updateTabUI();
            }
        });

        coldBtn = root.findViewById(R.id.tv_cold_btn);
        coldBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentTabId = R.id.tv_cold_btn;
                updateTabUI();
            }
        });

        frozenBtn = root.findViewById(R.id.tv_frozen_btn);
        frozenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentTabId = R.id.tv_frozen_btn;
                updateTabUI();
            }
        });

        overflowBtn = root.findViewById(R.id.tv_overflow_btn);
        overflowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentTabId = R.id.tv_overflow_btn;
                updateTabUI();
            }
        });
    }

    private void initOrderListRV() {
        orderRVAdapter = new ShelfOrderListRVAdapter(getContext(), shelfManager, currentOrderTemp);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        orderListRV.setLayoutManager(layoutManager);
        orderListRV.setAdapter(orderRVAdapter);
        orderListRV.setItemAnimator(new DefaultItemAnimator());
    }

    private void updateTabUI() {
        hotBtn.setActivated(false);
        coldBtn.setActivated(false);
        frozenBtn.setActivated(false);
        overflowBtn.setActivated(false);

        switch (currentTabId) {
            case R.id.tv_hot_btn:
                hotBtn.setActivated(true);
                currentOrderTemp = OrderTemperature.Hot;
                updateDevOrderSize();
                updateOrderList();
                break;
            case R.id.tv_cold_btn:
                coldBtn.setActivated(true);
                currentOrderTemp = OrderTemperature.Cold;
                updateDevOrderSize();
                updateOrderList();
                break;
            case R.id.tv_frozen_btn:
                frozenBtn.setActivated(true);
                currentOrderTemp = OrderTemperature.Frozen;
                updateDevOrderSize();
                updateOrderList();
                break;
            case R.id.tv_overflow_btn:
                overflowBtn.setActivated(true);
                currentOrderTemp = OrderTemperature.None;
                updateDevOrderSize();
                updateOrderList();
                break;
            default:
                //Impossible
                throw new RuntimeException("updateTabUI, error id!");
        }
    }

    private void updateDevOrderSize() {
        String fmt = getString(R.string.fmt_order_count);
        String msg = String.format(Locale.US, fmt, shelfManager.deviceOrderSize(currentOrderTemp));
        devOrderSizeTV.setText(msg);
    }

    private void updateTotalOrderCount() {
        String fmt = getResources().getString(R.string.fmt_total_order_count);
        String msg = String.format(Locale.US, fmt, shelfManager.totalOrderSize());
        orderCountTV.setText(msg);
    }

    public ShelfTabFragment(ShelfManagerI shelfManager) {
        this.shelfManager = shelfManager;
        /*
        hotOrderList = shelfManager.deviceOrderList(OrderTemperature.Hot);
        coldOrderList = shelfManager.deviceOrderList(OrderTemperature.Cold);
        frozenOrderList = shelfManager.deviceOrderList(OrderTemperature.Frozen);
        overflowOrderList = shelfManager.deviceOrderList(OrderTemperature.None);
        */
    }

    //This method will be called from shelf manager or courier manager or order server thread!
    @Override
    public synchronized void stateChanged() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                updateTotalOrderCount();
                updateDevOrderSize();
                updateOrderList();
            }
        });
    }

    private void updateOrderList() {
        orderRVAdapter.setOrders(shelfManager.deviceOrderList(currentOrderTemp));
    }
}
