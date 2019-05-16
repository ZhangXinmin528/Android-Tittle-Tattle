package com.coding.zxm.libutil;

import android.text.TextUtils;

/**
 * Created by ZhangXinmin on 2019/1/30.
 * Copyright (c) 2018 . All rights reserved.
 */
public final class DisplayUtil {

    public static final String PARAMS_LABEL = "params_label";

    private DisplayUtil() {
        throw new UnsupportedOperationException("U con't do this!");
    }

    public static String isEmpty(String value) {

        if (TextUtils.isEmpty(value)) {
            return "--";
        } else {
            return value;
        }
    }
}
