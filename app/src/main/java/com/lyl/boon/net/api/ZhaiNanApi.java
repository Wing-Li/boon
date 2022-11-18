package com.lyl.boon.net.api;

import com.lyl.boon.net.entity.SuperGalleryEntity;
import com.lyl.boon.net.entity.SuperImageEntity;
import com.lyl.boon.net.entity.SuperMenuEntity;
import com.lyl.boon.net.entity.WanAndroidEntity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.http.Path;
import rx.Observable;
import rx.Subscriber;

/**
 * 宅男女神
 * https://www.gnvshen.com/
 */
public class ZhaiNanApi {

    private String baseUrl = "https://www.gnvshen.com/";

    public Observable<SuperGalleryEntity> getGalleryList(String type, int page) {
        return Observable.create(subscriber -> {
            SuperGalleryEntity superGalleryEntity = new SuperGalleryEntity();
            List<SuperGalleryEntity.ListBean> listBeanList = new ArrayList<>();

            try {
                String url = "";
                if (page == 1) {
                    url = baseUrl + "gallery/" + type + "/";
                } else {
                    url = baseUrl + "gallery/" + type + "/" + page + ".html";
                }
                Document document = Jsoup.connect(url).get();
                Elements galleryList = document.select("div#listdiv ul li");

                SuperGalleryEntity.ListBean listBean;
                for (Element gallery : galleryList) {
                    listBean = new SuperGalleryEntity.ListBean();
                    Element link = gallery.selectFirst("div.galleryli_div a");
                    Element img = gallery.selectFirst("div.galleryli_div img");

                    String imageUrl = img.attr("data-original").replace("img", "t1");

                    listBean.setId(link.attr("href")); // g/38047/
                    listBean.setGroup_title(img.attr("title"));
                    listBean.setQhimg_thumb_url(imageUrl);
                    listBean.setQhimg_url(imageUrl);
                    listBean.setQhimg_width(528);
                    listBean.setQhimg_height(2210);
                    listBeanList.add(listBean);
                }

            } catch (Exception e) {
                e.printStackTrace();
                subscriber.onError(e);
            }

            superGalleryEntity.setList(listBeanList);
            subscriber.onNext(superGalleryEntity);
        });
    }

    public Observable<SuperImageEntity> getGalleryInfo(String id, int page) {
        return Observable.create(subscriber -> {
            SuperImageEntity superImageEntity = new SuperImageEntity();
            List<SuperImageEntity.ListBean> listBeanList = new ArrayList<>();

            try {
                String url = baseUrl + id + page + ".html";
                Document document = Jsoup.connect(url).get();
                Elements imgs = document.select("div#high ul img");

                SuperImageEntity.ListBean listBean;
                for (Element img : imgs) {
                    listBean = new SuperImageEntity.ListBean();

                    String imageUrl = img.attr("src").replace("img", "t1");
                    listBean.setPic_url(imageUrl);
                    listBean.setQhimg_url(imageUrl);
                    listBean.setQhimg_thumb_url(imageUrl);
                    listBeanList.add(listBean);
                }

            } catch (Exception e) {
                e.printStackTrace();
                subscriber.onError(e);
            }

            superImageEntity.setList(listBeanList);
            subscriber.onNext(superImageEntity);
        });
    }
}
