package com.dhht.cloudcat.data.source.remote;

public class MyFile {

    /**
     * myFileId : 121212121212
     * fileUrl : http://lc-fgtnb2h8.yorhp.com/df56fc6b-69dc-45e2-ba01-50d585b31625%23437.png
     * fileName : df56fc6b-69dc-45e2-ba01-50d585b31625#437.png
     * fileType : .png
     * fileSize : 0
     * fileTag : test
     * createTime : 1543558586488
     */

    private long myFileId;

    private String userId;

    private String fileUrl;

    private String fileName;

    private String localPath;

    private String fileType;

    private double fileSize;

    private String fileTag;

    private long createTime;

    public long getMyFileId() {
        return myFileId;
    }

    public void setMyFileId(long myFileId) {
        this.myFileId = myFileId;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public double getFileSize() {
        return fileSize;
    }

    public void setFileSize(double fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileTag() {
        return fileTag;
    }

    public void setFileTag(String fileTag) {
        this.fileTag = fileTag;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }
}

