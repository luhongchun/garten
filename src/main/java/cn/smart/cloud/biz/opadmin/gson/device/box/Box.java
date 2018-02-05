package cn.smart.cloud.biz.opadmin.gson.device.box;

import cn.smart.cloud.biz.opadmin.gson.device.Device;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Box extends Device {

    private boolean alarmEnabled;
    private String pushAccountId;

    public boolean isAlarmEnabled() {
        return alarmEnabled;
    }

    public void setAlarmEnabled(boolean alarmEnabled) {
        this.alarmEnabled = alarmEnabled;
    }

    public String getPushAccountId() {
        return pushAccountId;
    }

    public void setPushAccountId(String pushAccountId) {
        this.pushAccountId = pushAccountId;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
