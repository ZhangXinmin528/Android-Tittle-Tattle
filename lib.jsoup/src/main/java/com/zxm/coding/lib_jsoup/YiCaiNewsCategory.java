package com.zxm.coding.lib_jsoup;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by ZhangXinmin on 2020/3/19.
 * Copyright (c) 2020 . All rights reserved.
 * 第一财经新闻分类
 */
@StringDef({YiCaiConstant.YICAI_NEWS_GUSHI, YiCaiConstant.YICAI_NEWS_JINRONG,
        YiCaiConstant.YICAI_NEWS_HAIWAISHICHANG, YiCaiConstant.YICAI_NEWS_QUANQIU,
        YiCaiConstant.YICAI_NEWS_KEJI, YiCaiConstant.YICAI_NEWS_ZIXUN})
@Retention(RetentionPolicy.SOURCE)
public @interface YiCaiNewsCategory {
}
