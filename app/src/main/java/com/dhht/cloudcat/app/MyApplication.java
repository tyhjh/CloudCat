package com.dhht.cloudcat.app;

import android.app.Application;

import com.dhht.cloudcat.util.InternetUtil;
import com.dhht.cloudcat.util.SharedPreferenceUtil;
import com.yorhp.picturepick.PicturePickUtil;

import log.LogUtils;
import toast.ToastUtil;

public class MyApplication extends Application {

    public static final boolean IS_DEBUG = true;

    @Override
    public void onCreate() {
        super.onCreate();
        PicturePickUtil.init("com.dhht.cloudcat");
        ToastUtil.init(this);
        LogUtils.init(IS_DEBUG, null);
        InternetUtil.init(this);
        SharedPreferenceUtil.init(this);
    }
}
/**
 *
 * 查看大图
 * 下载
 * 分享链接
 *tag标签
 *
 *
 */