package com.coding.zxm.libnet.adapter.vh;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.coding.zxm.libnet.R;

/**
 * Created by ZhangXinmin on 2019/2/22.
 * Copyright (c) 2018 . All rights reserved.
 */
public class MovieViewHolder extends RecyclerView.ViewHolder {

    private ImageView poster;
    private TextView movieNameCN;
    private TextView movieNameEN;
    private TextView director;
    private TextView type;

    public MovieViewHolder(@NonNull View itemView) {
        super(itemView);

        poster = itemView.findViewById(R.id.iv_movie_poster);
        movieNameCN = itemView.findViewById(R.id.tv_movie_name_cn);
        movieNameEN = itemView.findViewById(R.id.tv_movie_name_en);
        director = itemView.findViewById(R.id.tv_movie_director);
        type = itemView.findViewById(R.id.tv_movie_type);
    }

    public ImageView getPoster() {
        return poster;
    }

    public TextView getMovieNameCN() {
        return movieNameCN;
    }

    public TextView getMovieNameEN() {
        return movieNameEN;
    }

    public TextView getDirector() {
        return director;
    }

    public TextView getType() {
        return type;
    }
}
