package com.dhht.cloudcat.data.source.remote;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface RetrofiteApi {

    /**
     * 上传文件
     *
     * @param pictureFile
     * @return
     */

    @POST("file")
    @Multipart
    Call<Result<MyFile>> uploadFile(@Query("myFileId") Long myFileId,
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
    @GET("tag/files")
    Call<Result<List<MyFile>>> getFilesByTag(@Query("userId") String userId,@Query("fileTag") String fileTag);

    /**
     * 获取文件
     *
     * @param userId
     * @return
     */
    @GET("files")
    Call<Result<List<MyFile>>> getFiles(@Query("userId") String userId);

    /**
     * 删除文件
     *
     * @param myFileId
     * @return
     */
    @DELETE("file")
    Call<Result<String>> deleteFile(@Query("myFileId") Long myFileId);

    @Streaming
    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);

}
