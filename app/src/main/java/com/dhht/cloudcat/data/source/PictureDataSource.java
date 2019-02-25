package com.dhht.cloudcat.data.source;

import com.dhht.cloudcat.data.Picture;

import java.util.List;

/**
 * 对图片进行操作
 *
 * @author tyhj
 */
public interface PictureDataSource {

    interface GetPicsCallback {
        /**
         * 获取图片返回回调
         *
         * @param pictureList
         */
        void onPicGet(List<Picture> pictureList);
    }


    interface SavePicCallBack {
        /**
         * 保存图片回调
         *
         * @param picture
         */
        void onSavePic(Picture picture);
    }


    /**
     * 根据ID获取图片
     *
     * @param picId
     */
    void getPic(Long picId,GetPicsCallback callback);

    /**
     * 获取所有图片
     *
     * @param userId
     * @param getPicsCallback
     */
    void getPics(String userId, GetPicsCallback getPicsCallback);

    /**
     * 获取所有图片
     *
     * @param userId
     * @param tag
     * @param getPicsCallback
     */
    void getPicsByTag(String userId, String tag, GetPicsCallback getPicsCallback);

    /**
     * 删除一张图片
     *
     * @param picId
     */
    void deletePic(Long picId);

    /**
     * 保存图片
     *
     * @param picture
     * @param userId
     * @param savePicCallBack
     */
    void savePic(Picture picture,String userId, SavePicCallBack savePicCallBack);

    /**
     * 更新图片
     *
     * @param picture
     */
    void uploadPic(Picture picture);

    /**
     * 删除所有图片
     *
     * @param userId
     */
    void deleteAllPic(String userId);

}
