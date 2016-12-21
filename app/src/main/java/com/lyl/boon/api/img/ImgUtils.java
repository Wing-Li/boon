package com.lyl.boon.api.img;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.lyl.boon.R;

/**
 * Wing_Li
 * 2016/4/14.
 */
public class ImgUtils {

    private static final int TYPE_NULL = 0;
    private static final int TYPE_FITCENTER = 1;
    private static final int TYPE_CENTERCROP = 2;
    private static final int TYPE_GIF = 3;


    private static final int placeholderRes = R.drawable.gary_bg;
    private static final int errorRes = R.drawable.error_img;

    /**
     * @param context
     * @param url               图片的地址
     * @param imageView         ImageView
     * @param thumbnail         简单的缩略图:0.1f 作为参数，Glide 将会显示原始图像的10%的大小
     * @param scaleType         SCALETYPE_NULL:没有类型，
     * @param diskCacheStrategy DiskCacheStrategy.NONE 什么都不缓存，就像刚讨论的那样
     *                          DiskCacheStrategy.SOURCE 仅仅只缓存原来的全分辨率的图像。在我们上面的例子中，将会只有一个1000x1000 像素的图片
     *                          DiskCacheStrategy.RESULT 仅仅缓存最终的图像，即，降低分辨率后的（或者是转换后的）
     *                          DiskCacheStrategy.ALL 缓存所有版本的图像（默认行为）
     */
    public static void load(Context context, String url, ImageView imageView, float thumbnail, int scaleType,
                            DiskCacheStrategy diskCacheStrategy) {
        if (scaleType == TYPE_NULL) {
            Glide.with(context).load(url).placeholder(placeholderRes).error(errorRes).thumbnail(thumbnail)
                    .diskCacheStrategy(diskCacheStrategy).into(imageView);
        } else if (scaleType == TYPE_FITCENTER) {
            Glide.with(context).load(url).placeholder(placeholderRes).error(errorRes).thumbnail(thumbnail)
                    .diskCacheStrategy(diskCacheStrategy).fitCenter().into(imageView);
        } else if (scaleType == TYPE_CENTERCROP) {
            Glide.with(context).load(url).placeholder(placeholderRes).error(errorRes).thumbnail(thumbnail)
                    .diskCacheStrategy(diskCacheStrategy).centerCrop().into(imageView);
        } else if (scaleType == TYPE_GIF) {
            Glide.with(context).load(url).asGif().placeholder(placeholderRes).error(errorRes).thumbnail(thumbnail)
                    .diskCacheStrategy(diskCacheStrategy).fitCenter().into(imageView);
        }
    }

    public static void load(Context context, int url, ImageView imageView) {
        Glide.with(context).load(url).placeholder(placeholderRes).error(errorRes).fitCenter().into(imageView);
    }

    public static void load(Context context, String url, ImageView imageView) {
        load(context, url, imageView, 0.2f, TYPE_NULL, DiskCacheStrategy.RESULT);
    }

    public static void loadF(Context context, String url, ImageView imageView) {
        load(context, url, imageView, 0.2f, TYPE_FITCENTER, DiskCacheStrategy.RESULT);
    }

    public static void loadC(Context context, String url, ImageView imageView) {
        load(context, url, imageView, 0.2f, TYPE_CENTERCROP, DiskCacheStrategy.RESULT);
    }

    public static void loadGif(Context context, String url, ImageView imageView) {
        load(context, url, imageView, 0.2f, TYPE_GIF, DiskCacheStrategy.RESULT);
    }

    public static void getBitmap(Context context, String url, Target simpleTarget) {
        Glide.with(context).load(url).asBitmap().into(simpleTarget);
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
    public static void cleanAll(Context context) {
        clearDiskCache(context);
        clearMemory(context);
    }

}
