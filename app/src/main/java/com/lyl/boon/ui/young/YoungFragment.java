package com.lyl.boon.ui.young;

import android.content.Intent;
import android.view.View;

import com.lyl.boon.R;
import com.lyl.boon.net.Network;
import com.lyl.boon.net.entity.SuperGalleryEntity;
import com.lyl.boon.ui.base.fragment.BaseRecyclerFragment;
import com.lyl.boon.ui.image.ImageActivity;
import com.lyl.boon.utils.MyUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 妹子
 */
public class YoungFragment extends BaseRecyclerFragment<SuperGalleryEntity.ListBean> {

    private ArrayList<String> mImgs;
    private ArrayList<String> mLoaded;

    @Override
    protected void initListType() {
        mListType = BaseRecyclerFragment.TYPE_STAG_V;
    }

    @Override
    protected void initData() {
        mData = new ArrayList<SuperGalleryEntity.ListBean>();
        mAdapter = new YongAdapter(getHolder(), mData, R.layout.item_image_v);
        mLoaded = new ArrayList<>();
    }

    @Override
    protected void setSubscribe(Observer observer) {
        int page = MyUtils.random(21);
        // qingchun   jipin  taiwan
        subscription = Network.getZhaiNanApi().getGalleryList("taiwan", page).map(superGalleryEntity -> {
            List<SuperGalleryEntity.ListBean> listBeanList = new ArrayList<>();
            if (superGalleryEntity.getList().size() > 0) {
                SuperGalleryEntity.ListBean listBean;
                for (int i = 0; i < 10; i++) {

                    int galleryIndex = MyUtils.random(20);
                    int sizeIndex = MyUtils.random(15);
                    SuperGalleryEntity.ListBean bean = superGalleryEntity.getList().get(galleryIndex);

                    // https://image.haonvshen.com/zb_users/upload/2021/09/20210914005301163155198175401.jpg
                    // https://image.haonvshen.com/zb_users/upload/2021/09/20210914005303163155198354159.jpg
                    String imgUrl = bean.getQhimg_url();
                    if (!mLoaded.contains(imgUrl)) {
                        listBean = new SuperGalleryEntity.ListBean();
                        listBean.setQhimg_url(imgUrl);
                        listBean.setQhimg_thumb_url(imgUrl);
                        listBeanList.add(listBean);

                        mLoaded.add(imgUrl);
                    }
                }
            }
            return listBeanList;
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

    @Override
    protected void ItemClickListener(View itemView, int viewType, int position) {
        mData = mAdapter.getList();

        if (mImgs == null) mImgs = new ArrayList<>();
        mImgs.clear();

        for (SuperGalleryEntity.ListBean entity : mData) {
            mImgs.add(entity.getQhimg_url());
        }

        Intent intent = ImageActivity.getIntent(getHolder(), mImgs, position);
        startActivity(intent);
        getHolder().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}
