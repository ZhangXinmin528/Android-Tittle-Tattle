package com.coding.zxm.video.live;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.coding.zxm.video.R;
import com.coding.zxm.video.util.Constant;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;

/**
 * Created by ZhangXinmin on 2018/11/15.
 * Copyright (c) 2018 . All rights reserved.
 * 直播
 */
public class LiveActivity extends AppCompatActivity {
    private final static String TAG = LiveActivity.class.getSimpleName();

    private Context mContext;
    private LiveVideoPlayer mPlayer;

    private String mVideoUrl;

    private OrientationUtils mOrientationUtils;
    private String mVideoTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_play);

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
        mPlayer = findViewById(R.id.player_live);
        mOrientationUtils = new OrientationUtils(this, mPlayer);
        mOrientationUtils.setEnable(false);
    }

    private void initPlayerConfig() {
        if (TextUtils.isEmpty(mVideoUrl))
            return;

//        ImageView coverIv = new ImageView(mContext);
//        coverIv.setImageResource(R.drawable.camera_cover);

        new GSYVideoOptionBuilder()
                .setRotateViewAuto(false)//是否开启自动选装
                .setAutoFullWithSize(true)//设置自动适应播放
                .setShowFullAnimation(false)//是否设置全屏动画
                .setNeedLockFull(true)//全屏锁定
//                .setThumbImageView(coverIv)//设置封面
                .setVideoTitle(mVideoTitle)//设置视频标题
                .setIsTouchWiget(true)//是否支持手势控制
                .setLockLand(true)//锁定横屏
                .setLooping(false)//是否循环播放
                .setCacheWithPlay(false)//是否边播边缓存
                .setUrl(mVideoUrl)//播放地址
                //.setSetUpLazy(true)//设置该属性封面无效
                .setVideoAllCallBack(new GSYSampleCallBack() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        if (mOrientationUtils != null) {
                            //开始播放了才能旋转屏幕
                            mOrientationUtils.setEnable(true);
                        }
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        if (mOrientationUtils != null) {
                            mOrientationUtils.backToProtVideo();
                        }
                    }
                })
                .setLockClickListener(new LockClickListener() {
                    @Override
                    public void onClick(View view, boolean lock) {
                        if (mOrientationUtils != null) {
                            //配合下方的onConfigurationChanged
                            mOrientationUtils.setEnable(!lock);
                        }
                    }
                })
                .build(mPlayer);

        //返回按钮
        mPlayer.getBackButton().setVisibility(View.GONE);

        //全屏功能
        mPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOrientationUtils.resolveByClick();
                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                mPlayer.startWindowFullscreen(mContext, true, true);
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
        if (mOrientationUtils != null) {
            mOrientationUtils.backToProtVideo();
        }

        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();

    }
}
