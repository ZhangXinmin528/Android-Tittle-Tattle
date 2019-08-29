package com.coding.zxm.recyclerviewhelper.viewholder;

import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

/**
 * Created by ZhangXinmin on 2019/8/29.
 * Copyright (c) 2019 . All rights reserved.
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    private final SparseArray<View> mViews;

    private final View mItemView;

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        mViews = new SparseArray<>();

        mItemView = itemView;
    }

    /**
     * Sets the text to be displayed using a string resource identifier.Usually for{@link TextView}.
     *
     * @param viewId the ID to search for
     * @param resid  the resource identifier of the string resource to be displayed
     * @return
     */
    public BaseViewHolder setText(@IdRes int viewId, @StringRes int resid) {
        final View view = getView(viewId);
        if (view != null) {
            if (view instanceof TextView) {
                final TextView textView = (TextView) view;
                textView.setText(resid);
            }
        }
        return this;
    }

    /**
     * Sets the text to be displayed using a string resource identifier.Usually for{@link TextView}.
     *
     * @param viewId the ID to search for
     * @param text   text to be displayed
     * @return
     */
    public BaseViewHolder setText(@IdRes int viewId, CharSequence text) {
        final View view = getView(viewId);
        if (view != null) {
            if (view instanceof TextView) {
                final TextView textView = (TextView) view;
                textView.setText(text);
            }
        }
        return this;
    }

    /**
     * Set the text color for the {@link TextView}.
     *
     * @param viewId the ID to search for
     * @param color  A color value in the form 0xAARRGGBB.
     * @return
     */
    public BaseViewHolder setTextColor(@IdRes int viewId, @ColorInt int color) {
        final View view = getView(viewId);
        if (view != null) {
            if (view instanceof TextView) {
                final TextView textView = (TextView) view;
                textView.setTextColor(color);
            }
        }
        return this;
    }

    /**
     * Find the view with the given ID.
     *
     * @param viewId the ID to search for
     * @param <T>    a view with given ID if found, or {@code null} otherwise
     * @return
     */
    public <T extends View> T getView(@IdRes int viewId) {
        if (viewId != View.NO_ID) {
            View view = mViews.get(viewId);
            if (view == null) {
                view = mItemView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        } else {
            return null;
        }
    }
}
