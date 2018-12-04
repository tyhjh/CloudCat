package com.dhht.cloudcat.data.source.remote.download;

import com.dhht.cloudcat.data.source.remote.RetrofiteApi;
import com.dhht.cloudcat.util.AppExecutors;
import com.dhht.cloudcat.util.FileUtil;
import com.dhht.cloudcat.util.MRetrofite;

import java.io.File;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownLoadDataSource {

    private static DownLoadDataSource instance;
    private RetrofiteApi retrofiteApi;
    private AppExecutors mAppExecutors;

    public static DownLoadDataSource getInstance(AppExecutors appExecutors) {
        if (instance == null) {
            instance = new DownLoadDataSource(appExecutors);
        }
        return instance;
    }

    public DownLoadDataSource(AppExecutors appExecutors) {
        mAppExecutors = appExecutors;
        retrofiteApi = MRetrofite.getInstance().create(RetrofiteApi.class);
    }

    public void downLoad(String url, final String savePath, final DownloadCallback callback) {
        retrofiteApi.downloadFileWithDynamicUrlSync(url).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()) {
                            FileUtil.writeResponseBodyToDisk(response.body(), savePath);
                            mAppExecutors.mainThread().execute(new Runnable() {
                                @Override
                                public void run() {
                                    File file = new File(savePath);
                                    if (file.exists()) {
                                        callback.downLoadFinish(file);
                                    } else {
                                        callback.downLoadFail();
                                    }
                                }
                            });
                        }
                    }
                };
                mAppExecutors.diskIO().execute(runnable);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.downLoadFail();
            }
        });
    }

    public interface DownloadCallback {
        void downLoadFail();

        void downLoadFinish(File file);
    }


}
