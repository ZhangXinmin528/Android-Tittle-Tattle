package com.coding.zxm.android_tittle_tattle.adapter;

import android.support.annotation.NonNull;

import com.coding.zxm.android_tittle_tattle.R;
import com.coding.zxm.recyclerviewhelper.AbsRecyclerAdapter;
import com.coding.zxm.recyclerviewhelper.viewholder.BaseViewHolder;
import com.zxm.utils.core.log.MLogger;

import java.util.List;

/**
 * Created by ZhangXinmin on 2019/9/1.
 * Copyright (c) 2019 . All rights reserved.
 */
public class RvAdapter extends AbsRecyclerAdapter<String, BaseViewHolder> {

    public RvAdapter(@NonNull List<String> data) {
        super(R.layout.layout_rv_list_item, data);
    }

    @Override
    protected void bindView(BaseViewHolder holder, String data) {
        holder.setOnItemClickListener();

        holder.setText(R.id.tv_rv_item_name, data);

        holder.setOnItemChildClickListener(R.id.tv_rv_item_name);
    }
}
