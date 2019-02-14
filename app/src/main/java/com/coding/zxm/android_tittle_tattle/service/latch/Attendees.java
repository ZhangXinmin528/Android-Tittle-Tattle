package com.coding.zxm.android_tittle_tattle.service.latch;

/**
 * Created by ZhangXinmin on 2019/2/14.
 * Copyright (c) 2018 . All rights reserved.
 */
public class Attendees implements Runnable {
    private String name;
    private final VideoController controller;

    public Attendees(String name, VideoController controller) {
        // TODO Auto-generated constructor stub
        this.controller = controller;
        this.name = name;
    }

    @Override
    public void run() {
        synchronized (controller) {
            // TODO Auto-generated method stub
            final long millis = (long) (Math.random() * 10);
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            controller.arrive(name);
        }

    }
}
