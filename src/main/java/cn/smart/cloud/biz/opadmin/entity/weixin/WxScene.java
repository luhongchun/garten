package cn.smart.cloud.biz.opadmin.entity.weixin;

import cn.smart.cloud.biz.opadmin.entity.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class WxScene extends BaseEntity {

    private String name;
    private String wxSceneId;
    private String bizSceneId;
    private WxSceneType type = WxSceneType.KINDERGARTEN;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWxSceneId() {
        return wxSceneId;
    }

    public void setWxSceneId(String wxSceneId) {
        this.wxSceneId = wxSceneId;
    }

    public String getBizSceneId() {
        return bizSceneId;
    }

    public void setBizSceneId(String bizSceneId) {
        this.bizSceneId = bizSceneId;
    }

    public WxSceneType getType() {
        return type;
    }

    public void setType(WxSceneType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}