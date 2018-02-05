package cn.smart.cloud.biz.opadmin.entity.garten.data;

import cn.smart.cloud.biz.opadmin.entity.account.Account;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class ChildAccount {

    private String id;
    private String aliasName;
    private List<Account> parents;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public List<Account> getParents() {
        return parents;
    }

    public void setParents(List<Account> parents) {
        this.parents = parents;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}