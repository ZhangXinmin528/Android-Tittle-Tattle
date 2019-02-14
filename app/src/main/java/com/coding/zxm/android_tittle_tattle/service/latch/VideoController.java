package com.coding.zxm.android_tittle_tattle.service.latch;

import android.annotation.SuppressLint;

import com.coding.zxm.android_tittle_tattle.util.Logger;

import java.util.concurrent.CountDownLatch;

/**
 * Created by ZhangXinmin on 2019/2/14.
 * Copyright (c) 2018 . All rights reserved.
 */
public class VideoController implements Runnable {
    private static final String TAG = VideoController.class.getSimpleName();

    private final CountDownLatch controller;

    public VideoController(int count) {
        controller = new CountDownLatch(count);
    }

    @SuppressLint("DefaultLocale")
    public void arrive(String name) {
        Logger.e(TAG, String.format("Attendees[%s] is arrived.", name));

        controller.countDown();

        Logger.e(TAG, String.format("Remaining participants count is %d.", controller.getCount()));
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void run() {
        // TODO Auto-generated method stub
        Logger.e(TAG,
                String.format("VideoController is running, initializse count is %d.", controller.getCount()));

        try {
            controller.await();

            Logger.e(TAG, "Initialition is finished!");

        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
