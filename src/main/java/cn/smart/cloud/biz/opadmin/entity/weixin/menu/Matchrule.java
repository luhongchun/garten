package cn.smart.cloud.biz.opadmin.entity.weixin.menu;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Author: jjj
 * Date: 16-1-8
 * Time: 上午1:38
 */
public class Matchrule {

    private String group_id;
    private String sex;
    private String country;
    private String province;
    private String city;
    private String client_platform_type;

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getClient_platform_type() {
        return client_platform_type;
    }

    public void setClient_platform_type(String client_platform_type) {
        this.client_platform_type = client_platform_type;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
