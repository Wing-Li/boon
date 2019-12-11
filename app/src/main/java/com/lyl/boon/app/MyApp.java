package com.lyl.boon.app;

import android.os.Environment;
import android.text.TextUtils;

import androidx.multidex.MultiDexApplication;

import com.lyl.boon.BuildConfig;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.File;

import cn.leancloud.AVOSCloud;

/**
 * Wing_Li
 * 2016/3/31.
 */
public class MyApp extends MultiDexApplication {

    /**
     * App 存放文件的路径
     **/
    private static String mAppPath;

    @Override
    public void onCreate() {
        super.onCreate();
        initBugly();
        initLeanCloud();
    }

    /**
     * 初始化 leancloud
     */
    private void initLeanCloud() {
        AVOSCloud.initialize(this, BuildConfig.LEANCLOUD_APPID, BuildConfig.LEANCLOUD_APPKEY, "https://boon.lylyl.cn");
    }

    /**
     * 初始化BUG分析 和 应用升级检测
     */
    private void initBugly() {
        String buglyAppId = BuildConfig.BUGLYAPPID;
        if (!TextUtils.isEmpty(buglyAppId)) {
            CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getApplicationContext());
            strategy.setAppReportDelay(60000);
            Bugly.init(getApplicationContext(), buglyAppId, true, strategy);
        }
    }

    /**
     * 获取存放文件的了路径
     */
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
