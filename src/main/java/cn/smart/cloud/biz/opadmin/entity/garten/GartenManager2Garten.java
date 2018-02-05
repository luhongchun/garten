package cn.smart.cloud.biz.opadmin.entity.garten;

import cn.smart.cloud.biz.opadmin.entity.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class GartenManager2Garten extends BaseEntity {

    private String gartenManagerId;
    private String gartenId;

    public String getGartenManagerId() {
        return gartenManagerId;
    }

    public void setGartenManagerId(String gartenManagerId) {
        this.gartenManagerId = gartenManagerId;
    }

    public String getGartenId() {
        return gartenId;
    }

    public void setGartenId(String gartenId) {
        this.gartenId = gartenId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}