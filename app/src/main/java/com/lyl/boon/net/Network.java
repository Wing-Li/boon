package com.lyl.boon.net;

import androidx.annotation.NonNull;

import com.lyl.boon.BuildConfig;
import com.lyl.boon.net.api.WanAndroidApi;
import com.lyl.boon.net.api.ZhaiNanApi;
import com.lyl.boon.net.api.ZhuangbiApi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Wing_Li
 * 2016/3/30.
 */
public class Network {

    /**
     * 玩Android请求的地址
     **/
    private static final String URL_WANANDROID = "http://www.wanandroid.com/";
    /**
     * 装逼图片地址
     */
    private static final String URL_ZHUANG = "http://www.zhuangbi.info/";

    /**
     * 设置超时的时间
     **/
    private static final int DEFAULT_TIMEOUT = 5;

    private static OkHttpClient.Builder httpClientBuilder;
    private static WanAndroidApi wanAndroidApi;
    private static ZhuangbiApi zhuangbiApi;
    private static ZhaiNanApi zhaiNanApi;


    private Network() {
    }

    /**
     * 初始化 OkHttp
     */
    private static void initOkHttp() {
        httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        // devCompile 'com.squareup.okhttp3:logging-interceptor:3.8.0'
        // compile 'com.squareup.okhttp3:okhttp:3.8.0'
        if ("dev".equals(BuildConfig.ENVIRONMENT)) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClientBuilder.addInterceptor(logging);
        }
    }

    /**
     * 获取 玩Android 请求的操作。
     */
    public static WanAndroidApi getWanAndroidList() {
        if (wanAndroidApi == null) {
            wanAndroidApi = getRetrofit(URL_WANANDROID).create(WanAndroidApi.class);
        }
        return wanAndroidApi;
    }

    /**
     * 装逼图片的接口
     */
    public static ZhuangbiApi getZhuangbi() {
        if (zhuangbiApi == null) {
            zhuangbiApi = getRetrofit(URL_ZHUANG).create(ZhuangbiApi.class);
        }
        return zhuangbiApi;
    }

    /**
     * 宅男女神的接口
     */
    public static ZhaiNanApi getZhaiNanApi() {
        if (zhaiNanApi == null) {
            zhaiNanApi = new ZhaiNanApi();
        }
        return zhaiNanApi;
    }

    @NonNull
    private static Retrofit getRetrofit(String url) {
        if (httpClientBuilder == null) {
            initOkHttp();
        }
        return new Retrofit.Builder()//
                .client(httpClientBuilder.build())//
                .baseUrl(url)//
                .addConverterFactory(GsonConverterFactory.create())//
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//
                .build();
    }

}
