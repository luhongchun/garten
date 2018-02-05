package cn.smart.cloud.biz.opadmin.gson;

import cn.smart.cloud.biz.opadmin.constant.Constant;
import cn.smart.cloud.biz.opadmin.entity.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Position extends BaseEntity {

    private String name;
    private Constant.PositionType type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Constant.PositionType getType() {
        return type;
    }

    public void setType(Constant.PositionType type) {
        this.type = type;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
