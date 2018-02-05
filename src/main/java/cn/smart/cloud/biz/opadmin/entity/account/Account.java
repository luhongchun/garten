package cn.smart.cloud.biz.opadmin.entity.account;

/*
 * @Title: Account.java
 * @Description:
 * @author jiangjunjie jiangjunjie@smart-f.cn
 * @date Aug 5, 2014 10:14:00 AM
 * @version V1.0
 */

import cn.smart.cloud.biz.opadmin.entity.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Account extends BaseEntity {

    private String baseUserId;
    private String aliasName;
    private boolean close = true;

    public String getBaseUserId() {
        return baseUserId;
    }

    public void setBaseUserId(String baseUserId) {
        this.baseUserId = baseUserId;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public boolean isClose() {
        return close;
    }

    public void setClose(boolean close) {
        this.close = close;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
