package com.lyl.boon.ui.favorite

import android.content.Context
import com.lyl.boon.R
import com.lyl.boon.net.entity.FavoriteEntity
import com.lyl.boon.ui.base.apdter.MyBaseAdapter
import com.lyl.boon.utils.DateUtils
import org.byteam.superadapter.internal.SuperViewHolder

/**
 * Wing_Li
 * 2016/4/7.
 */
class FavoriteAdapter(context: Context?, items: List<FavoriteEntity?>?, layoutResId: Int)
    : MyBaseAdapter<FavoriteEntity?>(context, items, layoutResId) {

    override fun onBind(holder: SuperViewHolder, viewType: Int, position: Int, data: FavoriteEntity?) {
        data?.let {
            holder.setText(R.id.item_favorite_title, it.title)
            holder.setText(R.id.item_favorite_author, it.author)
            holder.setText(R.id.item_favorite_date, DateUtils.formatDate(it.createAt))
        }
    }
}