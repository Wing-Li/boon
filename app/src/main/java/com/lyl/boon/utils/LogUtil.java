package com.lyl.boon.utils;

import android.util.Log;

/**
 * @author LX
 * @ClassName LogUtil
 * @Description Debug调试工具类
 * @date 2015年1月9日 下午5:29:55
 */
public class LogUtil {

    private static final String TAG = "Boon";

    private LogUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /*
     * 是否需要打印bug，可以在application的onCreate函数里面初始化
     */
    public static final boolean isDebug = true;

	/*
     * 下面四个是默认tag的函数
	 */

    public static void i(String msg) {
        if (isDebug && msg != null)
            Log.i(TAG, msg);
    }

    public static void d(String msg) {
        if (isDebug && msg != null)
            Log.d(TAG, msg);
    }

    public static void e(String msg) {
        if (isDebug && msg != null)
            Log.e(TAG, msg);
    }

    public static void v(String msg) {
        if (isDebug && msg != null)
            Log.v(TAG, msg);
    }

	/*
     * 下面是传入自定义tag的函数
	 */

    public static void i(String tag, String msg) {
        if (isDebug && msg != null)
            Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug && msg != null)
            Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug && msg != null)
            Log.e(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug && msg != null)
            Log.v(tag, msg);
    }

}
