package com.dhht.cloudcat.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MRetrofite {

    public static String baseUrlLocal = "http://192.168.43.158:9999/fileManager/";
    public static String baseUrlRemote= "http://circleface.tyhjh.com:8999/fileManager/";
    private Retrofit retrofit;

    private MRetrofite() {
        retrofit = new Retrofit
                .Builder()
                .baseUrl(baseUrlRemote)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Retrofit getInstance() {
        return Holder.mRetrofite.retrofit;
    }

    static class Holder {
        static MRetrofite mRetrofite = new MRetrofite();
    }

}
