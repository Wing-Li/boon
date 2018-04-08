package com.lyl.boon.ui.wanandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lyl.boon.R;
import com.lyl.boon.net.Network;
import com.lyl.boon.net.entity.WanAndroidEntity;
import com.lyl.boon.ui.base.fragment.BaseRecyclerFragment;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Wing_Li
 * 2016/4/5.
 */
public class WanAndroidFragment extends BaseRecyclerFragment<WanAndroidEntity.DataBean.DatasBean> {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        page = 0;
    }

    @Override
    protected void initListType() {
        mListType = BaseRecyclerFragment.TYPE_LIST;
    }

    @Override
    protected void initData() {
        mData = new ArrayList<WanAndroidEntity.DataBean.DatasBean>();
        mAdapter = new WanAndroidAdapter(getHolder(), mData, R.layout.item_develop);
    }

    @Override
    protected void setSubscribe(Observer observer) {
        subscription = Network.getWanAndroidList().getWanAndroidList(page).map(new Func1<WanAndroidEntity, List<WanAndroidEntity.DataBean.DatasBean>>() {

            @Override
            public List<WanAndroidEntity.DataBean.DatasBean> call(WanAndroidEntity wanAndroidEntity) {
                WanAndroidEntity.DataBean data = wanAndroidEntity.getData();
                if (data != null && data.getDatas().size() > 0){
                    return data.getDatas();
                }
                return null;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

    @Override
    protected void ItemClickListener(View itemView, int viewType, int position) {
        WanAndroidEntity.DataBean.DatasBean gankDataEntity = (WanAndroidEntity.DataBean.DatasBean) mAdapter.getItem(position);
        //避免内存泄露，开启一个新的进程来加载WebView。
        Intent intent = new Intent("com.lyl.boon.main.web.Html5Activity");
        Bundle bundle = new Bundle();
        bundle.putString("desc", gankDataEntity.getTitle());
        bundle.putString("url", gankDataEntity.getLink());
        intent.putExtra("bundle", bundle);
        startActivity(intent);
    }

}
