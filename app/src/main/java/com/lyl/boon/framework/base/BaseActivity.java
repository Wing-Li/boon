package com.lyl.boon.framework.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lyl.boon.R;
import com.lyl.boon.app.MyApp;

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
    public TextView mActionTitle;
    //标题图片
    public ImageView mActionImage;
    //侧边栏开关
    public ImageView mActionRightImg;
    //返回按钮
    public TextView mActionBack;

    protected Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        //记录此activity已经打开
        appApplication = (MyApp) getApplication();
    }


    /**
     * 初始化actionbar
     */
    protected void initActionbar() {
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams( ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER );
        View viewTitleBar = getLayoutInflater().inflate( R.layout.action_bar_title, null );

        actionBar = getSupportActionBar();

        if (actionBar == null && lp == null && viewTitleBar == null) {
            return;
        }

        actionBar.setCustomView( viewTitleBar, lp );
        actionBar.setDisplayShowHomeEnabled( false );//去掉导航
        actionBar.setDisplayShowTitleEnabled( false );//去掉标题
        actionBar.setDisplayOptions( ActionBar.DISPLAY_SHOW_CUSTOM );
        actionBar.setDisplayShowCustomEnabled( true );
        //标题图片
        mActionImage = (ImageView) getSupportActionBar().getCustomView().findViewById( R.id.action_bar_cat_img );
        //标题文字
        mActionTitle = (TextView) getSupportActionBar().getCustomView().findViewById( R.id.action_bar_title_txt );
        //右边图标
        mActionRightImg = (ImageView) getSupportActionBar().getCustomView().findViewById( R.id.action_bar_title_menu );
        //返回
        mActionBack = (TextView) getSupportActionBar().getCustomView().findViewById( R.id.action_bar_back_txt );

        mActionBack.setVisibility( View.GONE );
        mActionTitle.setVisibility( View.VISIBLE );
    }

    public void showToast(String str) {
        Toast.makeText( getApplicationContext(), str, Toast.LENGTH_SHORT ).show();
    }

    public void showToast(int res) {
        Toast.makeText( getApplicationContext(), res, Toast.LENGTH_SHORT ).show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
