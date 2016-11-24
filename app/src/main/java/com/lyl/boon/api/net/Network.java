package com.lyl.boon.api.net;

import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
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
     * 天狗的图片请求地址
     */
    private static final String URL_TNGOU = "http://www.tngou.net/tnfs/api/";
    /**
     * 装逼图片地址
     */
    private static final String URL_ZHUANG = "http://zhuangbi.info/";

    /**
     * 天狗图片 访问图片时候的地址
     */
    public static final String TNGOU_IMG = "http://tnfs.tngou.net/img";

    /**
     * 设置超时的时间
     **/
    private static final int DEFAULT_TIMEOUT = 5;

    private static OkHttpClient.Builder httpClientBuilder;
    private static GankApi gankApi;
    private static TngouApi tngouApi;
    private static ZhuangbiApi zhuangbiApi;


    private Network() {
    }

    private static void initOkHttp() {
        httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout( DEFAULT_TIMEOUT, TimeUnit.SECONDS );
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
     * 获取天狗接口的 请求的操作。
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
