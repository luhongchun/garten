package cn.smart.cloud.biz.opadmin.gson.device;

import cn.smart.cloud.biz.opadmin.entity.BaseEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PushDeviceCommand extends BaseEntity {
    @Expose
    @SerializedName("device_type")
    private String deviceType;
    @Expose
    @SerializedName("device_id")
    private String deviceId;
    @Expose
    @SerializedName("action_type")
    private String actionType;
    @Expose
    private String action;
    @Expose
    private String param;

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}