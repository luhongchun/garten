package cn.smart.cloud.biz.opadmin.entity.garten.user;

import cn.smart.cloud.biz.opadmin.entity.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Author: jjj
 * Date: 15-12-24
 * Time: 下午6:48
 */
public class GartenUserRelation extends BaseEntity {

    private String fromGartenUserId;
    private String toGartenUserId;
    private GartenUserRelationType type;

    public String getFromGartenUserId() {
        return fromGartenUserId;
    }

    public void setFromGartenUserId(String fromGartenUserId) {
        this.fromGartenUserId = fromGartenUserId;
    }

    public String getToGartenUserId() {
        return toGartenUserId;
    }

    public void setToGartenUserId(String toGartenUserId) {
        this.toGartenUserId = toGartenUserId;
    }

    public GartenUserRelationType getType() {
        return type;
    }

    public void setType(GartenUserRelationType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
