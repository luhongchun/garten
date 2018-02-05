package cn.smart.cloud.biz.opadmin.entity.weixin;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Author: jjj
 * Date: 15-9-12
 * Time: 下午5:26
 */
public class WxJsConfig {

    // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
    private boolean debug;
    // 必填，公众号的唯一标识
    private String appId;
    // 必填，生成签名的时间戳
    private long timestamp;
    // 必填，生成签名的随机串
    private String nonceStr;
    // 必填，签名，见附录1
    private String signature;
    // 指定用到的api列表
    private List<String> jsApiList;
    // 必填，config本身属于哪个页面
    private String pageUrl;

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public List<String> getJsApiList() {
        return jsApiList;
    }

    public void setJsApiList(List<String> jsApiList) {
        this.jsApiList = jsApiList;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}