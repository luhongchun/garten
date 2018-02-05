package cn.smart.cloud.biz.opadmin.entity.user;

import cn.smart.cloud.biz.opadmin.entity.BaseEntity;

public class SysUser extends BaseEntity {

    private String password;
    private String userName;
    private String loginId;
    private String email;
    private String phone;
    private boolean disabled;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(boolean isDisabled) {
        this.disabled = isDisabled;
    }

}