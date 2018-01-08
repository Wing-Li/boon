package com.lyl.boon.ui.learn;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.lyl.boon.R;
import com.lyl.boon.net.entity.GankDataEntity;
import com.lyl.boon.ui.base.apdter.MyBaseAdapter;
import com.lyl.boon.utils.MyUtils;

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

        holder.setText(R.id.item_develop_title, data.getDesc().trim());

        String who = data.getWho();
        if (TextUtils.isEmpty(who)) who = mContext.getString(R.string.default_who);

        TextView whoTv = holder.getView(R.id.item_develop_tho);
        whoTv.setText(who.trim());
        whoTv.setTextColor(mContext.getResources().getColorStateList(MyUtils.getColors()));

        holder.setText(R.id.item_develop_date, " - " + data.getCreatedAt().substring(0, 10).trim());
    }

}
