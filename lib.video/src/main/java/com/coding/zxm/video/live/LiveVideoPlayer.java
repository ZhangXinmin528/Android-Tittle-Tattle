package com.coding.zxm.video.live;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.coding.zxm.video.R;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;

/**
 * Created by ZhangXinmin on 2018/11/15.
 * Copyright (c) 2018 . All rights reserved.
 * 直播播放器
 */
public class LiveVideoPlayer extends StandardGSYVideoPlayer {

    public LiveVideoPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public LiveVideoPlayer(Context context) {
        super(context);
    }

    public LiveVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_control_live_video;
    }

    //主屏按钮
    @Override
    protected void updateStartImage() {
        if (mIfCurrentIsFullscreen) {
            if (mStartButton instanceof ImageView) {
                ImageView imageView = (ImageView) mStartButton;
                if (mCurrentState == CURRENT_STATE_PLAYING) {
                    imageView.setImageResource(R.drawable.video_click_pause_selector);
                } else if (mCurrentState == CURRENT_STATE_ERROR) {
                    imageView.setImageResource(R.drawable.video_click_play_selector);
                } else {
                    imageView.setImageResource(R.drawable.video_click_play_selector);
                }
            }
        } else {
            super.updateStartImage();
        }
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

    //播放控制器恢复正常
    @Override
    protected void resolveNormalVideoShow(View oldF, ViewGroup vp, GSYVideoPlayer gsyVideoPlayer) {
        LiveVideoPlayer player = (LiveVideoPlayer) gsyVideoPlayer;
        player.dismissProgressDialog();
        player.dismissVolumeDialog();
        player.dismissBrightnessDialog();
        super.resolveNormalVideoShow(oldF, vp, gsyVideoPlayer);
    }
}
