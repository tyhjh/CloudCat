package com.dhht.cloudcat.util;

import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

public class ClipbordUtil {
    static Application sApplication;

    public static void init(Application application) {
        sApplication = application;
    }

    public static void copyTxt(String msg) {
        ClipData clipData = ClipData.newPlainText("", msg);
        ClipboardManager cmb = (ClipboardManager) sApplication.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setPrimaryClip(clipData);
    }

}
