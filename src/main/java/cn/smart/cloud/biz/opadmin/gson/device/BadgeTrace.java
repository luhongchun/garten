package cn.smart.cloud.biz.opadmin.gson.device;

import cn.smart.cloud.biz.opadmin.entity.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class BadgeTrace extends BaseEntity {

    private String detectorSnFrom;
    private String detectorSnTo;
    private long time;

    /**
     * @return the detectorSnFrom
     */
    public String getDetectorSnFrom() {
        return detectorSnFrom;
    }

    /**
     * @param detectorSnFrom the detectorSnFrom to set
     */
    public void setDetectorSnFrom(String detectorSnFrom) {
        this.detectorSnFrom = detectorSnFrom;
    }

    /**
     * @return the detectorSnTo
     */
    public String getDetectorSnTo() {
        return detectorSnTo;
    }

    /**
     * @param detectorSnTo the detectorSnTo to set
     */
    public void setDetectorSnTo(String detectorSnTo) {
        this.detectorSnTo = detectorSnTo;
    }

    /**
     * @return the time
     */
    public long getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
