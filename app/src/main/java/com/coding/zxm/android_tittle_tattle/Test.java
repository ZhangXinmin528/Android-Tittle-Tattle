package com.coding.zxm.android_tittle_tattle;

import android.util.ArraySet;

import com.coding.zxm.android_tittle_tattle.algorithm.linkedlist.SingleLinkedList;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by ZhangXinmin on 2020/4/26.
 * Copyright (c) 2020 . All rights reserved.
 */
public class Test {
    public static void main(String[] args) {
        final String data = "ADABEFFFDCBGH发货价啦AFG";
        System.out.println(dup(data));
    }

    public static String dup(String s) {

        final int lengh = s.length();
        final char[] data = s.toCharArray();
        Set<String> set = new LinkedHashSet<>();

        for (int i = 0; i < lengh; i++) {
            final char c = data[i];
            set.add(c+"");
        }
        return set.toString();
    }


}
