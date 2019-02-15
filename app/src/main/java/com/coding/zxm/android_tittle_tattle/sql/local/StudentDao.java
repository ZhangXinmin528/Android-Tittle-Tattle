package com.coding.zxm.android_tittle_tattle.sql.local;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.coding.zxm.android_tittle_tattle.sql.local.model.Student;

/**
 * Created by ZhangXinmin on 2019/2/14.
 * Copyright (c) 2018 . All rights reserved.
 */
public final class StudentDao {

    private OriginalSqlHelper sqlHelper;
    private SQLiteDatabase sqLiteDatabase;

    public StudentDao(@NonNull OriginalSqlHelper sqlHelper) {
        this.sqlHelper = sqlHelper;
        sqLiteDatabase = sqlHelper.getWritableDatabase();
    }

    /**
     * Convenience method for inserting a row into the database.
     *
     * @param student
     * @return the row ID of the newly inserted row, or -1 if an error occurred,or student is valid.
     */
    public long insertOrThrow(Student student) {
        if (student != null) {
            final ContentValues values = new ContentValues();
            values.put(Constats.COLUMN_ID, student.getId());
            values.put(Constats.COLUMN_NAME, student.getName());
            values.put(Constats.COLUMN_PROVINCE, student.getProvince());
            return sqLiteDatabase.insertOrThrow(Constats.TABLE_NAME, null, values);
        }
        return -1;
    }

    /**
     * Convenience method for deleting rows in the database.
     *
     * @param whereClause the optional WHERE clause to apply when deleting.
     *                    Passing null will delete all rows.
     * @param whereArgs   You may include ?s in the where clause, which will be replaced by the
     *                    values from whereArgs. The values
     *                    will be bound as Strings.
     * @return the number of rows affected if a whereClause is passed in, 0
     * otherwise. To remove all rows and get a count pass "1" as the
     * whereClause.
     */
    public int delete(@NonNull String whereClause, @NonNull String[] whereArgs) {
        if (!TextUtils.isEmpty(whereClause) && whereArgs != null) {
            return sqLiteDatabase.delete(Constats.TABLE_NAME, whereClause, whereArgs);
        } else {
            return -1;
        }
    }

    /**
     * Convenience method for deleting rows in the database.
     *
     * @param key
     * @param whereArgs You may include ?s in the where clause, which will be replaced by the
     *                  values from whereArgs. The values
     *                  will be bound as Strings.
     * @return the number of rows affected if a whereClause is passed in, 0
     * otherwise. To remove all rows and get a count pass "1" as the
     * whereClause.
     */
    public int deleteByKey(@NonNull String key, @NonNull String[] whereArgs) {
        if (!TextUtils.isEmpty(key)) {
            return sqLiteDatabase.delete(Constats.TABLE_NAME, key + " = ?", whereArgs);
        } else return -1;
    }

    /**
     * Convenience method for updating rows in the database.
     *
     * @param student
     * @return the number of rows affected or -1 if a student is valid
     */
    public int upodate(Student student) {
        if (student != null) {
            final ContentValues values = new ContentValues();
            values.put(Constats.COLUMN_ID, student.getId());
            values.put(Constats.COLUMN_NAME, student.getName());
            values.put(Constats.COLUMN_PROVINCE, student.getProvince());
            return sqLiteDatabase.update(Constats.TABLE_NAME, values, Constats.COLUMN_ID + "= ?",
                    new String[]{student.getId() + ""});
        } else {
            return -1;
        }
    }
}
