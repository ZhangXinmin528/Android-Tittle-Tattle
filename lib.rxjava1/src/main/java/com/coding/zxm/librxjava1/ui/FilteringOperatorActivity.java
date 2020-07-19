package com.coding.zxm.librxjava1.ui;

import android.content.Intent;
import android.text.TextUtils;

import androidx.appcompat.app.ActionBar;

import com.coding.zxm.libcore.ui.BaseActivity;
import com.coding.zxm.librxjava1.R;
import com.zxm.utils.core.log.MLogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ZhangXinmin on 2018/1/21.
 * Copyright (c) 2018 . All rights reserved.
 * 过滤操作符界面
 */

public class FilteringOperatorActivity extends BaseActivity {

    @Override
    protected Object setLayout() {
        return R.layout.activity_operator_filtering;
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

        //1.Debounce操作符
//        operatorDebounce();

        //2.Distinct操作符
//        operatorDistinct();

        //3.ElementAt操作符
//        operatorElementAt();

        //4.Filter操作符
//        operatorFilter();

        //5.First操作符
//        operatorFirst();

        //6.Last操作符
//        operatorLast();

        //7.IgnoreElements操作符
//        operatorIgnoreElements();

        //8.Sample操作符
//        operatorSample();

        //9.Skip操作符
//        operatorSkip();

        //10.Take操作符
        operatorTake();
    }

    /**
     * 1.Debounce操作符
     */
    private void operatorDebounce() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {

                for (int i = 0; i < 10; i++) {
                    int sleep = 100;
                    if (i % 3 == 0) {
                        sleep = 300;
                    }

                    try {
                        Thread.sleep(sleep);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    MLogger.i(TAG, "发送..onNext:" + i);
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.computation())
                .throttleWithTimeout(200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        MLogger.i(TAG, "operatorDebounce..onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLogger.i(TAG, "operatorDebounce..onError");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        MLogger.i(TAG, "operatorDebounce..onNext:" + integer);
                    }
                });
    }

    /**
     * 2.Distinct操作符
     */
    private void operatorDistinct() {
        Observable
                .just(1, 2, 1, 3, 2)
                .distinct()
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        MLogger.i(TAG, "operatorDistinct..call:" + integer);
                    }
                });
    }

    /**
     * 3.ElementAt操作符
     */
    private void operatorElementAt() {
        Observable.just(1, 2, 3, 4, 5)
                .elementAt(3)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        MLogger.i(TAG, "elementAt..call:" + integer);
                    }
                });

        Observable.just(1, 2, 3, 4, 5)
                .elementAtOrDefault(10, 10)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        MLogger.i(TAG, "elementAtOrDefault..call:" + integer);
                    }
                });
    }

    /**
     * 4.Filter操作符
     */
    private void operatorFilter() {
        Observable.just("Hello", 1, 2, "Hi")
                .ofType(String.class)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        MLogger.i(TAG, "operatorFilter..ofType..call:" + s);
                    }
                });

        final List<Integer> list = new ArrayList<>();
        list.addAll(Arrays.asList(new Integer[]{1, 10, 15, 20, 2}));

        Observable.from(list)
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer > 10;
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        MLogger.i(TAG, "operatorFilter..filter..call:" + integer);
                    }
                });
    }

    /**
     * 5.First操作符
     */
    private void operatorFirst() {
        Observable.just(1, 2, 3)
                .first()
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        MLogger.i(TAG, "operatorFirst..first1..call:" + integer);
                    }
                });

        Observable.just(1, 2, 3)
                .first(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer > 2;
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        MLogger.i(TAG, "operatorFirst..first2..call:" + integer);
                    }
                });

        Observable.just(1, 2, 3)
                .firstOrDefault(10, new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer > 5;
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        MLogger.i(TAG, "operatorFirst..first3..call:" + integer);
                    }
                });
    }

    /**
     * 6.Last操作符
     */
    private void operatorLast() {
        Observable.just(1, 2, 4, 3)
                .last()
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        MLogger.i(TAG, "operatorLast..last1..call:" + integer);
                    }
                });

        Observable.just(1, 2, 4, 3)
                .last(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer > 2;
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        MLogger.i(TAG, "operatorLast..last2..call:" + integer);
                    }
                });

        Observable.just(1, 2, 4, 3)
                .lastOrDefault(10, new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer > 5;
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        MLogger.i(TAG, "operatorLast..last3..call:" + integer);
                    }
                });
    }

    /**
     * 7.IgnoreElements操作符
     */
    private void operatorIgnoreElements() {
        Observable.just(1, 2, 3)
                .ignoreElements()
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        MLogger.i(TAG, "operatorIgnoreElements..onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLogger.i(TAG, "operatorIgnoreElements..onError");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        MLogger.i(TAG, "operatorIgnoreElements..onNext:" + integer);
                    }
                });

    }

    /**
     * 8.Sample操作符
     */
    private void operatorSample() {

        Observable
                .create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        for (int i = 0; i <= 10; i++) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            subscriber.onNext(i);
                        }
                        subscriber.onCompleted();
                    }
                })
                .sample(300, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        MLogger.i(TAG, "operatorSample..sample..call:" + integer);
                    }
                });

        Observable
                .create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        for (int i = 0; i <= 10; i++) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            subscriber.onNext(i);
                        }
                        subscriber.onCompleted();
                    }
                })
                .throttleFirst(300, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        MLogger.i(TAG, "operatorSample.throttleFirst..call:" + integer);
                    }
                });
    }

    /**
     * 9.Skip操作符
     */
    private void operatorSkip() {

        Observable
                .just(1, 2, 3, 4, 5)
                .skip(3)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        MLogger.i(TAG, "operatorSkip..skip(int)..call:" + integer);
                    }
                });

        Observable.interval(100, TimeUnit.MILLISECONDS)
                .skip(1000, TimeUnit.MILLISECONDS)
                .take(5)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        MLogger.i(TAG, "operatorSkip..skip(long,TimeUnit)..onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        MLogger.i(TAG, "operatorSkip..skip(long,TimeUnit)..onError:" + e.getMessage());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        MLogger.i(TAG, "operatorSkip..skip(long,TimeUnit)..onNext:" + aLong);
                    }
                });

        Observable.just(0, 1, 2, 3, 4, 5)
                .skipLast(4)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        MLogger.i(TAG, "operatorSkip..skipLast(int)..call:" + integer);
                    }
                });
    }

    /**
     * 10.Take操作符
     */
    private void operatorTake() {
        Observable
                .just(1, 2, 3, 4, 5)
                .take(3)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        MLogger.i(TAG, "operatorTake..take(int)..call:" + integer);
                    }
                });

        Observable
                .just(1, 2, 3, 4, 5)
                .takeLast(3)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        MLogger.i(TAG, "operatorTake..takeLast(int)..call:" + integer);
                    }
                });

        Observable
                .just(1, 2, 3, 4, 5, 6)
                .takeLastBuffer(3)
                .subscribe(new Action1<List<Integer>>() {
                    @Override
                    public void call(List<Integer> integers) {
                        MLogger.i(TAG, "operatorTake..takeLastBuffer(int)..call:" + integers);
                    }
                });
    }
}
