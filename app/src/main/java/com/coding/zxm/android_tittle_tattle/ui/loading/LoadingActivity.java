package com.coding.zxm.android_tittle_tattle.ui.loading;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.coding.zxm.android_tittle_tattle.R;
import com.coding.zxm.libcore.ui.BaseActivity;
import com.coding.zxm.libutil.DisplayUtil;
import com.zxm.utils.core.dialog.LoadingDialog;

/**
 * Created by ZhangXinmin on 2019/7/22.
 * Copyright (c) 2019 . All rights reserved.
 * 展示loading
 */
public class LoadingActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected Object setLayout() {
        return R.layout.activity_loading;
    }

    @Override
    protected void initParamsAndValues() {
        Intent intent = getIntent();
        if (intent != null) {
            final String label = intent.getStringExtra(DisplayUtil.PARAMS_LABEL);
            if (!TextUtils.isEmpty(label)) {
                setTitle(label);
            }
        }
    }

    @Override
    protected void initViews() {

        findViewById(R.id.btn_show_loading).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_show_loading:
                new LoadingDialog.Builder(mContext)
                        .setContentView(R.layout.layout_loading_dialog)
                        .setCancelable(false)
                        .setMessage("正在加载中...", R.id.tv_loading_msg)
                        .setHeight(200)
                        .setWidth(200)
                        .showDialog();
                break;
        }
    }
}
