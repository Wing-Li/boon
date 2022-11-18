package com.lyl.boon.ui.young;

import android.content.Context;
import android.widget.ImageView;

import com.lyl.boon.R;
import com.lyl.boon.net.entity.SuperGalleryEntity;
import com.lyl.boon.utils.ImgUtils;
import com.lyl.boon.ui.base.apdter.MyBaseAdapter;

import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * Wing_Li
 * 2016/4/14.
 */
public class YongAdapter extends MyBaseAdapter<SuperGalleryEntity.ListBean> {

    private Context context;

    public YongAdapter(Context context, List<SuperGalleryEntity.ListBean> items, int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int position, SuperGalleryEntity.ListBean item) {
        super.onBind(holder, viewType, position, item);
        ImgUtils.load(context, item.getQhimg_url(), (ImageView) holder.getView(R.id.item_image));
    }

}
