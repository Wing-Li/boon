package com.lyl.boon.ui.superboon;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.lyl.boon.ui.base.fragment.BaseMenuFragment;

import java.util.List;

/**
 * Wing_Li
 * 2016/4/1.
 */
public class SuperBoonFragment extends BaseMenuFragment {

    public static final String SUPER_TYPE = "type";

    @Override
    protected void setFragment(List<String> titles, List<Fragment> fragments) {
        addFragment(titles, fragments, "骨感", "gugan");
        addFragment(titles, fragments, "女神", "nvshen");
        addFragment(titles, fragments, "极品", "jipin");
        addFragment(titles, fragments, "美腿", "meitui");
        addFragment(titles, fragments, "波涛汹涌", "botaoxiongyongtu");
        addFragment(titles, fragments, "人间胸器", "renjianxiongqi");
        addFragment(titles, fragments, "娇小萝莉", "jiaoxiaoluoli");
        addFragment(titles, fragments, "童颜巨乳", "tongyanjurutu");
        addFragment(titles, fragments, "肉感", "rougan");
        addFragment(titles, fragments, "白嫩", "bainen");
        addFragment(titles, fragments, "小麦色", "xiaomaise");
        addFragment(titles, fragments, "香肩", "xiangjian");
        addFragment(titles, fragments, "玉足", "yuzu");
        addFragment(titles, fragments, "蜜桃臀", "mitaotun");
        addFragment(titles, fragments, "尤物", "youwu");
        addFragment(titles, fragments, "美臀", "meituntu");
    }

    private void addFragment(List<String> titles, List<Fragment> fragments, String title, String id) {
        titles.add(title);

        Fragment fragment = new SuperBoonListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(SUPER_TYPE, id);
        fragment.setArguments(bundle);
        fragments.add(fragment);
    }
}
