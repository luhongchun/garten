package cn.smart.cloud.biz.opadmin.entity.weixin;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class WxCommonResult {

    public static final WxCommonResult OK = new WxCommonResult(0L, "ok");
    public static final WxCommonResult ERROR = new WxCommonResult(-1L, "error");

    private Long errcode;
    private String errmsg;

    public WxCommonResult() {

    }

    public WxCommonResult(Long errcode, String errmsg) {
        setErrcode(errcode);
        setErrmsg(errmsg);
    }

    public Long getErrcode() {
        return errcode;
    }

    public void setErrcode(Long errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof WxCommonResult == false)
            return super.equals(obj);
        WxCommonResult equalsObj = (WxCommonResult) obj;

        return equalsObj.getErrcode() == getErrcode();
    }
}
