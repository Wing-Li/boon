package com.lyl.boon.net.api;

import com.lyl.boon.net.entity.WanAndroidEntity;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Wing_Li
 * 2016/3/30.
 */
public interface WanAndroidApi {

    @GET("article/list/{page}/json")
    Observable<WanAndroidEntity> getWanAndroidList(@Path("page") int page);

    @GET("wxarticle/list/{id}/{page}/json")
    Observable<WanAndroidEntity> getWxArticleList(@Path("id") String id, @Path("page") int page);

}
