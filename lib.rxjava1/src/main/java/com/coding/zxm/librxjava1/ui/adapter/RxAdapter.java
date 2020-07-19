package com.coding.zxm.librxjava1.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coding.zxm.libcore.listender.OnItemClickListener;
import com.coding.zxm.librxjava1.R;
import com.coding.zxm.librxjava1.ui.adapter.vh.RxViewHolder;
import com.coding.zxm.libutil.DisplayUtil;

import java.util.List;

/**
 * Created by ZhangXinmin on 2019/1/30.
 * Copyright (c) 2018 . All rights reserved.
 */
public class RxAdapter extends RecyclerView.Adapter<RxViewHolder> {

    private List<String> dataList;
    private OnItemClickListener mListener;

    public RxAdapter(List<String> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RxViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_rx_list_item, viewGroup, false);
        return new RxViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final RxViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(RxAdapter.this, holder.itemView, position);
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
