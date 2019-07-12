package com.coding.zxm.video;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.coding.zxm.video.easy.EasyPlayActivity;
import com.coding.zxm.video.empty.EmptyPlayActivity;
import com.coding.zxm.video.live.LiveActivity;
import com.coding.zxm.video.local.LocalActivity;
import com.coding.zxm.video.util.Constant;
import com.coding.zxm.video.vod.VodActivity;

/**
 * Created by ZhangXinmin on 2019/7/12.
 * Copyright (c) 2019 . All rights reserved.
 * Current Project:Android-Tittle-Tattle
 */
public class VideoNavigationActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_navigation);

        context = this;

        findViewById(R.id.btn_normal_vod).setOnClickListener(this);
        findViewById(R.id.btn_live).setOnClickListener(this);
        findViewById(R.id.btn_local).setOnClickListener(this);
        findViewById(R.id.btn_empty).setOnClickListener(this);
        findViewById(R.id.btn_list).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();//正常播放器：点播
        if (viewId == R.id.btn_normal_vod) {
            Intent normal = new Intent(context, VodActivity.class);
            Bundle norBdle = new Bundle();
            norBdle.putString(Constant.PARAMS_URL, API.MI_CAMERA);
            norBdle.putString(Constant.PARAMS_VIDEO_TITLE, "米家小相机");
            normal.putExtras(norBdle);
            startActivity(normal);
            //直播
        } else if (viewId == R.id.btn_live) {
            Intent live = new Intent(context, LiveActivity.class);
            Bundle liveArgs = new Bundle();
            liveArgs.putString(Constant.PARAMS_URL, API.TV_CCTV1);
            liveArgs.putString(Constant.PARAMS_VIDEO_TITLE, "CCTV1");
            live.putExtras(liveArgs);
            startActivity(live);
            //本地播放
        } else if (viewId == R.id.btn_local) {
            Intent local = new Intent(context, LocalActivity.class);
            startActivity(local);
            //无UI播放器
        } else if (viewId == R.id.btn_empty) {
            Intent empty = new Intent(context, EmptyPlayActivity.class);
            Bundle eptBdle = new Bundle();
            eptBdle.putString(Constant.PARAMS_URL, API.MI_PC);
            empty.putExtras(eptBdle);
            startActivity(empty);
            //列表循环播放
        } else if (viewId == R.id.btn_list) {
            Intent easy = new Intent(context, EasyPlayActivity.class);
            startActivity(easy);
        }
    }
}
