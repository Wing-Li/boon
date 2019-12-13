package com.lyl.boon.ui.favorite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lyl.boon.R;
import com.lyl.boon.net.LeanCloudCallBack;
import com.lyl.boon.net.LeanCloudNet;
import com.lyl.boon.net.entity.FavoriteEntity;
import com.lyl.boon.ui.base.fragment.BaseRecyclerFragment;
import com.lyl.boon.ui.web.Html5Activity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FavoriteFragment extends BaseRecyclerFragment<FavoriteEntity> {

    @Override
    protected void initListType() {
        mListType = BaseRecyclerFragment.TYPE_LIST;
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        mAdapter = new FavoriteAdapter(getHolder(), mData, R.layout.item_favorite);
    }

    @Override
    protected void setSubscribe(Observer observer) {
        subscription = Observable.create(new Observable.OnSubscribe<List<FavoriteEntity>>() {

            @Override
            public void call(final Subscriber<? super List<FavoriteEntity>> subscriber) {
                LeanCloudNet.INSTANCE.fetchFavorite(new LeanCloudCallBack<List<FavoriteEntity>>() {
                    @Override
                    public void onSuccess(List<FavoriteEntity> favoriteEntities) {
                        subscriber.onNext(favoriteEntities);
                    }

                    @Override
                    public void onError(int code, @NotNull String msg, @Nullable Exception e) {
                    }
                });
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

    @Override
    protected void ItemClickListener(View itemView, int viewType, int position) {
        FavoriteEntity entity = (FavoriteEntity) mAdapter.getItem(position);
        //避免内存泄露，开启一个新的进程来加载WebView。
        Intent intent = new Intent(getContext(), Html5Activity.class);
        Bundle bundle = new Bundle();
        bundle.putString("desc", entity.getTitle());
        bundle.putString("author", entity.getAuthor());
        bundle.putString("url", entity.getUrl());
        intent.putExtra("bundle", bundle);
        startActivity(intent);
    }

}
