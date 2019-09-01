package com.coding.zxm.recyclerviewhelper.listener;

import android.view.PointerIcon;
import android.view.View;

import com.coding.zxm.recyclerviewhelper.AbsRecyclerAdapter;

/**
 * Created by ZhangXinmin on 2019/8/30.
 * Copyright (c) 2019 . All rights reserved.
 * <p>
 * Interface definition for a callback to be invoked when an item in this
 * RecyclerView itemView has been clicked.
 */
public interface OnItemClickListener {

    /**
     * allback method to be invoked when an item in this RecyclerView has
     * been clicked.
     *
     * @param adapter  the adpater
     * @param view     The itemView within the RecyclerView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     */
    void onItemClick(AbsRecyclerAdapter adapter, View view, int position);
}
