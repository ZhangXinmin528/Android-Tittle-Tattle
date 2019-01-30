package com.coding.zxm.android_tittle_tattle.adapter.vh;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.coding.zxm.android_tittle_tattle.R;

/**
 * Created by ZhangXinmin on 2019/1/30.
 * Copyright (c) 2018 . All rights reserved.
 */
public class SortViewHolder extends RecyclerView.ViewHolder {
    private TextView name;

    public SortViewHolder(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.tv_home_item_name);
    }

    public TextView getName() {
        return name;
    }
}
