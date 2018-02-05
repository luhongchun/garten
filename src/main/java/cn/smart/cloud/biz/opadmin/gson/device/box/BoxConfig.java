package cn.smart.cloud.biz.opadmin.gson.device.box;

import cn.smart.cloud.biz.opadmin.entity.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by jjj on 15-5-21.
 */
public class BoxConfig extends BaseEntity {

    private String boxId;
    private String versionCode = "1";
    // unit kb/s
    private int uploadBandwidth;
    private boolean alarmEnabled = true;
    private boolean recordEnabled = true;
    private boolean trustedListEnabled = true;
    private String kinderGartenId;
    private int cosVersion = 1;
    private String streamIndex = "MainProfile";
    private int recordChannelCnt = 4;
    private String bizType;
    private String commandId;

    public String getBoxId() {
        return boxId;
    }

    public void setBoxId(String boxId) {
        this.boxId = boxId;
    }

    public boolean isAlarmEnabled() {
        return alarmEnabled;
    }

    public void setAlarmEnabled(boolean alarmEnabled) {
        this.alarmEnabled = alarmEnabled;
    }

    public boolean isRecordEnabled() {
        return recordEnabled;
    }

    public void setRecordEnabled(boolean recordEnabled) {
        this.recordEnabled = recordEnabled;
    }

    public boolean isTrustedListEnabled() {
        return trustedListEnabled;
    }

    public void setTrustedListEnabled(boolean trustedListEnabled) {
        this.trustedListEnabled = trustedListEnabled;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public int getUploadBandwidth() {
        return uploadBandwidth;
    }

    public void setUploadBandwidth(int uploadBandwidth) {
        this.uploadBandwidth = uploadBandwidth;
    }

    public String getKinderGartenId() {
        return kinderGartenId;
    }

    public void setKinderGartenId(String kinderGartenId) {
        this.kinderGartenId = kinderGartenId;
    }

    public int getCosVersion() {
        return cosVersion;
    }

    public void setCosVersion(int cosVersion) {
        this.cosVersion = cosVersion;
    }

    public String getStreamIndex() {
        return streamIndex;
    }

    public void setStreamIndex(String streamIndex) {
        this.streamIndex = streamIndex;
    }

    public int getRecordChannelCnt() {
        return recordChannelCnt;
    }

    public void setRecordChannelCnt(int recordChannelCnt) {
        this.recordChannelCnt = recordChannelCnt;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getCommandId() {
        return commandId;
    }

    public void setCommandId(String commandId) {
        this.commandId = commandId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}