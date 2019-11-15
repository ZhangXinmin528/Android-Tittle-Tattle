package com.zxm.coding.lib_stacking;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by ZhangXinmin on 2019/11/12.
 * Copyright (c) 2019 . All rights reserved.
 * Realize the effect of card stacking
 */
public class StackingLayoutManager extends RecyclerView.LayoutManager {

    public static final int DEFAULT_TRANSLATE_Y = 14;
    public static final float DEFAULT_ROTATE_DEGREE = 15f;
    private static final String TAG = "StackingLayoutManager";
    private static final int NO_VALUE = -1;
    /**
     * Default visiable item count.
     */
    private static final int DEFAULT_VISIABLE_COUNT = 2;
    private static final int DEFAULT_PADDING_VALUE = 12;
    private static final float DEFAULT_SCALE = 0.1f;
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

            View itemView = viewHolder.itemView;
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                float ratio = dX / (recyclerView.getWidth() * getSwipeThreshold(viewHolder));
                if (ratio > 1) {
                    ratio = 1;
                } else if (ratio < -1) {
                    ratio = -1;
                }
                itemView.setRotation(ratio * DEFAULT_ROTATE_DEGREE);
                int childCount = recyclerView.getChildCount();
                if (childCount > DEFAULT_VISIABLE_COUNT) {
                    for (int position = 1; position < childCount - 1; position++) {
                        int index = childCount - position - 1;
                        View view = recyclerView.getChildAt(position);
                        view.setScaleX(1 - index * DEFAULT_SCALE + Math.abs(ratio) * DEFAULT_SCALE);
                        view.setScaleY(1 - index * DEFAULT_SCALE + Math.abs(ratio) * DEFAULT_SCALE);
                        view.setTranslationY((index - Math.abs(ratio)) * itemView.getMeasuredHeight() / DEFAULT_TRANSLATE_Y);
                    }
                } else {
                    for (int position = 0; position < childCount - 1; position++) {
                        int index = childCount - position - 1;
                        View view = recyclerView.getChildAt(position);
                        view.setScaleX(1 - index * DEFAULT_SCALE + Math.abs(ratio) * DEFAULT_SCALE);
                        view.setScaleY(1 - index * DEFAULT_SCALE + Math.abs(ratio) * DEFAULT_SCALE);
                        view.setTranslationY((index - Math.abs(ratio)) * itemView.getMeasuredHeight() / DEFAULT_TRANSLATE_Y);
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

        final int itemCount = getItemCount();

        Log.d(TAG, "onLayoutChildren()..is pre layout:" + state.isPreLayout());

        //confirm adapter has items and RecyclerView has prepares to layout
        if (itemCount == 0 || state.isPreLayout())
            return;
        //Temporarily detach and scrap all currently attached child views.
        detachAndScrapAttachedViews(recycler);

        if (itemCount > DEFAULT_VISIABLE_COUNT) {
            for (int position = DEFAULT_VISIABLE_COUNT; position >= 0; position--) {
                final View view = recycler.getViewForPosition(position);
                addView(view);
                measureChildWithMargins(view, 0, 0);
                int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
                int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);

                layoutDecoratedWithMargins(view, widthSpace / 2, heightSpace / 5,
                        widthSpace / 2 + getDecoratedMeasuredWidth(view),
                        heightSpace / 5 + getDecoratedMeasuredHeight(view));

                if (position == DEFAULT_VISIABLE_COUNT) {
                    view.setScaleX(1 - (position - 1) * DEFAULT_SCALE);
                    view.setScaleY(1 - (position - 1) * DEFAULT_SCALE);
                    view.setTranslationY((position - 1) * view.getMeasuredHeight() / DEFAULT_TRANSLATE_Y);
                } else if (position > 0) {
                    view.setScaleX(1 - position * DEFAULT_SCALE);
                    view.setScaleY(1 - position * DEFAULT_SCALE);
                    view.setTranslationY(position * view.getMeasuredHeight() / DEFAULT_TRANSLATE_Y);
                }
            }
        } else {
            for (int position = itemCount - 1; position >= 0; position--) {
                final View view = recycler.getViewForPosition(position);
                addView(view);
                measureChildWithMargins(view, 0, 0);
                int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
                int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);
                layoutDecoratedWithMargins(view, widthSpace / 2, heightSpace / 5,
                        widthSpace / 2 + getDecoratedMeasuredWidth(view),
                        heightSpace / 5 + getDecoratedMeasuredHeight(view));

                if (position > 0) {
                    view.setScaleX(1 - position * DEFAULT_SCALE);
                    view.setScaleY(1 - position * DEFAULT_SCALE);
                    view.setTranslationY(position * view.getMeasuredHeight() / DEFAULT_TRANSLATE_Y);
                }
            }
        }

    }

}
