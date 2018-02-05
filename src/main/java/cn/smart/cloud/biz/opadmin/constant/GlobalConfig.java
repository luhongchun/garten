package cn.smart.cloud.biz.opadmin.constant;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;

public class GlobalConfig {


    public static final String WX_BASE_URL = "https://dev.wx.smart-f.cn";
    public static final String FORMAT_REDIRECT_URL = WX_BASE_URL + "/wx/auth/get_base_auth_result?instanceId=%s&url=%s";

    @Bean
    public Gson bindGson() {
        return new Gson();
    }
}
