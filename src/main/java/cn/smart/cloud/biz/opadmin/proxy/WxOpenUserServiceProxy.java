package cn.smart.cloud.biz.opadmin.proxy;

import cn.smart.cloud.biz.opadmin.entity.weixin.WxOpenUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service
public class WxOpenUserServiceProxy {

    private static final String BASE_URL_OPEN_USER = ServiceConstant.MSVC_INSTANCE_NAME_WECHAT_SERVER + "wx/openuser";

    @Autowired
    private SfRestTemplate restTemplate;

    public WxOpenUser create(WxOpenUser openUser) {
        WxOpenUser result = restTemplate.post(
                BASE_URL_OPEN_USER + "/create", openUser, WxOpenUser.class);
        return result;
    }

    public WxOpenUser update(WxOpenUser openUser) {
        WxOpenUser result = restTemplate.post(
                BASE_URL_OPEN_USER + "/update", openUser, WxOpenUser.class);
        return result;
    }

    public void delete(String id) {
        restTemplate.delete(BASE_URL_OPEN_USER + "/delete/" + id);
    }

    public WxOpenUser getWxOpenUserByOpenId(String openId) {
        if (StringUtils.isEmpty(openId)) {
            return null;
        }
        WxOpenUser user = restTemplate.get(
                BASE_URL_OPEN_USER + "/getByOpenId/" + openId,
                WxOpenUser.class);
        return user;
    }

    public WxOpenUser getWxOpenUserByBaseUserIdAndSubscriptionId(String baseUserId, String subscriptionId) {
        WxOpenUser user = restTemplate.get(
                BASE_URL_OPEN_USER + "/getByBaseUserIdAndSubscriptionId/" + baseUserId + "/" + subscriptionId,
                WxOpenUser.class);
        return user;
    }

}