package cn.smart.cloud.biz.opadmin.entity.weixin;

import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class WxTemplateMessageData {

    @SerializedName("value")
    private String value;
    @SerializedName("color")
    private String color = COLOR_DEFAULT;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public static String COLOR_DEFAULT = "#173177";
}