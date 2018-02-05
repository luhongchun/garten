package cn.smart.cloud.biz.opadmin.util;

import cn.smart.cloud.biz.opadmin.entity.weixin.WxAccessToken;
import cn.smart.cloud.biz.opadmin.entity.weixin.WxCustomMessage;
import cn.smart.cloud.biz.opadmin.entity.weixin.WxJsTicket;
import cn.smart.cloud.biz.opadmin.entity.weixin.WxTemplateMessage;
import cn.smart.cloud.biz.opadmin.gson.weixin.WxRequestResult;
import cn.smart.cloud.biz.opadmin.gson.weixin.WxUserListResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

public class WxBaseUtil {

    private static final Logger logger = LoggerFactory.getLogger(WxBaseUtil.class);

    @Autowired
    private static RestTemplate restTemplate = new RestTemplate();

    public static WxAccessToken getAccessToken(String appId, String secret) {
        WxAccessToken accessToken = null;
        try {
            URI uri = WxURIFactory.getWxAccessTokenURI(appId, secret);
            accessToken = restTemplate.getForObject(uri, WxAccessToken.class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return accessToken;
    }

    public static WxJsTicket getJsTicket(String access_token) {
        WxJsTicket JsTicket = null;
        try {
            URI uri = WxURIFactory.getWxJsTicketURI(access_token);
            logger.info("getJsTicket, uri:" + uri.toString());
            JsTicket = restTemplate.getForObject(uri, WxJsTicket.class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return JsTicket;
    }

    public static WxRequestResult sendCustomerMessage(String accessToken, WxCustomMessage message) {
        WxRequestResult result = null;
        try {
            URI uri;
            uri = WxURIFactory.getWxSendCustomMessageURI(accessToken);
            result = restTemplate.postForObject(uri, message, WxRequestResult.class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static WxUserListResult getUserList(String accessToken) {
        WxUserListResult result = null;
        try {
            URI uri;
            uri = WxURIFactory.getWxUserListURI(accessToken);
            result = restTemplate.getForObject(uri, WxUserListResult.class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static WxUserListResult getUserList(String accessToken, String nextOpenId) {
        WxUserListResult result = null;
        try {
            URI uri;
            uri = WxURIFactory.getWxUserListURI(accessToken, nextOpenId);
            result = restTemplate.getForObject(uri, WxUserListResult.class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static WxRequestResult sendTemplateMessage(String accessToken, WxTemplateMessage message) {
        WxRequestResult result = null;
        try {
            URI uri;
            uri = WxURIFactory.getWxSendTemplateMessageURI(accessToken);
            result = restTemplate.postForObject(uri, message, WxRequestResult.class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return result;
    }
}
