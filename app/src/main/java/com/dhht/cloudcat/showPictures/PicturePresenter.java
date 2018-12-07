package com.dhht.cloudcat.showPictures;

import android.content.Context;
import android.text.TextUtils;

import com.dhht.cloudcat.data.Picture;
import com.dhht.cloudcat.data.source.PictureDataSource;
import com.dhht.cloudcat.data.source.PictureRepository;
import com.dhht.cloudcat.util.AppExecutors;

import java.io.File;
import java.util.List;

import util.ClipbordUtil;
import util.InternetUtil;

public class PicturePresenter implements PicturesContract.Presenter {

    PictureRepository mPictureRepository;
    PicturesActivity mPicturesActivity;
    AppExecutors mAppExecutors;

    public PicturePresenter(PicturesContract.View view) {
        mPicturesActivity = (PicturesActivity) view;
        mPictureRepository = new PictureRepository((Context) view);
        mAppExecutors = new AppExecutors();
    }

    @Override
    public void getAllPic(final String userId, final String tag) {
        mPictureRepository.getLocalDataSource().getPics(userId, tag, new PictureDataSource.GetPicsCallback() {
            @Override
            public void onPicGet(final List<Picture> localPictureList) {
                getView().showPic(localPictureList);
                uploadAllPic(localPictureList);
                mPictureRepository.getRemoteDataSource().getPics(userId, tag, new PictureDataSource.GetPicsCallback() {
                    @Override
                    public void onPicGet(List<Picture> remotePictureList) {
                        for (Picture picture : remotePictureList) {
                            if (!localPictureList.contains(picture)) {
                                localPictureList.add(0, picture);
                                mPictureRepository.getLocalDataSource().savePic(picture, null);
                            }
                        }
                        if (getView() != null) {
                            getView().showPic(localPictureList);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void deletePic(Picture picture) {
        mPictureRepository.deletePic(picture.getId());
    }

    @Override
    public void addPic(File file, List<Picture> pictureList, String tag) {
        final Picture picture = new Picture(file);
        picture.setTag(tag);
        if (pictureList.contains(picture)) {
            getView().addPicFail("图片已存在");
            return;
        }
        getView().addPic(picture);
        mPictureRepository.savePic(picture, new PictureDataSource.SavePicCallBack() {
            @Override
            public void onSavePic(Picture newPicture) {
                mPictureRepository.uploadPic(newPicture);
                if (getView() != null) {
                    getView().uploadPicFinish();
                }
                ClipbordUtil.copyTxt(newPicture.getRemotePath());
            }
        });
    }

    @Override
    public void uploadAllPic(List<Picture> pictureList) {
        if (InternetUtil.isWifi()) {
            for (Picture picture : pictureList) {
                if (TextUtils.isEmpty(picture.getRemotePath())) {
                    mPictureRepository.savePic(picture, new PictureDataSource.SavePicCallBack() {
                        @Override
                        public void onSavePic(Picture newPicture) {
                            mPictureRepository.uploadPic(newPicture);
                        }
                    });
                }
            }
        }
    }

    @Override
    public void clearDatabase() {
        mPictureRepository.deleteAllPic(null);
    }

    @Override
    public PicturesActivity getView() {
        return mPicturesActivity;
    }
}
