package com.coding.zxm.video.vod;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.coding.zxm.video.R;
import com.coding.zxm.video.util.Constant;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;

/**
 * Created by ZhangXinmin on 2018/11/14.
 * Copyright (c) 2018 . All rights reserved.
 * 正常播放示例
 */
public class VodActivity extends AppCompatActivity {

    private Context mContext;
    private VodVideoPlayer mPlayer;
    private String mVideoUrl;
    private String mVideoTitle;

    private OrientationUtils mOrientationUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_play);

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
                if (bundle.containsKey(Constant.PARAMS_VIDEO_TITLE)) {
                    mVideoTitle = bundle.getString(Constant.PARAMS_VIDEO_TITLE);
                }
            }
        }
    }

    private void initViews() {
        mPlayer = findViewById(R.id.player_normal);
        mOrientationUtils = new OrientationUtils(this, mPlayer);
    }

    private void initPlayerConfig() {
        if (TextUtils.isEmpty(mVideoUrl))
            return;

        ImageView coverIv = new ImageView(mContext);
        coverIv.setImageResource(R.drawable.camera_cover);

        new GSYVideoOptionBuilder()
                .setThumbImageView(coverIv)//设置封面
                .setVideoTitle(mVideoTitle)//设置视频标题
                .setIsTouchWiget(true)//是否支持手势控制
                .setLooping(true)//是否循环播放
                .setCacheWithPlay(true)//是否边播边缓存
                .setUrl(mVideoUrl)//播放地址
                .build(mPlayer);

        //全屏功能
        mPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOrientationUtils.resolveByClick();
            }
        });
        //返回键支持
        mPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //开始播放
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
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        if (mOrientationUtils != null) {
            mOrientationUtils.releaseListener();
        }
    }

    @Override
    public void onBackPressed() {
        if (mOrientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            mPlayer.getFullscreenButton().performClick();
            return;
        }

        mPlayer.setVideoAllCallBack(null);
        super.onBackPressed();

    }
}
