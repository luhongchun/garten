package cn.smart.cloud.biz.opadmin.gson.weixin;

import cn.smart.cloud.biz.opadmin.entity.weixin.WxCommonResult;
import cn.smart.cloud.biz.opadmin.entity.weixin.WxSubscription;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class UpdateMenuResult {

    private WxSubscription subscription;
    private WxCommonResult result;


    public WxSubscription getSubscription() {
        return subscription;
    }


    public void setSubscription(WxSubscription subscription) {
        this.subscription = subscription;
    }


    public WxCommonResult getResult() {
        return result;
    }


    public void setResult(WxCommonResult result) {
        this.result = result;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}