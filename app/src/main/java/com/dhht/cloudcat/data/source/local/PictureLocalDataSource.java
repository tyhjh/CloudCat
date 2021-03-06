package com.dhht.cloudcat.data.source.local;

import com.dhht.cloudcat.data.Picture;
import com.dhht.cloudcat.data.source.PictureDataSource;
import com.dhht.cloudcat.util.AppExecutors;

import java.util.ArrayList;
import java.util.List;

public class PictureLocalDataSource implements PictureDataSource {

    private static volatile PictureLocalDataSource INSTANCE;

    private PictureDao mPictureDao;

    private AppExecutors mAppExecutors;

    private PictureLocalDataSource(AppExecutors appExecutors, PictureDao pictureDao) {
        mPictureDao = pictureDao;
        mAppExecutors = appExecutors;
    }

    public static PictureLocalDataSource getInstance(AppExecutors appExecutors, PictureDao pictureDao) {
        if (INSTANCE == null) {
            synchronized (PictureLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PictureLocalDataSource(appExecutors, pictureDao);
                }
            }
        }
        return INSTANCE;
    }


    @Override
    public void getPic(final Long picId, final GetPicsCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final Picture picture = mPictureDao.getPicById(picId);
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        List<Picture> pictureList = new ArrayList<>();
                        pictureList.add(picture);
                        callback.onPicGet(pictureList);
                    }
                });
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getPics(String userId, final GetPicsCallback getPicsCallback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Picture> pictureList = mPictureDao.getPics();
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        getPicsCallback.onPicGet(pictureList);
                    }
                });
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getPicsByTag(String userId, final String tag, final GetPicsCallback getPicsCallback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Picture> pictureList = mPictureDao.getPicsByTag(tag);
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        getPicsCallback.onPicGet(pictureList);
                    }
                });
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }


    @Override
    public void deletePic(final Long picId) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mPictureDao.deletePicById(picId);
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }


    @Override
    public void savePic(final Picture picture,String userId, final SavePicCallBack savePicCallBack) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mPictureDao.insertPic(picture);
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void uploadPic(final Picture picture) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mPictureDao.updataPic(picture);
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void deleteAllPic(String userId) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mPictureDao.deleteAllPic();
            }
        };
        mAppExecutors.diskIO().execute(runnable);
    }


}
