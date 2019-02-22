package com.coding.zxm.android_tittle_tattle.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coding.zxm.android_tittle_tattle.R;
import com.coding.zxm.android_tittle_tattle.adapter.vh.SortViewHolder;
import com.coding.zxm.android_tittle_tattle.util.DisplayUtil;
import com.coding.zxm.libcore.listender.OnItemClickListener;

import java.util.List;

/**
 * Created by ZhangXinmin on 2019/1/30.
 * Copyright (c) 2018 . All rights reserved.
 */
public class SortAdapter extends RecyclerView.Adapter<SortViewHolder> {

    private List<String> dataList;
    private OnItemClickListener mListener;

    public SortAdapter(List<String> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public SortViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_home_list_item, viewGroup, false);
        return new SortViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SortViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(SortAdapter.this, holder.itemView, position);
                }
            }
        });

        final String item = getItem(position);
        holder.getName().setText(DisplayUtil.isEmpty(item));
    }

    @Override
    public int getItemCount() {
        if (dataList != null && !dataList.isEmpty()) {
            return dataList.size();
        }
        return 0;
    }

    /**
     * Get element for data resource.
     *
     * @param position
     * @return
     */
    public String getItem(int position) {

        if (dataList != null && !dataList.isEmpty()) {
            return dataList.get(position);
        } else {
            return "";
        }
    }

    /**
     * Item click
     *
     * @param listener
     */
    public void setOnItmeClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }
}
