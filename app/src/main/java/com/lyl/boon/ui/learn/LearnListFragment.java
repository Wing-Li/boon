package com.lyl.boon.ui.learn;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.lyl.boon.R;
import com.lyl.boon.net.Network;
import com.lyl.boon.net.entity.WanAndroidEntity;
import com.lyl.boon.ui.base.fragment.BaseRecyclerFragment;
import com.lyl.boon.ui.web.Html5Activity;

import java.util.ArrayList;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 当有多个加载页面的时候，可以把 上拉刷新。下拉加载，在封装一个BaseLoadFragment；留下 Data 和 ItemView 即可。之后使用直接继承
 * Wing_Li
 * 2016/4/5.
 */
public class LearnListFragment extends BaseRecyclerFragment<WanAndroidEntity.DataBean.DatasBean> {

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
        mData = new ArrayList<>();
        mAdapter = new DevelopAdapter(getHolder(), mData, R.layout.item_develop);
        View itemHeader = LayoutInflater.from(getHolder()).inflate(R.layout.item_header, null);
        mAdapter.addHeaderView(itemHeader);
    }

    @Override
    protected void setSubscribe(Observer observer) {
        subscription = Network.getWanAndroidList().getWxArticleList(type, page).map(wanAndroidEntity -> {
            return wanAndroidEntity.getData().getDatas();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

    @Override
    protected void ItemClickListener(View itemView, int viewType, int position) {
        WanAndroidEntity.DataBean.DatasBean datasBean = (WanAndroidEntity.DataBean.DatasBean) mAdapter.getItem(position - 1);
        //避免内存泄露，开启一个新的进程来加载WebView。
        Intent intent = new Intent(getContext(), Html5Activity.class);
        Bundle bundle = new Bundle();
        bundle.putString("desc", datasBean.getTitle());
        bundle.putString("author", datasBean.getAuthor());
        bundle.putString("url", datasBean.getLink());
        intent.putExtra("bundle", bundle);
        startActivity(intent);
    }

}
