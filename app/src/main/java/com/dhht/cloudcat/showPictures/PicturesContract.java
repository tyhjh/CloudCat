package com.dhht.cloudcat.showPictures;

import com.dhht.cloudcat.app.BasePresenter;
import com.dhht.cloudcat.app.BaseView;
import com.dhht.cloudcat.data.Picture;

import java.io.File;
import java.util.List;

public interface PicturesContract {

    interface View extends BaseView {
        /**
         * 显示图片、刷新列表
         *
         * @param pictureList
         */
        void showPic(List<Picture> pictureList);

        /**
         * 删除本地图片成功
         *
         * @param position
         */
        void deletePic(int position);

        /**
         * 添加本地图片
         *
         * @param picture
         */
        void addPic(Picture picture);

        /**
         * 添加图片失败
         *
         * @param msg
         */

        void addPicFail(String msg);

        /**
         * 上传图片成功
         */
        void uploadPicFinish();
    }

    interface Presenter extends BasePresenter {

        /**
         * 获取所有图片
         *
         * @param userId
         */
        void getAllPic(String userId);


        /**
         * 获取Tag图片
         *
         * @param userId
         * @param tag
         */
        void getPicsByTag(String userId, String tag);

        /**
         * 删除本地图片
         *
         * @param picture
         */
        void deletePic(Picture picture);

        /**
         * 添加本地图片
         *
         * @param file
         * @param pictureList
         * @param tag
         */
        void addPic(File file, List<Picture> pictureList, String tag);


        /**
         * 清空数据库
         */
        void clearDatabase();

    }
}
