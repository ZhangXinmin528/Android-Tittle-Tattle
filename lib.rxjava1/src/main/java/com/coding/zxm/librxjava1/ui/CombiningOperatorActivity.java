package com.coding.zxm.librxjava1.ui;


import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;

import com.coding.zxm.libcore.ui.BaseActivity;
import com.coding.zxm.librxjava1.R;
import com.zxm.utils.core.log.MLogger;


import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;

/**
 * Created by ZhangXinmin on 2018/1/21.
 * Copyright (c) 2018 . All rights reserved.
 * 组合操作符界面
 */

public class CombiningOperatorActivity extends BaseActivity {

    @Override
    protected Object setLayout() {
        return R.layout.activity_operator_combining;
    }

    @Override
    protected void initParamsAndValues() {
        Intent intent = getIntent();
        if (intent != null) {
            final String label = intent.getStringExtra("params_label");
            if (!TextUtils.isEmpty(label)) {
                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setTitle(label);
                }
            }
        }
    }

    @Override
    protected void initViews() {

        //1.Merge操作符
//        operatorMerge();

        //2.ZIP操作符
        operatorZIP();
    }

    /**
     * 1.Merge操作符
     */
    private void operatorMerge() {
        Observable observableBig =
                Observable
                        .just("A", "B", "C");

        Observable observableSmall =
                Observable
                        .just("a", "b", "c");

        Observable
                .merge(observableSmall, observableBig)
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        MLogger.i(TAG,"operatorMerge..onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLogger.e(TAG,"operatorMerge..onError");
                    }

                    @Override
                    public void onNext(String s) {
                        MLogger.i(TAG,"operatorMerge..onNext:" + s);
                    }
                });

    }

    /**
     * 2.ZIP操作符
     */
    private void operatorZIP() {

        Observable letterObser =
                Observable.just("你好！", "Hello!", "Hi!");

        Observable numObser =
                Observable.just("A", "B", "C");

        Observable
                .zip(letterObser, numObser, new Func2<String, String, String>() {
                    @Override
                    public String call(String s, String s2) {
                        return s + s2;
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        MLogger.i(TAG,"operatorZIP..onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLogger.e(TAG,"operatorZIP..onError");
                    }

                    @Override
                    public void onNext(String s) {
                        MLogger.i(TAG,"operatorZIP..onNext:" + s);
                    }
                });
    }

    /**
     * 3.Join操作符
     */
    private void operatorJoin() {

    }


}
