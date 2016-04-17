package com.lyl.boon.ui.view;

import android.content.Context;
import android.webkit.WebView;

/**
 * Wing_Li
 * 2016/4/12.
 */
public class MyWebView extends WebView {
    public MyWebView(Context context) {
        super(context);
    }


    @Override
    public void setOnScrollChangeListener(OnScrollChangeListener l) {
        super.setOnScrollChangeListener(l);

    }
}
