package cn.smart.cloud.biz.opadmin.gson.device.box;

import cn.smart.cloud.biz.opadmin.entity.BaseEntity;

public class Box2TrustedCamera extends BaseEntity {

    private String boxId;

    private String cameraId;

    public String getBoxId() {
        return boxId;
    }

    public void setBoxId(String boxId) {
        this.boxId = boxId;
    }

    public String getCameraId() {
        return cameraId;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }

}