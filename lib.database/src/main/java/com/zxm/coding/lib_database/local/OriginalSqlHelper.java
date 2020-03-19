package com.zxm.coding.lib_database.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.zxm.utils.core.log.MLogger;

/**
 * Created by ZhangXinmin on 2019/2/14.
 * Copyright (c) 2018 . All rights reserved.
 */
public class OriginalSqlHelper extends SQLiteOpenHelper {

    private static final String TAG = OriginalSqlHelper.class.getSimpleName();


    public OriginalSqlHelper(@Nullable Context context) {
        super(context, Constats.DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String sql = "create table if not exists " + Constats.TABLE_NAME + "(" +
                Constats.COLUMN_ID + " integer primary key," +
                Constats.COLUMN_NAME + " varchar(10)," +
                Constats.COLUMN_PROVINCE + " varchar(15)" +
                ")";

        db.execSQL(sql);
        MLogger.d(TAG, "SqLiteDatabase onCreate!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        MLogger.d(TAG, "SqLiteDatabase onUpgrade,newVersion : !" + newVersion);
    }
}
