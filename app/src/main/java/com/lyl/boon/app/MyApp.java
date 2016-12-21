package com.lyl.boon.app;

import android.app.Application;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Wing_Li
 * 2016/3/31.
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initBugly();
    }

    /**
     * 初始化BUG分析 和 应用升级检测
     */
    private void initBugly() {
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getApplicationContext());
        strategy.setAppReportDelay(60000);
        Bugly.init(getApplicationContext(), "410b074705", true, strategy);
    }
    
}
