package com.lyl.boon.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.lyl.boon.R;
import com.lyl.boon.main.image.ImageActivity;

import java.util.ArrayList;

/**
 * Wing_Li
 * 2016/4/15.
 */
public class IntentUtils {

    /**
     * 跳转到全屏图片列表显示
     *
     * @param activity
     * @param imgs 图片地址 列表
     * @param position 显示第 position 个
     */
    public static void startImageActivity(Activity activity, ArrayList<String> imgs, int position) {
        Intent intent = new Intent(activity, ImageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("imgs", imgs);
        bundle.putInt("position", position);
        intent.putExtra("bundle", bundle);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
