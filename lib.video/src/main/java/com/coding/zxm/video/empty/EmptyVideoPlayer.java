package com.coding.zxm.video.empty;

import android.content.Context;
import android.util.AttributeSet;

import com.coding.zxm.video.R;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

/**
 * Created by ZhangXinmin on 2018/11/15.
 * Copyright (c) 2018 . All rights reserved.
 * 无任何控制UI的播放器
 */
public class EmptyVideoPlayer extends StandardGSYVideoPlayer {

    public EmptyVideoPlayer(Context context) {
        super(context);
    }

    public EmptyVideoPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public EmptyVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_control_empty_video;
    }

    @Override
    protected void touchSurfaceMoveFullLogic(float absDeltaX, float absDeltaY) {
        super.touchSurfaceMoveFullLogic(absDeltaX, absDeltaY);

        //不给触摸快进，如果需要，屏蔽下方代码即可
        mChangePosition = false;

        //不给触摸音量，如果需要，屏蔽下方代码即可
//        mChangeVolume = false;

        //不给触摸亮度，如果需要，屏蔽下方代码即可
//        mBrightness = false;
    }
}
