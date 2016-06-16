package com.lyl.boon.main.joke;

import android.content.Context;
import android.widget.ImageView;

import com.lyl.boon.R;
import com.lyl.boon.api.img.ImgUtils;
import com.lyl.boon.entity.ZhuangbiEntiry;
import com.lyl.boon.framework.base.apdter.MyBaseAdapter;

import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * Wing_Li
 * 2016/4/14.
 */
public class JokeListAdapter extends MyBaseAdapter<ZhuangbiEntiry> {
    private Context context;

    public JokeListAdapter(Context context, List<ZhuangbiEntiry> items, int layoutResId) {
        super( context, items, layoutResId );
        this.context = context;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int position, ZhuangbiEntiry item) {
        super.onBind( holder, viewType, position, item );

        holder.setText( R.id.item_grid_title, item.getDescription() );

        String url = item.getImage_url();

        boolean gif = url.endsWith( "gif" );
        if (gif) {
            ImgUtils.loadGif( context, url, (ImageView) holder.getView( R.id.item_grid_img ) );
        } else {
            ImgUtils.loadC( context, url, (ImageView) holder.getView( R.id.item_grid_img ) );
        }
    }
}
