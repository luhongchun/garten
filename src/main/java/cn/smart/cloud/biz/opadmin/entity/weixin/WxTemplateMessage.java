package cn.smart.cloud.biz.opadmin.entity.weixin;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Map;

public class WxTemplateMessage {

    private String toUserOpenId;
    private String templateId;
    private String url;
    private String topColor = TOP_COLOR_DEFAULT;
    private Map<String, WxTemplateMessageData> data;

    public String getToUserOpenId() {
        return toUserOpenId;
    }

    public void setToUserOpenId(String toUserOpenId) {
        this.toUserOpenId = toUserOpenId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTopColor() {
        return topColor;
    }

    public void setTopColor(String topColor) {
        this.topColor = topColor;
    }

    public Map<String, WxTemplateMessageData> getData() {
        return data;
    }

    public void setData(Map<String, WxTemplateMessageData> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public static String TOP_COLOR_DEFAULT = "#FF0000";

}