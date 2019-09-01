package com.coding.zxm.recyclerviewhelper.viewholder;

import android.graphics.Bitmap;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.coding.zxm.recyclerviewhelper.AbsRecyclerAdapter;
import com.coding.zxm.recyclerviewhelper.listener.OnItemChildClickListener;
import com.coding.zxm.recyclerviewhelper.listener.OnItemClickListener;

/**
 * Created by ZhangXinmin on 2019/8/29.
 * Copyright (c) 2019 . All rights reserved.
 */
public final class BaseViewHolder extends RecyclerView.ViewHolder {

    private final SparseArray<View> mViews;

    private final View mItemView;

    private AbsRecyclerAdapter mAdapter;

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        mViews = new SparseArray<>();

        mItemView = itemView;
    }

    /**
     * Set the adapter for this view holder{@link BaseViewHolder}.
     *
     * @param adapter the adapter for {@link RecyclerView}
     */
    public void setAdapter(@NonNull AbsRecyclerAdapter adapter) {
        this.mAdapter = adapter;
    }

    //For TextView

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

    //For ImageView

    /**
     * Sets a drawable as the content of this ImageView.
     *
     * @param viewId the ID to search for
     * @param resId  the resource identifier of the drawable
     * @return
     */
    public BaseViewHolder setImageResource(@IdRes int viewId, @DrawableRes int resId) {
        final View view = getView(viewId);
        if (view != null) {
            if (view instanceof ImageView) {
                final ImageView imageView = (ImageView) view;
                imageView.setImageResource(resId);
            }
        }
        return this;

    }

    /**
     * Sets a Bitmap as the content of this ImageView.
     *
     * @param viewId the ID to search for
     * @param bm     the resource identifier of the drawable
     * @return
     */
    public BaseViewHolder setImageBitmap(@IdRes int viewId, @NonNull Bitmap bm) {
        final View view = getView(viewId);
        if (view != null) {
            if (view instanceof ImageView) {
                final ImageView imageView = (ImageView) view;
                imageView.setImageBitmap(bm);
            }
        }
        return this;

    }

    //For CheckBox

    /**
     * Changes the checked state of this button.
     *
     * @param viewId    the ID to search for
     * @param isChecked true to check the button, false to uncheck it
     * @return
     */
    public BaseViewHolder setChecked(@IdRes int viewId, boolean isChecked) {
        final View view = getView(viewId);
        if (view != null) {
            if (view instanceof CheckBox) {
                final CheckBox checkBox = (CheckBox) view;
                checkBox.setChecked(isChecked);
            }
        }
        return this;
    }

    //For View

    /**
     * Sets the background color for this view.
     *
     * @param viewId the ID to search for
     * @param color  the color of the background
     * @return
     */
    public BaseViewHolder setBackgroundColor(@IdRes int viewId, @ColorInt int color) {
        final View view = getView(viewId);
        if (view != null) {
            view.setBackgroundColor(color);
        }
        return this;
    }

    /**
     * Set the background to a given resource. The resource should refer to
     * a Drawable object or 0 to remove the background.
     *
     * @param viewId the ID to search for
     * @param resid  The identifier of the resource.
     * @return
     */
    public BaseViewHolder setBackgroundResource(@IdRes int viewId, @DrawableRes int resid) {
        final View view = getView(viewId);
        if (view != null) {
            view.setBackgroundResource(resid);
        }
        return this;
    }

    /**
     * Sets the opacity of the view to a value from 0 to 1, where 0 means the view is
     * completely transparent and 1 means the view is completely opaque.
     *
     * @param viewId the ID to search for
     * @param alpha  The opacity of the view.
     * @return
     */
    public BaseViewHolder setAlpha(@IdRes int viewId, @FloatRange(from = 0.0, to = 1.0) float alpha) {
        final View view = getView(viewId);
        if (view != null) {
            view.setAlpha(alpha);
        }
        return this;
    }

    /**
     * Set the visibility state of this view.
     *
     * @param viewId     the ID to search for
     * @param visibility One of {@link View#VISIBLE}, {@link View#INVISIBLE}, or {@link View#GONE}.
     * @return
     */
    public BaseViewHolder setVisiable(@IdRes int viewId, int visibility) {
        final View view = getView(viewId);
        if (view != null) {
            view.setVisibility(View.VISIBLE);
        }
        return this;
    }

    /**
     * Enables or disables click events for this view.
     *
     * @param viewId    the ID to search for
     * @param clickable true to make the view clickable, false otherwise
     * @return
     */
    public BaseViewHolder setClickable(@IdRes int viewId, boolean clickable) {
        final View view = getView(viewId);
        if (view != null) {
            view.setClickable(clickable);
        }
        return this;
    }

    /**
     * Sets the tag associated with this view.
     *
     * @param viewId the ID to search for
     * @param tag    an Object to tag the view with
     * @return
     */
    public BaseViewHolder setTag(@IdRes int viewId, @NonNull Object tag) {
        final View view = getView(viewId);
        if (view != null) {
            view.setTag(tag);
        }
        return this;
    }

    //Set listener

    /**
     * Register a callback to be invoked when this view is clicked. If this view is not
     * clickable, it becomes clickable.
     *
     * @return
     */
    @Deprecated
    public BaseViewHolder setOnItemClickListener() {
        if (mItemView != null) {
            if (!mItemView.isClickable()) {
                mItemView.setClickable(true);
            }
            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mAdapter != null) {
                        final OnItemClickListener listener = mAdapter.getOnItemClickListener();
                        if (listener != null) {
                            listener.onItemClick(mAdapter, mItemView, getItemLayoutPosition());
                        }
                    }
                }
            });
        }
        return this;
    }

    /**
     * Register a callback to be invoked when this view is clicked.It's usally in the itemview.
     *
     * @param viewId the ID to search for
     * @return
     */
    public BaseViewHolder setOnItemChildClickListener(@IdRes int viewId) {
        final View view = getView(viewId);
        if (view != null) {
            if (!view.isClickable()) {
                view.setClickable(true);
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mAdapter != null) {
                        final OnItemChildClickListener listener =
                                mAdapter.getItemChildClickListener();
                        if (listener != null) {
                            listener.onItemChildClick(mAdapter, view, getItemLayoutPosition());
                        }
                    }
                }
            });
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

    private int getItemLayoutPosition() {
        final int layoutPosition = getLayoutPosition();
        if (mAdapter != null && layoutPosition >= mAdapter.getHeaderLayoutCount()) {
            return layoutPosition - mAdapter.getHeaderLayoutCount();
        }
        return 0;
    }
}
