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
        addFragment(titles, fragments, "鸿洋", "408");
        addFragment(titles, fragments, "郭霖", "409");
        addFragment(titles, fragments, "code小生", "414");
        addFragment(titles, fragments, "谷歌开发者", "415");
        addFragment(titles, fragments, "Android群英传", "413");
        addFragment(titles, fragments, "美团技术团队", "417");
    }

    private void addFragment(List<String> titles, List<Fragment> fragments, String title, String id) {
        titles.add(title);

        Fragment fragment = new LearnListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(LEARN_TYPE, id);
        fragment.setArguments(bundle);
        fragments.add(fragment);
    }
}
