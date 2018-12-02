package com.dhht.cloudcat.data.source.remote;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface RetrofiteApi {

    /**
     * 上传文件
     *
     * @param pictureFile
     * @return
     */

    @POST("file")
    @Multipart
    Call<MyFile> uploadFile(@Query("myFileId") Long myFileId,
                            @Query("localPath") String localPath,
                            @Query("userId") String userId,
                            @Query("fileTag") String fileTag,
                            @Part MultipartBody.Part pictureFile);


    /**
     * 获取文件
     *
     * @param userId
     * @return
     */
    @GET("files")
    Call<List<MyFile>> getFiles(@Query("userId") String userId);

    /**
     * 删除文件
     *
     * @param myFileId
     * @return
     */
    @DELETE("file")
    Call<Result> deleteFile(@Query("myFileId") Long myFileId);

}
