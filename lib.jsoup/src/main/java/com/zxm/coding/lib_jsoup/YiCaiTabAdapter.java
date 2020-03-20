package com.zxm.coding.lib_jsoup;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by ZhangXinmin on 2020/3/20.
 * Copyright (c) 2020 . All rights reserved.
 */
public class YiCaiTabAdapter extends FragmentPagerAdapter {

    private List<String> mDataList;

    public YiCaiTabAdapter(@NonNull FragmentManager fm, @NonNull List<String> dataList) {
        super(fm);
        mDataList = dataList;
    }

    @Override
    public Fragment getItem(int position) {
        if (mDataList != null) {
            switch (position) {
                case 0://A股

                    break;
                case 1://金融

                    break;
                case 2://海外市场

                    break;
                case 3://全球

                    break;
                case 4://科技

                    break;
                case 5://资讯

                    break;
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        if (mDataList != null) {
            return mDataList.size();
        }
        return 0;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (mDataList != null) {
            return mDataList.get(position);
        }
        return super.getPageTitle(position);
    }
}
