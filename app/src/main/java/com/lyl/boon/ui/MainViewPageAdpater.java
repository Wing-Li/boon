package com.lyl.boon.ui;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Wing_Li
 * 2016/4/1.
 */
public class MainViewPageAdpater extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private List<String> titles;

    public MainViewPageAdpater(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    public MainViewPageAdpater(FragmentManager fm) {
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
