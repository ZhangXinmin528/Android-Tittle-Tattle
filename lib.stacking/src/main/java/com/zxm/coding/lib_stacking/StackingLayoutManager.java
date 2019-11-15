package com.zxm.coding.lib_stacking;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by ZhangXinmin on 2019/11/12.
 * Copyright (c) 2019 . All rights reserved.
 * Realize the effect of card stacking
 */
public class StackingLayoutManager extends RecyclerView.LayoutManager {

    private static final String TAG = "StackingLayoutManager";

    private static final int NO_VALUE = -1;

    /**
     * Default visiable item count.
     */
    private static final int DEFAULT_VISIABLE_COUNT = 2;

    private static final int DEFAULT_PADDING_VALUE = 12;

    private Context mContext;

    private RecyclerView mRecyclerView;
    private ItemTouchHelper mItemTouchHelper;

    private ItemTouchHelper.Callback mCallback = new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlags = 0;
            int swipeFlags = 0;
            if (recyclerView != null) {
                final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof StackingLayoutManager) {
                    swipeFlags = ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
                }
            }
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                              RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            final long itemId = viewHolder.getLayoutPosition();
            switch (direction) {
                case ItemTouchHelper.RIGHT:
                    Toast.makeText(mContext, "onSwiped()..direction : RIGHT" + "..position : " + itemId, Toast.LENGTH_SHORT).show();
                    break;
                case ItemTouchHelper.LEFT:
                    Toast.makeText(mContext, "onSwiped()..direction : LEFT" + "..position : " + itemId, Toast.LENGTH_SHORT).show();
                    break;
            }

            viewHolder.itemView.setOnTouchListener(null);
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            return true;
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                                @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                int actionState, boolean isCurrentlyActive) {

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                final int position = recyclerView.getChildLayoutPosition(viewHolder.itemView);
                final float swipeThreshold = recyclerView.getWidth() * getSwipeThreshold(viewHolder);
                float ratio = dX / swipeThreshold;

                if (ratio > 1) {
                    ratio = 1;
                } else if (ratio < -1) {
                    ratio = -1;
                }
                viewHolder.itemView.setRotation(ratio * 15);

                final int childCount = recyclerView.getChildCount();

                for (int i = 1; i < childCount-1; i++) {
                    final View childView = recyclerView.getChildAt(i);
                    final int index = childCount - position - 1;
                    if (childView != null) {
                        childView.setScaleX(1 - index * 0.1f + Math.abs(ratio) * 0.1f);
                        childView.setScaleY(1 - index * 0.1f + Math.abs(ratio) * 0.1f);
                        childView.setTranslationY(index - Math.abs(ratio) * childView.getMeasuredHeight() / 14);
                    }
                }
            }
        }

        @Override
        public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            viewHolder.itemView.setRotation(0f);
        }
    };


    public StackingLayoutManager(Context context) {
        mContext = context;
        init();
    }

    private void init() {

        mItemTouchHelper = new ItemTouchHelper(mCallback);
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onAttachedToWindow(RecyclerView view) {
        super.onAttachedToWindow(view);
        if (view != null) {
            mItemTouchHelper.attachToRecyclerView(view);
            mRecyclerView = view;
        }
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

        final int size = itemCount <= DEFAULT_VISIABLE_COUNT ?
                itemCount - 1 : DEFAULT_VISIABLE_COUNT;

        for (int i = size; i >= 0; i--) {
            final View childView = recycler.getViewForPosition(i);
            //add view to the RecyclerView
            addView(childView);
            measureChildWithMargins(childView, 0, 0);

            layoutChildView(childView, i);
        }


    }

    /**
     * Lay out all relevant child views from the given adapter.
     *
     * @param childView child view
     * @param position
     */
    private void layoutChildView(@NonNull View childView, final int position) {
        if (childView == null) {
            return;
        }

        final int parentWidth = getWidth();
        final int parentHeight = getHeight();

        final int childWidth = getDecoratedMeasuredWidth(childView);
        final int childHeight = getDecoratedMeasuredHeight(childView);

        final int left = (parentWidth - childWidth) / 2;
        final int top = (parentHeight - childHeight) / 2;
        final int right = left + childWidth;
        final int bottom = top + childHeight;

        layoutDecoratedWithMargins(childView, left, top, right, bottom);

        childView.setScaleX(1 - position * 0.1f);
        childView.setScaleY(1 - position * 0.1f);
        childView.setTranslationY(position * (parentHeight - childHeight) / 9);

        childView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mRecyclerView != null) {
                    final RecyclerView.ViewHolder viewHolder = mRecyclerView.getChildViewHolder(v);
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        if (mItemTouchHelper != null) {
                            mItemTouchHelper.startSwipe(viewHolder);
                        }
                    }
                }
                return false;
            }
        });
    }


}
