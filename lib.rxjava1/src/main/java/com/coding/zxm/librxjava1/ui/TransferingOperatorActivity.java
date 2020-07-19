package com.coding.zxm.librxjava1.ui;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;

import com.coding.zxm.libcore.ui.BaseActivity;
import com.coding.zxm.librxjava1.R;

import java.io.IOException;
import java.io.InputStream;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ZhangXinmin on 2018/1/21.
 * Copyright (c) 2018 . All rights reserved.
 * 转换操作符界面
 */

public class TransferingOperatorActivity extends BaseActivity {

    private ImageView mImageView;

    @Override

    protected Object setLayout() {
        return R.layout.activity_operator_transfering;
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
        mImageView = findViewById(R.id.iv_transfer);

        setImageView();
    }

    /**
     * 加载图片
     */
    private void setImageView() {
        final String path = "img/explore.webp";
        Observable
                .just(path)
                .map(new Func1<String, Bitmap>() {
                    @Override
                    public Bitmap call(String s) {
                        return getAssetBitmap(s);
                    }
                })
                .filter(new Func1<Bitmap, Boolean>() {
                    @Override
                    public Boolean call(Bitmap bitmap) {
                        return bitmap != null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        mImageView.setImageBitmap(bitmap);
                    }
                });
    }

    /**
     * 获取Asset文件夹下图片
     *
     * @param path
     * @return
     * @hide
     */
    private Bitmap getAssetBitmap(String path) {
        Bitmap bitmap = null;
        if (!TextUtils.isEmpty(path)) {

            final AssetManager manager = getAssets();
            InputStream inputStream;
            try {
                inputStream = manager.open(path);
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }


}
