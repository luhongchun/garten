package cn.smart.cloud.biz.opadmin.entity.garten.user;

import cn.smart.cloud.biz.opadmin.entity.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class GartenUser extends BaseEntity {

    private String baseUserId;
    private String aliasName;
    private String gartenId;

    public String getBaseUserId() {
        return baseUserId;
    }

    public void setBaseUserId(String baseUserId) {
        this.baseUserId = baseUserId;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getGartenId() {
        return gartenId;
    }

    public void setGartenId(String gartenId) {
        this.gartenId = gartenId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GartenUser user = (GartenUser) o;

        if (getId().equals(user.getId()))
            return true;

        return false;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}