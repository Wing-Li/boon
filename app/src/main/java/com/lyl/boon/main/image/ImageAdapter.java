package com.lyl.boon.main.image;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.lyl.boon.R;
import com.lyl.boon.api.img.ImgUtils;
import com.lyl.boon.app.MyApp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * Wing_Li
 * 2016/4/15.
 */
public class ImageAdapter extends PagerAdapter {
    Context mContext;

    List<String> imgs;

    public ImageAdapter(Context context, List<String> imgs) {
        this.mContext = context;
        this.imgs = imgs;
    }

    @Override
    public int getCount() {
        return imgs == null ? 0 : imgs.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 需要移除该View，避免View重复加载。
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    /**
     * 加载View
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final String imgUrl = imgs.get(position);

//        View view = LayoutInflater.from( mContext ).inflate( R.layout.item_image_show, container );
//        PhotoView img = (PhotoView) view.findViewById( R.id.item_list_image );

        PhotoView photoView = new PhotoView(mContext);
        photoView.setAdjustViewBounds(true);
        ImgUtils.load(mContext, imgUrl, photoView);
        photoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (TextUtils.isEmpty(imgUrl)){
                    Toast.makeText(mContext.getApplicationContext(), R.string.img_err,Toast.LENGTH_SHORT).show();
                    return false;
                }
                new AlertDialog.Builder(mContext).setTitle("保存图片")//
                        .setMessage("您要将图片保存到本地吗？")//
                        .setNegativeButton("保存", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ImgUtils.getBitmap(mContext, imgUrl, simpleTarget);
                            }
                        })//
                        .setPositiveButton("取消", null).create().show();
                return true;
            }
        });

        ((ViewPager) container).addView(photoView);
        return photoView;
    }

    private SimpleTarget simpleTarget = new SimpleTarget<Bitmap>() {
        @Override
        public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
            String path = MyApp.getAppPath();
            String imgName = "boon_" + System.currentTimeMillis() + ".jpg";
            File file = new File(path, imgName);
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(file);
                resource.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
            Toast.makeText(mContext, "保存成功", Toast.LENGTH_SHORT).show();
        }
    };
}
