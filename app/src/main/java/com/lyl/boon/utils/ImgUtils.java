package com.lyl.boon.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.lyl.boon.R;

import java.io.File;

/**
 * Wing_Li
 * 2016/4/14.
 */
public class ImgUtils {

    private static final int placeholderRes = R.drawable.bg_gary;
    private static final int errorRes = R.drawable.error_img;

    private static RequestOptions baseOptions = new RequestOptions()//
            .placeholder(placeholderRes)//
            .error(errorRes)//
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

//    DiskCacheStrategy.NONE 什么都不缓存
//    DiskCacheStrategy.DATA 仅仅只缓存原来的全分辨率的图像。
//    DiskCacheStrategy.RESOURCE 仅仅缓存最终的图像，即，降低分辨率后的（或者是转换后的）
//    DiskCacheStrategy.ALL 缓存所有版本的图像
//    DiskCacheStrategy.AUTOMATIC: 表示让Glide根据图片资源智能地选择使用哪一种缓存策略（默认选项）。

    /**
     * @param context
     * @param url       图片的地址
     * @param imageView ImageView
     * @param thumbnail 简单的缩略图:0.1f 作为参数，Glide 将会显示原始图像的10%的大小
     */
    public static void load(Context context, String url, ImageView imageView, float thumbnail) {
        // 加载GIF慢
        // https://github.com/bumptech/glide/issues/513#issuecomment-117690923、
//        Glide glide = Glide.get(context);
//        OkHttpUrlLoader.Factory factory = new OkHttpUrlLoader.Factory(Network.httpClient);
//
//        glide.register(GlideUrl.class, InputStream.class, factory);
        Glide.with(context).load(url).apply(baseOptions).thumbnail(thumbnail).into(imageView);
    }

    public static void load(Context context, int url, ImageView imageView) {
        Glide.with(context).load(url).apply(baseOptions).into(imageView);
    }

    public static void load(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).apply(baseOptions).into(imageView);
    }

    public static void load(Context context, String url, ImageView imageView, int w, int h) {
        if (w <= 0 || h <= 0) {
            load(context, url, imageView);
        } else {
            Glide.with(context).load(url).apply(baseOptions).apply(new RequestOptions().override(w, h)).into(imageView);
        }
    }

    /**
     * 加载圆形图片。
     */
    public static void loadCircle(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).apply(baseOptions).apply(RequestOptions.circleCropTransform()).into(imageView);
    }

    /**
     * 加载圆形图片。加载 uri ，一般用来加载本地图片
     */
    public static void loadCircle(Context context, Uri uri, ImageView imageView) {
        Glide.with(context).load(uri).apply(baseOptions).apply(RequestOptions.circleCropTransform()).into(imageView);
    }

    /**
     * 获取 Bitmap 图片
     *
     * @param context
     * @param url
     * @param simpleTarget
     */
    public static void getBitmap(Context context, String url, SimpleTarget simpleTarget) {
        Glide.with(context).asBitmap().load(url).into(simpleTarget);
    }

    /**
     * 下载图片
     *
     * @param context
     * @param url
     * @param downloadImage
     */
    public static void downloadImg(final Context context, final String url, final DownloadImage downloadImage) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FutureTarget<File> target = Glide.with(context)//
                            .asFile()//
                            .load(url)//
                            .submit();
                    final File file = target.get();

                    Handler handler = new Handler(context.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (file != null && file.exists()) {
                                downloadImage.downloadImage(file);
                            } else {
                                downloadImage.downloadImage(null);
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public interface DownloadImage {
        void downloadImage(File imgFile);
    }

    /**
     * 释放内存
     */
    public static void clearMemory(Context context) {
        Glide.get(context).clearMemory();
    }

    /**
     * 取消所有正在下载或等待下载的任务。
     */
    public static void cancelAllTasks(Context context) {
        Glide.with(context).pauseRequests();
    }

    /**
     * 恢复所有任务
     */
    public static void resumeAllTasks(Context context) {
        Glide.with(context).resumeRequests();
    }

    /**
     * 清除磁盘缓存
     */
    public static void clearDiskCache(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();
            }
        }).start();
    }

    /**
     * 清除所有缓存
     */
    public static void clearAll(Context context) {
        clearDiskCache(context);
        clearMemory(context);
    }
}
