package cn.smart.cloud.biz.opadmin.gson;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class UploadVodResult {

    private String childId;
    private String videoUrl;
    private String coverUrl;
    private String cameraId;
    private String videoType;
    private String videoDesc;
    private String videoDescShare;

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getCameraId() {
        return cameraId;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    public String getVideoDesc() {
        return videoDesc;
    }

    public void setVideoDesc(String videoDesc) {
        this.videoDesc = videoDesc;
    }

    public String getVideoDescShare() {
        return videoDescShare;
    }

    public void setVideoDescShare(String videoDescShare) {
        this.videoDescShare = videoDescShare;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
