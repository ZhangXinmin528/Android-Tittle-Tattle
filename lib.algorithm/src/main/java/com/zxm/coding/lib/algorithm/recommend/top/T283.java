package com.zxm.coding.lib.algorithm.recommend.top;
//给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
//
// 示例:
//
// 输入: [0,1,0,3,12]
//输出: [1,3,12,0,0]
//
// 说明:
//
//
// 必须在原数组上操作，不能拷贝额外的数组。
// 尽量减少操作次数。
//
// Related Topics 数组 双指针


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.Arrays;

/**
 * Created by ZhangXinmin on 2020/6/18.
 * Copyright (c) 2020 . All rights reserved.
 */
public class T283 {
    public static void main(String[] args) {
        new T283().moveZeroes(new int[]{0, 1, 0, 3, 12});
        new T283().moveZeroes(new int[]{0, 1, 0, 1, 3, 12});
    }

    //[0,1,0,3,12]
    public void moveZeroes(int[] nums) {
        int last = -1;
        int lastIndex = -1;
        if (nums != null && nums.length > 0) {
            final int length = nums.length;
            lastIndex = length - 1;
            for (int i = 0; i < length / 2; i++) {

                final int start = nums[i];
                if (start == 0) {
                    for (; lastIndex > length / 2 - 1; lastIndex--) {
                        last = nums[lastIndex];
                        if (last != 0) {
                            System.out.println("start : " + start + "..lastIndex : " + lastIndex);
                            continue;
                        }
                    }
                    nums[i] = last;
                    nums[lastIndex] = 0;
                }
            }

            System.out.println(Arrays.toString(nums));
        }
    }
}
