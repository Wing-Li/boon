package com.lyl.boon.ui.adapter.base;

import android.content.Context;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * 本着二次封装的原则，把使用的这个库在封装一遍
 * Wing_Li
 * 2016/4/6.
 */
public class MyBaseAdapter<T> extends SuperAdapter<T> {


    public MyBaseAdapter(Context context, List<T> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int position, T item) {

    }

}
