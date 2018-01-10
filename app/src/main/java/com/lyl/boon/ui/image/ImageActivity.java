package com.lyl.boon.ui.image;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.lyl.boon.R;
import com.lyl.boon.ui.base.BaseActivity;
import com.lyl.boon.view.HackyViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 全屏显示图片
 */
public class ImageActivity extends BaseActivity {

    @Bind(R.id.image_viewpager)
    HackyViewPager imageViewpager;

    // 图片列表 及 当前位置
    private List<String> imgs;
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

    public static Intent getIntent(Activity activity, ArrayList<String> imgs, int position){
        Intent intent = new Intent(activity, ImageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("imgs", imgs);
        bundle.putInt("position", position);
        intent.putExtra("bundle", bundle);
        return intent;
    }

    private void initData() {
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle != null) {
            imgs = bundle.getStringArrayList("imgs");
            position = bundle.getInt("position");
        } else {
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
