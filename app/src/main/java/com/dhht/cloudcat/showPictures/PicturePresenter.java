package com.dhht.cloudcat.showPictures;

import android.content.Context;

import com.dhht.cloudcat.app.Const;
import com.dhht.cloudcat.data.Picture;
import com.dhht.cloudcat.data.source.PictureDataSource;
import com.dhht.cloudcat.data.source.PictureRepository;
import com.dhht.cloudcat.util.AppExecutors;

import java.io.File;
import java.util.List;

import util.SharedPreferencesUtil;

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
    public void getAllPic(final String userId) {
        mPictureRepository.getLocalDataSource().getPics(userId, new PictureDataSource.GetPicsCallback() {
            @Override
            public void onPicGet(final List<Picture> localPictureList) {
                getView().showPic(localPictureList);
                mPictureRepository.getRemoteDataSource().getPics(userId, new PictureDataSource.GetPicsCallback() {
                    @Override
                    public void onPicGet(List<Picture> remotePictureList) {
                        for (Picture picture : remotePictureList) {
                            if (!localPictureList.contains(picture)) {
                                localPictureList.add(0, picture);
                                mPictureRepository.getLocalDataSource().savePic(picture, userId, null);
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
    public void getPicsByTag(final String userId, final String tag) {
        if (Const.Txt.allPic.equals(tag)) {
            getAllPic(userId);
            return;
        }

        mPictureRepository.getLocalDataSource().getPicsByTag(userId, tag, new PictureDataSource.GetPicsCallback() {
            @Override
            public void onPicGet(final List<Picture> localPictureList) {
                getView().showPic(localPictureList);
                mPictureRepository.getRemoteDataSource().getPicsByTag(userId, tag, new PictureDataSource.GetPicsCallback() {
                    @Override
                    public void onPicGet(List<Picture> remotePictureList) {
                        for (Picture picture : remotePictureList) {
                            if (!localPictureList.contains(picture)) {
                                localPictureList.add(0, picture);
                                mPictureRepository.getLocalDataSource().savePic(picture, userId, null);
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
        String userId = SharedPreferencesUtil.getString(Const.Txt.userName, null);
        getView().addPic(picture);
        mPictureRepository.savePic(picture, userId, new PictureDataSource.SavePicCallBack() {
            @Override
            public void onSavePic(Picture newPicture) {
                if(newPicture!=null){
                    mPictureRepository.uploadPic(newPicture);
                    if (getView() != null) {
                        getView().uploadPicFinish();
                    }
                }
            }
        });
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
