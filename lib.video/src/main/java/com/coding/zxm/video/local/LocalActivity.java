package com.coding.zxm.video.local;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.coding.zxm.video.R;
import com.coding.zxm.video.vod.VodVideoPlayer;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by ZhangXinmin on 2018/11/15.
 * Copyright (c) 2018 . All rights reserved.
 * 本地视频播放
 */
@RuntimePermissions
public class LocalActivity extends AppCompatActivity {
    private Context mContext;
    private VodVideoPlayer mPlayer;
    private String mVideoUrl;

    private OrientationUtils mOrientationUtils;

    /**
     * 获取文件路径
     *
     * @return
     */
    public static String getLocalVideoPath() {
        return "file://" + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getPath();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_play);

        initParamsAndValues();

        initViews();

        initPlayerConfig();
    }

    private void initParamsAndValues() {
        mContext = this;

        //权限
        mVideoUrl = getLocalVideoPath() + "/mi_pc_design_video.mp4";

        LocalActivityPermissionsDispatcher.checkReadStroageWithPermissionCheck(this);
    }

    private void initViews() {
        mPlayer = findViewById(R.id.player_local);
        mOrientationUtils = new OrientationUtils(this, mPlayer);
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    void checkReadStroage() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        initPlayerConfig();
    }

    @OnPermissionDenied(Manifest.permission.READ_EXTERNAL_STORAGE)
    void onReadStroageDenied() {
        Toast.makeText(mContext, "文件读取权限被拒绝！", Toast.LENGTH_SHORT).show();
    }

    @OnShowRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
    void showRationaleForReadStroage(PermissionRequest request) {
        showRationaleDialog("需要文件读取权限~", request);
    }

    @OnNeverAskAgain(Manifest.permission.READ_EXTERNAL_STORAGE)
    void onReadStroageNeverAsk() {
        Toast.makeText(mContext, "文件读取权限被禁止，不再询问", Toast.LENGTH_SHORT).show();
    }

    private void initPlayerConfig() {
        if (TextUtils.isEmpty(mVideoUrl))
            return;

        ImageView coverIv = new ImageView(mContext);
        coverIv.setImageResource(R.drawable.camera_cover);

        new GSYVideoOptionBuilder()
                .setThumbImageView(coverIv)//设置封面
                .setVideoTitle("小米笔记本设计视频")//设置视频标题
                .setIsTouchWiget(true)//是否支持手势控制
                .setLooping(true)//是否循环播放
                .setCacheWithPlay(true)//是否边播边缓存
                .setUrl(mVideoUrl)//播放地址
                .build(mPlayer);


        //返回按钮
        mPlayer.getBackButton().setVisibility(View.VISIBLE);

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LocalActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    private void showRationaleDialog(@NonNull String message, final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage(message)
                .show();
    }
}
