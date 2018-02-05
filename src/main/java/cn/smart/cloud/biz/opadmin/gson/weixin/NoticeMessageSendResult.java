package cn.smart.cloud.biz.opadmin.gson.weixin;

import cn.smart.cloud.biz.opadmin.entity.weixin.WxSubscription;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class NoticeMessageSendResult {

    private WxSubscription subscription;
    private int sendCount;
    private int successCount;
    private int failCount;

    public WxSubscription getSubscription() {
        return subscription;
    }

    public void setSubscription(WxSubscription subscription) {
        this.subscription = subscription;
    }

    public int getSendCount() {
        return sendCount;
    }

    public void setSendCount(int sendCount) {
        this.sendCount = sendCount;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public int getFailCount() {
        return failCount;
    }

    public void setFailCount(int failCount) {
        this.failCount = failCount;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}