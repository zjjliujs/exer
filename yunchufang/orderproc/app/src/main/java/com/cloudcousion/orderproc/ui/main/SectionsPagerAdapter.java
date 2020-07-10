package com.cloudcousion.orderproc.ui.main;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cloudcousion.orderproc.R;
import com.cloudcousion.orderserver.OrderServerI;
import com.cloudcousion.ordersys.OrderSimulator;
import com.cloudcousion.ordersys.shelf.ShelfManagerI;
import com.cloudcousion.ordersys.shelf.ShelfStateListenerI;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_server_list, R.string.tab_shelf_list};
    private final Context mContext;
    private final OrderSimulator simulator;

    public SectionsPagerAdapter(Context context, FragmentManager fm
            , OrderSimulator simulator) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
        this.simulator = simulator;
    }

    @Override
    public Fragment getItem(int position) {
        logDebug("getItem entry! position:" + position);
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position) {
            case 0: {
                ServerOrderListFragment fragment = new ServerOrderListFragment(simulator.orderServer);
                return fragment;
            }
            case 1: {
                ShelfOrderListFragment fragment = new ShelfOrderListFragment(simulator.shelfManager);
                return fragment;
            }
            default:
                return PlaceholderFragment.newInstance(position + 1);
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
        return 2;
    }

    private void logDebug(String msg) {
        Log.d(getClass().getSimpleName(), msg);
    }
}