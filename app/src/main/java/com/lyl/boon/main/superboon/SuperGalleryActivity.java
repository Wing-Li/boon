package com.lyl.boon.main.superboon;

import android.os.Bundle;

import com.lyl.boon.R;
import com.lyl.boon.framework.base.BaseActivity;

public class SuperGalleryActivity extends BaseActivity {

    private SuperGalleryFragment galleryFragment;
    private int mId;
    private String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_gallery);

        Bundle budele = getIntent().getBundleExtra("budele");
        mId = budele.getInt("id");
        mTitle = budele.getString("title");

        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState) {
        initActionbar();

        if (galleryFragment == null) {
            galleryFragment = new SuperGalleryFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("id", mId);
            bundle.putString("title", mTitle);
            galleryFragment.setArguments(bundle);
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.activity_super_gallery, galleryFragment).commit();
        }
    }

}
