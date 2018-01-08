package com.lyl.boon.ui.learn;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.lyl.boon.R;
import com.lyl.boon.net.Network;
import com.lyl.boon.net.entity.BaseGankEntiry;
import com.lyl.boon.net.entity.GankDataEntity;
import com.lyl.boon.ui.base.fragment.BaseRecyclerFragment;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 当有多个加载页面的时候，可以把 上拉刷新。下拉加载，在封装一个BaseLoadFragment；留下 Data 和 ItemView 即可。之后使用直接继承
 * Wing_Li
 * 2016/4/5.
 */
public class LearnListFragment extends BaseRecyclerFragment<GankDataEntity> {

    /**
     * 当前页面显示的内容类型： LearnFragment.TYPE
     **/
    private String type;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle bundle = getArguments();
        type = bundle.getString(LearnFragment.LEARN_TYPE);
    }

    @Override
    protected void initListType() {
        mListType = BaseRecyclerFragment.TYPE_LIST;
    }

    @Override
    protected void initData() {
        mData = new ArrayList<GankDataEntity>();
        mAdapter = new DevelopAdapter(getHolder(), mData, R.layout.item_develop);
        View itemHrader = LayoutInflater.from(getHolder()).inflate(R.layout.item_header, null);
        mAdapter.addHeaderView(itemHrader);
    }

    @Override
    protected void setSubscribe() {
        subscription = Network.getGankMenuList().getGankList(type, page).map(new Func1<BaseGankEntiry<List<GankDataEntity>>, List<GankDataEntity>>() {
            @Override
            public List<GankDataEntity> call(BaseGankEntiry<List<GankDataEntity>> baseGankEntiry) {
                if (!baseGankEntiry.isError()) {
                    return baseGankEntiry.getResults();
                }
                return null;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

    @Override
    protected void ItemClickListener(View itemView, int viewType, int position) {
        GankDataEntity gankDataEntity = (GankDataEntity) mAdapter.getItem(position - 1);
        //避免内存泄露，开启一个新的进程来加载WebView。
        Intent intent = new Intent("com.lyl.boon.main.web.Html5Activity");
        Bundle bundle = new Bundle();
        bundle.putString("desc", gankDataEntity.getDesc());
        bundle.putString("url", gankDataEntity.getUrl());
        intent.putExtra("bundle", bundle);
        startActivity(intent);
    }

}
