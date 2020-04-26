package com.coding.zxm.android_tittle_tattle;

import com.coding.zxm.android_tittle_tattle.algorithm.linkedlist.SingleLinkedList;

/**
 * Created by ZhangXinmin on 2020/4/26.
 * Copyright (c) 2020 . All rights reserved.
 */
public class Test {
    public static void main(String[] args) {

        SingleLinkedList<Integer> list = new SingleLinkedList<>();
        for (int i = 1; i < 6; i++) {
            list.add(i);
        }

        System.out.println("last : " + list.getLast().t);
        System.out.println("last : " + reverseList(list.getFirst()).t);

    }

    public static SingleLinkedList.ListNode reverseList(SingleLinkedList.ListNode head) {
//        SingleLinkedList.ListNode pre = null;
//        SingleLinkedList.ListNode temp= null;
        SingleLinkedList.ListNode curr = head;
        while (curr.next != null) {
            curr = curr.next;
        }
        return curr;
    }

}
