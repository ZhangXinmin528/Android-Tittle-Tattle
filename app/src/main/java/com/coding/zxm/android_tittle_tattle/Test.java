package com.coding.zxm.android_tittle_tattle;

import java.util.Arrays;

/**
 * Created by ZhangXinmin on 2020/4/26.
 * Copyright (c) 2020 . All rights reserved.
 */
public class Test {
    public static void main(String[] args) {
        String input = "12&34&5";
        final String[] result = input.split("a");
        System.out.println(Arrays.asList(result));

    }
}
