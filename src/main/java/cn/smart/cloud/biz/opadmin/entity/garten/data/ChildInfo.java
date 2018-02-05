package cn.smart.cloud.biz.opadmin.entity.garten.data;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ChildInfo extends GartenUserInfo {

    private String guardianId;
    private List<GartenUserInfo> parents;
    private String status;

    public String getGuardianId() {
        return guardianId;
    }

    public void setGuardianId(String guardianId) {
        this.guardianId = guardianId;
    }

    public List<GartenUserInfo> getParents() {
        return parents;
    }

    public void setParents(List<GartenUserInfo> parents) {
        this.parents = parents;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}