package com.coding.zxm.lib_pool.pool;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by ZhangXinmin on 2019/5/8.
 * Copyright (c) 2018 . All rights reserved.
 */
public class SynchronizedPool<T> implements Pool<T> {

    private final Object poolLock = new Object();

    private int maxPoolSize;
    private boolean isPoolActive;

    private LinkedBlockingQueue<T> queue;
    private ObjectFactory<T> factory;

    public SynchronizedPool(@IntRange(from = 1) int maxPoolSize, @NonNull ObjectFactory<T> factory) {
        if (maxPoolSize <= 0) {
            throw new IllegalArgumentException("The max pool size must be > 0");
        } else {
            this.maxPoolSize = maxPoolSize;
        }

        isPoolActive = true;
        this.factory = factory;
        queue = new LinkedBlockingQueue<>(maxPoolSize);

        fillUpPool();
    }

    @Nullable
    @Override
    public T get() {
        synchronized (this.poolLock) {
            if (isPoolActive) {
                return this.queue.poll();
            } else {
                throw new UnsupportedOperationException("Object pool is not active!");
            }
        }
    }

    @Override
    public boolean release(@NonNull T instance) {
        synchronized (this.poolLock) {
            if (isPoolActive) {
                if (this.queue.contains(instance)) {
                    throw new IllegalStateException("Already in the pool!");
                } else {
                    try {
                        queue.put(instance);
                        return true;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return false;
                    }
                }
            } else {
                throw new UnsupportedOperationException("Object pool is not active!");
            }
        }
    }

    @Override
    public void shutDown() {
        if (factory != null) {
            factory = null;
        }
        if (queue != null && !queue.isEmpty()) {
            queue.clear();
            queue = null;
        }
        isPoolActive = false;

    }

    private void fillUpPool() {
        for (int i = 0; i < maxPoolSize; i++) {
            try {
                queue.put(factory.createNew());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getElementSize() {
        if (queue != null) {
            return queue.size();
        } else {
            return 0;
        }
    }
}
