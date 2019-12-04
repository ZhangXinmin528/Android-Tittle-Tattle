package com.coding.zxm.lib_okhttp.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import okio.GzipSink;
import okio.Okio;

/**
 * Created by ZhangXinmin on 2019/12/4.
 * Copyright (c) 2019 . All rights reserved.
 */
public class GzipRequestInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request oriRequest = chain.request();
        if (oriRequest.body() == null ||
                oriRequest.headers("Content-Encoding") != null) {
            return chain.proceed(oriRequest);
        }

        final Request compressedRequest = oriRequest.newBuilder()
                .header("Content-Encoding", "gzip")
                .method(oriRequest.method(), gzip(oriRequest.body()))
                .build();
        return chain.proceed(compressedRequest);
    }

    private RequestBody gzip(final RequestBody body) {

        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return body.contentType();
            }

            @Override
            public long contentLength() throws IOException {
                return -1;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                final BufferedSink gzipSink = Okio.buffer(new GzipSink(sink));
                body.writeTo(gzipSink);
                gzipSink.close();
            }
        };
    }
}
