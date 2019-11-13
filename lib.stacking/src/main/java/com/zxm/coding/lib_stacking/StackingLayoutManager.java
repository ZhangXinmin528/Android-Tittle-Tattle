package com.zxm.coding.lib_stacking;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ZhangXinmin on 2019/11/12.
 * Copyright (c) 2019 . All rights reserved.
 * Realize the effect of card stacking
 */
public class StackingLayoutManager extends RecyclerView.LayoutManager {

    private static final String TAG = "StackingLayoutManager";
    /**
     * Default visiable item count.
     */
    private static final int DEFAULT_VISIABLE_COUNT = 3;

    private int mCurrentPosition;

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        Log.d(TAG, "onLayoutChildren()..is pre layout:" + state.isPreLayout());

        final int itemCount = getItemCount();
        //confirm adapter has items and RecyclerView has prepares to layout
        if (itemCount == 0 || state.isPreLayout())
            return;
        //Temporarily detach and scrap all currently attached child views.
        detachAndScrapAttachedViews(recycler);
        final int parentWidth = getWidth();
        final int parentHeight = getHeight();

        for (int i = 3; i >= 0; i--) {
            final View childView = recycler.getViewForPosition(i);
            //add view to the RecyclerView
            addView(childView);
            measureChildWithMargins(childView, 0, 0);

            final int childWidth = getDecoratedMeasuredWidth(childView);
            final int childHeight = getDecoratedMeasuredHeight(childView);

            int left, top, right, bottom;
            left = (parentWidth - childWidth) / 2;
            top = (parentHeight - childHeight) / 2;
            right = left + childWidth;
            bottom = top + childHeight;

            layoutDecoratedWithMargins(childView, left, top, right, bottom);

//            childView.setScaleX(1 - i * 0.05f);
//            childView.setScaleY(1 - i * 0.05f);
            childView.setTranslationX(childWidth * i / 15f);
            childView.setTranslationY(childHeight * i / 20f);


        }

    }


}
