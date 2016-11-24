package com.lyl.boon.framework.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.lyl.boon.R;
import com.lyl.boon.framework.base.BaseFragment;
import com.lyl.boon.main.ViewPageAdpater;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Wing_Li
 * 2016/4/5.
 */
public abstract class BaseMenuFragment extends BaseFragment {

    @Bind(R.id.m_tablayout)
    TabLayout mTablayout;
    @Bind(R.id.m_viewpager)
    ViewPager mViewpager;

    protected List<String> mTitles;
    protected List<Fragment> mFragments;
    protected ViewPageAdpater mViewPageAdpater;

    /**
     * Action 的标题
     */
    protected TextView mTitleTxt;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_base;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated( savedInstanceState );
        initView();
        initData();
    }

    private void initData() {
        mTitles = new ArrayList<>();
        mFragments = new ArrayList<>();

        setFragment();

        setTitle( 0 );//默认标题是第一个fragment的标题
    }

    protected void initView() {
        mViewPageAdpater = new ViewPageAdpater( getChildFragmentManager() );
        mViewpager.setAdapter( mViewPageAdpater );
        mTablayout.setupWithViewPager( mViewpager );

        mTitleTxt = (TextView) getHolder().getSupportActionBar().getCustomView().findViewById( R.id.action_bar_title_txt );

        mViewpager.addOnPageChangeListener( new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTitle( position );
                onPageChanged( position );
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        } );
    }

    protected void setTabLayout() {
        if (mViewPageAdpater.getCount() > 4) {
            mTablayout.setTabMode( TabLayout.MODE_SCROLLABLE );
        } else {
            mTablayout.setTabMode( TabLayout.MODE_FIXED );
        }
    }

    /**
     * 改变Action的标题
     */
    private void setTitle(int position) {
        if (mTitles != null && mTitles.size() > 0) {
            mTitleTxt.setText( mTitles.get( position ) );
        }
    }

    /**
     * 滑动页面所改变的内容;给子类提供一个方法，如果有需要的话，可以继承
     */
    protected void onPageChanged(int position) {

    }

    /**
     * 建立 Fragment List，设置页面
     */
    protected abstract void setFragment();

}
