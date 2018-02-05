package cn.smart.cloud.biz.opadmin.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import cn.smart.cloud.biz.opadmin.constant.Constant;

public class BaseRole {

    private String id;

    private String name;
    private Constant.RoleType roleType;
    private String organizationId;
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Constant.RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(Constant.RoleType roleType) {
        this.roleType = roleType;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
