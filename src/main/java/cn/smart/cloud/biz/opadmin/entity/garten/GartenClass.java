package cn.smart.cloud.biz.opadmin.entity.garten;

import cn.smart.cloud.biz.opadmin.entity.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class GartenClass extends BaseEntity {

    private String name;
    private String grade;
    private String organizationId;
    private String gartenId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
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