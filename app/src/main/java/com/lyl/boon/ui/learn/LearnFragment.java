package com.lyl.boon.ui.learn;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import com.lyl.boon.app.Constant;
import com.lyl.boon.ui.base.fragment.BaseMenuFragment;

import java.util.List;

/**
 * Wing_Li
 * 2016/4/1.
 */
public class LearnFragment extends BaseMenuFragment {

    public static final String LEARN_TYPE = "type";

    @Override
    protected void setFragment(List<String> titles, List<Fragment> fragments) {
        addFragment(titles, fragments, Constant.GANK_TYPE_ANDROID, Constant.GANK_TYPE_ANDROID);
        addFragment(titles, fragments, Constant.GANK_TYPE_IOS, Constant.GANK_TYPE_IOS);
        addFragment(titles, fragments, Constant.GANK_TYPE_WEB, Constant.GANK_TYPE_WEB);
    }

    private void addFragment(List<String> titles, List<Fragment> fragments, String title, String type) {
        titles.add(title);

        Fragment fragment = new LearnListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(LEARN_TYPE, type);
        fragment.setArguments(bundle);
        fragments.add(fragment);
    }
}
