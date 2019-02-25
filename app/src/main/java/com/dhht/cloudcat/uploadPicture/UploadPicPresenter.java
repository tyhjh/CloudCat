package com.dhht.cloudcat.uploadPicture;

import android.content.Context;

import com.dhht.cloudcat.app.BaseView;
import com.dhht.cloudcat.app.Const;
import com.dhht.cloudcat.data.Picture;
import com.dhht.cloudcat.data.source.PictureDataSource;
import com.dhht.cloudcat.data.source.PictureRepository;
import com.dhht.cloudcat.util.AppExecutors;

import util.SharedPreferencesUtil;

/**
 * @author tyhj
 * @date 2019/2/24
 */

public class UploadPicPresenter implements UploadPicContract.Presenter {

    PictureRepository mPictureRepository;
    AppExecutors mAppExecutors;
    String userId = SharedPreferencesUtil.getString(Const.Txt.userName, null);

    public UploadPicPresenter(Context context) {
        mPictureRepository = new PictureRepository(context);
        mAppExecutors = new AppExecutors();
    }

    @Override
    public void getAllPic(PictureDataSource.GetPicsCallback callback) {
        mPictureRepository.getLocalDataSource().getPics(userId, callback);
    }

    @Override
    public void uploadPic(Picture picture, PictureDataSource.SavePicCallBack callBack) {
        mPictureRepository.getRemoteDataSource().savePic(picture, userId,callBack);
    }

    @Override
    public void updateLocalPic(Picture picture) {
        mPictureRepository.uploadPic(picture);
    }


    @Override
    public BaseView getView() {
        return null;
    }

    public AppExecutors getmAppExecutors() {
        return mAppExecutors;
    }
}
