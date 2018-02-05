package cn.smart.cloud.biz.opadmin.proxy;

import cn.smart.cloud.biz.opadmin.constant.Constant;
import cn.smart.cloud.biz.opadmin.entity.BaseRole;
import cn.smart.cloud.biz.opadmin.entity.BaseUserCapacity;
import cn.smart.cloud.biz.opadmin.entity.GartenManager;
import cn.smart.cloud.biz.opadmin.entity.SfService;
import cn.smart.cloud.biz.opadmin.entity.garten.Garten;
import cn.smart.cloud.biz.opadmin.entity.user.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class BaseCoreServiceProxy {

    private static final Logger logger = LoggerFactory.getLogger(BaseCoreServiceProxy.class);

    private static final String BASE_URL_BASE_ROLE = ServiceConstant.MSVC_INSTANCE_NAME_BASE_SERVICE
            + "baserole";
    private static final String BASE_URL_BASE_CAP = ServiceConstant.MSVC_INSTANCE_NAME_BASE_SERVICE
            + "baseusercapacity";
    private static final String BASE_URL_BASE_PERM = ServiceConstant.MSVC_INSTANCE_NAME_BASE_SERVICE
            + "permission";
    private static final String BASE_URL_BASE_BIZ = ServiceConstant.MSVC_INSTANCE_NAME_BASE_SERVICE
            + "base/biz";
    private static final String BASE_URL_SYS_USER = ServiceConstant.MSVC_INSTANCE_NAME_BASE_SERVICE
            + "sysuser";
   // private static final String BASE_URL_GARTEN_USER = ServiceConstant.MSVC_INSTANCE_NAME_GARTEN_SERVICE;
    private static final String BASE_URL_GARTEN_USER = ServiceConstant.MSVC_INSTANCE_NAME_GARTEN_SERVICE
           .substring(0, ServiceConstant.MSVC_INSTANCE_NAME_GARTEN_SERVICE.length() - 1);
    @Autowired
    private SfRestTemplate rstl;

    public BaseRole getBaseRoleById(String id) {
        return rstl.get(BASE_URL_BASE_ROLE + "/get/" + id, BaseRole.class);
    }

    public BaseRole createBaseRole(BaseRole baseRole) {
        return rstl.post(BASE_URL_BASE_ROLE + "/create", baseRole, BaseRole.class);
    }

    public Boolean deleteBaseRole(String id) {
        return rstl.get(BASE_URL_BASE_ROLE + "/delete/" + id, Boolean.class);
    }

    public BaseRole updateBaseRole(BaseRole baseRole) {
        return rstl.post(BASE_URL_BASE_ROLE + "/update", baseRole, BaseRole.class);
    }

    public SfService getServiceByName(String name) {
        return rstl.get(BASE_URL_BASE_PERM + "/getServiceByName/" + name, SfService.class);
    }

    public boolean checkBizRegistered(String baseUserId, Constant.PermissionType permissionType) {
        return rstl.get(BASE_URL_BASE_BIZ + "/registered/check/"
                        + baseUserId + "/" + permissionType.name(), Boolean.class);
    }

    public BaseUserCapacity getUserCapacity(String baseUserId) {
        logger.info("getUserCapacity, baseUserId:" + baseUserId);
        return rstl.get(BASE_URL_BASE_CAP + "/getByBaseUserId/" + baseUserId, BaseUserCapacity.class);
    }

    public BaseUserCapacity updateUserCapacity(BaseUserCapacity entity) {
        return rstl.post(BASE_URL_BASE_CAP + "/update", entity, BaseUserCapacity.class);
    }

    public boolean deleteUserCapacity(String id) {
        return rstl.get(BASE_URL_BASE_CAP + "/delete/" + id, Boolean.class);
    }

    //============================ Face Related ============================

    public SysUser getUserByLoginId(String loginId) {
        logger.info("getUserByLoginId, loginId:" + loginId);
        return rstl.get(BASE_URL_SYS_USER + "/getUserByLoginId/" + loginId, SysUser.class);
    }

    public SysUser getSysUser(String sysUserId) {
        return rstl.get(BASE_URL_SYS_USER + "/get/" + sysUserId, SysUser.class);
    }

    public List<SysUser> getSysUsers() {
        return Arrays.asList(rstl.get(BASE_URL_SYS_USER + "/getUserList", SysUser[].class));
    }

    public SysUser saveSysUser(SysUser sysUser) {
        return rstl.post(BASE_URL_SYS_USER + "/save", sysUser, SysUser.class);
    }

    public void deleteSysUser(String sysUserId) {
        rstl.get(BASE_URL_SYS_USER + "/delete/" + sysUserId, null);
    }
    //============================ Garten  admin ============================
    public SysUser  getBaseUrlGartenUserId(String loginId,String password) {
        logger.info("getBaseUrlGartenUserId, loginId:" + loginId+"password:"+password);
      //  return rstl.get(BASE_URL_GARTEN_USER + "/security/loginManager/", String.class);
        return rstl.get(BASE_URL_GARTEN_USER + "/security/login/"+loginId+"/"+password, SysUser.class);
    }
    public GartenManager  verifyGartenManager(String loginId,String password) {
        logger.info("verifyGartenManager0, loginId:" + loginId+"password:"+password);
        return rstl.get(BASE_URL_GARTEN_USER + "/garten/manager/verifyGartenManager/"+loginId+"/"+password, GartenManager.class);
    }
    public Garten[]  getGartensByManagerId(String managerId) {
        logger.info("getGartensByManagerId, managerId:" + managerId);
        return rstl.get(BASE_URL_GARTEN_USER + "/garten/manager/getGartensByManagerId/"+managerId, Garten[].class);
    }
    public String  getGartenManagerId(String loginId) {
        logger.info("getGartenManagerId, loginId:" + loginId);
        return rstl.get(BASE_URL_GARTEN_USER + "/garten/manager/getGartenManagerId/"+loginId,String.class);
    }


}