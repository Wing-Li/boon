package com.lyl.boon.ui.superboon;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.lyl.boon.R;
import com.lyl.boon.net.Network;
import com.lyl.boon.net.entity.SuperImageEntirty;
import com.lyl.boon.net.entity.SuperImageEntirty.ListBean;
import com.lyl.boon.ui.base.fragment.BaseRecyclerFragment;
import com.lyl.boon.ui.image.ImageActivity;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 超级福利 点击之后 进入图库的页面
 */
public class SuperGalleryFragment extends BaseRecyclerFragment<ListBean> {

    /**
     * 当前页面显示的分类id
     */
    private int galleryId;
    private String title;

    /**
     * 每页显示多少个
     */
    private static final int ROWS = 20;

    private ArrayList<String> imgs;

    /**
     * 是否加载过
     */
    private boolean isLoaded;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle bundle = getArguments();
        galleryId = bundle.getInt("id");
        title = bundle.getString("title");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (TextUtils.isEmpty(title)) {
            title = getString(R.string.app_name);
        }
        setTitle(title);

        mSwipeRefreshLayout.setEnabled(false);
    }

    @Override
    protected void initListType() {
        mListType = BaseRecyclerFragment.TYPE_STAG_H;
    }

    @Override
    protected void initData() {
        mData = new ArrayList<ListBean>();
        mAdapter = new SuperGalleryAdapter(getHolder(), mData, R.layout.item_image_h);
    }

    @Override
    protected void setSubscribe() {
        //TODO
        //这里设计不太合理
        //因为 父类 ，无法判断 下一次是否还有数据，每一次到这里都会重复加载数据。
        //提供接口的服务器并没有判断，下一次是否还有数据。
        if (isLoaded) {
            setRefreshing(false);
            return;
        }

        subscription = Network.getTngou().getGalleryInfo(galleryId).map(new Func1<SuperImageEntirty, List<ListBean>>() {
            @Override
            public List<ListBean> call(SuperImageEntirty superImageEntirty) {
                if (superImageEntirty.isStatus()) {
                    List<ListBean> listBeen = superImageEntirty.getList();
                    isLoaded = true;
                    return listBeen;
                }
                return null;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

    @Override
    protected void ItemClickListener(View itemView, int viewType, int position) {
        super.ItemClickListener(itemView, viewType, position);
        mData = mAdapter.getList();

        if (imgs == null) imgs = new ArrayList<>();
        imgs.clear();

        for (ListBean listBean : mData) {
            imgs.add(Network.TNGOU_IMG + listBean.getSrc());
        }

        Intent intent = ImageActivity.getIntent(getHolder(), imgs, position);
        startActivity(intent);
        getHolder().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}
