package com.dhht.cloudcat.app;

import android.app.Application;
import android.os.Environment;

import com.dhht.cloudcat.util.ClipbordUtil;
import com.dhht.cloudcat.util.InternetUtil;
import com.dhht.cloudcat.util.SharedPreferenceUtil;
import com.yorhp.picturepick.PicturePickUtil;

import java.io.File;

import log.LogUtils;
import toast.ToastUtil;

public class MyApplication extends Application {

    public static final boolean IS_DEBUG = true;
    public static String appDir;
    public static final String APP_NAME = "A_cloudcat/";

    @Override
    public void onCreate() {
        super.onCreate();
        PicturePickUtil.init("com.dhht.cloudcat");
        ToastUtil.init(this);
        LogUtils.init(IS_DEBUG, null);
        InternetUtil.init(this);
        SharedPreferenceUtil.init(this);
        ClipbordUtil.init(this);
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