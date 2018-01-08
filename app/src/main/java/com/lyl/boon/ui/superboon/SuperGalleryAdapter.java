package com.lyl.boon.ui.superboon;

import android.content.Context;
import android.widget.ImageView;

import com.lyl.boon.R;
import com.lyl.boon.utils.ImgUtils;
import com.lyl.boon.net.Network;
import com.lyl.boon.net.entity.SuperImageEntirty;
import com.lyl.boon.ui.base.apdter.MyBaseAdapter;

import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * Wing_Li
 * 2016/4/14.
 */
public class SuperGalleryAdapter extends MyBaseAdapter<SuperImageEntirty.ListBean> {

    private Context context;

    public SuperGalleryAdapter(Context context, List<SuperImageEntirty.ListBean> items, int layoutResId) {
        super( context, items, layoutResId );
        this.context = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int position, SuperImageEntirty.ListBean item) {
        super.onBind( holder, viewType, position, item );
        ImgUtils.loadF( context, Network.TNGOU_IMG + item.getSrc(), (ImageView) holder.getView( R.id.item_image_h ) );
    }

}
