package com.dhht.cloudcat.data.source.remote;

import com.dhht.cloudcat.data.Picture;
import com.dhht.cloudcat.data.source.PictureDataSource;
import com.dhht.cloudcat.util.AppExecutors;
import com.dhht.cloudcat.util.MRetrofite;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import log.LogUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PictureRemoteDataSource implements PictureDataSource {

    private static PictureRemoteDataSource instance;
    RetrofiteApi retrofiteApi;

    public static PictureRemoteDataSource getInstance() {
        if (instance == null) {
            instance = new PictureRemoteDataSource();
        }
        return instance;
    }

    public PictureRemoteDataSource() {
        retrofiteApi = MRetrofite.getInstance().create(RetrofiteApi.class);
    }

    @Override
    public void getPics(String userId, final GetPicsCallback getPicsCallback) {
        retrofiteApi.getFiles(userId).enqueue(new Callback<List<MyFile>>() {
            @Override
            public void onResponse(Call<List<MyFile>> call, Response<List<MyFile>> response) {
                List<Picture> pictureList = new ArrayList<>();
                List<MyFile> myFiles = response.body();
                for (MyFile myFile : myFiles) {
                    Picture picture=new Picture(myFile);
                    pictureList.add(picture);
                }
                getPicsCallback.onPicGet(pictureList);
            }

            @Override
            public void onFailure(Call<List<MyFile>> call, Throwable t) {
                LogUtils.e(t.getMessage());
            }
        });
    }


    @Override
    public void deletePic(Long picId) {
        retrofiteApi.deleteFile(picId).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }


    @Override
    public void savePic(final Picture picture, final SavePicCallBack savePicCallBack) {
        File file = new File(picture.getLocalPath());
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("myFile", file.getName(), body);

        retrofiteApi.uploadFile(picture.getId(), picture.getLocalPath(), "Tyhj", "picture", part).enqueue(new Callback<MyFile>() {
            @Override
            public void onResponse(Call<MyFile> call, Response<MyFile> response) {
                MyFile myFile1 = response.body();
                picture.setRemotePath(myFile1.getFileUrl());
                if(savePicCallBack!=null){
                    savePicCallBack.onSavePic(picture);
                }
                LogUtils.e("上传图片成功");
            }

            @Override
            public void onFailure(Call<MyFile> call, Throwable t) {
                LogUtils.e("上传图片失败"+t.getMessage());
            }
        });
    }

    @Override
    public void uploadPic(Picture picture) {

    }

}
