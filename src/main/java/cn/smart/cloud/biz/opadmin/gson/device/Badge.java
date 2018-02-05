package cn.smart.cloud.biz.opadmin.gson.device;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Author: jjj
 * Date: 15-7-16
 * Time: 下午5:49
 */
public class Badge extends Device {

    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}