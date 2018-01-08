package com.lyl.boon.ui.superboon;

import android.content.Context;
import android.widget.ImageView;

import com.lyl.boon.R;
import com.lyl.boon.utils.ImgUtils;
import com.lyl.boon.net.Network;
import com.lyl.boon.net.entity.SuperGalleryEntiry;
import com.lyl.boon.ui.base.apdter.MyBaseAdapter;

import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * Wing_Li
 * 2016/4/14.
 */
public class SuperBoonListAdapter extends MyBaseAdapter<SuperGalleryEntiry> {

    private Context context;

    public SuperBoonListAdapter(Context context, List<SuperGalleryEntiry> items, int layoutResId) {
        super( context, items, layoutResId );
        this.context = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int position, SuperGalleryEntiry item) {
        super.onBind( holder, viewType, position, item );
        String url = Network.TNGOU_IMG + item.getImg();
        ImgUtils.load( context, url, (ImageView) holder.getView( R.id.item_image_v ) );
    }

}
