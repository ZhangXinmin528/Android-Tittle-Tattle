package com.coding.zxm.android_tittle_tattle.util;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.coding.zxm.android_tittle_tattle.ui.rxjava.RxJava1Activity;
import com.coding.zxm.android_tittle_tattle.ui.sql.OriginalSqlActivity;
import com.coding.zxm.android_tittle_tattle.ui.thread.CountDownLatchActivity;
import com.coding.zxm.android_tittle_tattle.ui.thread.IntentServiceActivity;
import com.coding.zxm.android_tittle_tattle.ui.thread.ThreadActivity;
import com.coding.zxm.android_tittle_tattle.ui.thread.ThreadPoolActivity;
import com.coding.zxm.libnet.ui.MovieActivity;
import com.coding.zxm.librxjava1.ui.CombiningOperatorActivity;
import com.coding.zxm.librxjava1.ui.CreatingOperatorActivity;
import com.coding.zxm.librxjava1.ui.ErrorHandlingOperatorActivity;
import com.coding.zxm.librxjava1.ui.FilteringOperatorActivity;
import com.coding.zxm.librxjava1.ui.RxJavaABCActivity;
import com.coding.zxm.librxjava1.ui.TransferingOperatorActivity;
import com.coding.zxm.librxjava1.ui.UtilityOperatorActivity;

/**
 * Created by ZhangXinmin on 2019/1/30.
 * Copyright (c) 2018 . All rights reserved.
 */
public final class SortDispatcher {
    private SortDispatcher() {
        throw new UnsupportedOperationException("U con't do this!");
    }

    /**
     * Dispatch home event.
     *
     * @param context
     * @param position
     * @param label
     */
    public static void dispatchHomeEvent(@NonNull Context context, @IntRange(from = 0) int position, @NonNull String label) {
        Intent intent = new Intent();
        if (!TextUtils.isEmpty(label)) {
            switch (position) {
                case 0:
                    intent.setClass(context, ThreadActivity.class);
                    intent.putExtra(DisplayUtil.PARAMS_LABEL, label);
                    context.startActivity(intent);
                    break;
                case 1:
                    intent.setClass(context, OriginalSqlActivity.class);
                    intent.putExtra(DisplayUtil.PARAMS_LABEL, label);
                    context.startActivity(intent);
                    break;
                case 2:
                    intent.setClass(context, RxJava1Activity.class);
                    intent.putExtra(DisplayUtil.PARAMS_LABEL, label);
                    context.startActivity(intent);
                    break;
                case 3:
                    intent.setClass(context, MovieActivity.class);
                    intent.putExtra(DisplayUtil.PARAMS_LABEL, label);
                    context.startActivity(intent);
                    break;
            }
        }
    }

    /**
     * Dispatch home event.
     *
     * @param context
     * @param position
     * @param label
     */
    public static void dispatchThreadEvent(@NonNull Context context, @IntRange(from = 0) int position,
                                           @NonNull String label) {

        Intent intent = new Intent();
        if (!TextUtils.isEmpty(label)) {
            switch (position) {
                case 0:
                    intent.setClass(context, IntentServiceActivity.class);
                    intent.putExtra(DisplayUtil.PARAMS_LABEL, label);
                    context.startActivity(intent);
                    break;
                case 1:
                    intent.setClass(context, ThreadPoolActivity.class);
                    intent.putExtra(DisplayUtil.PARAMS_LABEL, label);
                    context.startActivity(intent);
                    break;
                case 2:
                    intent.setClass(context, CountDownLatchActivity.class);
                    intent.putExtra(DisplayUtil.PARAMS_LABEL, label);
                    context.startActivity(intent);
                    break;
            }
        }
    }

    public static void dispatchRxJavaEvent(@NonNull Context context, @IntRange(from = 0) int position,
                                           @NonNull String label) {
        Intent intent = new Intent();
        if (!TextUtils.isEmpty(label)) {
            switch (position) {
                case 0:
                    intent.setClass(context, RxJavaABCActivity.class);
                    intent.putExtra(DisplayUtil.PARAMS_LABEL, label);
                    context.startActivity(intent);
                    break;
                case 1:
                    intent.setClass(context, CreatingOperatorActivity.class);
                    intent.putExtra(DisplayUtil.PARAMS_LABEL, label);
                    context.startActivity(intent);
                    break;
                case 2:
                    intent.setClass(context, TransferingOperatorActivity.class);
                    intent.putExtra(DisplayUtil.PARAMS_LABEL, label);
                    context.startActivity(intent);
                    break;
                case 3:
                    intent.setClass(context, FilteringOperatorActivity.class);
                    intent.putExtra(DisplayUtil.PARAMS_LABEL, label);
                    context.startActivity(intent);
                    break;
                case 4:
                    intent.setClass(context, CombiningOperatorActivity.class);
                    intent.putExtra(DisplayUtil.PARAMS_LABEL, label);
                    context.startActivity(intent);
                    break;
                case 5:
                    intent.setClass(context, ErrorHandlingOperatorActivity.class);
                    intent.putExtra(DisplayUtil.PARAMS_LABEL, label);
                    context.startActivity(intent);
                    break;
                case 6:
                    intent.setClass(context, UtilityOperatorActivity.class);
                    intent.putExtra(DisplayUtil.PARAMS_LABEL, label);
                    context.startActivity(intent);
                    break;
            }
        }
    }
}
