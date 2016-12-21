package com.lyl.boon.app;

import android.app.Application;
import android.os.Environment;
import android.text.TextUtils;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.File;

/**
 * Wing_Li
 * 2016/3/31.
 */
public class MyApp extends Application {

    public static String mAppPath;

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

    public static String getAppPath() {
        if (!TextUtils.isEmpty(mAppPath)) {
            return mAppPath;
        }
        File sdCard = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile();
        File file = new File(sdCard, "boon");
        if (!file.exists()) {
            file.mkdirs();
        }
        mAppPath = file.getAbsolutePath();
        return mAppPath;
    }

}
