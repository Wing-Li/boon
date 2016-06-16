package com.lyl.boon.main.web;

import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.lyl.boon.R;
import com.lyl.boon.framework.base.BaseActivity;
import com.lyl.boon.ui.view.loading.LoadingView;
import com.lyl.boon.utils.LogUtil;


public class Html5Activity extends BaseActivity {


    private String mUrl;

    private LinearLayout mLayout;
    private LoadingView mLoadingView;
    private WebView mWebView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        mUrl = bundle.getString("url");
        LogUtil.d("Web--Url:", mUrl);

        initActionbar();
        mLayout = (LinearLayout) findViewById(R.id.web_layout);
        mLoadingView = (LoadingView) findViewById(R.id.loadingView);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mWebView = new WebView(getApplicationContext());
        mWebView.setLayoutParams(params);
        mLayout.addView(mWebView);

        WebSettings mWebSettings = mWebView.getSettings();
        mWebSettings.setSupportZoom(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setDefaultTextEncodingName("utf-8");
        mWebSettings.setLoadsImagesAutomatically(true);
        mWebSettings.setSupportMultipleWindows(true);

        //调用JS方法.安卓版本大于17,加上注解 @JavascriptInterface
        mWebSettings.setJavaScriptEnabled(true);

        saveData(mWebSettings);
        newWin(mWebSettings);

        mWebView.setWebChromeClient(webChromeClient);
        mWebView.setWebViewClient(webViewClient);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mWebView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    stopLoading();
                }


            });
        }

        mWebView.loadUrl(mUrl);
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
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setDatabaseEnabled(true);
        mWebSettings.setAppCacheEnabled(true);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        mWebSettings.setAppCachePath(appCachePath);
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
            WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
            transport.setWebView(mWebView);
            resultMsg.sendToTarget();
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

    private void stopLoading() {
        if (mLoadingView != null && mLoadingView.isShown()) {
            mLoadingView.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();

            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }

        System.exit(0);

        super.onDestroy();
    }


}
