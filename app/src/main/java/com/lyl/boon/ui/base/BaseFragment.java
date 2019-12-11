package com.lyl.boon.ui.base;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextSwitcher;
import android.widget.Toast;

import com.lyl.boon.R;

import butterknife.ButterKnife;
import rx.Subscription;

/**
 * Created by dongjunkun on 2016/2/2.
 */
public abstract class BaseFragment extends Fragment {

    protected View rootView;
    private BaseActivity holder;
    protected Subscription subscription;

    @Override
    public void onAttach(Context context) {
        super.onAttach( context );
        if (context instanceof BaseActivity) {
            holder = (BaseActivity) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate( getLayoutResource(), container, false );
            ButterKnife.bind( this, rootView );
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView( rootView );
        }

        ButterKnife.bind( this, rootView );

        return rootView;
    }

    protected abstract int getLayoutResource();

    public String getName() {
        return BaseFragment.class.getName();
    }


    public BaseActivity getHolder() {
        if (holder == null) {
            throw new IllegalArgumentException( "the acticity must be extends BaseActivity" );
        }
        return holder;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind( this );
        unsubscribe();
    }

    protected void unsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
            subscription = null;
        }
    }

    protected  void setTitle(String title){
        View view = getHolder().getSupportActionBar().getCustomView();
        TextSwitcher titleV = (TextSwitcher) view.findViewById(R.id.action_bar_title_txt);
        titleV.setText(title);
    }

    protected void showToast(String str) {
        Toast.makeText(getHolder().getApplicationContext(), str, Toast.LENGTH_SHORT ).show();
    }

    protected void showToast(int res) {
        Toast.makeText(getHolder().getApplicationContext(), res, Toast.LENGTH_SHORT ).show();
    }

}
