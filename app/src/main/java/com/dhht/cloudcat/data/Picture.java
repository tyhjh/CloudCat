package com.dhht.cloudcat.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.dhht.cloudcat.data.source.remote.MyFile;

import java.io.File;


@Entity(tableName = "pictures")
public class Picture {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "pictureid")
    private Long mId;

    private String name;//名字

    private String folderName;//文件夹名字

    @Nullable
    private String localPath;//本地地址

    @Nullable
    private String remotePath;//远程地址

    public Picture(MyFile myFile) {
        mId = myFile.getMyFileId();
        localPath = myFile.getLocalPath();
        remotePath = myFile.getFileUrl();
    }

    public Picture() {
    }

    public Picture(File file) {
        mId = System.currentTimeMillis();
        name = file.getName();
        localPath = file.getPath();
        folderName = file.getParent();
    }

    @NonNull
    public Long getId() {
        return mId;
    }

    public void setId(@NonNull Long id) {
        mId = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(@NonNull String folderName) {
        this.folderName = folderName;
    }

    @Nullable
    public String getLocalPath() {
        return localPath;
    }

    public File getFile() {
        if (!TextUtils.isEmpty(localPath)) {
            File file = new File(localPath);
            if (file.exists()) {
                return file;
            }
        }
        return null;
    }

    public void setLocalPath(@Nullable String localPath) {
        this.localPath = localPath;
    }

    @Nullable
    public String getRemotePath() {
        return remotePath;
    }

    public void setRemotePath(@Nullable String remotePath) {
        this.remotePath = remotePath;
    }

    public String getPicture() {
        if (localPath == null) {
            return remotePath;
        }
        return localPath;
    }

    @Override
    public boolean equals(Object obj) {
        Picture picture = (Picture) obj;
        if (picture != null && mId.equals(picture.mId)) {
            return true;
        }
        return false;
    }
}