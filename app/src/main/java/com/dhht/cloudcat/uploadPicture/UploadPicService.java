package com.dhht.cloudcat.uploadPicture;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;

import com.dhht.cloudcat.data.Picture;
import com.dhht.cloudcat.data.source.PictureDataSource;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import util.InternetUtil;

public class UploadPicService extends Service {
    UploadPicPresenter picPresenter;

    public UploadPicService() {

    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        picPresenter = new UploadPicPresenter(getApplicationContext());
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                picPresenter.getAllPic(new PictureDataSource.GetPicsCallback() {
                    @Override
                    public void onPicGet(final List<Picture> pictureList) {
                        picPresenter.getmAppExecutors().networkIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                if (InternetUtil.isWifi()) {
                                    for (Picture picture : pictureList) {
                                        if (TextUtils.isEmpty(picture.getRemotePath())) {
                                            picPresenter.uploadPic(picture, new PictureDataSource.SavePicCallBack() {
                                                @Override
                                                public void onSavePic(Picture newPicture) {
                                                    if (newPicture != null) {
                                                        picPresenter.updateLocalPic(newPicture);
                                                    }

                                                }
                                            });
                                        }
                                    }
                                }
                            }
                        });
                    }
                });
            }
        }, 0, 60 * 5 * 1000);
    }
}
