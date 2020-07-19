package com.zxm.coding.lib_database.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.zxm.coding.lib_database.local.model.Student;

import java.util.ArrayList;
import java.util.List;

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
     * @param whereClause
     * @param whereArgs   You may include ?s in the where clause, which will be replaced by the
     *                    values from whereArgs. The values
     *                    will be bound as Strings.
     * @return the number of rows affected if a whereClause is passed in, 0
     * otherwise. To remove all rows and get a count pass "1" as the
     * whereClause.
     */
    public int deleteWhereClause(@NonNull String whereClause, @NonNull String[] whereArgs) {
        if (!TextUtils.isEmpty(whereClause)) {
            return sqLiteDatabase.delete(Constats.TABLE_NAME, whereClause + " = ?", whereArgs);
        } else return -1;
    }

    /**
     * Convenience method for updating rows in the database.
     *
     * @param student
     * @return the number of rows affected or -1 if a student is valid
     */
    public int update(Student student) {
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

    /**
     * @param selection
     * @param selectionArgs
     * @return
     */
    public List<Student> query(String selection, String[] selectionArgs) {
        final List<Student> result = new ArrayList<>();
        if (!TextUtils.isEmpty(selection) && selectionArgs != null) {
            final Cursor cursor = sqLiteDatabase.query(Constats.TABLE_NAME,
                    new String[]{Constats.COLUMN_ID, Constats.COLUMN_NAME, Constats.COLUMN_PROVINCE},
                    selection + "= ?", selectionArgs, null, null, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    final int id = cursor.getInt(cursor.getColumnIndex(Constats.COLUMN_ID));
                    final String name = cursor.getString(cursor.getColumnIndex(Constats.COLUMN_NAME));
                    final String province = cursor.getString(cursor.getColumnIndex(Constats.COLUMN_PROVINCE));

                    final Student student = new Student();
                    student.setId(id);
                    student.setName(name);
                    student.setProvince(province);
                    result.add(student);
                }
            }
        }
        return result;

    }

    /**
     * Query all element.
     *
     * @return
     */
    public List<Student> queryAll() {
        final List<Student> result = new ArrayList<>();
        final Cursor cursor = sqLiteDatabase.rawQuery("select * from " + Constats.TABLE_NAME,
                null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                final int id = cursor.getInt(cursor.getColumnIndex(Constats.COLUMN_ID));
                final String name = cursor.getString(cursor.getColumnIndex(Constats.COLUMN_NAME));
                final String province = cursor.getString(cursor.getColumnIndex(Constats.COLUMN_PROVINCE));

                final Student student = new Student();
                student.setId(id);
                student.setName(name);
                student.setProvince(province);
                result.add(student);
            }
        }
        return result;

    }

    /**
     * Get data count.
     *
     * @return
     */
    public int queryCount() {
        final Cursor cursor = sqLiteDatabase.rawQuery("select count(*) from " + Constats.TABLE_NAME,
                null);

        cursor.moveToFirst();
        return cursor.getInt(0);
    }
}
