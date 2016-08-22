package com.lyl.boon.main.image;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lyl.boon.R;
import com.lyl.boon.api.img.ImgUtils;

import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * Wing_Li
 * 2016/4/15.
 */
public class ImageAdapter extends PagerAdapter {

    List<String> imgs;

    Context mContext;

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
        ((ViewPager) container).removeView( (View) object );
    }

    /**
     * 加载View
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        String imgUrl = imgs.get( position );
        View view = LayoutInflater.from( mContext ).inflate( R.layout.item_image_show, container );
        PhotoView img = (PhotoView) view.findViewById( R.id.item_list_image );
        ImgUtils.load( mContext, imgUrl, img );

        ((ViewPager) container).addView( view );
        return view;
    }
}
