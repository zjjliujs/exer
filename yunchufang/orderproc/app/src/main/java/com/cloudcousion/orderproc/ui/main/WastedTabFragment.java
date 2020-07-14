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
import com.cloudcousion.ordersys.kitchen.CookedOrder;
import com.cloudcousion.ordersys.shelf.ShelfManager;
import com.cloudcousion.ordersys.shelf.ShelfStateListenerI;

import java.util.List;
import java.util.Locale;

public class WastedTabFragment extends ValuedOrderFragment implements ShelfStateListenerI {

    private final ShelfManager shelfManager;

    public WastedTabFragment(ShelfManager shelfManager) {
        super();
        this.shelfManager = shelfManager;
        this.shelfManager.registerStateListener(this);
    }

    @Override
    protected List<CookedOrder> getOrderList() {
        return shelfManager.getWasteOrders();
    }
}
