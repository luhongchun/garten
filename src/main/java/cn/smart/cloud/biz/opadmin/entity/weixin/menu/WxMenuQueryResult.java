package cn.smart.cloud.biz.opadmin.entity.weixin.menu;


import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Author: jjj
 * Date: 16-1-8
 * Time: 下午4:40
 */
public class WxMenuQueryResult {

    private Integer errcode;
    private String errmsg;
    private WxCustomMenu menu;
    private List<WxIndividualMenu> conditionalmenu;

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public WxCustomMenu getMenu() {
        return menu;
    }

    public void setMenu(WxCustomMenu menu) {
        this.menu = menu;
    }

    public List<WxIndividualMenu> getConditionalmenu() {
        return conditionalmenu;
    }

    public void setConditionalmenu(List<WxIndividualMenu> conditionalmenu) {
        this.conditionalmenu = conditionalmenu;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
