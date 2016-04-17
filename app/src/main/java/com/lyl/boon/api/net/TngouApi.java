package com.lyl.boon.api.net;

import com.lyl.boon.entity.BaseTngouEntiry;
import com.lyl.boon.entity.SuperGalleryEntiry;
import com.lyl.boon.entity.SuperImageEntirty;
import com.lyl.boon.entity.SuperMenuEntiry;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Wing_Li
 * 2016/4/14.
 */
public interface TngouApi {

    /**
     * www.tngou.net/tnfs/api/classify
     * 取得图片分类，可以通过分类id取得热词列表
     */
    @GET("classify")
    Observable<BaseTngouEntiry<List<SuperMenuEntiry>>> getMenu();

    /**
     * http://www.tngou.net/tnfs/api/list?id=1&page=1&rows=20
     * 取得 图库 列表，也可以用分类id作为参数
     */
    @GET("list")
    Observable<BaseTngouEntiry<List<SuperGalleryEntiry>>> getGalleryList(@Query("id") int id, @Query("page") int page, @Query("rows") int rows);

    /**
     * http://www.tngou.net/tnfs/api/show?id=112
     * 取得热点图片详情，通过热点id取得该对应详细内容信息
     */
    @GET("show")
    Observable<SuperImageEntirty> getGalleryInfo(@Query("id") int id);


}
