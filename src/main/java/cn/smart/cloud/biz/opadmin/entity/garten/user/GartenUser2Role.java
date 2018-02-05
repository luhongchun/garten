package cn.smart.cloud.biz.opadmin.entity.garten.user;

import cn.smart.cloud.biz.opadmin.entity.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Author: jjj
 * Date: 16-1-6
 * Time: 下午9:38
 */
public class GartenUser2Role extends BaseEntity {

    private String gartenUserId;
    private String baseRoleId;

    public String getGartenUserId() {
        return gartenUserId;
    }

    public void setGartenUserId(String gartenUserId) {
        this.gartenUserId = gartenUserId;
    }

    public String getBaseRoleId() {
        return baseRoleId;
    }

    public void setBaseRoleId(String baseRoleId) {
        this.baseRoleId = baseRoleId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
