package com.coding.zxm.libnet;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by ZhangXinmin on 2019/2/25.
 * Copyright (c) 2018 . All rights reserved.
 */
public final class RetrofitHelper {


    public static RetrofitHelper getInstance() {
        return Holder.INSTANCE;
    }

    private final static class Holder {
        private static RetrofitHelper INSTANCE = new RetrofitHelper();
    }

    public static class Builder {
        private Retrofit retrofit;
        private OkHttpClient okHttpClient;
    }

    public static class RetrofitBuildeController {

    }
}
