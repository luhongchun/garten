package cn.smart.cloud.biz.opadmin.entity.weixin;

import cn.smart.cloud.biz.opadmin.entity.BaseMongoEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Author: jjj
 * Date: 15-9-12
 * Time: 下午6:02
 */
public class WxJsTicket extends BaseMongoEntity {

    private String appId;
    private String ticket;
    private long timestamp;
    private int errcode;
    private String errmsg;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

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

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}