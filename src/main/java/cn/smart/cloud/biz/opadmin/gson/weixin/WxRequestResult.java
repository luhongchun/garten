package cn.smart.cloud.biz.opadmin.gson.weixin;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class WxRequestResult {
    private int errcode;
    private String errmsg;
    private String msgid;

    public static final int INVALID_CREDENTAIL = 40001;

    /**
     * @return the errcode
     */
    public int getErrcode() {
        return errcode;
    }

    /**
     * @param errcode the errcode to set
     */
    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    /**
     * @return the errmsg
     */
    public String getErrmsg() {
        return errmsg;
    }

    /**
     * @param errmsg the errmsg to set
     */
    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
