package com.dhht.cloudcat.data.source;

import com.dhht.cloudcat.data.Picture;

import java.util.List;

/**
 * 对图片进行操作
 */
public interface PictureDataSource {

    interface GetPicsCallback {
        void onPicGet(List<Picture> pictureList);
    }

    interface SavePicCallBack {
        void onSavePic(Picture picture);
    }

    //获取所有图片
    void getPics(String userId, String tag,GetPicsCallback getPicsCallback);

    //删除一张图片
    void deletePic(Long picId);

    //保存图片
    void savePic(Picture picture, SavePicCallBack savePicCallBack);

    //更新图片
    void uploadPic(Picture picture);

    /**
     * 删除所有图片
     * @param userId
     */
    void deleteAllPic(String userId);

}
