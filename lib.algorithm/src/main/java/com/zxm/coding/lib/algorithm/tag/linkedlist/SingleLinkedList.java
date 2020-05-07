package com.zxm.coding.lib.algorithm.tag.linkedlist;

/**
 * Created by ZhangXinmin on 2020/4/26.
 * Copyright (c) 2020 . All rights reserved.
 */
public final class SingleLinkedList<T> {
    private ListNode first;
    private int size;

    public SingleLinkedList() {
        size = 0;
    }

    public boolean add(T t) {
        final ListNode node = new ListNode(t);
        if (first == null) {
            first = node;
            size++;
            return true;
        } else {
            try {
                ListNode last = getNote(size - 1);
                last.next = node;
                size++;
                return true;
            } catch (UnsupportedOperationException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    public ListNode getLast() {
        return getNote(size - 1);
    }

    private ListNode getNote(int index) {
        if (index < 0 || index > size) {
            throw new UnsupportedOperationException("index illegal~");
        }
        if (index == 0) {
            return first;
        }

        ListNode listNode = first;
        for (int i = 0; i < index; i++) {
            listNode = listNode.next;
        }
        return listNode;
    }

    public ListNode getFirst() {
        return first;
    }

    /* private boolean remove() {
        if (first == null) {
            return false;
        }
        try {
            final ListNode last = getNote(size-1);

        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        }
        return false;
    }*/

    public int getSize() {
        return size;
    }


}
