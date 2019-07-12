package com.coding.zxm.video.vod;

import android.content.Context;
import android.util.AttributeSet;

import com.coding.zxm.video.R;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

/**
 * Created by ZhangXinmin on 2019/7/10.
 * Copyright (c) 2019 . All rights reserved.
 * Current Project:EasyPlayer_Master
 */
public class VodVideoPlayer extends StandardGSYVideoPlayer {

    public VodVideoPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public VodVideoPlayer(Context context) {
        super(context);
    }

    public VodVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_control_vod_video;
    }

    //全屏按钮
    @Override
    public int getEnlargeImageRes() {
        return R.drawable.icon_fullscreen_player;
    }

    //退出全屏按钮
    @Override
    public int getShrinkImageRes() {
        return R.drawable.icon_quit_fullscreen_player;
    }
}
