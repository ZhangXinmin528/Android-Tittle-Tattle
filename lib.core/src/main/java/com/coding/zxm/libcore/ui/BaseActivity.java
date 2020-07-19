package com.coding.zxm.libcore.ui;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

    protected void setTitle(@NonNull String title, @IdRes int id) {
        setTitle(title, null, id);
    }

    protected void setTitle(@NonNull String title, @NonNull String subTitle, @IdRes int id) {
        final Toolbar toolbar = findViewById(id);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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
