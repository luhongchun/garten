package cn.smart.cloud.biz.opadmin.gson.report;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class ReportMessageCamera extends ReportMessageDevice {

    private String cameraId;
    private String name;
    private String recordStatus;
    private double recordWeight;
    private String videoStatistics;
    private List<PersonVideo> personVideo;

    public String getCameraId() {
        return cameraId;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }

    public double getRecordWeight() {
        return recordWeight;
    }

    public void setRecordWeight(double recordWeight) {
        this.recordWeight = recordWeight;
    }

    public String getVideoStatistics() {
        return videoStatistics;
    }

    public void setVideoStatistics(String videoStatistics) {
        this.videoStatistics = videoStatistics;
    }

    public List<PersonVideo> getPersonVideo() {
        return personVideo;
    }

    public void setPersonVideo(List<PersonVideo> personVideo) {
        this.personVideo = personVideo;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}