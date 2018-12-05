package com.dhht.cloudcat.showBigPicture;

import com.dhht.cloudcat.app.MyApplication;
import com.dhht.cloudcat.data.Picture;
import com.dhht.cloudcat.data.source.local.AppDataBase;
import com.dhht.cloudcat.data.source.local.PictureLocalDataSource;
import com.dhht.cloudcat.data.source.remote.download.DownLoadDataSource;
import com.dhht.cloudcat.util.AppExecutors;

import java.io.File;

public class ImgPresenter implements ImgContract.Presenter {

    private ImgFragment mView;
    AppExecutors appExecutors;
    DownLoadDataSource downLoadDataSource;
    PictureLocalDataSource mPictureLocalDataSource;

    public ImgPresenter(ImgFragment view) {
        mView = view;
        appExecutors = new AppExecutors();
        downLoadDataSource = DownLoadDataSource.getInstance(appExecutors);
        mPictureLocalDataSource = PictureLocalDataSource.getInstance(appExecutors, AppDataBase.getInstance(mView.getContext()).pictureDao());
    }

    @Override
    public void downLoadFile(final Picture picture) {
        String savePath = MyApplication.appDir + picture.getName();
        downLoadDataSource.downLoad(picture.getRemotePath(), savePath, new DownLoadDataSource.DownloadCallback() {
            @Override
            public void downLoadFail() {
                if (mView.isResumed()) {
                    mView.downLoadFail();
                }
            }

            @Override
            public void downLoadFinish(File file) {
                if (mView.isResumed()) {
                    mView.downLoadFinish(file.getPath());
                }
                picture.setLocalPath(file.getPath());
                updatePicture(picture);
            }
        });
    }

    @Override
    public void updatePicture(Picture picture) {
        mPictureLocalDataSource.uploadPic(picture);
    }


    @Override
    public ImgFragment getView() {
        return mView;
    }
}
