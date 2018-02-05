package cn.smart.cloud.biz.opadmin.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class AccountBizInfo {

    private String accountId;
    private String bizType;
    private String bizId;
    private String dueDate;
    private boolean expired = true;
    private String renewUrl;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public String getRenewUrl() {
        return renewUrl;
    }

    public void setRenewUrl(String renewUrl) {
        this.renewUrl = renewUrl;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}