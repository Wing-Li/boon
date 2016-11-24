package com.lyl.boon.main.superboon;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lyl.boon.R;
import com.lyl.boon.api.net.Network;
import com.lyl.boon.entity.BaseTngouEntiry;
import com.lyl.boon.entity.SuperGalleryEntiry;
import com.lyl.boon.framework.base.fragment.BaseRecyclerFragment;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 超级福利的列表页面
 */
public class SuperBoonListFragment extends BaseRecyclerFragment<SuperGalleryEntiry> {

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
        mData = new ArrayList<SuperGalleryEntiry>();
        mAdapter = new SuperBoonListAdapter( getHolder(), mData, R.layout.item_image_v );
    }

    @Override
    protected void setSubscribe() {
        subscription = Network.getTngou().getGalleryList( typeId, page, ROWS ).map( new Func1<BaseTngouEntiry<List<SuperGalleryEntiry>>,
                List<SuperGalleryEntiry>>() {
            @Override
            public List<SuperGalleryEntiry> call(BaseTngouEntiry<List<SuperGalleryEntiry>> listBaseTngouEntiry) {
                if (listBaseTngouEntiry.isStatus()) {
                    return listBaseTngouEntiry.getTngou();
                }
                return null;
            }
        } ).subscribeOn( Schedulers.io() ).observeOn( AndroidSchedulers.mainThread() ).subscribe( observer );
    }

    @Override
    protected void ItemClickListener(View itemView, int viewType, int position) {
        SuperGalleryEntiry galleryEntiry = (SuperGalleryEntiry) mAdapter.getItem( position );

        Intent intent = new Intent( getHolder(), SuperGalleryActivity.class );
        Bundle bundle = new Bundle();
        bundle.putInt( "id", galleryEntiry.getId() );
        bundle.putString( "title", galleryEntiry.getTitle() );
        intent.putExtra( "budele", bundle );
        startActivity( intent );
        getHolder().overridePendingTransition( R.anim.fade_in, R.anim.fade_out );
    }

}
