package com.zxm.coding.lib_stacking.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxm.coding.lib_stacking.R;

/**
 * Created by ZhangXinmin on 2019/11/12.
 * Copyright (c) 2019 . All rights reserved.
 */
public class StackingVh extends RecyclerView.ViewHolder {

    private ImageView poster;
    private TextView movieName;
    private TextView movieDirector;

    public StackingVh(@NonNull View itemView) {
        super(itemView);
        poster = itemView.findViewById(R.id.iv_move_poster);
        movieName = itemView.findViewById(R.id.tv_movie_name);
        movieDirector = itemView.findViewById(R.id.tv_movie_director);
    }

    public ImageView getPoster() {
        return poster;
    }

    public TextView getMovieName() {
        return movieName;
    }

    public TextView getMovieDirector() {
        return movieDirector;
    }
}
