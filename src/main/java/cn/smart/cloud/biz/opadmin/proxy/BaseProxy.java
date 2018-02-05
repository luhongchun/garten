package cn.smart.cloud.biz.opadmin.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;

@Service
class BaseProxy {

    @Autowired
    protected SfRestTemplate restTpl;

    protected  <T> T restGet(String action, Class<T> responseType) {
        return restTpl.get((ServiceConstant.MSVC_INSTANCE_NAME_GARTEN_SERVICE + "garten") + action,
                responseType);
    }

    protected <T> T restPost(String action, Object src, Class<T> responseType) {
        return restTpl.post((ServiceConstant.MSVC_INSTANCE_NAME_GARTEN_SERVICE + "garten") + action,
                src, responseType);
    }

    protected <T> T restPostFile(String action, File file, Class<T> responseType, Map<String, ?> params) {
        return restTpl.postFile((ServiceConstant.MSVC_INSTANCE_NAME_GARTEN_SERVICE + "garten") + action,
                file, responseType, params);
    }

    protected void restDel(String action) {
        restTpl.delete((ServiceConstant.MSVC_INSTANCE_NAME_GARTEN_SERVICE + "garten") + action);
    }
}
