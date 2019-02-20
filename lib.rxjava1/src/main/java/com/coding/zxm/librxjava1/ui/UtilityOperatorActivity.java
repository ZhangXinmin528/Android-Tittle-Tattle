package com.coding.zxm.librxjava1.ui;

import com.coding.zxm.libcore.ui.BaseActivity;
import com.coding.zxm.libcore.util.Logger;
import com.coding.zxm.librxjava1.R;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import rx.Notification;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.schedulers.TimeInterval;
import rx.schedulers.Timestamped;

/**
 * Created by ZhangXinmin on 2018/1/21.
 * Copyright (c) 2018 . All rights reserved.
 * 辅助性操作符界面
 */

public class UtilityOperatorActivity extends BaseActivity {

    private int count;

    @Override
    protected Object setLayout() {
        return R.layout.activity_operator_combining;
    }

    @Override
    protected void initParamsAndValues() {

    }

    @Override
    protected void initViews() {

        //1.Delay操作符
//        operatorDelay();

        //2.Do操作符
//        operatorDo();

        //3.Materialize操作符
//        operatorMaterialize();

        //4.SubscribeOn操作符
//        operatorSubscribeOn();

        //5.TimeInterval操作符
//        operatorTimeInterval();

        //6.Timeout操作符
//        operatorTimeout();

        //7.Timestamp操作符
//        operatorTimestamp();

        //8.Using操作符
//        operatorUsing();

        //9.To操作符
        operatorTo();
    }

    /**
     * 1.Delay操作符
     */
    private void operatorDelay() {
        Observable<Long> observable =
                Observable.interval(1, TimeUnit.SECONDS)
                        .take(5);
        Logger.i(TAG,"operatorDelay..create observable");
        //delay
        observable
                .delay(3, TimeUnit.SECONDS)
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        Logger.i(TAG,"operatorDelay..delay..onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.i(TAG,"operatorDelay..delay..onError:" + e.getMessage());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Logger.i(TAG,"operatorDelay..delay..onNext:" + aLong);
                    }
                });

        //delaySubscription
        observable.delaySubscription(10, TimeUnit.SECONDS)
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        Logger.i(TAG,"operatorDelay..delaySubscription..onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.i(TAG,"operatorDelay..delaySubscription..onError:" + e.getMessage());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Logger.i(TAG,"operatorDelay..delaySubscription..onNext:" + aLong);
                    }
                });
    }

    /**
     * 2.Do操作符
     */
    private void operatorDo() {
        Observable
                .just(1, 2, 3, 4, 5, 6)
                //Observable每发射一项数据就会调用一次
                .doOnEach(new Action1<Notification<? super Integer>>() {
                    @Override
                    public void call(Notification<? super Integer> notification) {
                        Logger.i(TAG,"operatorDo..doOnEach:" + notification.getValue());
                    }
                })
                //执行OnError时会调用该方法
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.i(TAG,"operatorDo..doOnError:" + throwable.getMessage());
                    }
                })
                //终止时会调用
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        Logger.i(TAG,"operatorDo..doOnTerminate");
                    }
                })
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        Logger.i(TAG,"operatorDo..onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.i(TAG,"operatorDo..onError:" + e.getMessage());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Logger.i(TAG,"operatorDo..onNext:" + integer);
                    }
                });
    }

    /**
     * 3.Materialize操作符
     */
    private void operatorMaterialize() {

        Observable
                .just("Hi!", "Hello", "你好")
                .materialize()
                .subscribe(new Action1<Notification<String>>() {
                    @Override
                    public void call(Notification<String> stringNotification) {
                        Logger.i(TAG,"operatorMaterialize..materialize..call:[Kind:"
                                + stringNotification.getKind() + "..Value:"
                                + stringNotification.getValue() + "]");
                    }
                });

    }

    /**
     * 4.SubscribeOn操作符
     */
    private void operatorSubscribeOn() {
        Observable
                .just("Android", "IOS")
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        Logger.i(TAG,"operatorSubscribeOn..map..Thread:" + Thread.currentThread().getId());
                        return s + "..Developer";
                    }
                })
                .observeOn(Schedulers.newThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Logger.i(TAG,"operatorSubscribeOn..Thread:" + Thread.currentThread().getId());
                        Logger.i(TAG,"operatorSubscribeOn..call:" + s);
                    }
                });
    }

    /**
     * 5.TimeInterval操作符
     */
    private void operatorTimeInterval() {
        Observable
                .create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        for (int i = 3; i <= 7; i++) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            subscriber.onNext(i);
                        }
                        subscriber.onCompleted();
                    }
                })
                .subscribeOn(Schedulers.io())
                .timeInterval()
                .subscribe(new Subscriber<TimeInterval<Integer>>() {
                    @Override
                    public void onCompleted() {
                        Logger.i(TAG,"operatorTimeInterval..onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.i(TAG,"operatorTimeInterval..onError:" + e.getMessage());
                    }

                    @Override
                    public void onNext(TimeInterval<Integer> integerTimeInterval) {
                        Logger.i(TAG,"operatorTimeInterval..onNext:[Value:" + integerTimeInterval.getValue()
                                + "..IntervalInMilliseconds:"
                                + integerTimeInterval.getIntervalInMilliseconds() + "]");
                    }
                });
    }

    /**
     * 6.Timeout操作符
     */
    private void operatorTimeout() {
        Observable
                .create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        for (int i = 0; i < 10; i++) {
                            try {
                                Thread.sleep(i * 100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            subscriber.onNext(i);
                        }
                        subscriber.onCompleted();
                    }
                })
                .timeout(200, TimeUnit.MILLISECONDS, Observable.just(100, 200))
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        Logger.i(TAG,"operatorTimeout..onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.i(TAG,"operatorTimeout..onError:" + e.getMessage());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Logger.i(TAG,"operatorTimeout..onNext:" + integer);
                    }
                });
    }

    /**
     * 7.Timestamp操作符
     */
    private void operatorTimestamp() {
        Observable
                .just(1, 2, 3, 4, 5)
                .timestamp()
                .subscribe(new Subscriber<Timestamped<Integer>>() {
                    @Override
                    public void onCompleted() {
                        Logger.i(TAG,"operatorTimestamp..onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.i(TAG,"operatorTimestamp..onError:" + e.getMessage());
                    }

                    @Override
                    public void onNext(Timestamped<Integer> integerTimestamped) {
                        Logger.i(TAG,"operatorTimestamp..onNext:[Value:" + integerTimestamped.getValue() +
                                "..TimestampMillis:" + integerTimestamped.getTimestampMillis() + "]");
                    }
                });
    }

    /**
     * 8.Using操作符
     */
    private void operatorUsing() {
        Observable
                .using(new Func0<Integer>() {
                           @Override
                           public Integer call() {
                               return new Random().nextInt(100);
                           }
                       }, new Func1<Integer, Observable<String>>() {
                           @Override
                           public Observable<String> call(Integer integer) {
                               Logger.i(TAG,"operatorUsing..创建资源");
                               return Observable.just("数字内容：" + integer);
                           }
                       }, new Action1<Integer>() {
                           @Override
                           public void call(Integer integer) {
                               Logger.i(TAG,"operatorUsing..释放资源");
                           }
                       }
                )
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Logger.i(TAG,"operatorUsing..call:" + s);
                    }
                });
    }

    /**
     * 9.To操作符
     */
    private void operatorTo() {
        Observable
                .just("Hello", "Hi", "你好")
                .toList()
                .subscribe(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> strings) {
                        Logger.i(TAG,"operatorTo..call:" + strings);
                    }
                });
    }

}
