package com.lyl.boon.main.image;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.lyl.boon.R;
import com.lyl.boon.framework.base.BaseActivity;
import com.lyl.boon.ui.view.HackyViewPager;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ImageActivity extends BaseActivity {

    @Bind(R.id.image_viewpager)
    HackyViewPager imageViewpager;

    private List<String> imgs;
    private String title;
    private int position;

    private ImageAdapter mImageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏,隐藏状态栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);

        getSupportActionBar().hide();
        initData();
        initView();
    }

    private void initData() {
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle != null) {
            imgs = bundle.getStringArrayList("imgs");
            position = bundle.getInt("position");
        }else {
            showToast(R.string.msg_net_erro);
            return;
        }
    }

    private void initView() {
        mImageAdapter = new ImageAdapter(this, imgs);
        imageViewpager.setAdapter(mImageAdapter);
        imageViewpager.setOffscreenPageLimit(2);
        imageViewpager.setCurrentItem(position);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}
