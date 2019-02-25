package com.dhht.cloudcat.data.source;

import android.content.Context;

import com.dhht.cloudcat.data.Picture;
import com.dhht.cloudcat.data.source.local.AppDataBase;
import com.dhht.cloudcat.data.source.local.PictureLocalDataSource;
import com.dhht.cloudcat.data.source.remote.PictureRemoteDataSource;
import com.dhht.cloudcat.util.AppExecutors;

public class PictureRepository implements PictureDataSource {

    PictureLocalDataSource mLocalDataSource;
    PictureRemoteDataSource mRemoteDataSource;

    public PictureRepository(Context context) {
        mLocalDataSource = PictureLocalDataSource.getInstance(new AppExecutors(),
                AppDataBase.getInstance(context).pictureDao());
        mRemoteDataSource = PictureRemoteDataSource.getInstance();
    }

    @Override
    public void getPic(Long picId, GetPicsCallback callback) {
        mLocalDataSource.getPic(picId, callback);
    }

    @Override
    public void getPics(String userId, GetPicsCallback getPicsCallback) {

    }

    @Override
    public void getPicsByTag(String userId, String tag, GetPicsCallback getPicsCallback) {

    }


    @Override
    public void deletePic(Long picId) {
        mLocalDataSource.deletePic(picId);
        mRemoteDataSource.deletePic(picId);
    }


    @Override
    public void savePic(Picture picture,String userId, SavePicCallBack savePicCallBack) {
        mLocalDataSource.savePic(picture,userId, savePicCallBack);
        mRemoteDataSource.savePic(picture,userId, savePicCallBack);
    }

    @Override
    public void uploadPic(Picture picture) {
        mLocalDataSource.uploadPic(picture);
    }

    @Override
    public void deleteAllPic(String userId) {
        mLocalDataSource.deleteAllPic(userId);
    }

    public PictureLocalDataSource getLocalDataSource() {
        return mLocalDataSource;
    }

    public PictureRemoteDataSource getRemoteDataSource() {
        return mRemoteDataSource;
    }
}
