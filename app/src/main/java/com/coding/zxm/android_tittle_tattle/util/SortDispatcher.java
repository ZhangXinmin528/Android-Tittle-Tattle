package com.coding.zxm.android_tittle_tattle.util;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.coding.zxm.android_tittle_tattle.ui.loading.LoadingActivity;
import com.coding.zxm.android_tittle_tattle.ui.lunchmode.LunchModeActivity;
import com.coding.zxm.android_tittle_tattle.ui.thread.CountDownLatchActivity;
import com.coding.zxm.android_tittle_tattle.ui.thread.IntentServiceActivity;
import com.coding.zxm.android_tittle_tattle.ui.thread.ThreadActivity;
import com.coding.zxm.android_tittle_tattle.ui.thread.ThreadPoolActivity;
import com.coding.zxm.lib_okhttp.ui.OkUsageActivity;
import com.coding.zxm.lib_queue.LinkeBlockingQueueActivity;
import com.coding.zxm.lib_xml.ui.XmlTestActivity;
import com.coding.zxm.libcore.route.RoutePath;
import com.coding.zxm.libimage.BitmapActivity;
import com.coding.zxm.libutil.DisplayUtil;
import com.coding.zxm.video.VideoNavigationActivity;
import com.zxm.coding.lib_jsoup.YiCaiNewsActivity;
import com.zxm.coding.lib_list.ui.ListExampleActivity;
import com.zxm.coding.lib_stacking.ui.StackingActivity;

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
                //任务栈
                case 0:
                    intent.setClass(context, LunchModeActivity.class);
                    intent.putExtra(DisplayUtil.PARAMS_LABEL, label);
                    context.startActivity(intent);
                    break;
                //线程
                case 1:
                    intent.setClass(context, ThreadActivity.class);
                    intent.putExtra(DisplayUtil.PARAMS_LABEL, label);
                    context.startActivity(intent);
                    break;
                //数据库
                case 2:
                    ARouter.getInstance()
                            .build(RoutePath.ROUTE_ORIGINAL_SQL)
                            .withString(DisplayUtil.PARAMS_LABEL, label)
                            .navigation();
                    break;
                case 3:
                    //Retrofit
                    ARouter.getInstance()
                            .build(RoutePath.ROUTE_NET_MOVIE)
                            .withString(DisplayUtil.PARAMS_LABEL, label)
                            .navigation();
                    break;
                case 4:
                    //对象池
                    ARouter.getInstance()
                            .build(RoutePath.ROUTE_POOL_OBJECT)
                            .withString(DisplayUtil.PARAMS_LABEL, label)
                            .navigation();
                    break;
                case 5:
                    //消息轮询
                    ARouter.getInstance()
                            .build(RoutePath.ROUTE_POOLING_EXAMPLE)
                            .withString(DisplayUtil.PARAMS_LABEL, label)
                            .navigation();
                    break;
                case 6:
                    //TODO:location
                    //OkHttp
                    intent.setClass(context, OkUsageActivity.class);
                    intent.putExtra(DisplayUtil.PARAMS_LABEL, label);
                    context.startActivity(intent);
                    break;
                case 7:
                    //Queue
                    intent.setClass(context, LinkeBlockingQueueActivity.class);
                    intent.putExtra(DisplayUtil.PARAMS_LABEL, label);
                    context.startActivity(intent);
                    break;
                case 8:
                    //视频播放
                    intent.setClass(context, VideoNavigationActivity.class);
                    intent.putExtra(DisplayUtil.PARAMS_LABEL, label);
                    context.startActivity(intent);
                    break;
                case 9:
                    //Loading
                    intent.setClass(context, LoadingActivity.class);
                    intent.putExtra(DisplayUtil.PARAMS_LABEL, label);
                    context.startActivity(intent);
                    break;
                case 10:
                    //xml解析
                    intent.setClass(context, XmlTestActivity.class);
                    intent.putExtra(DisplayUtil.PARAMS_LABEL, label);
                    context.startActivity(intent);
                    break;
                case 11:
                    //RecyclerViewHelper
//                    intent.setClass(context, RvActivity.class);
//                    intent.putExtra(DisplayUtil.PARAMS_LABEL, label);
//                    context.startActivity(intent);
                    break;
                case 12:
                    //卡片堆叠效果
                    intent.setClass(context, StackingActivity.class);
                    intent.putExtra(DisplayUtil.PARAMS_LABEL, label);
                    context.startActivity(intent);
                    break;
                case 13:
                    //LeakCanary
                    /*intent.setClass(context, StackingActivity.class);
                    intent.putExtra(DisplayUtil.PARAMS_LABEL, label);
                    context.startActivity(intent);*/
                    break;
                case 14:
                    //爬虫
                    intent.setClass(context, YiCaiNewsActivity.class);
                    intent.putExtra(DisplayUtil.PARAMS_LABEL, label);
                    context.startActivity(intent);
                    break;
                case 15:
                    //列表效果
                    intent.setClass(context, ListExampleActivity.class);
                    intent.putExtra(DisplayUtil.PARAMS_LABEL, label);
                    context.startActivity(intent);
                    break;
                case 16:
                    //Bitmap
                    intent.setClass(context, BitmapActivity.class);
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

}
