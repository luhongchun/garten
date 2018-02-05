package cn.smart.cloud.biz.opadmin.entity.weixin;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by jiangjunjie on 14-12-26.
 */
public class WxTemplate {

    private String subscriptionId;

    private WxTemplateType type;

    private String templateId;

    private String description;

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public WxTemplateType getType() {
        return type;
    }

    public void setType(WxTemplateType type) {
        this.type = type;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}