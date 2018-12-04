package com.dhht.cloudcat.showBigPicture;

import com.dhht.cloudcat.app.BasePresenter;
import com.dhht.cloudcat.app.BaseView;
import com.dhht.cloudcat.data.Picture;

public interface ImgContract {

    interface View extends BaseView {
        void downLoadFinish(String path);

        void downLoadFail();
    }

    interface Presenter extends BasePresenter {

        void downLoadFile(Picture picture);

        void updatePicture(Picture picture);
    }
}
