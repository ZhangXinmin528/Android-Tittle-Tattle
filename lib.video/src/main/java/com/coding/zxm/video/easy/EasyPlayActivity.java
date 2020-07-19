package com.coding.zxm.video.easy;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.coding.zxm.video.R;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.model.GSYVideoModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangXinmin on 2019/7/11.
 * Copyright (c) 2019 . All rights reserved.
 * Current Project:EasyPlayer_Master
 */
public class EasyPlayActivity extends AppCompatActivity {
    private EasyVideoPlayer mVideoPlayer;

    private List<GSYVideoModel> mSourceList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looper_play);

        initParamsAndValues();

        initViews();

        initPlayerConfig();
    }

    private void initParamsAndValues() {
        mSourceList = new ArrayList<>();

        mSourceList.add(new GSYVideoModel("https://v.mifile.cn/b2c-mimall-media/ede570b80713de48f481533f2f4e12ee.mp4",
                "米家小相机"));
        mSourceList.add(new GSYVideoModel("https://v.mifile.cn/b2c-mimall-media/4521a54d776fe9bec6718458a26bf861.mp4",
                "小爱智能闹钟"));
        mSourceList.add(new GSYVideoModel("https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/74c1596d028aa98498a6015a87010b87.mp4",
                "小米9：战斗天使"));
        mSourceList.add(new GSYVideoModel("https://v.mifile.cn/b2c-mimall-media/aaf03a966adab652e1ce2df9f5cfc58f.mp4",
                "小米游戏本"));

    }

    private void initViews() {
        mVideoPlayer = findViewById(R.id.player_easy);
    }

    private void initPlayerConfig() {
        mVideoPlayer.setUp(mSourceList, true, 0);
        mVideoPlayer.startPlayLogic();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mVideoPlayer != null) {
            mVideoPlayer.onVideoPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mVideoPlayer != null) {
            mVideoPlayer.onVideoResume();
        }
    }

    @Override
    protected void onDestroy() {
        GSYVideoManager.releaseAllVideos();
        if (mVideoPlayer != null) {
            mVideoPlayer.release();
        }
//        if (mOrientationUtils != null) {
//            mOrientationUtils.releaseListener();
//        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        GSYVideoManager.releaseAllVideos();
        if (mVideoPlayer != null) {
            mVideoPlayer.release();
            mVideoPlayer.setVideoAllCallBack(null);
        }
        super.onBackPressed();

    }

}
