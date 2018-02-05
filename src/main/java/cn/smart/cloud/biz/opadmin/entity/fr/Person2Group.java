package cn.smart.cloud.biz.opadmin.entity.fr;


import cn.smart.cloud.biz.opadmin.entity.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Person2Group extends BaseEntity {

    private String personId;
    private String groupId;

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
