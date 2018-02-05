package cn.smart.cloud.biz.opadmin.entity.weixin.menu;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Author: jjj
 * Date: 16-1-8
 * Time: 上午1:17
 */
public class WxMenuId {

    private String menuid;
    private Integer errcode;
    private String errmsg;

    public String getMenuid() {
        return menuid;
    }

    public void setMenuid(String menuid) {
        this.menuid = menuid;
    }

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

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}