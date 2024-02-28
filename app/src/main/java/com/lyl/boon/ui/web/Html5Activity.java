package com.lyl.boon.ui.web;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.ListPopupWindow;

import com.lyl.boon.R;
import com.lyl.boon.net.LeanCloudCallBack;
import com.lyl.boon.net.LeanCloudNet;
import com.lyl.boon.net.model.UserModel;
import com.lyl.boon.ui.account.LoginActivity;
import com.lyl.boon.ui.base.BaseActivity;
import com.lyl.boon.utils.MyUtils;
import com.lyl.boon.view.loading.LoadingView;
import com.lyl.boon.utils.LogUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;


public class Html5Activity extends BaseActivity {

    private String mUrl;
    private String mAuthor;
    private String mDesc;

    private boolean isFavorite;

    private FrameLayout mLayout;
    private LoadingView mLoadingView;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle != null) {
            mDesc = bundle.getString("desc");
            mAuthor = bundle.getString("author");
            mUrl = bundle.getString("url");
            LogUtil.d("Web--Url:", mUrl);
        } else {
            showToast(getString(R.string.param_error));
            return;
        }

        initActionbar();
        setBackIcon();

        initData();

        initWebView();
        initMoreItem();
    }

    private void initData() {
        LeanCloudNet.INSTANCE.isFavorite(mDesc, mAuthor, mUrl, new LeanCloudCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                isFavorite = aBoolean;
                LogUtil.d(mDesc + "=== 已收藏");
            }

            @Override
            public void onError(int code, @NotNull String msg, @Nullable Exception e) {
            }
        });
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        mLayout = (FrameLayout) findViewById(R.id.web_layout);
        mLoadingView = (LoadingView) findViewById(R.id.loadingView);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mWebView = new WebView(getApplicationContext());
        mWebView.setLayoutParams(params);
        mLayout.addView(mWebView);
        mLoadingView.bringToFront();

        WebSettings mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);  //支持js
        mWebSettings.setSupportZoom(true);
        mWebSettings.setBuiltInZoomControls(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setLoadsImagesAutomatically(true);

        saveData(mWebSettings);
        newWin(mWebSettings);

        mWebView.setWebChromeClient(webChromeClient);
        mWebView.setWebViewClient(webViewClient);

        mWebView.loadUrl(mUrl);
    }

    private void initMoreItem() {
        mActionRightImg.setVisibility(View.VISIBLE);
        mActionRightImg.setImageResource(R.drawable.ic_more);
        mActionRightImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initListPopuItem(v);
            }
        });
    }

    private void initListPopuItem(View view) {
        ArrayList<String> moreItems = new ArrayList<>();
        if (isFavorite) {
            moreItems.add("取消收藏");
        } else {
            moreItems.add("收藏");
        }
        moreItems.add("复制链接");
        moreItems.add("分享");

        final ListPopupWindow mListPop = new ListPopupWindow(this);
        mListPop.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, moreItems));
        mListPop.setWidth(500);
        mListPop.setHeight(LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        mListPop.setAnchorView(view);
        mListPop.setModal(true);
        mListPop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListPop.dismiss();

                switch (position) {
                    case 0: {
                        UserModel userModel = new UserModel(mContext);
                        if (userModel.getUserInfo() != null) {
                            if (isFavorite) {
                                LeanCloudNet.INSTANCE.deleteFavorite(mDesc, mAuthor, mUrl);
                                showToast("取消收藏");
                                isFavorite = false;
                            } else {
                                LeanCloudNet.INSTANCE.saveFavorite(mDesc, mAuthor, mUrl);
                                showToast("收藏成功");
                                isFavorite = true;
                            }
                        } else {
                            startActivity(new Intent(mContext, LoginActivity.class));
                        }
                        break;
                    }

                    case 1: {
                        MyUtils.setClipText(mContext, mUrl);
                        showToast("复制成功");
                        break;
                    }

                    case 2: {
                        String share = mDesc + " 链接地址:" + mUrl;
                        shareContent(mDesc, share);
                        break;
                    }
                }
            }
        });

        mListPop.show();
    }

    /**
     * 多窗口的问题
     */
    private void newWin(WebSettings mWebSettings) {
        //html中的_bank标签就是新建窗口打开，有时会打不开，需要加以下
        //然后 复写 WebChromeClient的onCreateWindow方法
        mWebSettings.setSupportMultipleWindows(true);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
    }

    /**
     * HTML5数据存储
     */
    private void saveData(WebSettings mWebSettings) {
        //有时候网页需要自己保存一些关键数据,Android WebView 需要自己设置
        File cacheDir = getApplicationContext().getCacheDir();
        if (cacheDir != null) {
            mWebSettings.setDomStorageEnabled(true);
            mWebSettings.setDatabaseEnabled(true);
        }
    }

    WebViewClient webViewClient = new WebViewClient() {

        /**
         * 多页面在同一个WebView中打开，就是不新建activity或者调用系统浏览器打开
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            stopLoading();
        }
    };

    WebChromeClient webChromeClient = new WebChromeClient() {

        //=========多窗口的问题==========================================================
        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            WebView.HitTestResult result = view.getHitTestResult();
            String data = result.getExtra();
            mWebView.loadUrl(data);
            return true;
        }
        //=========多窗口的问题==========================================================

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress > 80) {
                stopLoading();
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            mActionTitle.setText(title);
        }
    };

    private boolean stopLoading() {
        if (mLoadingView != null && mLoadingView.isShown()) {
            mLoadingView.setVisibility(View.GONE);
            mLayout.removeView(mLoadingView);
            return true;
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (stopLoading()) {
                return true;
            }
            if (mWebView.canGoBack()) {
                mWebView.goBack();
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mWebView != null) {
            mWebView.clearHistory();
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.loadUrl("about:blank");
            mWebView.stopLoading();
            mWebView.setWebChromeClient(null);
            mWebView.setWebViewClient(null);
            mWebView.destroy();
            mWebView = null;
        }
    }

}
