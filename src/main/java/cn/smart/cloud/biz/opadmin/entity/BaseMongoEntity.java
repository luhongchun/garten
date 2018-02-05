package cn.smart.cloud.biz.opadmin.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class BaseMongoEntity {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
