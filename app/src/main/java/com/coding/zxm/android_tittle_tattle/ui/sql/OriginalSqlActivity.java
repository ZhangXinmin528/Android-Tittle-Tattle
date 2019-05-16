package com.coding.zxm.android_tittle_tattle.ui.sql;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.coding.zxm.android_tittle_tattle.R;
import com.coding.zxm.android_tittle_tattle.adapter.SqlExampleAdapter;
import com.coding.zxm.android_tittle_tattle.sql.local.Constats;
import com.coding.zxm.android_tittle_tattle.sql.local.OriginalSqlManager;
import com.coding.zxm.android_tittle_tattle.sql.local.StudentDao;
import com.coding.zxm.android_tittle_tattle.sql.local.model.Student;
import com.coding.zxm.libutil.DisplayUtil;
import com.coding.zxm.libcore.listender.OnItemClickListener;
import com.coding.zxm.libcore.ui.BaseActivity;
import com.coding.zxm.libutil.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangXinmin on 2019/2/14.
 * Copyright (c) 2018 . All rights reserved.
 * 数据库的增删改查；
 */
public class OriginalSqlActivity extends BaseActivity implements View.OnClickListener, OnItemClickListener {

    //文本输入
    private TextInputEditText mIdEt;
    private TextInputEditText mNameEt;
    private TextInputEditText mProvinceEt;

    //列表
    private TextView mCountTv;
    private RecyclerView mRecyclerView;
    private List<Student> mDataList;
    private SqlExampleAdapter mAdapter;

    private StudentDao mStudentDao;

    @Override
    protected Object setLayout() {
        return R.layout.activity_original_sql;
    }

    @Override
    protected void initParamsAndValues() {
        Intent intent = getIntent();
        if (intent != null) {
            final String label = intent.getStringExtra(DisplayUtil.PARAMS_LABEL);
            if (!TextUtils.isEmpty(label)) {
                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setTitle(label);
                }
            }
        }

        mDataList = new ArrayList<>();

        mAdapter = new SqlExampleAdapter(mDataList);

        mStudentDao = OriginalSqlManager.getInstance(mContext).getStudentDao();

    }

    @Override
    protected void initViews() {
        //文本输入
        mIdEt = findViewById(R.id.et_sql_input_id);
        mNameEt = findViewById(R.id.et_sql_input_name);
        mProvinceEt = findViewById(R.id.et_sql_input_province);

        findViewById(R.id.btn_sql_add).setOnClickListener(this);
        findViewById(R.id.btn_sql_delete).setOnClickListener(this);
        findViewById(R.id.btn_sql_update).setOnClickListener(this);
        findViewById(R.id.btn_sql_query).setOnClickListener(this);
        findViewById(R.id.btn_sql_count).setOnClickListener(this);

        mCountTv = findViewById(R.id.tv_sql_data_count);
        mRecyclerView = findViewById(R.id.rv_original_sql);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItmeClickListener(this);

        queryAndUpdate();

        updateDataCount();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sql_add:
                insertData();
                break;
            case R.id.btn_sql_delete:
                deleteElement();
                break;
            case R.id.btn_sql_update:
                updateElement();
                break;
            case R.id.btn_sql_query:
                queryAndUpdate();
                break;
            case R.id.btn_sql_count:
                updateDataCount();
                break;
        }
    }

    private void updateElement() {
        final int id = Integer.parseInt(mIdEt.getEditableText().toString().trim());
        final String name = mNameEt.getEditableText().toString().trim();
        final String province = mProvinceEt.getEditableText().toString().trim();

        final Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setProvince(province);

        final int rowId = mStudentDao.update(student);
        Logger.d(TAG, "update item row id = " + rowId);
    }

    /**
     * Delete element.
     */
    private void deleteElement() {
        final String name = mNameEt.getEditableText().toString().trim();
        final long rowId = mStudentDao.deleteWhereClause(Constats.COLUMN_NAME, new String[]{name});
        Logger.d(TAG, "delete item where " + Constats.COLUMN_NAME + " = " + name + ", row id = " + rowId);
    }

    /**
     * Insert a data into sqLiteDatabase.
     */
    private void insertData() {
        final int id = Integer.parseInt(mIdEt.getEditableText().toString().trim());
        final String name = mNameEt.getEditableText().toString().trim();
        final String province = mProvinceEt.getEditableText().toString().trim();

        final Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setProvince(province);

        long rowId = mStudentDao.insertOrThrow(student);
        if (rowId > 0) {
            mIdEt.getEditableText().clear();
            mNameEt.getEditableText().clear();
            mProvinceEt.getEditableText().clear();
        }
        Logger.d(TAG, "insertOrThrow item row id = " + rowId);
    }

    /**
     * Query all data and update list.
     */
    private void queryAndUpdate() {

        final List<Student> list = mStudentDao.queryAll();
        if (!mDataList.isEmpty()) {
            mDataList.clear();
        }
        if (list != null && !list.isEmpty()) {
            mDataList.addAll(list);
        }
        mAdapter.notifyDataSetChanged();

    }

    /**
     * Query data count.
     */
    private void updateDataCount() {
        //数据条数
        final int count = mStudentDao.queryCount();
        mCountTv.setText(getString(R.string.all_data_count, count));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onItemClick(@NonNull RecyclerView.Adapter adapter, @NonNull View view, int position) {
        final Student student = ((SqlExampleAdapter) adapter).getItem(position);
        if (student != null) {
            mIdEt.setText(student.getId() + "");
            mNameEt.setText(student.getName());
            mProvinceEt.setText(student.getProvince());
        }
    }
}
