package com.mgdapps.livemarketcap;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 1/5/2018.
 */

public class ViewpagerAdapterHome extends FragmentPagerAdapter {

    List<Fragment> fragment=new ArrayList<>();

    public ViewpagerAdapterHome(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment)
    {
        this.fragment.add(fragment);
    }

    @Override
    public Fragment getItem(int position) {
        return fragment.get(position);
    }

    @Override
    public int getCount() {
        return fragment.size();
    }
}
