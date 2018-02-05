package cn.smart.cloud.biz.opadmin.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WxAuthService {

    private static final String BASE_URL_WX_AUTH = ServiceConstant.MSVC_INSTANCE_NAME_WECHAT_SERVER + "wx/auth";
    @Autowired
    SfRestTemplate restTemplate;

    public String wxOAuth(String instanceId, String url) {
        String result = restTemplate.get(
                BASE_URL_WX_AUTH + "/baseAuth/" + instanceId + "/" + url,
                String.class);
        return result;
    }

    public String getOAuthUri(String instanceId) {
        String result = restTemplate.get(
                BASE_URL_WX_AUTH + "/base/" + instanceId,
                String.class);
        return result;
    }
}