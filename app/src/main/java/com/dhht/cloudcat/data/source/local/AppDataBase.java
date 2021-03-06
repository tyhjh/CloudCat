package com.dhht.cloudcat.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.dhht.cloudcat.data.Picture;


@Database(entities = {Picture.class}, version = 1,exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    private static AppDataBase INSTANCE;
    private static final Object sLock = new Object();

    public abstract PictureDao pictureDao();

    public static AppDataBase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        AppDataBase.class, "androidmvp.db").build();
            }
            return INSTANCE;
        }
    }

}
