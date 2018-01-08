package com.lyl.boon.ui.young;

import android.content.Intent;
import android.view.View;

import com.lyl.boon.R;
import com.lyl.boon.net.Network;
import com.lyl.boon.net.entity.BaseGankEntiry;
import com.lyl.boon.net.entity.GankDataEntity;
import com.lyl.boon.ui.base.fragment.BaseRecyclerFragment;
import com.lyl.boon.ui.image.ImageActivity;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 妹子
 */
public class YoungFragment extends BaseRecyclerFragment<GankDataEntity> {

    private ArrayList<String> imgs;

    @Override
    protected void initListType() {
        mListType = BaseRecyclerFragment.TYPE_STAG_V;
    }

    @Override
    protected void initData() {
        mData = new ArrayList<GankDataEntity>();
        mAdapter = new YongAdapter( getHolder(), mData, R.layout.item_image_v );
    }

    @Override
    protected void setSubscribe() {
        subscription = Network.getGankMenuList().getGankList( "福利", page ).map( new Func1<BaseGankEntiry<List<GankDataEntity>>, List<GankDataEntity>>() {
            @Override
            public List<GankDataEntity> call(BaseGankEntiry<List<GankDataEntity>> baseGankEntiry) {
                if (!baseGankEntiry.isError()) {
                    return baseGankEntiry.getResults();
                }
                return null;
            }
        } ).subscribeOn( Schedulers.io() ).observeOn( AndroidSchedulers.mainThread() ).subscribe( observer );
    }

    @Override
    protected void ItemClickListener(View itemView, int viewType, int position) {
        super.ItemClickListener( itemView, viewType, position );
        mData = mAdapter.getList();

        if (imgs == null) imgs = new ArrayList<>();
        imgs.clear();

        for (GankDataEntity entity : mData) {
            imgs.add( entity.getUrl() );
        }

        Intent intent = ImageActivity.getIntent(getHolder(), imgs, position);
        startActivity(intent);
        getHolder().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}
