package com.coding.zxm.libimage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
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

    private int[] resIds = {R.drawable.icon_doman0, R.drawable.icon_doman1};
    private ImageView mShowIv;
    private Bitmap reuseBitmap;
    private int resIndex;

    public static boolean canUseForInBitmap(Bitmap candidate, BitmapFactory.Options targetOptions) {
        int width = targetOptions.outWidth / Math.max(targetOptions.inSampleSize, 1);
        int height = targetOptions.outHeight / Math.max(targetOptions.inSampleSize, 1);
        int byteCount = width * height * getBytesPerPixel(candidate.getConfig());

        return byteCount <= candidate.getAllocationByteCount();
    }

    private static int getBytesPerPixel(Bitmap.Config config) {
        int bytesPerPixel;
        switch (config) {
            case ALPHA_8:
                bytesPerPixel = 1;
                break;
            case RGB_565:
            case ARGB_4444:
                bytesPerPixel = 2;
                break;
            default:
                bytesPerPixel = 4;
                break;
        }
        return bytesPerPixel;
    }

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

    }

    @Override
    protected void initViews() {

        mShowIv = findViewById(R.id.iv_show_bitmap);
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        reuseBitmap = BitmapFactory.decodeResource(getResources(), resIds[0], options);

        findViewById(R.id.btn_switch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shitchBitmap();
            }
        });

    }

    private void shitchBitmap() {
        mShowIv.setImageBitmap(getBitmap());
    }

    private Bitmap getBitmap() {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        if (canUseForInBitmap(reuseBitmap, options)) {
            options.inMutable = true;
            options.inBitmap = reuseBitmap;
        }
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(getResources(), resIds[resIndex++ % 2], options);
    }
}
