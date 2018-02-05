package cn.smart.cloud.biz.opadmin.gson.video;

import cn.smart.cloud.biz.opadmin.entity.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

/**
 * @author jjj
 * @version 1.0
 * @created 16-03-2015 15:45:04
 */
public class M3u8 extends BaseEntity {

    private Date uploadTime;
    private long size;
    private String url;
    private M3u8ContentType contentType;
    //seconds
    private int duration;
    private int height;
    private int width;
    private String cameraId;

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public M3u8ContentType getContentType() {
        return contentType;
    }

    public void setContentType(M3u8ContentType contentType) {
        this.contentType = contentType;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
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