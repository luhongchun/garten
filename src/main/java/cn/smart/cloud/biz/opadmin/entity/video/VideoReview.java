package cn.smart.cloud.biz.opadmin.entity.video;

import cn.smart.cloud.biz.opadmin.entity.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class VideoReview extends BaseEntity {

    private String videoId;
    private boolean pass;
    private String description;
    private String requestId;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public boolean isPass() {
        return pass;
    }

    public void setPass(boolean pass) {
        this.pass = pass;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
