package cn.smart.cloud.biz.opadmin.gson.device;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class DeviceToken {

    private String accessToken;
    private String deviceId;
    private int expires;

    /**
     * @return the accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * @param accessToken the accessToken to set
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * @return the deviceId
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * @param deviceId the deviceId to set
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getExpires() {
        return expires;
    }

    public void setExpires(int expires) {
        this.expires = expires;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}