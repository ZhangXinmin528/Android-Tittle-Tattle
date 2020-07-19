package com.coding.zxm.lib_xml.ui;

import android.Manifest;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.coding.zxm.lib_xml.R;
import com.coding.zxm.lib_xml.model.QuestionEntity;
import com.coding.zxm.libcore.ui.BaseActivity;
import com.coding.zxm.libutil.DisplayUtil;
import com.zxm.utils.core.permission.PermissionChecker;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangXinmin on 2019/8/24.
 * Copyright (c) 2019 . All rights reserved.
 */
public class XmlTestActivity extends BaseActivity implements View.OnClickListener {

    private String mFileDir;
    private String mFileName;
    private TextView mResultTv;

    @Override
    protected Object setLayout() {
        return R.layout.activity_xml_test;
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

        if (!PermissionChecker.checkPersmission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            PermissionChecker.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1001);
        }

        mFileDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath();
        mFileName = File.separator + "quesstions.xml";
    }

    @Override
    protected void initViews() {
        mResultTv = findViewById(R.id.tv_result);

        findViewById(R.id.btn_generate_xml).setOnClickListener(this);
        findViewById(R.id.btn_obtain_xml).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int viewId = v.getId();
        if (viewId == R.id.btn_generate_xml) {
            generateXml(initTestData(), mFileDir + mFileName);
        } else if (viewId == R.id.btn_obtain_xml) {
            final List<QuestionEntity> list = obtainXmlFromAsset("quesstions.xml");

            final String result = list.toString();

            Log.d(TAG, "data : " + list.size() + "..result : " + result);

            mResultTv.setText(result);
        }
    }

    private List<QuestionEntity> initTestData() {
        final List<QuestionEntity> list = new ArrayList<>();

        final QuestionEntity one = new QuestionEntity();
        one.setId("1");
        one.setTopic("您目前所处的年龄阶段？：");
        one.setOptionA("A.65岁及以上");
        one.setOptionB("B.25岁以下");
        one.setOptionC("C.50（含）-65（不含）岁");
        one.setOptionD("D.25（含）-50（不含）岁");
        list.add(one);

        final QuestionEntity two = new QuestionEntity();
        two.setId("2");
        two.setTopic("知识水平");
        two.setOptionA("A.初中及以下");
        two.setOptionB("B.中专及高中");
        two.setOptionC("C.大专");
        two.setOptionD("D.本科及以上");
        list.add(two);

        return list;
    }

    private boolean generateXml(@NonNull List<QuestionEntity> data, @NonNull String filePath) {
        if (data == null || data.isEmpty() || TextUtils.isEmpty(filePath)) {
            return false;
        }

        final File file = new File(filePath);

        FileOutputStream fos = null;
        //设置输出流和编码格式
        try {
            //创建xml序列化实例
            final XmlSerializer xmlSerializer = Xml.newSerializer();
            fos = new FileOutputStream(file);
            //为XmlSerializer设置输出流与编码格式
            xmlSerializer.setOutput(fos, "UTF-8");
            //为XmlSerializer设置xml编码格式
            xmlSerializer.startDocument("UTF-8", true);
            //设置根元素
            xmlSerializer.startTag(null, "questions");
            //遍历数据写入标签与属性
            for (QuestionEntity entity : data) {
                xmlSerializer.startTag(null, "question");
                xmlSerializer.attribute(null, "id", entity.getId());

                xmlSerializer.startTag(null, "topic");
                xmlSerializer.text(entity.getTopic());
                xmlSerializer.endTag(null, "topic");

                xmlSerializer.startTag(null, "optionA");
                xmlSerializer.text(entity.getOptionA());
                xmlSerializer.endTag(null, "optionA");

                xmlSerializer.startTag(null, "optionB");
                xmlSerializer.text(entity.getOptionB());
                xmlSerializer.endTag(null, "optionB");

                xmlSerializer.startTag(null, "optionC");
                xmlSerializer.text(entity.getOptionC());
                xmlSerializer.endTag(null, "optionC");

                xmlSerializer.startTag(null, "optionD");
                xmlSerializer.text(entity.getOptionD());
                xmlSerializer.endTag(null, "optionD");

                xmlSerializer.endTag(null, "question");

            }
            xmlSerializer.endTag(null, "questions");
            xmlSerializer.endDocument();

            fos.flush();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<QuestionEntity> obtainXmlFromAsset(String fileName) {
        List<QuestionEntity> list = null;

        if (!TextUtils.isEmpty(fileName)) {
            //获取xml解析工厂
            final XmlPullParserFactory factory;
            XmlPullParser pullParser;

            try {
                factory = XmlPullParserFactory.newInstance();
                pullParser = factory.newPullParser();
                final AssetManager manager = getAssets();
                if (manager != null) {
                    final InputStream is = manager.open(fileName);
                    pullParser.setInput(is, "UTF-8");

                    int eventType = pullParser.getEventType();
                    QuestionEntity question = null;

                    /*while (eventType != XmlPullParser.END_DOCUMENT) {
                        switch (eventType) {

                        }
                    }*/

                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        switch (eventType) {
                            case XmlPullParser.START_DOCUMENT:
                                list = new ArrayList<>();
                                break;
                            case XmlPullParser.START_TAG:
                                if ("question".equals(pullParser.getName())) {
                                    question = new QuestionEntity();
                                    final String id = pullParser.getAttributeValue(0);
                                    question.setId(id);
                                } else if ("topic".equals(pullParser.getName())) {
                                    final String topic = pullParser.nextText();
                                    question.setTopic(topic);
                                } else if ("optionA".equals(pullParser.getName())) {
                                    final String optionA = pullParser.nextText();
                                    question.setOptionA(optionA);
                                } else if ("optionB".equals(pullParser.getName())) {
                                    final String optionB = pullParser.nextText();
                                    question.setOptionB(optionB);
                                } else if ("optionC".equals(pullParser.getName())) {
                                    final String optionC = pullParser.nextText();
                                    question.setOptionC(optionC);
                                } else if ("optionD".equals(pullParser.getName())) {
                                    final String optionD = pullParser.nextText();
                                    question.setOptionD(optionD);
                                }
                                break;
                            case XmlPullParser.END_TAG:
                                if ("question".equals(pullParser.getName())) {
                                    list.add(question);
                                    question = null;
                                }
                                break;
                        }
                        eventType = pullParser.next();
                    }
                }

            } catch (XmlPullParserException | IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
