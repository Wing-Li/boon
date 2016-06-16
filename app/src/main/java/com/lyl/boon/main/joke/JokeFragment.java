package com.lyl.boon.main.joke;

import android.view.View;

import com.lyl.boon.R;
import com.lyl.boon.api.net.Network;
import com.lyl.boon.entity.ZhuangbiEntiry;
import com.lyl.boon.framework.base.fragment.BaseRecyclerFragment;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func4;
import rx.schedulers.Schedulers;

/**
 * Wing_Li
 * 2016/4/1.
 */
public class JokeFragment extends BaseRecyclerFragment<ZhuangbiEntiry> {

    @Override
    protected void setSubscribe() {
        subscription = Observable.zip( Network.getZhuangbi().search( "可爱" ), Network.getZhuangbi().search( "110" ), Network.getZhuangbi().search( "在下" ),
                Network.getZhuangbi().search( "装逼" ), new Func4<List<ZhuangbiEntiry>, List<ZhuangbiEntiry>, List<ZhuangbiEntiry>, List<ZhuangbiEntiry>,
                        List<ZhuangbiEntiry>>() {
            @Override
            public List<ZhuangbiEntiry> call(List<ZhuangbiEntiry> zhuangbiEntiries, List<ZhuangbiEntiry> zhuangbiEntiries2, List<ZhuangbiEntiry>
                    zhuangbiEntiries3, List<ZhuangbiEntiry> zhuangbiEntiries4) {
                List<ZhuangbiEntiry> entiryList = new ArrayList<ZhuangbiEntiry>();
                entiryList.addAll( zhuangbiEntiries );
                entiryList.addAll( zhuangbiEntiries2 );
                entiryList.addAll( zhuangbiEntiries3 );
                entiryList.addAll( zhuangbiEntiries4 );
                return entiryList;
            }
        } ).subscribeOn( Schedulers.io() ).observeOn( AndroidSchedulers.mainThread() ).subscribe( observer );
    }

    @Override
    protected void initListType() {
        mListType = BaseRecyclerFragment.TYPE_GRID;
    }

    @Override
    protected void initData() {
        mData = new ArrayList<ZhuangbiEntiry>();
        mAdapter = new JokeListAdapter( getHolder(), mData, R.layout.item_grid );
    }

    @Override
    protected void ItemClickListener(View itemView, int viewType, int position) {

    }
}
