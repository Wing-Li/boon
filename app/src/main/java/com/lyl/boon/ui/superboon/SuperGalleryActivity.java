package com.lyl.boon.ui.superboon;

import android.os.Bundle;

import com.lyl.boon.R;
import com.lyl.boon.ui.base.BaseActivity;

public class SuperGalleryActivity extends BaseActivity {

    private SuperGalleryFragment galleryFragment;
    private int mId;
    private String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_gallery);

        Bundle budele = getIntent().getBundleExtra("budele");
        if (budele != null){
            mId = budele.getInt("id");
            mTitle = budele.getString("title");
        }else {
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
            bundle.putInt("id", mId);
            bundle.putString("title", mTitle);
            galleryFragment.setArguments(bundle);
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.activity_super_gallery, galleryFragment).commit();
        }
    }

}
