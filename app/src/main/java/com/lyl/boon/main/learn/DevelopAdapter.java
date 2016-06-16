package com.lyl.boon.main.learn;

import android.content.Context;

import com.lyl.boon.R;
import com.lyl.boon.entity.GankDataEntity;
import com.lyl.boon.framework.base.apdter.MyBaseAdapter;

import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * Wing_Li
 * 2016/4/7.
 */
public class DevelopAdapter extends MyBaseAdapter<GankDataEntity> {

    public DevelopAdapter(Context context, List<GankDataEntity> items, int layoutResId) {
        super(context, items, layoutResId);
    }


    @Override
    public void onBind(SuperViewHolder holder, int viewType, int position, GankDataEntity data) {
        super.onBind(holder, viewType, position, data);

        holder.setText(R.id.item_develop_title, data.getDesc());
    }
}
