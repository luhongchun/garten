package cn.smart.cloud.biz.opadmin.gson.device;

import cn.smart.cloud.biz.opadmin.constant.Constant;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Camera extends Device {
    private String boxId;
    private String coverImageUrl;
    private boolean trusted;
    private String cameraIp;
    private boolean fixed;
    private boolean close;
    private int sorted;

    private Constant.CameraServiceType serviceType;
    private String modelId;

    public int getSorted() {
        return sorted;
    }

    public void setSorted(int sorted) {
        this.sorted = sorted;
    }

    public boolean isTrusted() {
        return trusted;
    }

    public void setTrusted(boolean trusted) {
        this.trusted = trusted;
    }

    public String getCameraIp() {
        return cameraIp;
    }

    public void setCameraIp(String cameraIp) {
        this.cameraIp = cameraIp;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    /**
     * @return the boxId
     */
    public String getBoxId() {
        return boxId;
    }

    /**
     * @param boxId the boxId to set
     */
    public void setBoxId(String boxId) {
        this.boxId = boxId;
    }

    /**
     * @return the coverImageUrl
     */
    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    /**
     * @param coverImageUrl the coverImageUrl to set
     */
    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    /**
     * @return the close
     */
    public boolean isClose() {
        return close;
    }

    /**
     * @param close the close to set
     */
    public void setClose(boolean close) {
        this.close = close;
    }

    public Constant.CameraServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(Constant.CameraServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}