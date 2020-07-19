package com.zxm.coding.lib_database;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coding.zxm.libcore.listender.OnItemClickListener;
import com.zxm.coding.lib_database.local.model.Student;

import java.util.List;

/**
 * Created by ZhangXinmin on 2019/2/15.
 * Copyright (c) 2018 . All rights reserved.
 */
public class SqlExampleAdapter extends RecyclerView.Adapter<SqlViewHolder> {
    private List<Student> mDataList;
    private OnItemClickListener mListener;

    public SqlExampleAdapter(List<Student> dataList) {
        this.mDataList = dataList;
    }

    @NonNull
    @Override
    public SqlViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_sql_item, viewGroup, false);
        return new SqlViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final SqlViewHolder helper, final int position) {
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(SqlExampleAdapter.this, helper.itemView, position);
                }
            }
        });
        final Student student = getItem(position);
        if (student != null) {
            helper.getId().setText(student.getId() + "");
            helper.getName().setText(student.getName());
            helper.getProvince().setText(student.getProvince());
        }
    }

    @Override
    public int getItemCount() {
        if (mDataList == null || mDataList.isEmpty()) {
            return 0;
        } else {
            return mDataList.size();
        }
    }

    /**
     * Get item element.
     *
     * @param position
     * @return
     */
    public Student getItem(@IntRange(from = 0) int position) {
        if (mDataList != null && !mDataList.isEmpty()) {
            if (position < mDataList.size()) {
                return mDataList.get(position);
            }
        }
        return null;
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
