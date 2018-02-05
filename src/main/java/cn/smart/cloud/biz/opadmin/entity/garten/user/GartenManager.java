package cn.smart.cloud.biz.opadmin.entity.garten.user;

import org.apache.commons.lang3.builder.ToStringBuilder;

import cn.smart.cloud.biz.opadmin.entity.BaseEntity;

public class GartenManager extends BaseEntity {
	
	private String loginId;
	private String password;
	private boolean disabled;
	private String baseUserId;

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(boolean isDisabled) {
		this.disabled = isDisabled;
	}

	public String getBaseUserId() {
        return baseUserId;
    }

    public void setBaseUserId(String baseUserId) {
        this.baseUserId = baseUserId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}