package com.taikang.lib_pool;

import com.taikang.model.Student;

/**
 * Created by ZhangXinmin on 2019/5/16.
 * Copyright (c) 2018 . All rights reserved.
 */
public class StudentFactory implements ObjectFactory<Student> {
    @Override
    public Student createNew() {
        return new Student();
    }
}
