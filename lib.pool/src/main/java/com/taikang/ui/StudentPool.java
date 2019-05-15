package com.taikang.ui;

import com.taikang.lib_pool.SynchronizedPool;
import com.taikang.model.Student;

/**
 * Created by ZhangXinmin on 2019/5/15.
 * Copyright (c) 2018 . All rights reserved.
 */
public class StudentPool extends SynchronizedPool<Student> {
    public StudentPool(int maxPoolSize) {
        super(maxPoolSize);
    }
}
