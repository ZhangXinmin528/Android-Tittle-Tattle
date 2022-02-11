package com.zxm.coding.lib.algorithm.recommend.top;

//给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
//
// 说明：
//
// 你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？
//
// 示例 1:
//
// 输入: [2,2,1]
//输出: 1
//
//
// 示例 2:
//
// 输入: [4,1,2,1,2]
//输出: 4
// Related Topics 位运算 哈希表

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZhangXinmin on 2020/6/18.
 * Copyright (c) 2020 . All rights reserved.
 */
public class T136 {

    public static void main(String[] args) {
        final int value = new T136().singleNumber(new int[]{4, 1, 2, 1, 2});
        System.out.println(value);
    }

    //[4,1,2,1,2]
    public int singleNumber(int[] nums) {
        final Map<Integer, Integer> map = new HashMap<>();
        final int size = nums.length;

        String
        for (int i = 0; i < size; i++) {
            final int value = nums[i];
            if (map.containsKey(value)) {
                map.put(value, map.get(value) + 1);
            } else {
                map.put(value, 1);
            }
        }

        for (int i = 0; i < size; i++) {
            final int value = nums[i];
            if (map.get(value) == 1) {
                return value;
            }
        }
        return -1;
    }
}
