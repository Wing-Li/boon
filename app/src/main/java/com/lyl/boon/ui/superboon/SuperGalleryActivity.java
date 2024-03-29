package com.lyl.boon.ui.superboon;

import android.os.Bundle;

import com.lyl.boon.R;
import com.lyl.boon.ui.base.BaseActivity;

public class SuperGalleryActivity extends BaseActivity {

    private SuperGalleryFragment galleryFragment;
    private String mDetailsUrl;
    private String mTitle;
    private String mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_gallery);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mDetailsUrl = bundle.getString("detailsUrl");
            mTitle = bundle.getString("title");
            mMenu = bundle.getString("menu");
        } else {
            showToast(R.string.msg_net_erro);
            return;
        }

        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState) {
        initActionbar();

        if (galleryFragment == null) {
            galleryFragment = new SuperGalleryFragment();
            Bundle bundle = new Bundle();
            bundle.putString("detailsUrl", mDetailsUrl);
            bundle.putString("title", mTitle);
            bundle.putString("menu", mMenu);
            galleryFragment.setArguments(bundle);
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.activity_super_gallery, galleryFragment).commit();
        }
    }

}
