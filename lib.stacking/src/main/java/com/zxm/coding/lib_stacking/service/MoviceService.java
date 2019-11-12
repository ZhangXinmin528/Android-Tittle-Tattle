package com.zxm.coding.lib_stacking.service;


import com.zxm.coding.lib_stacking.model.DoubanMovie;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ZhangXinmin on 2019/2/25.
 * Copyright (c) 2018 . All rights reserved.
 */
public interface MoviceService {

//    Call<ResponseBody> getTop250(@Query("start") int start, @Query("count") int count);

    /**
     * 获取豆瓣Top250
     *
     * @param start start index
     * @param count count
     * @return
     */
    @GET("top250")
    Call<DoubanMovie> getTop250(@Query("start") int start, @Query("count") int count);

    /**
     * 获取豆瓣Top250
     *
     * @param start start index
     * @param count count
     * @return
     */
    @GET("top250")
    Observable<DoubanMovie> getDoubanTop(@Query("start") int start, @Query("count") int count);

    /**
     * 获取北美票房榜
     *
     * @return
     */
    @GET("us_box")
    Observable<DoubanMovie> getBoxList();

    /**
     * 获取新片榜
     *
     * @return
     */
    @GET("new_movies")
    Observable<DoubanMovie> getNewMovies();
}
