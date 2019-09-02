package com.coding.zxm.recyclerviewhelper;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coding.zxm.recyclerviewhelper.listener.OnItemChildClickListener;
import com.coding.zxm.recyclerviewhelper.listener.OnItemClickListener;
import com.coding.zxm.recyclerviewhelper.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangXinmin on 2019/8/30.
 * Copyright (c) 2019 . All rights reserved.
 */
public abstract class AbsRecyclerAdapter<D, V extends BaseViewHolder> extends RecyclerView.Adapter<V> {

    protected static final int VIEW_HEADER = 0x00000111;
    protected static final int VIEW_LOADING = 0x00000222;
    protected static final int VIEW_FOOTER = 0x00000333;
    protected static final int VIEW_EMPTY = 0x00000555;

    protected static final String TAG = "AbsRecyclerAdapter";
    protected Context mContext;
    protected LayoutInflater mLayoutInflater;

    private int mLayoutResId;
    private List<D> mData;

    //header
    private LinearLayoutCompat mHeaderLayout;

    //Item click
    private OnItemClickListener mItemClickListener;

    //Item child click
    private OnItemChildClickListener mItemChildClickListener;

    public AbsRecyclerAdapter(@LayoutRes int layoutResId, @NonNull List<D> data) {
        this.mData = data == null ? new ArrayList<>() : data;
        if (layoutResId != -1) {
            this.mLayoutResId = layoutResId;
        }
        Log.d(TAG, "构造器~");
    }

    @NonNull
    @Override
    public V onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Log.d(TAG, "onCreateViewHolder..viewType : " + viewType);
        V baseViewHolder = null;
        this.mContext = viewGroup.getContext();
        this.mLayoutInflater = LayoutInflater.from(mContext);

        switch (viewType) {
            case VIEW_HEADER:

                break;
            case VIEW_LOADING:

                break;
            case VIEW_EMPTY:

                break;
            case VIEW_FOOTER:

                break;
            default:
                baseViewHolder = createBaseViewHolder(viewGroup);
                break;
        }
        baseViewHolder.setAdapter(this);
        return baseViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull V holder, int position) {
        final int viewType = holder.getItemViewType();

        Log.d(TAG, "onBindViewHolder..viewType : " + viewType + ".. position : " + position);

        switch (viewType) {
            case VIEW_HEADER:

                break;
            case VIEW_LOADING:

                break;
            case VIEW_EMPTY:

                break;
            case VIEW_FOOTER:

                break;
            default:
                bindView(holder, getItem(position));
                break;
        }

    }

    /**
     * Implement this method and then bind views with the holder.
     *
     * @param holder a viewholder.
     * @param data   an element of data
     */
    protected abstract void bindView(V holder, D data);

    @Override
    public int getItemCount() {
        if (mData != null && !mData.isEmpty()) {
            return mData.size();
        }
        return 0;
    }

    /**
     * Get the data element int the position.
     *
     * @param position the position
     * @return
     */
    public D getItem(@IntRange(from = 0) int position) {
        final int size = getItemCount();
        if (size > 0) {
            if (position < size) {
                return mData.get(position);
            }
        }
        return null;
    }

    /**
     * Get the instance of {@link OnItemClickListener}.
     *
     * @return
     */
    public OnItemClickListener getOnItemClickListener() {
        return mItemClickListener;
    }

    /**
     * Register a callback to be invoked when an item in this RecyclerView has
     * been clicked.
     *
     * @param listener The callback that will be invoked.
     */
    @Deprecated
    public void setOnItemClickListener(@NonNull OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    /**
     * Get the instance of {@link OnItemChildClickListener}.
     *
     * @return
     */
    public OnItemChildClickListener getItemChildClickListener() {
        return mItemChildClickListener;
    }

    /**
     * Register a callback to be invoked when an item child view in this RecyclerView has
     * been clicked.
     *
     * @param listener The callback that will be invoked.
     */
    public void setItemChildClickListener(@NonNull OnItemChildClickListener listener) {
        this.mItemChildClickListener = listener;
    }

    //HeaderLayout

    /**
     * Add the view at last or update the view at the specified position in the group horizontally.
     *
     * @param header header view
     * @param index the positon
     * @return
     */
    public int addOrUpdateHorizontally(@NonNull View header, @IntRange(from = 0) int index) {
        return addOrUpdateHeader(header, index, LinearLayoutCompat.HORIZONTAL);
    }

    /**
     * Add the view at last or update the view at the specified position in the group vertically.
     *
     * @param header header view
     * @param index the positon
     * @return
     */
    public int addOrUpdateVertically(@NonNull View header, @IntRange(from = 0) int index) {
        return addOrUpdateHeader(header, index, LinearLayoutCompat.VERTICAL);
    }

    /**
     * Add the view at last or update the view at the specified position in the group.
     *
     * @param header      header view
     * @param index       the positon
     * @param orientation {@code #LinearLayoutCompat.VERTICAL} or {@code #LinearLayoutCompat.HORIZONTAL}
     * @return
     */
    public int addOrUpdateHeader(@NonNull View header, @IntRange(from = 0) int index, int orientation) {
        if (header != null) {
            if (mHeaderLayout == null) {
                final int childCount = mHeaderLayout.getChildCount();
                if (index > childCount) {
                    return addHeaderView(header, index, orientation);
                } else {
                    mHeaderLayout.removeViewAt(index);
                    mHeaderLayout.addView(header, index);
                    return index;
                }
            }
        }
        return -1;
    }

    /**
     * Add header view to the recyclerview.
     *
     * @param header      header view
     * @param index       position in the header layout
     * @param orientation {@code #LinearLayoutCompat.VERTICAL} or {@code #LinearLayoutCompat.HORIZONTAL}
     * @return
     */
    protected int addHeaderView(@NonNull View header, @IntRange(from = 0) int index, int orientation) {
        if (mHeaderLayout == null) {
            mHeaderLayout = new LinearLayoutCompat(header.getContext());
            mHeaderLayout.setOrientation(orientation);
            if (orientation == LinearLayoutCompat.VERTICAL) {
                mHeaderLayout.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
            } else {
                mHeaderLayout.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                ));
            }
        }

        final int childCount = mHeaderLayout.getChildCount();
        if (index < 0 || index > childCount) {
            index = childCount;
        }
        if (header != null) {
            mHeaderLayout.addView(header, index);
            return index;
        } else {
            return -1;
        }
    }

    /**
     * Get the header layout count.
     *
     * @return
     */
    public int getHeaderLayoutCount() {
        if (mHeaderLayout == null || mHeaderLayout.getChildCount() == 0) {
            return 0;
        }
        return 1;
    }

    /**
     * Get the Header Layout.
     *
     * @return
     */
    public LinearLayoutCompat getHeaderLayout() {
        return mHeaderLayout;
    }

    private V createBaseViewHolder(@NonNull ViewGroup viewGroup) {
        return (V) new BaseViewHolder(getItemView(mLayoutResId, viewGroup));
    }

    //Inflate a new item view hierarchy from the specified xml resource.
    private View getItemView(@LayoutRes int layoutResId, @NonNull ViewGroup parent) {
        return mLayoutInflater.inflate(layoutResId, parent, false);
    }

}
