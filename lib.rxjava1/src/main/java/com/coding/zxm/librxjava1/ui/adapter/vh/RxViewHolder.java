package com.coding.zxm.librxjava1.ui.adapter.vh;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coding.zxm.librxjava1.R;

/**
 * Created by ZhangXinmin on 2019/1/30.
 * Copyright (c) 2018 . All rights reserved.
 */
public class RxViewHolder extends RecyclerView.ViewHolder {
    private TextView name;

    public RxViewHolder(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.tv_home_item_name);
    }

    public TextView getName() {
        return name;
    }
}
