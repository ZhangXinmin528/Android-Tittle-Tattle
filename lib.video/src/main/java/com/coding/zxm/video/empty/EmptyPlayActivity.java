package com.coding.zxm.video.empty;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.coding.zxm.video.R;
import com.coding.zxm.video.util.Constant;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

/**
 * Created by ZhangXinmin on 2018/11/15.
 * Copyright (c) 2018 . All rights reserved.
 * 无UI播放器
 */
public class EmptyPlayActivity extends AppCompatActivity {
    private Context mContext;
    private EmptyVideoPlayer mPlayer;
    private String mVideoUrl;

//    private OrientationUtils mOrientationUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_play);

        initParamsAndValues();

        initViews();

        initPlayerConfig();
    }

    private void initParamsAndValues() {
        mContext = this;

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                if (bundle.containsKey(Constant.PARAMS_URL)) {
                    mVideoUrl = bundle.getString(Constant.PARAMS_URL);
                }
            }
        }
    }

    private void initViews() {
        mPlayer = findViewById(R.id.player_empty);
//        mOrientationUtils = new OrientationUtils(this, mPlayer);
    }

    private void initPlayerConfig() {
        if (TextUtils.isEmpty(mVideoUrl))
            return;

        //设置播放地址
        mPlayer.setUp(mVideoUrl, true, "米家小相机");

        //
        mPlayer.startPlayLogic();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPlayer != null) {
            mPlayer.onVideoPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPlayer != null) {
            mPlayer.onVideoResume();
        }
    }

    @Override
    protected void onDestroy() {
        GSYVideoManager.releaseAllVideos();
        if (mPlayer != null) {
            mPlayer.release();
        }
//        if (mOrientationUtils != null) {
//            mOrientationUtils.releaseListener();
//        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        GSYVideoManager.releaseAllVideos();
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer.setVideoAllCallBack(null);
        }
        super.onBackPressed();

    }
}
