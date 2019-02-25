package com.dhht.cloudcat.uploadPicture;

import com.dhht.cloudcat.app.BasePresenter;
import com.dhht.cloudcat.data.Picture;
import com.dhht.cloudcat.data.source.PictureDataSource;

/**
 * @author tyhj
 * @date 2019/2/24
 */

public interface UploadPicContract {

    interface Presenter extends BasePresenter {
        /**
         * 获取本地所有图片
         */
        void getAllPic(PictureDataSource.GetPicsCallback callback);

        /**
         * 开始上传图片
         *
         * @param picture
         */
        void uploadPic(Picture picture, PictureDataSource.SavePicCallBack callBack);


        /**
         * 更新本地图片
         *
         * @param picture
         */
        void updateLocalPic(Picture picture);

    }
}
