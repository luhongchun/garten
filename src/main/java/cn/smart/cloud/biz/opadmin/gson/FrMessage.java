package cn.smart.cloud.biz.opadmin.gson;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class FrMessage {

    public static final String VIDEO_TYPE_CLOSEUP = "closeup";
    public static final String VIDEO_TYPE_PRIVATE = "private";
    public static final String VIDEO_TYPE_SCAN_LEFT_RIGHT = "scanLr";
    public static final String VIDEO_TYPE_SCAN_PRESETPOS = "scanPp";

    private String personId;
    private String personInfo;
    private long startTime;
    private long endTime;
    private String cameraSn;
    private String videoType;

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(String personInfo) {
        this.personInfo = personInfo;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getCameraSn() {
        return cameraSn;
    }

    public void setCameraSn(String cameraSn) {
        this.cameraSn = cameraSn;
    }

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}