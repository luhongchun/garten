package cn.smart.cloud.biz.opadmin.proxy;

import cn.smart.cloud.biz.opadmin.constant.Constant;
import cn.smart.cloud.biz.opadmin.entity.BaseUser;
import cn.smart.cloud.biz.opadmin.entity.weixin.WxOpenUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;


@Service
public class BaseUserServiceProxy {

    private static final Logger logger = LoggerFactory.getLogger(BaseUserServiceProxy.class);
    private static final String BASE_URL_BASE_USER = ServiceConstant.MSVC_INSTANCE_NAME_BASE_SERVICE + "baseuser";

    @Autowired
    private SfRestTemplate rslt;
    @Autowired
    BaseCoreServiceProxy baseServiceProxy;

    public BaseUser create(BaseUser baseUser) {
        return rslt.post(BASE_URL_BASE_USER + "/create", baseUser, BaseUser.class);
    }

    public BaseUser update(BaseUser baseUser) {
        return rslt.post(BASE_URL_BASE_USER + "/update", baseUser, BaseUser.class);
    }

    public Boolean delete(String id) {
        return rslt.get(BASE_URL_BASE_USER + "/delete/" + id, Boolean.class);
    }

    public BaseUser get(String id) {
        BaseUser user = rslt.get(
                BASE_URL_BASE_USER + "/get/" + id, BaseUser.class);
        return user;
    }

    public BaseUser getByPhoneNum(String phoneNum) {
        logger.info("getByPhoneNum, phoneNum:" + phoneNum);
        BaseUser user = rslt.get(
                BASE_URL_BASE_USER + "/getByPhoneNum/" + phoneNum, BaseUser.class);
        logger.info("get, user:" + user);
        return user;
    }

    public List<BaseUser> getAll() {
        BaseUser[] userList = rslt.get(
                BASE_URL_BASE_USER + "/getAll",
                BaseUser[].class);
        return Arrays.asList(userList);
    }

    /**
     * -------------------        biz              ---------------------
     */
    public boolean checkRegister(WxOpenUser wxOpenUser) {
        String baseUserId = wxOpenUser.getBaseUserId();
        if (StringUtils.isEmpty(baseUserId))
            return false;
        return get(baseUserId) != null;
    }

    public boolean checkPermissionCap(String baseUserId, Constant.PermissionType type) {
        //check role?
        logger.info("checkPermissionCap, baseUserId:" + baseUserId);
        if (StringUtils.isEmpty(baseUserId))
            return false;
        if (type == Constant.PermissionType.VIDEO_LIVE)
            return true;//for default
        BaseUser baseUser = get(baseUserId);
        logger.info("checkPermissionCap, baseUser:" + baseUser);
        if (baseUser == null || !baseServiceProxy.checkBizRegistered(
                baseUser.getId(), type)) {
            return false;
        }
        return true;
    }

}