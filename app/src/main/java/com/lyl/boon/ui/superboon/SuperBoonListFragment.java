package com.lyl.boon.ui.superboon;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lyl.boon.R;
import com.lyl.boon.net.Network;
import com.lyl.boon.net.entity.SuperGalleryEntity;
import com.lyl.boon.ui.base.fragment.BaseRecyclerFragment;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 超级福利的列表页面
 */
public class SuperBoonListFragment extends BaseRecyclerFragment<SuperGalleryEntity.ListBean> {

    /**
     * 当前页面显示的分类id
     */
    private int typeId;

    /**
     * 每页显示多少个
     */
    private static final int ROWS = 20;

    @Override
    public void onAttach(Context context) {
        super.onAttach( context );
        typeId = getArguments().getInt( SuperBoonFragment.SUPER_TYPE );
    }

    @Override
    protected void initListType() {
        mListType = BaseRecyclerFragment.TYPE_STAG_V;
    }

    @Override
    protected void initData() {
        mData = new ArrayList<SuperGalleryEntity.ListBean>();
        mAdapter = new SuperBoonListAdapter( getHolder(), mData, R.layout.item_image_v );
    }

    @Override
    protected void setSubscribe(Observer observer) {
        int count = page * ROWS;
        Network.getTngou().getGalleryList( typeId, count ).map(new Func1<SuperGalleryEntity, List<SuperGalleryEntity.ListBean>>() {

            @Override
            public List<SuperGalleryEntity.ListBean> call(SuperGalleryEntity superGalleryEntity) {
                if (superGalleryEntity.getCount() > 0){
                    return superGalleryEntity.getList();
                }
                return null;
            }
        }).subscribeOn( Schedulers.io() ).observeOn( AndroidSchedulers.mainThread() ).subscribe( observer );
    }

    @Override
    protected void ItemClickListener(View itemView, int viewType, int position) {
        SuperGalleryEntity.ListBean galleryEntiry = (SuperGalleryEntity.ListBean) mAdapter.getItem( position );

        Intent intent = new Intent( getHolder(), SuperGalleryActivity.class );
        Bundle bundle = new Bundle();
        bundle.putInt( "menu", typeId );
        bundle.putString( "id", galleryEntiry.getId() );
        bundle.putString( "title", galleryEntiry.getGroup_title() );
        intent.putExtra( "budele", bundle );
        startActivity( intent );
        getHolder().overridePendingTransition( R.anim.fade_in, R.anim.fade_out );
    }

}
