package com.lyl.boon.framework.base;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.lyl.boon.R;
import com.lyl.boon.app.MyApp;
import com.lyl.boon.main.AboutActivity;

/**
 * Wing_Li
 * 2016/3/30.
 */
public class BaseActivity extends AppCompatActivity {

    private MyApp appApplication;

    // 界面顶部Bar
    public ActionBar actionBar;
    //整个顶部
    //标题
    public TextSwitcher mActionTitle;
    //标题图片
    public ImageView mActionRightImg;
    //侧边栏开关
    public ImageView mActionLeftImg;
    //返回按钮
    public TextView mActionBack;

    protected Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //记录此activity已经打开
        appApplication = (MyApp) getApplication();
    }

    /**
     * 初始化actionbar
     */
    protected void initActionbar() {
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar
                .LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View viewTitleBar = getLayoutInflater().inflate(R.layout.action_bar_title, null);

        actionBar = getSupportActionBar();
        if (actionBar == null) return;

        actionBar.setCustomView(viewTitleBar, lp);
        actionBar.setDisplayShowHomeEnabled(false);//去掉导航
        actionBar.setDisplayShowTitleEnabled(false);//去掉标题
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);

        View actionView = getSupportActionBar().getCustomView();
        if (actionView == null) return;

        //右边图标
        mActionRightImg = (ImageView) actionView.findViewById(R.id.action_bar_right_img);
        //标题文字
        mActionTitle = (TextSwitcher) actionView.findViewById(R.id.action_bar_title_txt);
        //左边图标
        mActionLeftImg = (ImageView) actionView.findViewById(R.id.action_bar_left_img);
        //左边文字
        mActionBack = (TextView) actionView.findViewById(R.id.action_bar_back_txt);
        setTitleAnims();
    }

    private void setTitleAnims() {
        mActionTitle.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                final TextView textView = new TextView(BaseActivity.this);
                textView.setSingleLine(true);
                textView.setTextAppearance(BaseActivity.this, R.style.action_title_style);
                textView.setGravity(Gravity.CENTER);
                textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                textView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textView.setSelected(true);
                    }
                }, 1738);
                return textView;
            }
        });
        mActionTitle.setInAnimation(this, android.R.anim.fade_in);
        mActionTitle.setOutAnimation(this, android.R.anim.fade_out);
    }

    public void setAppAbout(){
        mActionBack.setVisibility(View.GONE);
        mActionLeftImg.setVisibility(View.VISIBLE);
        mActionLeftImg.setImageResource(R.drawable.ic_info_outline_black_24dp);
        mActionLeftImg.setColorFilter(Color.GRAY);
        mActionLeftImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaseActivity.this, AboutActivity.class));
            }
        });
    }

    public void showToast(String str) {
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int res) {
        Toast.makeText(getApplicationContext(), res, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

}
