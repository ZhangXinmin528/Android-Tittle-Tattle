package com.zxm.coding.lib_stacking;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by ZhangXinmin on 2019/11/12.
 * Copyright (c) 2019 . All rights reserved.
 */
public class StackingLayoutManager extends RecyclerView.LayoutManager {

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
