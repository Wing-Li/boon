package com.lyl.boon.ui.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.lyl.boon.R;
import com.lyl.boon.ui.base.BaseFragment;
import com.lyl.boon.ui.MainViewPageAdpater;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 带 ViewPager 的 Fragment 基类封装
 * 使用者只需要重写 setFragment() 方法填充数据，就可以显示内容。
 *
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
    protected MainViewPageAdpater mViewPageAdpater;


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_base;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        mTitles = new ArrayList<>();
        mFragments = new ArrayList<>();

        // 初始化 标题 和 页面
        // 因为 Java 的对象是引用传递，所以这里直接将列表的引用传过去，使用者只需要填充数据就可以显示内容
        setFragment(mTitles, mFragments);

        // 将标题和页面设置给 ViewPager
        mViewPageAdpater.setFragments( mFragments, mTitles );

        //默认标题是第一个fragment的标题
        setTitle(0);
    }

    protected void initView() {
        mViewPageAdpater = new MainViewPageAdpater(getChildFragmentManager());
        mViewpager.setAdapter(mViewPageAdpater);
        mTablayout.setupWithViewPager(mViewpager);

        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTitle(position);
                onPageChanged(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    protected void setTabLayout() {
        if (mViewPageAdpater.getCount() > 4) {
            mTablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        } else {
            mTablayout.setTabMode(TabLayout.MODE_FIXED);
        }
    }

    /**
     * 改变Action的标题
     */
    private void setTitle(int position) {
        if (mTitles != null && mTitles.size() > 0) {
            setTitle(mTitles.get(position));
        }
    }

    /**
     * 滑动页面所改变的内容;给子类提供一个方法，如果有需要的话，可以继承
     */
    protected void onPageChanged(int position) {

    }

    /**
     * 建立 Fragment List，设置页面
     *
     * @param titles 页面的标题，添加的数量，必须与 页面 对应
     * @param fragments Fragment 页面，添加的数量必须与 标题 对应
     */
    protected abstract void setFragment(List<String> titles, List<Fragment> fragments);

}
