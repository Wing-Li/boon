package com.lyl.boon.ui.image;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lyl.boon.R;
import com.lyl.boon.app.MyApp;
import com.lyl.boon.utils.FileUtils;
import com.lyl.boon.utils.ImgUtils;

import java.io.File;
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
                new AlertDialog.Builder(mContext).setTitle(mContext.getString(R.string.image_save))//
                        .setMessage(mContext.getString(R.string.image_save_msg))//
                        .setNegativeButton(mContext.getString(R.string.save), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ImgUtils.downloadImg(mContext, imgUrl, new ImgUtils.DownloadImage() {
                                    @Override
                                    public void downloadImage(File imgFile) {
                                        if (imgFile != null){
                                            String path = MyApp.getAppPath();
                                            String imgName = "boon_" + System.currentTimeMillis() + ".jpg";
                                            File file = new File(path, imgName);

                                            // 移动下载的图片到 目标路径
                                            boolean moveFile = FileUtils.moveFile(imgFile.getAbsolutePath(), file.getAbsolutePath());

                                            if (moveFile){
                                                mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
                                                Toast.makeText(mContext.getApplicationContext(), R.string.save_success, Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(mContext.getApplicationContext(), R.string.save_fail, Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(mContext.getApplicationContext(), R.string.save_fail, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        })//
                        .setPositiveButton(mContext.getString(R.string.cancel), null).create().show();
                return true;
            }
        });

        ((ViewPager) container).addView(photoView);
        return photoView;
    }

}
