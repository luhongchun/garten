package cn.smart.cloud.biz.opadmin.entity;

/**
 * Author: jjj
 * Date: 15-12-26
 * Time: 下午3:33
 */
public class SfPermission {

    private String name;
    private String description;

    private String serviceId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

}
