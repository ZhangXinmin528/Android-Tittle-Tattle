package com.coding.zxm.android_tittle_tattle.loading;

import android.view.View;

import com.coding.zxm.android_tittle_tattle.R;
import com.coding.zxm.lib_loading.widget.LoadingDialog;
import com.coding.zxm.libcore.ui.BaseActivity;

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
                        .display(200, 200);
                break;
        }
    }
}
