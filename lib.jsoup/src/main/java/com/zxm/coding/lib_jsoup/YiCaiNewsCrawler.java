package com.zxm.coding.lib_jsoup;

import android.text.TextUtils;

import com.zxm.coding.lib_jsoup.model.YiCaiEntity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.zxm.coding.lib_jsoup.model.YiCaiEntity.TYPE_IMAGE_TAG;
import static com.zxm.coding.lib_jsoup.model.YiCaiEntity.TYPE_IMAGE_TEXT;
import static com.zxm.coding.lib_jsoup.model.YiCaiEntity.TYPE_TEXT;

/**
 * Created by ZhangXinmin on 2020/3/19.
 * Copyright (c) 2020 . All rights reserved.
 * 第一财经新闻爬虫解析类
 */
public final class YiCaiNewsCrawler {
    private static final String TAG = "YiCaiNewsCrawler";
    private static final int DEFAULT_SIZE = 5;

    //抓取线程池
    private ExecutorService mCrawlerPools;

    private YiCaiNewsCrawler() {
        mCrawlerPools = Executors.newFixedThreadPool(DEFAULT_SIZE);
    }

    public static YiCaiNewsCrawler getInstance() {
        return YiCaiHolder.INSTANCE;
    }

    /**
     * 获取
     *
     * @param category
     * @return
     */
    public List<YiCaiEntity> getYiCaiNews(@YiCaiNewsCategory final String category) {

        final List<YiCaiEntity> dataList = new ArrayList<>();

        if (!TextUtils.isEmpty(category)) {
            Future<List<YiCaiEntity>> future = mCrawlerPools.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        final Document doc = Jsoup.connect(YiCaiConstant.BASE_URL + category).get();
                        //获取新闻列表
                        final Element newsList = doc.getElementById("newslist");
                        final Elements news = newsList.getElementsByClass("f-db");

                        for (Element itemEle : news) {
                            final YiCaiEntity entity = new YiCaiEntity();
                            entity.setCategory(category);
                            //链接
                            final String linkfy = itemEle.attr("href");
                            entity.setLinkfy(YiCaiConstant.BASE_URL + linkfy);

                            final Element mListEle = itemEle.selectFirst("div.m-list");
                            if (mListEle != null) {
                                final int size = mListEle.childNodeSize();
                                Element textEle = null;
                                Element titleEle = null;
                                Element contentEle = null;
                                Element authorEle = null;
                                Element imgEle = null;
                                switch (size) {
                                    case 2://文字
                                        textEle = mListEle.getElementsByTag("div").first();

                                        titleEle = textEle.getElementsByTag("h2").first();
                                        contentEle = textEle.getElementsByTag("p").first();
                                        authorEle = textEle.getElementsByClass("author").first();

                                        entity.setType(TYPE_TEXT);
                                        entity.setTitle(titleEle.text());
                                        entity.setBriefNews(contentEle.text());
                                        entity.setTimeStamp(authorEle.text());
//                                        MLogger.d(TAG, "文字..item ： " + entity.toString());
                                        break;
                                    case 4://图文混排：
                                        imgEle = mListEle.selectFirst("div.lef")
                                                .getElementsByTag("img").first();

                                        textEle = mListEle.selectFirst("div.lef").nextElementSibling();

                                        titleEle = textEle.getElementsByTag("h2").first();
                                        contentEle = textEle.getElementsByTag("p").first();
                                        authorEle = textEle.getElementsByClass("author").first()
                                                .getElementsByTag("span").first();

                                        entity.setType(TYPE_IMAGE_TEXT);
                                        entity.setTitle(titleEle.text());
                                        entity.setBriefNews(contentEle.text());
                                        entity.setTimeStamp(authorEle.text());
                                        entity.setThumbnailUrl("https:" + imgEle.attr("src"));

//                                        MLogger.d(TAG, "图文混排..item ： " + entity.toString());
                                        break;
                                    case 6://带标签图文混排：
                                        final Element tagEle = mListEle.selectFirst("div.titletips");

                                        imgEle = mListEle.selectFirst("div.lef")
                                                .getElementsByTag("img").first();

                                        textEle = mListEle.selectFirst("div.lef").nextElementSibling();

                                        titleEle = textEle.getElementsByTag("h2").first();
                                        contentEle = textEle.getElementsByTag("p").first();
                                        authorEle = textEle.getElementsByClass("author").first()
                                                .getElementsByTag("span").first();

                                        entity.setType(TYPE_IMAGE_TAG);
                                        entity.setTag(tagEle.text());
                                        entity.setTitle(titleEle.text());
                                        entity.setBriefNews(contentEle.text());
                                        entity.setTimeStamp(authorEle.text());
                                        entity.setThumbnailUrl("https:" + imgEle.attr("src"));
//                                        MLogger.d(TAG, "带标签图文混排..item ： " + entity.toString());
                                        break;
                                }

                            }
                            dataList.add(entity);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }, dataList);

            try {
                dataList.addAll(future.get());
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return dataList;

    }

    private static class YiCaiHolder {
        private static YiCaiNewsCrawler INSTANCE = new YiCaiNewsCrawler();
    }
}
