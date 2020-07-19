package com.coding.zxm.libcore.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by ZhangXinmin on 2017/8/22.
 * Copyright (c) 2017 . All rights reserved.
 * Fragment基类
 */

public abstract class BaseFragment extends Fragment {

    protected Context mContext;
    protected String tag = this.getClass().getSimpleName();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initParamsAndValues();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView;
        if (setRootLayout() instanceof Integer) {
            rootView = inflater.inflate((Integer) setRootLayout(), container, false);
        } else if (setRootLayout() instanceof View) {
            rootView = (View) setRootLayout();
        } else {
            throw new ClassCastException("You must do setRootLayout method!");
        }
        if (rootView != null)
            initViews(rootView);

        return rootView;
    }

    /**
     * 设置布局
     *
     * @return
     */
    protected abstract Object setRootLayout();

    /**
     * 初始化参数
     */
    public abstract void initParamsAndValues();

    /**
     * 初始化控件
     *
     * @param rootView
     */
    protected abstract void initViews(View rootView);

}
