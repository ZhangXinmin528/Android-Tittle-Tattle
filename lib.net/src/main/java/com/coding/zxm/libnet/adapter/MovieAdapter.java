package com.coding.zxm.libnet.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.coding.zxm.libcore.listender.OnItemClickListener;
import com.coding.zxm.libimage.model.GlideApp;
import com.coding.zxm.libnet.R;
import com.coding.zxm.libnet.adapter.vh.MovieViewHolder;
import com.coding.zxm.libnet.model.MovieEntity;
import com.coding.zxm.libnet.model.Staff;
import com.zxm.utils.core.text.ClickableMovementMethod;
import com.zxm.utils.core.text.SpanUtils;

import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by ZhangXinmin on 2019/2/22.
 * Copyright (c) 2018 . All rights reserved.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private Context mContext;
    private List<MovieEntity> mDataList;
    private OnItemClickListener mListener;

    public MovieAdapter(List<MovieEntity> dataList) {
        this.mDataList = dataList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        final View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.layout_movie_item, viewGroup, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(MovieAdapter.this, holder.itemView, position);
                }
            }
        });

        final MovieEntity entity = getItem(position);
        if (entity != null) {
            //海报
            GlideApp.with(holder.itemView)
                    .load(entity.getImages().getMedium())
                    .into(holder.getPoster());

            //title
            SpannableStringBuilder nameCnBuilder =
                    SpanUtils.getBuilder(mContext, entity.getTitle(), true)
                            .setTextColor(Color.BLACK)
                            .append("(" + entity.getYear() + ")", false)
                            .setTextColor(Color.GRAY)
                            .create();
            holder.getMovieNameCN().setText(nameCnBuilder);

            holder.getMovieNameEN().setText(entity.getOriginal_title());

            //导演
            final TextView directorTv = holder.getDirector();
            SpannableStringBuilder directorBuilder =
                    SpanUtils.getBuilder(mContext, "导演：", true)
                            .setTextColor(Color.GRAY)
                            .append(generateDirectors(entity), true)
                            .setClickSpan(new ClickableSpan() {
                                @Override
                                public void onClick(View widget) {
                                    Toast.makeText(mContext, "点击了导演", Toast.LENGTH_SHORT).show();
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

            //类型
            final TextView type = holder.getType();
            SpannableStringBuilder typeBuilder =
                    SpanUtils.getBuilder(mContext, "类型：", true)
                            .setTextColor(Color.GRAY)
                            .append(generateMovieType(entity), true)
                            .setClickSpan(new ClickableSpan() {
                                @Override
                                public void onClick(View widget) {
                                    Toast.makeText(mContext, "点击了类型", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void updateDrawState(TextPaint ds) {
                                    ds.setColor(Color.BLUE);
                                    ds.setUnderlineText(true);
                                }
                            }).create();

            type.setText(typeBuilder);
            type.setMovementMethod(ClickableMovementMethod.getInstance());
            type.setClickable(false);
            type.setHighlightColor(Color.TRANSPARENT);

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

    /**
     * 获取电影类型
     *
     * @param entity
     * @return
     */
    private String generateMovieType(@NonNull MovieEntity entity) {
        if (entity != null) {
            final List<String> genresList = entity.getGenres();
            if (genresList != null && !genresList.isEmpty()) {
                final int size = genresList.size();
                final StringBuffer sb = new StringBuffer();
                for (int i = 0; i < size; i++) {
                    sb.append(genresList.get(i));
                    if (i < size - 1) {
                        sb.append("/");
                    }
                }
                return sb.toString();
            }
        }
        return "";
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
