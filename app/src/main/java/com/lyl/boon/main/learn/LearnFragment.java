package com.lyl.boon.main.learn;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.lyl.boon.app.Constant;
import com.lyl.boon.framework.base.fragment.BaseMenuFragment;

/**
 * Wing_Li
 * 2016/4/1.
 */
public class LearnFragment extends BaseMenuFragment {

    public static final String LEARN_TYPE = "type";

    @Override
    protected void setFragment() {
        addFragment( Constant.GANK_TYPE_ANDROID, Constant.GANK_TYPE_ANDROID );
        addFragment( Constant.GANK_TYPE_IOS, Constant.GANK_TYPE_IOS );
        addFragment( Constant.GANK_TYPE_WEB, Constant.GANK_TYPE_WEB );

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
