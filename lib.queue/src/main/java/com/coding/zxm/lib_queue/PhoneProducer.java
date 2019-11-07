package com.coding.zxm.lib_queue;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;

import com.coding.zxm.lib_queue.model.PhoneEntity;
import com.coding.zxm.lib_queue.polling.TaskEvent;
import com.coding.zxm.lib_queue.polling.handler.TaskProducer;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by ZhangXinmin on 2019/11/7.
 * Copyright (c) 2019 . All rights reserved.
 */
public class PhoneProducer extends TaskProducer<PhoneEntity> {

    public PhoneProducer(LinkedBlockingQueue<PhoneEntity> queue, PhoneEntity phoneEntity) {
        super(queue, phoneEntity);
    }

    @Override
    protected void reportInsertState(boolean state, @NonNull PhoneEntity phoneEntity) {
        final Intent intent = new Intent(TaskEvent.ACTION_ADD_TASK);
        intent.putExtra(TaskEvent.EXTRA_TASK_ENTITY,phoneEntity);
//        LocalBroadcastManager.getInstance()
    }
}
