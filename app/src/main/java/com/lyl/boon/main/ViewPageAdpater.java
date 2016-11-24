package com.lyl.boon.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Wing_Li
 * 2016/4/1.
 */
public class ViewPageAdpater extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private List<String> titles;

    public ViewPageAdpater(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    public ViewPageAdpater(FragmentManager fm) {
        super(fm);
    }

    public void setFragments(List<Fragment> fragments, List<String> titles) {
        this.fragments = fragments;
        this.titles = titles;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titles != null) {
            return titles.get(position);
        }
        return "";
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }

}
