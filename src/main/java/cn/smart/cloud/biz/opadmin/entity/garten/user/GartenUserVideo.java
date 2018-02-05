package cn.smart.cloud.biz.opadmin.entity.garten.user;

import cn.smart.cloud.biz.opadmin.entity.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class GartenUserVideo extends BaseEntity {

    private String childId;
    private String videoDesc;
    private String videoDescShare;
    private String videoId;
    private String type;
    private String qrcodeSn;
    private String qrcodeUrl;

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
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

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQrcodeSn() {
        return qrcodeSn;
    }

    public void setQrcodeSn(String qrcodeSn) {
        this.qrcodeSn = qrcodeSn;
    }

    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
