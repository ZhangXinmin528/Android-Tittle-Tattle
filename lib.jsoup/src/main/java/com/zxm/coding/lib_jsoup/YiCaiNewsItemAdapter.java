package com.zxm.coding.lib_jsoup;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxm.coding.lib_jsoup.model.YiCaiEntity;

import java.util.List;

/**
 * Created by ZhangXinmin on 2020/3/23.
 * Copyright (c) 2020 . All rights reserved.
 */
public class YiCaiNewsItemAdapter extends BaseMultiItemQuickAdapter<YiCaiEntity, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public YiCaiNewsItemAdapter(List<YiCaiEntity> data) {
        super(data);
        addItemType(YiCaiEntity.TYPE_IMAGE_TEXT, R.layout.layout_yicai_news_img_item);
        addItemType(YiCaiEntity.TYPE_TEXT, R.layout.layout_yicai_news_text_item);
        addItemType(YiCaiEntity.TYPE_IMAGE_TAG, R.layout.layout_yicai_news_tag_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, YiCaiEntity item) {
        if (item != null) {
            switch (item.getItemType()) {
                //文本
                case YiCaiEntity.TYPE_TEXT:
                    helper.setText(R.id.tv_yicai_text_title, item.getTitle())
                            .setText(R.id.tv_yicai_text_brief, item.getBriefNews())
                            .setText(R.id.tv_yicai_text_timestamp, item.getTimeStamp());
                    break;
                //图文混合
                case YiCaiEntity.TYPE_IMAGE_TEXT:
                    helper.setText(R.id.tv_yicai_img_title, item.getTitle())
                            .setText(R.id.tv_yicai_img_timestamp, item.getTimeStamp());
                    final ImageView iv = helper.getView(R.id.iv_yicai_img_thumb);
                    Glide.with(iv)
                            .load(item.getThumbnailUrl())
                            .into(iv);
                    break;
                //tag
                case YiCaiEntity.TYPE_IMAGE_TAG:
                    helper.setText(R.id.tv_yicai_tag_title, item.getTitle())
                            .setText(R.id.tv_yicai_tag_timestamp, item.getTimeStamp())
                            .setText(R.id.tv_yicai_tag, item.getTag());
                    final ImageView iv_tag = helper.getView(R.id.iv_yicai_tag_thumb);
                    Glide.with(iv_tag)
                            .load(item.getThumbnailUrl())
                            .into(iv_tag);
                    break;
            }

        }
    }
}
