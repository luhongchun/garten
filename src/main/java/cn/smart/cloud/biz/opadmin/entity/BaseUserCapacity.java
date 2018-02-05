package cn.smart.cloud.biz.opadmin.entity;

/**
 * Author: jjj
 * Date: 15-12-26
 * Time: 下午3:03
 */
public class BaseUserCapacity extends BaseEntity {

    private String baseUserId;
    private boolean registeredVideoLive;
    private boolean registeredVideoCloseup;
    private boolean registeredVideoInout;
    private boolean registeredBusMap;
    private boolean registeredShuttleInfo;
    private boolean bCameraManager;

    public String getBaseUserId() {
        return baseUserId;
    }

    public void setBaseUserId(String baseUserId) {
        this.baseUserId = baseUserId;
    }

    public boolean isRegisteredVideoLive() {
        return registeredVideoLive;
    }

    public void setRegisteredVideoLive(boolean registeredVideoLive) {
        this.registeredVideoLive = registeredVideoLive;
    }

    public boolean isRegisteredVideoCloseup() {
        return registeredVideoCloseup;
    }

    public void setRegisteredVideoCloseup(boolean registeredVideoCloseup) {
        this.registeredVideoCloseup = registeredVideoCloseup;
    }

    public boolean isRegisteredVideoInout() {
        return registeredVideoInout;
    }

    public void setRegisteredVideoInout(boolean registeredVideoInout) {
        this.registeredVideoInout = registeredVideoInout;
    }

    public boolean isRegisteredBusMap() {
        return registeredBusMap;
    }

    public void setRegisteredBusMap(boolean registeredBusMap) {
        this.registeredBusMap = registeredBusMap;
    }

    public boolean isRegisteredShuttleInfo() {
        return registeredShuttleInfo;
    }

    public void setRegisteredShuttleInfo(boolean registeredShuttleInfo) {
        this.registeredShuttleInfo = registeredShuttleInfo;
    }

    public boolean isbCameraManager() {
        return bCameraManager;
    }

    public void setbCameraManager(boolean bCameraManager) {
        this.bCameraManager = bCameraManager;
    }

}