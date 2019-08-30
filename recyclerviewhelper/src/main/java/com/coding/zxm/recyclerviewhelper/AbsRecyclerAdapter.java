package com.coding.zxm.recyclerviewhelper;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.coding.zxm.recyclerviewhelper.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangXinmin on 2019/8/30.
 * Copyright (c) 2019 . All rights reserved.
 */
public abstract class AbsRecyclerAdapter<D, V extends BaseViewHolder> extends RecyclerView.Adapter<V> {

    private int mLayoutResId;
    private List<D> mData;

    public AbsRecyclerAdapter(@LayoutRes int layoutResId, @NonNull List<D> data) {
        this.mData = data == null ? new ArrayList<>() : data;
        if (layoutResId != -1) {
            this.mLayoutResId = layoutResId;
        }
    }



    @NonNull
    @Override
    public V onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull V v, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
