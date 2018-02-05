package cn.smart.cloud.biz.opadmin.entity.garten.user;

import cn.smart.cloud.biz.opadmin.entity.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Author: jjj
 * Date: 16-1-6
 * Time: 下午9:38
 */
public class GartenUser2GartenClass extends BaseEntity {

    private String gartenUserId;
    private String gartenClassId;

    public String getGartenUserId() {
        return gartenUserId;
    }

    public void setGartenUserId(String gartenUserId) {
        this.gartenUserId = gartenUserId;
    }

    public String getGartenClassId() {
        return gartenClassId;
    }

    public void setGartenClassId(String gartenClassId) {
        this.gartenClassId = gartenClassId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}