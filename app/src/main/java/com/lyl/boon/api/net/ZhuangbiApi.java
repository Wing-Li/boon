package com.lyl.boon.api.net;


import com.lyl.boon.entity.ZhuangbiEntiry;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Wing_Li
 * 2016/4/15.
 */
public interface ZhuangbiApi {
    @GET("search")
    Observable<List<ZhuangbiEntiry>> search(@Query("q") String id);
}
