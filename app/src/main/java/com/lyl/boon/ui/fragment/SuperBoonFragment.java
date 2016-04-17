package com.lyl.boon.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.lyl.boon.R;
import com.lyl.boon.api.net.Network;
import com.lyl.boon.entity.BaseTngouEntiry;
import com.lyl.boon.entity.SuperMenuEntiry;
import com.lyl.boon.ui.fragment.basefragment.BaseMenuFragment;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Wing_Li
 * 2016/4/1.
 */
public class SuperBoonFragment extends BaseMenuFragment {

    public static final String SUPER_TYPE = "type";

    @Override
    protected void setFragment() {
        subscription = Network.getTngou().getMenu().map( new Func1<BaseTngouEntiry<List<SuperMenuEntiry>>, List<SuperMenuEntiry>>() {
            @Override
            public List<SuperMenuEntiry> call(BaseTngouEntiry<List<SuperMenuEntiry>> listBaseTngouEntiry) {
                if (listBaseTngouEntiry.isStatus()) {
                    return listBaseTngouEntiry.getTngou();
                }
                return null;
            }
        } ).subscribeOn( Schedulers.io() ).observeOn( AndroidSchedulers.mainThread() ).subscribe( menuoObservable );
    }

    private void addFragment(String title, int id) {
        mTitles.add( title );

        Fragment fragment = new SuperBoonListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt( SUPER_TYPE, id );
        fragment.setArguments( bundle );
        mFragments.add( fragment );
    }

    Observer<List<SuperMenuEntiry>> menuoObservable = new Observer<List<SuperMenuEntiry>>() {

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            showToast( R.string.load_error );
        }

        @Override
        public void onNext(List<SuperMenuEntiry> superMenuEntiries) {
            if ((superMenuEntiries != null) && (superMenuEntiries.size() > 0)) {
                for (SuperMenuEntiry entiry : superMenuEntiries) {
                    addFragment( entiry.getTitle(), entiry.getId() );
                }

                mViewPageAdpater.setFragments( mFragments, mTitles );
                setTabLayout();
            }
        }
    };


}
