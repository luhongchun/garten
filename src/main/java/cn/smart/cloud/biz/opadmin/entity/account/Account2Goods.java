package cn.smart.cloud.biz.opadmin.entity.account;

import cn.smart.cloud.biz.opadmin.entity.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

public class Account2Goods extends BaseEntity {

    private String accountId;
    private String gartenGoodsId;
    private Date dueDate;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getGartenGoodsId() {
        return gartenGoodsId;
    }

    public void setGartenGoodsId(String gartenGoodsId) {
        this.gartenGoodsId = gartenGoodsId;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}