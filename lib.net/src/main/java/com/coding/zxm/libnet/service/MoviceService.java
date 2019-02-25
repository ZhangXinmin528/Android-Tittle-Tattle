package com.coding.zxm.libnet.service;

import com.coding.zxm.libnet.model.DoubanMovie;

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

}
