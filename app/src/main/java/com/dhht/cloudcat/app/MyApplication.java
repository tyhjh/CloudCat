package com.dhht.cloudcat.app;

import android.app.Application;
import android.os.Environment;


import com.yorhp.picturepick.PicturePickUtil;

import java.io.File;

import log.LogUtils;
import manager.UtilManager;
import toast.ToastUtil;

public class MyApplication extends Application {

    public static final boolean IS_DEBUG = true;
    public static String appDir;
    public static final String APP_NAME = "A_cloudcat/";

    @Override
    public void onCreate() {
        super.onCreate();
        PicturePickUtil.init("com.dhht.cloudcat");
        UtilManager.initAll(this);
        appDir = Environment.getExternalStorageDirectory() + "/" + APP_NAME;
    }

    public static void initDir() {
        File file = new File(appDir);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

}
/**
 * 下载
 * 分享链接
 * tag标签
 */