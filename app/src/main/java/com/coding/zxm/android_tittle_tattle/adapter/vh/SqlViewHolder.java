package com.coding.zxm.android_tittle_tattle.adapter.vh;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.coding.zxm.android_tittle_tattle.R;

/**
 * Created by ZhangXinmin on 2019/2/15.
 * Copyright (c) 2018 . All rights reserved.
 */
public class SqlViewHolder extends RecyclerView.ViewHolder {

    private TextView id;
    private TextView name;
    private TextView province;

    public SqlViewHolder(@NonNull View itemView) {
        super(itemView);

        id = itemView.findViewById(R.id.tv_sql_item_id);
        name = itemView.findViewById(R.id.tv_sql_item_name);
        province = itemView.findViewById(R.id.tv_sql_item_province);
    }

    public TextView getId() {
        return id;
    }

    public TextView getName() {
        return name;
    }

    public TextView getProvince() {
        return province;
    }
}
