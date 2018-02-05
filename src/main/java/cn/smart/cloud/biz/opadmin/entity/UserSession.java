package cn.smart.cloud.biz.opadmin.entity;

import cn.smart.cloud.biz.opadmin.entity.user.SysUser;

import java.io.Serializable;
import java.util.Date;

public class UserSession implements Serializable {

    private static final long serialVersionUID = -7971036662880490866L;

    public static final String USER_SESSION = "user_session";

    // 用户ID
    private String userId;

    // 用户代码
    private String userCode;

    // 用户名称
    private String userName;

    // 用户所属组织机构ID
    private String orgId;

    // 用户所属组织机构代码
    private String orgCode;

    // 用户所属组织机构名称
    private String orgName;

    // 用户所属组织机构类型
    private String orgType;

    // 用户email
    private String email;

    // 用户电话
    private String phone;

    // 登陆IP
    private String loginIp;

    // 登陆时间
    private Date loginDate;

    // 用户角色
    private String roles = "";

    // 用户权限
    private String licenses = "";

    public UserSession(SysUser user, Date loginDate) {

        this.userId = user.getId();
        this.userCode = user.getLoginId();
        this.userName = user.getUserName();
        this.loginDate = loginDate;
        this.phone = user.getPhone();
        this.email = user.getEmail();
//		if (user.getRoles() != null) {
//			for (Role role : user.getRoles()) {
//				this.roles += role.getRoleId() + ",";
//				for (String perm : role.getPerms()) {
//					if (this.licenses.indexOf(perm) == -1) {
//						this.licenses += perm + ",";
//					}
//				}
//			}
//		} else {
//			this.roles = "";
//		}
    }

    public UserSession() {
    }

    public String getUserId() {

        return this.userId;
    }

    public void setUserId(String userId) {

        this.userId = userId;
    }

    public String getUserCode() {

        return this.userCode;
    }

    public void setUserCode(String userCode) {

        this.userCode = userCode;
    }

    public String getUserName() {

        return this.userName;
    }

    public void setUserName(String userName) {

        this.userName = userName;
    }

    public String getLoginIp() {

        return this.loginIp;
    }

    public void setLoginIp(String loginIp) {

        this.loginIp = loginIp;
    }

    public Date getLoginDate() {

        return this.loginDate;
    }

    public void setLoginDate(Date loginDate) {

        this.loginDate = loginDate;
    }

    public String getOrgId() {

        return this.orgId;
    }

    public void setOrgId(String orgId) {

        this.orgId = orgId;
    }

    public String getOrgCode() {

        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {

        this.orgCode = orgCode;
    }

    public String getOrgName() {

        return this.orgName;
    }

    public void setOrgName(String orgName) {

        this.orgName = orgName;
    }

    public String getOrgType() {

        return this.orgType;
    }

    public void setOrgType(String orgType) {

        this.orgType = orgType;
    }

    public String getEmail() {

        return this.email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public String getPhone() {

        return this.phone;
    }

    public void setPhone(String phone) {

        this.phone = phone;
    }

    public String getRoles() {

        return this.roles;
    }

    public void setRoles(String roles) {

        this.roles = roles;
    }

    public String getLicenses() {

        return this.licenses;
    }

    public void setLicenses(String licenses) {

        this.licenses = licenses;
    }
}