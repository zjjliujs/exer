package com.cloudcousion.orderproc.ui.main;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cloudcousion.orderproc.R;
import com.cloudcousion.ordersys.OrderSimulator;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class TabPageAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_server_list
            , R.string.tab_shelf_orders
            , R.string.tab_fullfilled_orders
            , R.string.tab_wasted_orders};
    private final Context mContext;
    private final OrderSimulator simulator;

    public TabPageAdapter(Context context, FragmentManager fm
            , OrderSimulator simulator) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
        this.simulator = simulator;
    }

    @Override
    public Fragment getItem(int position) {
        logDebug("TabPageAdapter: getItem entry! position:" + position);
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (TAB_TITLES[position]) {
            case R.string.tab_server_list: {
                return new ServerTabFragment(simulator.orderServer);
            }
            case R.string.tab_shelf_orders: {
                return new ShelfTabFragment(simulator.shelfManager);
            }
            case R.string.tab_wasted_orders: {
                return new WastedTabFragment(simulator.shelfManager);
            }
            case R.string.tab_fullfilled_orders: {
                return new FullFillTabFragment(simulator.courierManager);
            }
            default:
                //Impossible!
                throw new RuntimeException("getItem, error position:" + position);
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return TAB_TITLES.length;
    }

    private void logDebug(String msg) {
        Log.d(getClass().getSimpleName(), msg);
    }
}