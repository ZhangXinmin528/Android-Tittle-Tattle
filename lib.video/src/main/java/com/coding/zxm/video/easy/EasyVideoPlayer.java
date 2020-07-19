package com.coding.zxm.video.easy;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.coding.zxm.video.R;
import com.shuyu.gsyvideoplayer.model.GSYVideoModel;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhangXinmin on 2019/7/11.
 * Copyright (c) 2019 . All rights reserved.
 * Current Project:EasyPlayer_Master
 */
public class EasyVideoPlayer extends StandardGSYVideoPlayer {

    private static final String TAG = EasyVideoPlayer.class.getSimpleName();

    private List<GSYVideoModel> sourceList;

    public EasyVideoPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public EasyVideoPlayer(Context context) {
        super(context);
    }

    public EasyVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_control_easy_video;
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

    /**
     * 设置播放URL
     *
     * @param url           播放url
     * @param position      需要播放的位置
     * @param cacheWithPlay 是否边播边缓存
     * @return
     */
    public boolean setUp(List<GSYVideoModel> url, boolean cacheWithPlay, int position) {
        return setUp(url, cacheWithPlay, position, null, new HashMap<String, String>());
    }

    /**
     * 设置播放URL
     *
     * @param url           播放url
     * @param cacheWithPlay 是否边播边缓存
     * @param position      需要播放的位置
     * @param cachePath     缓存路径，如果是M3U8或者HLS，请设置为false
     * @return
     */
    public boolean setUp(List<GSYVideoModel> url, boolean cacheWithPlay,
                         int position, File cachePath) {
        return setUp(url, cacheWithPlay, position, cachePath, new HashMap<String, String>());
    }

    /**
     * 设置播放URL
     *
     * @param url           播放url
     * @param cacheWithPlay 是否边播边缓存
     * @param position      需要播放的位置
     * @param cachePath     缓存路径，如果是M3U8或者HLS，请设置为false
     * @param mapHeadData   http header
     * @return
     */
    public boolean setUp(List<GSYVideoModel> url, boolean cacheWithPlay,
                         int position, File cachePath,
                         Map<String, String> mapHeadData) {

        return setUp(url, cacheWithPlay, position, cachePath, mapHeadData, true);
    }

    /**
     * 设置播放URL
     *
     * @param url           播放url
     * @param cacheWithPlay 是否边播边缓存
     * @param position      需要播放的位置
     * @param cachePath     缓存路径，如果是M3U8或者HLS，请设置为false
     * @param mapHeadData   http header
     * @param changeState   切换的时候释放surface
     * @return
     */
    protected boolean setUp(List<GSYVideoModel> url, boolean cacheWithPlay,
                            int position, File cachePath,
                            Map<String, String> mapHeadData,
                            boolean changeState) {
        sourceList = url;
        mPlayPosition = position;
        mMapHeadData = mapHeadData;
        final GSYVideoModel gsyVideoModel = url.get(position);
        Log.e(TAG, "setUp..index : " + mPlayPosition);
        return setUp(gsyVideoModel.getUrl(), cacheWithPlay, cachePath,
                gsyVideoModel.getTitle(), changeState);
    }

    @Override
    protected void cloneParams(GSYBaseVideoPlayer from, GSYBaseVideoPlayer to) {
        super.cloneParams(from, to);
        EasyVideoPlayer sf = (EasyVideoPlayer) from;
        EasyVideoPlayer st = (EasyVideoPlayer) to;
        st.mPlayPosition = sf.mPlayPosition;
        st.sourceList = sf.sourceList;
    }

    @Override
    public void onCompletion() {
        Log.e(TAG, "onCompletion..index : " + mPlayPosition);
        releaseNetWorkState();
        if (mPlayPosition < (sourceList.size())) {
            return;
        }
        super.onCompletion();
    }

    @Override
    public void onAutoCompletion() {
        Log.e(TAG, "onAutoCompletion..index : " + mPlayPosition);
        if (playNext()) {
            return;
        }
        super.onAutoCompletion();
    }

    /**
     * 播放下一集
     *
     * @return true表示还有下一集
     */
    public boolean playNext() {
        Log.e(TAG, "playNext..index : " + mPlayPosition);
        if (sourceList != null && !sourceList.isEmpty()) {
            final int size = sourceList.size();
            mPlayPosition += 1;
            mSaveChangeViewTIme = 0;
            setUp(sourceList, mCache, mPlayPosition % size, null, mMapHeadData, false);
            startPlayLogic();
            return true;
        }
        return false;
    }
}
