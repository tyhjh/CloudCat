package com.dhht.cloudcat.util;

import android.content.Context;
import android.media.MediaScannerConnection;

import java.io.File;

/**
 * @author Tyhj
 * @date 2018/12/5
 */

public class AppUtil {
    /**
     * 发送广播刷新图片到相册
     *
     * @param file 文件
     */
    public static void sendBroadcastToRefresh(File file, Context context) {
        /*try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), file.getName(), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);*/
        MediaScannerConnection.scanFile(context, new String[]{file.getAbsolutePath()}, new String[]{"image/jpeg"}, null);
    }
}
