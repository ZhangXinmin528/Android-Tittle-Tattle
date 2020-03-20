package com.coding.zxm.libcore.ui;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import com.coding.zxm.libcore.R;

/**
 * Created by ZhangXinmin on 2017/9/17.
 * Copyright (c) 2017 . All rights reserved.
 * The base class of activity.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected final String TAG = getClass().getSimpleName();

    protected Context mContext;
    protected Resources mResources;

    /**
     * set layout for activity
     *
     * @return
     */
    protected abstract Object setLayout();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //init base setting
        mContext = this;
        mResources = getResources();

        if (setLayout() instanceof Integer) {
            setContentView((Integer) setLayout());
        } else if (setLayout() instanceof View) {
            setContentView((View) setLayout());
        } else {
            throw new RuntimeException("You must use the method of 'setLayout()' " +
                    "to bind view for activity! ");
        }

        initParamsAndValues();

        initViews();

    }

    /**
     * init params and values for activity
     */
    protected abstract void initParamsAndValues();

    /**
     * init views for activity
     */
    protected abstract void initViews();

    protected ActionBar initToolbar() {
        final Toolbar toolbar = findViewById(R.id.toolbar_training);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        return actionBar;
    }

    protected void setTitle(@NonNull String title) {
        setTitle(title, null);
    }

    protected void setTitle(@NonNull String title, @NonNull String subTitle) {
        final ActionBar actionBar = initToolbar();
        if (actionBar != null) {
            if (!TextUtils.isEmpty(title)) {
                actionBar.setTitle(title);
            }
            if (!TextUtils.isEmpty(subTitle)) {
                actionBar.setSubtitle(subTitle);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
