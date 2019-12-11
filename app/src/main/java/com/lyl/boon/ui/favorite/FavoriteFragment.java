package com.lyl.boon.ui.favorite;

import android.view.View;

import com.lyl.boon.R;
import com.lyl.boon.net.LeanCloudCallBack;
import com.lyl.boon.net.LeanCloudNet;
import com.lyl.boon.net.entity.FavoriteEntity;
import com.lyl.boon.ui.base.fragment.BaseRecyclerFragment;

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

    }

}
