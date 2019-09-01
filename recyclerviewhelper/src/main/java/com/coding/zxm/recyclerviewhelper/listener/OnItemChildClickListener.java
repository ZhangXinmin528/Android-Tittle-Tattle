package com.coding.zxm.recyclerviewhelper.listener;

import android.view.View;

import com.coding.zxm.recyclerviewhelper.AbsRecyclerAdapter;

/**
 * Created by ZhangXinmin on 2019/9/1.
 * Copyright (c) 2019 . All rights reserved.
 * <p>
 * Interface definition for a callback to be invoked when an itemchild in this
 * view has been clicked
 */
public interface OnItemChildClickListener {

    /**
     * callback method to be invoked when an item child view in this itemview has been
     * click and held
     *
     * @param view     The view whihin the ItemView that was clicked
     * @param position The position of the view int the adapter
     */
    void onItemChildClick(AbsRecyclerAdapter adapter, View view, int position);
}
