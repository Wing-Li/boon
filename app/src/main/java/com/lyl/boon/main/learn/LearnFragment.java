package com.lyl.boon.main.learn;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.lyl.boon.app.AppConstant;
import com.lyl.boon.framework.base.fragment.BaseMenuFragment;

/**
 * Wing_Li
 * 2016/4/1.
 */
public class LearnFragment extends BaseMenuFragment {

    public static final String LEARN_TYPE = "type";

    @Override
    protected void setFragment() {
        addFragment( AppConstant.GANK_TYPE_ANDROID, AppConstant.GANK_TYPE_ANDROID );
        addFragment( AppConstant.GANK_TYPE_IOS, AppConstant.GANK_TYPE_IOS );
        addFragment( AppConstant.GANK_TYPE_WEB, AppConstant.GANK_TYPE_WEB );

        mViewPageAdpater.setFragments( mFragments, mTitles );
    }

    private void addFragment(String title, String type) {
        mTitles.add( title );

        Fragment fragment = new LearnListFragment();
        Bundle bundle = new Bundle();
        bundle.putString( LEARN_TYPE, type );
        fragment.setArguments( bundle );
        mFragments.add( fragment );
    }
}
