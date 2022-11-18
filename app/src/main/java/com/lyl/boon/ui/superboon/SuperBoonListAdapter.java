package com.lyl.boon.ui.superboon;

import android.content.Context;
import android.widget.ImageView;

import com.lyl.boon.R;
import com.lyl.boon.net.entity.SuperGalleryEntity;
import com.lyl.boon.ui.base.apdter.MyBaseAdapter;
import com.lyl.boon.utils.ImgUtils;

import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * Wing_Li
 * 2016/4/14.
 */
public class SuperBoonListAdapter extends MyBaseAdapter<SuperGalleryEntity.ListBean> {

    private Context context;

    public SuperBoonListAdapter(Context context, List<SuperGalleryEntity.ListBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int position, SuperGalleryEntity.ListBean item) {
        super.onBind(holder, viewType, position, item);
        ImgUtils.load(context, item.getQhimg_thumb_url(), (ImageView) holder.getView(R.id.item_image), item.getQhimg_width(), item.getQhimg_height());
    }

}
