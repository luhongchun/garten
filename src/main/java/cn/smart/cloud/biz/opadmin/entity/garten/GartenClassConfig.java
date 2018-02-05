package cn.smart.cloud.biz.opadmin.entity.garten;

import cn.smart.cloud.biz.opadmin.entity.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class GartenClassConfig extends BaseEntity {

    private String classId;
    private String videoLiveTime;
    private String videoPushTime;
    private String videoUploadTime;
    private String presetShotTime;
    private String shotWeight;
    private boolean uploadNoPerson;

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getVideoLiveTime() {
        return videoLiveTime;
    }

    public void setVideoLiveTime(String videoLiveTime) {
        this.videoLiveTime = videoLiveTime;
    }

    public String getVideoPushTime() {
        return videoPushTime;
    }

    public void setVideoPushTime(String videoPushTime) {
        this.videoPushTime = videoPushTime;
    }

    public String getVideoUploadTime() {
        return videoUploadTime;
    }

    public void setVideoUploadTime(String videoUploadTime) {
        this.videoUploadTime = videoUploadTime;
    }

    public String getPresetShotTime() {
        return presetShotTime;
    }

    public void setPresetShotTime(String presetShotTime) {
        this.presetShotTime = presetShotTime;
    }

    public String getShotWeight() {
        return shotWeight;
    }

    public void setShotWeight(String shotWeight) {
        this.shotWeight = shotWeight;
    }

    public boolean isUploadNoPerson() {
        return uploadNoPerson;
    }

    public void setUploadNoPerson(boolean uploadNoPerson) {
        this.uploadNoPerson = uploadNoPerson;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}