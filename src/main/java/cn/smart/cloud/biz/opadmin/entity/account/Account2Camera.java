package cn.smart.cloud.biz.opadmin.entity.account;

import cn.smart.cloud.biz.opadmin.entity.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

/*
 * @Title: AccountCamera.java
 * @Description: 
 * @author jiangjunjie jiangjunjie@smart-f.cn  
 * @date Aug 5, 2014 10:19:54 AM
 * @version V1.0  
 */

public class Account2Camera extends BaseEntity {

    private String accountId;

    private String cameraId;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
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