package com.coding.zxm.libimage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.coding.zxm.libcore.ui.BaseActivity;
import com.coding.zxm.libutil.DisplayUtil;

/**
 * Created by ZhangXinmin on 2020/6/12.
 * Copyright (c) 2020 . All rights reserved.
 * 结合Bitmap源码实现切换图片功能；
 */
public class BitmapActivity extends BaseActivity {

    private ImageView mShowIv;

    @Override
    protected Object setLayout() {
        return R.layout.activity_bitmap;
    }

    @Override
    protected void initParamsAndValues() {
        Intent intent = getIntent();
        if (intent != null) {
            final String label = intent.getStringExtra(DisplayUtil.PARAMS_LABEL);
            if (!TextUtils.isEmpty(label)) {
                setTitle(label, R.id.toolbar_bitmap);
            }
        }

        mShowIv = findViewById(R.id.iv_show_bitmap);
        final Bitmap bitmap0 = BitmapFactory.decodeResource(getResources(), R.drawable.icon_doman0);
        mShowIv.setImageBitmap(bitmap0);

        findViewById(R.id.btn_switch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    protected void initViews() {

    }
}
