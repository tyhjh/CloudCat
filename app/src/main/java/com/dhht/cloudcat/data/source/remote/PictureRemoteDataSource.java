package com.dhht.cloudcat.data.source.remote;

import com.dhht.cloudcat.data.Picture;
import com.dhht.cloudcat.data.source.PictureDataSource;
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
    public void getPic(Long picId, GetPicsCallback callback) {

    }

    @Override
    public void getPics(String userId, final GetPicsCallback getPicsCallback) {
        retrofiteApi.getFiles(userId).enqueue(new Callback<Result<List<MyFile>>>() {
            @Override
            public void onResponse(Call<Result<List<MyFile>>> call, Response<Result<List<MyFile>>> response) {
                List<Picture> pictureList = new ArrayList<>();
                List<MyFile> myFiles = response.body().getData();
                for (MyFile myFile : myFiles) {
                    Picture picture = new Picture(myFile);
                    pictureList.add(picture);
                }
                getPicsCallback.onPicGet(pictureList);
            }

            @Override
            public void onFailure(Call<Result<List<MyFile>>> call, Throwable t) {
                LogUtils.e(t.getMessage());
            }
        });
    }

    @Override
    public void getPicsByTag(String userId, String tag, final GetPicsCallback getPicsCallback) {
        retrofiteApi.getFilesByTag(userId, tag).enqueue(new Callback<Result<List<MyFile>>>() {
            @Override
            public void onResponse(Call<Result<List<MyFile>>> call, Response<Result<List<MyFile>>> response) {
                List<Picture> pictureList = new ArrayList<>();
                List<MyFile> myFiles = response.body().getData();
                for (MyFile myFile : myFiles) {
                    Picture picture = new Picture(myFile);
                    pictureList.add(picture);
                }
                getPicsCallback.onPicGet(pictureList);
            }

            @Override
            public void onFailure(Call<Result<List<MyFile>>> call, Throwable t) {
                LogUtils.e(t.getMessage());
            }
        });
    }


    @Override
    public void deletePic(Long picId) {
        retrofiteApi.deleteFile(picId).enqueue(new Callback<Result<String>>() {
            @Override
            public void onResponse(Call<Result<String>> call, Response<Result<String>> response) {

            }

            @Override
            public void onFailure(Call<Result<String>> call, Throwable t) {
            }
        });
    }


    @Override
    public void savePic(final Picture picture, String userId, final SavePicCallBack savePicCallBack) {
        File file = new File(picture.getLocalPath());
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("myFile", file.getName(), body);
        MultipartBody.Builder form = new MultipartBody.Builder();
        form.setType(MultipartBody.FORM);
        form.addFormDataPart("myFileId", picture.getId() + "");
        form.addFormDataPart("localPath", picture.getLocalPath() + "");
        form.addFormDataPart("userId", userId + "");
        form.addFormDataPart("fileTag", picture.getTag() + "");
        form.addPart(part);
        MultipartBody partBody = form.build();

        retrofiteApi.uploadFile(partBody).enqueue(new Callback<Result<MyFile>>() {
            @Override
            public void onResponse(Call<Result<MyFile>> call, Response<Result<MyFile>> response) {
                Result<MyFile> myFileResult = response.body();
                MyFile myFile1 = myFileResult.getData();
                if (myFile1 == null) {
                    return;
                }
                picture.setRemotePath(myFile1.getFileUrl());
                picture.setRemoteMiniPath(myFile1.getFileMiniUrl());
                if (savePicCallBack != null) {
                    savePicCallBack.onSavePic(picture);
                }
            }

            @Override
            public void onFailure(Call<Result<MyFile>> call, Throwable t) {
                if (savePicCallBack != null) {
                    savePicCallBack.onSavePic(null);
                }
                LogUtils.e("上传图片失败" + t.getMessage());
            }
        });
    }

    @Override
    public void uploadPic(Picture picture) {

    }

    @Override
    public void deleteAllPic(String userId) {

    }

}
