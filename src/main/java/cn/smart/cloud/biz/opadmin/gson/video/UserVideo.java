package cn.smart.cloud.biz.opadmin.gson.video;

import cn.smart.cloud.biz.opadmin.entity.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class UserVideo extends BaseEntity {

    private String coverUrl;
    private String videoUrl;
    private String videoUrlWithHeadTrail;
    private String cameraId;

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoUrlWithHeadTrail() {
        return videoUrlWithHeadTrail;
    }

    public void setVideoUrlWithHeadTrail(String videoUrlWithHeadTrail) {
        this.videoUrlWithHeadTrail = videoUrlWithHeadTrail;
    }

    public String getCameraId() {
        return cameraId;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}