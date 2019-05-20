package com.coding.zxm.librxjava1.ui;


import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;

import com.coding.zxm.libcore.ui.BaseActivity;
import com.coding.zxm.librxjava1.R;
import com.zxm.utils.core.log.MLogger;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func0;

/**
 * Created by ZhangXinmin on 2018/1/21.
 * Copyright (c) 2018 . All rights reserved.
 * 创建操作符界面
 */

public class CreatingOperatorActivity extends BaseActivity {

    @Override
    protected Object setLayout() {
        return R.layout.activity_operator_creating;
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

        //1.Create 操作符
//        operatorCreate();

        //2.From操作符
//        operatorFrom();

        //3.Just操作符
//        operatorJust();

        //4.Defer操作符
//        operatorDefer();

        //5.Interval操作符
//        operatorInterval();

        //6.Range操作符
//        operatorRange();

        //7.Repeat操作符
//        operatorRepeat();

        //8.Timer操作符
        operatorTimer();
    }


    /**
     * 1.Create 操作符
     */
    private void operatorCreate() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        for (int i = 1; i < 5; i++) {
                            subscriber.onNext(i);
                        }
                        subscriber.onCompleted();
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                MLogger.i(TAG,"operatorCreate..onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                MLogger.e(TAG,"operatorCreate..onError:" + e.getMessage());
            }

            @Override
            public void onNext(Integer integer) {
                MLogger.i(TAG,"operatorCreate..onNext:" + integer);
            }
        });
    }

    /**
     * 2.From操作符
     */
    private void operatorFrom() {
        final String[] arr = new String[]{"元素1", "元素2", "元素3", "元素4", "元素5"};
        Observable.from(arr)
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        MLogger.i(TAG,"operatorFrom..onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLogger.i(TAG,"operatorFrom..onError:" + e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        MLogger.i(TAG,"operatorFrom..onNext:" + s);
                    }
                });
    }

    /**
     * 3.Just操作符
     */
    private void operatorJust() {
        Observable.just("你好！", "Hello!")
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        MLogger.i(TAG,"operatorJust..onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLogger.e(TAG,"operatorJust..onError:" + e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        MLogger.i(TAG,"operatorJust..onNext:" + s);
                    }
                });
    }

    /**
     * 4.Defer操作符
     */
    private void operatorDefer() {
        Observable.defer(new Func0<Observable<Integer>>() {
            @Override
            public Observable<Integer> call() {
                return Observable.just(10);
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                MLogger.i(TAG,"operatorDefer..onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                MLogger.e(TAG,"operatorDefer..onError:" + e.getMessage());
            }

            @Override
            public void onNext(Integer integer) {
                MLogger.i(TAG,"operatorDefer..onNext:" + integer);
            }
        });
    }

    /**
     * 5.Interval操作符
     */
    private void operatorInterval() {
        Observable.interval(2, TimeUnit.SECONDS)
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        MLogger.i(TAG,"operatorInterval..onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLogger.e(TAG,"operatorInterval..onError:" + e.getMessage());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        MLogger.i(TAG,"operatorInterval..onNext:" + aLong);
                    }
                });
    }

    /**
     * 6.Range操作符
     */
    private void operatorRange() {
        Observable.range(2, 6)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        MLogger.i(TAG,"operatorRange..onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLogger.e(TAG,"operatorRange..onError:" + e.getMessage());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        MLogger.i(TAG,"operatorRange..onNext:" + integer);
                    }
                });
    }

    /**
     * 7.Repeat操作符
     */
    private void operatorRepeat() {
        Observable.just("你好！")
                .repeat(3)
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        MLogger.i(TAG,"operatorRepeat..onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLogger.e(TAG,"operatorRepeat..onError:" + e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        MLogger.i(TAG,"operatorRepeat..onNext:" + s);
                    }
                });
    }

    /**
     * 8.Timer操作符
     */
    private void operatorTimer() {
        Observable.timer(1, TimeUnit.SECONDS)
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        MLogger.i(TAG,"operatorTimer..onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLogger.e(TAG,"operatorTimer..onError:" + e.getMessage());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        MLogger.i(TAG,"operatorTimer..onNext:" + aLong);
                    }
                });
    }

}
