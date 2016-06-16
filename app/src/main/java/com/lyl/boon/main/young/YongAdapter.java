package com.lyl.boon.main.young;

import android.content.Context;
import android.widget.ImageView;

import com.lyl.boon.R;
import com.lyl.boon.api.img.ImgUtils;
import com.lyl.boon.entity.GankDataEntity;
import com.lyl.boon.framework.base.apdter.MyBaseAdapter;

import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * Wing_Li
 * 2016/4/14.
 */
public class YongAdapter extends MyBaseAdapter<GankDataEntity> {
    private Context context;
    public YongAdapter(Context context, List<GankDataEntity> items, int layoutResId) {
        super(context, items, layoutResId);
        this.context = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int position, GankDataEntity item) {
        super.onBind(holder, viewType, position, item);
        ImgUtils.loadF(context,item.getUrl(), (ImageView) holder.getView(R.id.item_image_v ));
    }
}
