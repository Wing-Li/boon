package com.lyl.boon.net;

import androidx.annotation.NonNull;

import com.lyl.boon.BuildConfig;
import com.lyl.boon.net.api.GankApi;
import com.lyl.boon.net.api.TngouApi;
import com.lyl.boon.net.api.WanAndroidApi;
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
     * Gank请求的地址
     **/
    private static final String URL_GANK = "http://gank.io/api/";
    /**
     * 玩Android请求的地址
     **/
    private static final String URL_WANANDROID = "http://www.wanandroid.com/";
    /**
     * 360图片 请求地址
     */
    private static final String URL_TNGOU = "http://image.so.com/";
    /**
     * 装逼图片地址
     */
    private static final String URL_ZHUANG = "http://www.zhuangbi.info/";

    /**
     * 设置超时的时间
     **/
    private static final int DEFAULT_TIMEOUT = 5;

    private static OkHttpClient.Builder httpClientBuilder;
    private static GankApi gankApi;
    private static WanAndroidApi wanAndroidApi;
    private static TngouApi tngouApi;
    private static ZhuangbiApi zhuangbiApi;


    private Network() {
    }

    /**
     * 初始化 OkHttp
     */
    private static void initOkHttp() {
        httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout( DEFAULT_TIMEOUT, TimeUnit.SECONDS );

        // devCompile 'com.squareup.okhttp3:logging-interceptor:3.8.0'
        // compile 'com.squareup.okhttp3:okhttp:3.8.0'
        if ("dev".equals(BuildConfig.ENVIRONMENT)) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClientBuilder.addInterceptor(logging);
        }
    }

    /**
     * 获取Gank.io 请求的操作。
     */
    public static GankApi getGankMenuList() {
        if (gankApi == null) {
            gankApi = getRetrofit( URL_GANK ).create( GankApi.class );
        }
        return gankApi;
    }

    /**
     * 获取 玩Android 请求的操作。
     */
    public static WanAndroidApi getWanAndroidList() {
        if (wanAndroidApi == null) {
            wanAndroidApi = getRetrofit( URL_WANANDROID ).create( WanAndroidApi.class );
        }
        return wanAndroidApi;
    }

    /**
     * 获取360图片接口的 请求的操作。
     */
    public static TngouApi getTngou() {
        if (tngouApi == null) {
            tngouApi = getRetrofit( URL_TNGOU ).create( TngouApi.class );
        }
        return tngouApi;
    }

    /**
     * 装逼图片的接口
     */
    public static ZhuangbiApi getZhuangbi(){
        if (zhuangbiApi==null){
            zhuangbiApi = getRetrofit( URL_ZHUANG ).create( ZhuangbiApi.class );
        }
        return zhuangbiApi;
    }

    @NonNull
    private static Retrofit getRetrofit(String url) {
        if (httpClientBuilder == null) {
            initOkHttp();
        }
        return new Retrofit.Builder()//
                .client( httpClientBuilder.build() )//
                .baseUrl( url )//
                .addConverterFactory( GsonConverterFactory.create() )//
                .addCallAdapterFactory( RxJavaCallAdapterFactory.create() )//
                .build();
    }

}
