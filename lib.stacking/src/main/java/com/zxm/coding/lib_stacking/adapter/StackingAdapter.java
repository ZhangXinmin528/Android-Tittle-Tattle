package com.zxm.coding.lib_stacking.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coding.zxm.libimage.model.GlideApp;
import com.zxm.coding.lib_stacking.R;
import com.zxm.coding.lib_stacking.model.MovieEntity;
import com.zxm.coding.lib_stacking.model.Staff;
import com.zxm.utils.core.text.ClickableMovementMethod;
import com.zxm.utils.core.text.SpanUtils;

import java.util.List;

/**
 * Created by ZhangXinmin on 2019/11/12.
 * Copyright (c) 2019 . All rights reserved.
 */
public class StackingAdapter extends RecyclerView.Adapter<StackingVh> {

    private Context mContext;
    private List<MovieEntity> mDataList;

    public StackingAdapter(List<MovieEntity> mDataList) {
        this.mDataList = mDataList;
    }

    @NonNull
    @Override
    public StackingVh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        final View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.layout_stacking_item, viewGroup, false);
        return new StackingVh(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StackingVh holder, int i) {
        final MovieEntity entity = getItem(i);
        if (entity != null) {
            //海报
            GlideApp.with(holder.itemView)
                    .load(entity.getImages().getMedium())
                    .into(holder.getPoster());

            holder.getMovieName().setText(String.format("《%s》", entity.getTitle()));

            //导演
            final TextView directorTv = holder.getMovieDirector();
            SpannableStringBuilder directorBuilder =
                    SpanUtils.getBuilder(mContext, "导演：", true)
                            .setTextColor(Color.WHITE)
                            .append(generateDirectors(entity), true)
                            .setClickSpan(new ClickableSpan() {
                                @Override
                                public void onClick(View widget) {
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    ds.setColor(Color.BLUE);
                                    ds.setUnderlineText(true);
                                }
                            }).create();

            directorTv.setText(directorBuilder);
            directorTv.setMovementMethod(ClickableMovementMethod.getInstance());
            directorTv.setClickable(false);
            directorTv.setHighlightColor(Color.TRANSPARENT);
        }
    }

    @Override
    public int getItemCount() {
        if (mDataList != null && !mDataList.isEmpty()) {
            return mDataList.size();
        }
        return 0;
    }

    /**
     * Get element for data resource.
     *
     * @param position
     * @return
     */
    public MovieEntity getItem(int position) {

        if (mDataList != null && !mDataList.isEmpty()) {
            return mDataList.get(position);
        } else {
            return null;
        }
    }

    /**
     * 获取导演人员信息
     *
     * @param entity
     * @return
     */
    private String generateDirectors(@NonNull MovieEntity entity) {
        if (entity != null) {
            final List<Staff> directors = entity.getDirectors();
            if (directors != null && !directors.isEmpty()) {
                final int size = directors.size();
                final StringBuilder sb = new StringBuilder();
                for (int i = 0; i < size; i++) {
                    sb.append(directors.get(i).getName());
                    if (i < size - 1) {
                        sb.append("/");
                    }
                }
                return sb.toString();
            }
        }
        return "";
    }
}
