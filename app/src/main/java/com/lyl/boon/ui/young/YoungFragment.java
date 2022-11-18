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

    private ArrayList<String> imgs;

    @Override
    protected void initListType() {
        mListType = BaseRecyclerFragment.TYPE_STAG_V;
    }

    @Override
    protected void initData() {
        mData = new ArrayList<SuperGalleryEntity.ListBean>();
        mAdapter = new YongAdapter(getHolder(), mData, R.layout.item_image_v);
    }

    @Override
    protected void setSubscribe(Observer observer) {
        int page = MyUtils.random(23);
        subscription = Network.getZhaiNanApi().getGalleryList("qingchun", page).map(superGalleryEntity -> {
            List<SuperGalleryEntity.ListBean> listBeanList = new ArrayList<>();
            if (superGalleryEntity.getList().size() > 0) {
                SuperGalleryEntity.ListBean listBean;
                for (int i = 0; i < 10; i++) {
                    listBean = new SuperGalleryEntity.ListBean();
                    int galleryIndex = MyUtils.random(20);
                    int sizeIndex = MyUtils.random(30);
                    SuperGalleryEntity.ListBean bean = superGalleryEntity.getList().get(galleryIndex);
                    // https://img.buuxk.com:85/gallery/28195/36882/cover/0.jpg
                    String imgUrl = bean.getQhimg_url()
                            .replace("img", "t1")
                            .replace("cover/0.jpg", "s/0");
                    if (sizeIndex < 10) {
                        imgUrl = imgUrl + "0" + sizeIndex + ".jpg";
                    } else {
                        imgUrl = imgUrl + sizeIndex + ".jpg";
                    }
                    // https://img.buuxk.com:85/gallery/28195/36882/s/001.jpg
                    listBean.setQhimg_url(imgUrl);
                    listBean.setQhimg_thumb_url(imgUrl);
                    listBeanList.add(listBean);
                }
            }
            return listBeanList;
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

    @Override
    protected void ItemClickListener(View itemView, int viewType, int position) {
        mData = mAdapter.getList();

        if (imgs == null) imgs = new ArrayList<>();
        imgs.clear();

        for (SuperGalleryEntity.ListBean entity : mData) {
            imgs.add(entity.getQhimg_url());
        }

        Intent intent = ImageActivity.getIntent(getHolder(), imgs, position);
        startActivity(intent);
        getHolder().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}
