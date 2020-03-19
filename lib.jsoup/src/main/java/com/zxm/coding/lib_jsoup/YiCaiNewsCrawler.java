package com.zxm.coding.lib_jsoup;

import android.text.TextUtils;

import com.zxm.coding.lib_jsoup.model.YiCaiEntity;
import com.zxm.utils.core.log.MLogger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ZhangXinmin on 2020/3/19.
 * Copyright (c) 2020 . All rights reserved.
 * 第一财经新闻爬虫解析类
 */
public final class YiCaiNewsCrawler {
    private static final String TAG = "YiCaiNewsCrawler";
    private static final int DEFAULT_SIZE = 10;

    //抓取线程池
    private ExecutorService mCrawlerPools;

    private YiCaiNewsCrawler() {
        mCrawlerPools = Executors.newFixedThreadPool(DEFAULT_SIZE);
    }

    public static YiCaiNewsCrawler getInstance() {
        return YiCaiHolder.INSTANCE;
    }

    public void getYiCaiNews(@YiCaiNewsCategory final String category) {

        if (!TextUtils.isEmpty(category)) {
            mCrawlerPools.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        final Document doc = Jsoup.connect(YiCaiConstant.BASE_URL + category).get();
                        //获取新闻列表
                        final Element newsList = doc.getElementById("newslist");
                        final Elements news = newsList.getElementsByClass("f-db");

                        for (Element itemEle : news) {
                            final YiCaiEntity entity = new YiCaiEntity();

                            final String linkfy = itemEle.attr("href");
                            entity.setLinkfy(linkfy);

                            if (itemEle.hasClass("m-list m-list-1 f-cb")) {//图文混合
                                final Elements fcbEle = itemEle.select("m-list m-list-1 f-cb");
                                if (fcbEle != null && !fcbEle.isEmpty()) {
                                    final Elements zoomInEle = fcbEle.select("lef f-fl m-zoomin");
                                    final Element imageEle = zoomInEle.first();
                                    if (imageEle != null) {
                                        //图片地址
                                        final String thumbnailUrl = imageEle.attr("src");
                                        entity.setThumbnailUrl(thumbnailUrl);

                                        final Element textEle = fcbEle.last();
                                        if (textEle != null) {
                                            final Elements TitleEle = textEle.getElementsByTag("h2");
                                            final String title = TitleEle.text();
                                            entity.setTitle(title);
                                            final Elements contentEle = textEle.getElementsByTag("p");
                                            final String content = contentEle.text();
                                            entity.setBriefNews(content);
                                            entity.setType(YiCaiEntity.TYPE_IMAGE_TEXT);

                                        }
                                    }
                                }
                            } else if (itemEle.hasClass("")) {//文字资讯

                            }
                            MLogger.d(TAG, "id : " + itemEle.id() + "..detial : " + entity.toString());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }


    }

    private static class YiCaiHolder {
        private static YiCaiNewsCrawler INSTANCE = new YiCaiNewsCrawler();
    }
}
