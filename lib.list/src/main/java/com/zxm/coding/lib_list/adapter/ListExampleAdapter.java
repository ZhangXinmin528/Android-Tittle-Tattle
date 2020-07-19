package com.zxm.coding.lib_list.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zxm.coding.lib_list.R;
import com.zxm.utils.core.time.TimeUtil;

import java.util.List;

/**
 * Created by ZhangXinmin on 2020/4/22.
 * Copyright (c) 2020 . All rights reserved.
 */
public class ListExampleAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ListExampleAdapter(@Nullable List<String> data) {
        super(R.layout.layout_list_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_item_content, item + ":" + TimeUtil.getNowString());
    }
}
