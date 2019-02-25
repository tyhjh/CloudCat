package com.dhht.cloudcat.showBigPicture;

import com.dhht.cloudcat.app.BasePresenter;
import com.dhht.cloudcat.app.BaseView;
import com.dhht.cloudcat.data.Picture;

public interface ImgContract {

    interface View extends BaseView {
        /**
         * 下载完成
         * @param path
         */
        void downLoadFinish(String path);

        /**
         * 下载失败
         */
        void downLoadFail();
    }

    interface Presenter extends BasePresenter {
        /**
         * 下载文件
         * @param picture
         */
        void downLoadFile(Picture picture);

        /**
         * 更新文件
         * @param picture
         */
        void updatePicture(Picture picture);
    }
}
