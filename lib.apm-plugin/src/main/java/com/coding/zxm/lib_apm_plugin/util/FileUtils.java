package com.coding.zxm.lib_apm_plugin.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.coding.zxm.lib_apm_plugin.api.ApmTask;
import com.coding.zxm.lib_apm_plugin.debug.storage.IOUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;

/**
 * 文件、sd卡相关工具类
 *
 * @author ArgusAPM Team
 */
public class FileUtils {
    public static final String SUB_TAG = "FileUtils";

    /**
     * 获取sd卡根目录
     *
     * @return
     */
    public static String getSDPath() {
        //sd卡是否挂载
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if (!sdCardExist) {
            return "";
        }
        final File sdDir = Environment.getExternalStorageDirectory();//获取sd卡根目录
        return sdDir.toString();
    }


    public static boolean writeFile(String filePath, String str) {
        boolean state = false;
        PrintStream ps = null;
        try {
            final File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }

            file.createNewFile();

            ps = new PrintStream(new FileOutputStream(file));
            ps.println(str);// 往文件里写入字符串
            state = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtil.safeClose(ps);
        }

        return state;
    }

    public static String readFile(String path) {
        if (TextUtils.isEmpty(path)) {
            return "";
        }

        File f = new File(path);

        if (!f.exists() || !f.isFile()) {
            return "";
        }

        BufferedReader br = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) != null) {
                if (TextUtils.isEmpty(line)) {
                    continue;
                }
                stringBuilder.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtil.safeClose(br);
        }

        return stringBuilder.toString();
    }

    public static String getApmConfigFilePath(Context c) {
        String result = "";
        if (c == null) {
            return result;
        }
        try {
            result = c.getFilesDir() + File.separator + ApmTask.APM_CONFIG_FILE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}
