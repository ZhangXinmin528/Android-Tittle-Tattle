package com.coding.zxm.recyclerviewhelper;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coding.zxm.recyclerviewhelper.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangXinmin on 2019/8/30.
 * Copyright (c) 2019 . All rights reserved.
 */
public abstract class AbsRecyclerAdapter<D, V extends BaseViewHolder> extends RecyclerView.Adapter<V> {

    public static final int VIEW_HEADER = 0x00000111;
    public static final int VIEW_LOADING = 0x00000222;
    public static final int VIEW_FOOTER = 0x00000333;
    public static final int VIEW_EMPTY = 0x00000555;

    protected Context mContext;
    protected LayoutInflater mLayoutInflater;

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
    public V onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        V baseViewHolder = null;
        this.mContext = viewGroup.getContext();
        this.mLayoutInflater = LayoutInflater.from(mContext);
        switch (viewType) {
            case VIEW_HEADER:
                break;
            case VIEW_LOADING:
                break;
            case VIEW_EMPTY:
                break;
            case VIEW_FOOTER:
                break;
            default:
                baseViewHolder = createBaseViewHolder(viewGroup);
                break;
        }
        baseViewHolder.setAdapter(this);
        return baseViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull V holder, int position) {
        final int viewType = holder.getItemViewType();
        switch (viewType) {
            case VIEW_HEADER:
                break;
            case VIEW_LOADING:
                break;
            case VIEW_EMPTY:
                break;
            case VIEW_FOOTER:

                break;
            default:

                break;
        }

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    private V createBaseViewHolder(@NonNull ViewGroup viewGroup) {
        return (V) new BaseViewHolder(getItemView(mLayoutResId, viewGroup));
    }

    //Inflate a new item view hierarchy from the specified xml resource.
    private View getItemView(@LayoutRes int layoutResId, @NonNull ViewGroup parent) {
        return mLayoutInflater.inflate(layoutResId, parent, false);
    }
}
