package cn.smart.cloud.biz.opadmin.controller.garten;

import cn.smart.cloud.biz.opadmin.constant.Constant;
import cn.smart.cloud.biz.opadmin.constant.GlobalConfig;
import cn.smart.cloud.biz.opadmin.constant.WxConstant;
import cn.smart.cloud.biz.opadmin.controller.MessageRender;
import cn.smart.cloud.biz.opadmin.entity.AccountBizInfo;
import cn.smart.cloud.biz.opadmin.entity.account.Account;
import cn.smart.cloud.biz.opadmin.entity.account.Account2Camera;
import cn.smart.cloud.biz.opadmin.entity.garten.GartenClass;
import cn.smart.cloud.biz.opadmin.entity.garten.data.ChildAccount;
import cn.smart.cloud.biz.opadmin.entity.garten.data.GartenInfo;
import cn.smart.cloud.biz.opadmin.entity.garten.user.GartenUser;
import cn.smart.cloud.biz.opadmin.entity.weixin.WxOpenUser;
import cn.smart.cloud.biz.opadmin.entity.weixin.WxSubscription;
import cn.smart.cloud.biz.opadmin.gson.BizVideoType;
import cn.smart.cloud.biz.opadmin.gson.UserCamera;
import cn.smart.cloud.biz.opadmin.gson.device.Camera;
import cn.smart.cloud.biz.opadmin.gson.video.VideoConfig;
import cn.smart.cloud.biz.opadmin.proxy.*;
import cn.smart.cloud.biz.opadmin.util.UriUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/biz/setting")
public class SettingController {

    private static final Logger logger = LoggerFactory.getLogger(SettingController.class);

    @Autowired
    private GartenServiceProxy gartenServiceProxy;
    @Autowired
    private BaseUserServiceProxy baseUserServiceProxy;
    @Autowired
    private DeviceServiceProxy deviceServiceProxy;
    @Autowired
    private WxServiceProxy wxServiceProxy;
    @Autowired
    private WxOpenUserServiceProxy wxOpenUserService;
    @Autowired
    WxAuthService wxAuthService;
    @Autowired
    VideoServiceProxy videoServiceProxy;
    @Autowired
    private Environment environment;

    @Value("${service.biz.wan.url.base}")
    String serverUrl;
    @Value("${service.biz.version}")
    String version;
    @Value("${text.biz.wx.subscribe}")
    String needReSubscribe;
    @Value("${text.biz.garten.video.play.live.open}")
    String playLiveOpen;
    @Value("${text.biz.garten.video.play.live.closed}")
    String playLiveClosed;

    public static final String ACTION_SYSTEM_SETTING = "action_system_setting";
    private static final String ACTION_CAMERA_SETTING = "action_camera_setting";
    private static final String ACTION_ACCOUNT_CLASS = "action_user_account_class";
    private static final String ACTION_ACCOUNT_CHILDREN = "action_user_account_children";
    private static final String ACTION_ACCOUNT_PARENT = "action_user_account_parent";
    //private static final String ACTION_ACCOUNT_SETTING     = "action_user_account_setting";
    private static final String ACTION_USER_CAMERA_SETTING = "action_user_camera_setting";
    private static final String ACTION_RENEW = "renew";

    @RequestMapping(value = ACTION_SYSTEM_SETTING + "_auth/{instanceId}")
    public String systemSettingAuth(@PathVariable String instanceId) {
        logger.info("systemSettingAuth, instanceId:" + instanceId);
        final String redirectUrl = String.format(GlobalConfig.FORMAT_REDIRECT_URL, instanceId, UriUtil.encode(
                serverUrl + "/biz/setting/" + ACTION_SYSTEM_SETTING));

        String url = wxAuthService.getOAuthUri(instanceId);
        String codeUrl = url.replace(WxConstant.WX_MOCK_REDIRECT_URI, UriUtil.encode(redirectUrl));
        logger.info("systemSettingAuth, codeUrl:" + codeUrl);

        return "redirect:" + codeUrl;
    }
        
    /*@RequestMapping(value = ACTION_CAMERA_SETTING+"_auth/{instanceId}")
    public String cameraSettingAuth(@PathVariable String instanceId) {
        logger.info("cameraSettingAuth, instanceId:" + instanceId);
        final String redirectUrl = String.format(FORMAT_REDIRECT_URL, instanceId, UriUtil.encode(
                serverUrl+"/biz/setting/"+ACTION_CAMERA_SETTING));
        
        String url = wxAuthService.getOAuthUri(instanceId);
        String codeUrl = url.replace(WxConstant.WX_MOCK_REDIRECT_URI, UriUtil.encode(redirectUrl));
        logger.info("cameraSettingAuth, codeUrl:"+codeUrl);
        
        return "redirect:"+codeUrl;
    }
    
    @RequestMapping(value = ACTION_ACCOUNT_SETTING+"_auth/{instanceId}")
    public String accountSettingAuth(@PathVariable String instanceId) {
        logger.info("accountSettingAuth, instanceId:" + instanceId);
        final String redirectUrl = String.format(FORMAT_REDIRECT_URL, instanceId, UriUtil.encode(
                serverUrl+"/biz/setting/"+ACTION_ACCOUNT_SETTING));
        
        String url = wxAuthService.getOAuthUri(instanceId);
        String codeUrl = url.replace(WxConstant.WX_MOCK_REDIRECT_URI, UriUtil.encode(redirectUrl));
        logger.info("accountSettingAuth, codeUrl:"+codeUrl);
        
        return "redirect:"+codeUrl;
    }*/

    @RequestMapping(value = ACTION_SYSTEM_SETTING + "/{openId}", method = RequestMethod.GET)
    public String systemSetting(HttpServletRequest request,
                                @PathVariable String openId) {
        logger.info("systemSetting, after auth, openId: " + openId);
        WxOpenUser wxOpenUser = wxOpenUserService.getWxOpenUserByOpenId(openId);
        String ret = checkSubAndReg(request, wxOpenUser);
        if (!StringUtils.isEmpty(ret))
            return ret;
        WxSubscription subscription = wxServiceProxy.getSubscriptionById(wxOpenUser.getSubscriptionId());
        String baseUserId = wxOpenUser.getBaseUserId();
        if (!baseUserServiceProxy.checkPermissionCap(baseUserId, Constant.PermissionType.MANAGE_CAMERA)) {
            String phoneNum = baseUserServiceProxy.get(baseUserId).getPhoneNum();
            logger.info("systemSetting, normal user:" + phoneNum);
            if (StringUtils.isBlank(phoneNum))
                phoneNum = "未知";
            phoneNum = phoneNum.substring(0, 3) + "******" + phoneNum.substring(9);
            List<AccountBizInfo> bizInfo = gartenServiceProxy.getBizInfo(baseUserId);
            GartenUser user = gartenServiceProxy.getAllByBaseUserId(baseUserId).get(0);
            GartenInfo gartenInfo = gartenServiceProxy.getGartenInfo(
                    gartenServiceProxy.get(user.getGartenId()).getGartenInfoId());
            VideoConfig vc = videoServiceProxy.getVideoConfigByKinderGartenIdAndBizType(
                    user.getGartenId(), BizVideoType.LIVE.name());
            List<Camera> cameras = gartenServiceProxy.getCamerasRemoveDuplicate(baseUserId);
            logger.info("systemSetting, phoneNum:" + phoneNum + ", cameraCnt:" + cameras.size() + ", v:" + version);

            request.setAttribute("phoneNum", phoneNum);
            request.setAttribute("openId", openId);
            request.setAttribute("bizInfo", bizInfo);
            request.setAttribute("online", gartenInfo == null ? false : gartenInfo.isPayOnline());
            request.setAttribute("history", vc.isHistory());
            request.setAttribute("cameras", cameras);
            request.setAttribute("version", version);
            request.setAttribute("environment", environment);
            request.setAttribute("piwikSiteId", subscription.getPiwikId());
            return "video/user_info";
        } else {
            request.setAttribute(WxConstant.WX_OPEN_ID, openId);
            request.setAttribute("environment", environment);
            request.setAttribute("piwikSiteId", subscription.getPiwikId());
            return "video/setting";
        }
    }

    @RequestMapping(value = ACTION_CAMERA_SETTING + "/{openId}", method = RequestMethod.GET)
    public String cameraSetting(HttpServletRequest request,
                                @PathVariable String openId) {
        logger.info("cameraSetting, after auth, openId: " + openId);
        WxOpenUser wxOpenUser = wxOpenUserService.getWxOpenUserByOpenId(openId);
        String ret = checkSubAndReg(request, wxOpenUser);
        if (!StringUtils.isEmpty(ret))
            return ret;
        WxSubscription subscription = wxServiceProxy.getSubscriptionById(wxOpenUser.getSubscriptionId());

        if (!baseUserServiceProxy.checkPermissionCap(wxOpenUser.getBaseUserId(), Constant.PermissionType.MANAGE_CAMERA)) {
            String warn = "您尚未开通该业务，请联系幼儿园办理";
            logger.warn(warn);
            return "prompt/prompt_not_in_service";
        }

        List<Camera> cameras = gartenServiceProxy.getCamerasRemoveDuplicate(wxOpenUser
                .getBaseUserId());
        logger.info("cameraSetting, camera cnt: " + cameras.size());
        if (cameras.isEmpty()) {
            return "prompt/camera_manage_none";
        }

        boolean allOpen = true;
        for (Camera camera : cameras) {
            if (camera.isClose()) {
                allOpen = false;
                break;
            }
        }

        request.setAttribute("cameras", cameras);
        request.setAttribute("allOpen", allOpen);
        request.setAttribute(WxConstant.WX_OPEN_ID, openId);
        request.setAttribute("environment", environment);
        request.setAttribute("piwikSiteId", subscription.getPiwikId());
        return "video/camera_manage_list";
    }

    @RequestMapping(value = ACTION_ACCOUNT_CLASS + "/{openId}", method = RequestMethod.GET)
    public String accountClass(HttpServletRequest request,
                               @PathVariable String openId) {
        logger.info("accountClass, after auth, openId: " + openId);
        WxOpenUser wxOpenUser = wxOpenUserService.getWxOpenUserByOpenId(openId);
        String ret = checkSubAndReg(request, wxOpenUser);
        if (!StringUtils.isEmpty(ret))
            return ret;
        WxSubscription subscription = wxServiceProxy.getSubscriptionById(wxOpenUser.getSubscriptionId());
        String instanceId = subscription.getInstanceId();

        List<GartenUser> gartenUsers = gartenServiceProxy.getAllByBaseUserId(wxOpenUser.getBaseUserId());

        if (gartenUsers.isEmpty()) {
            String warn = "您尚未开通该业务，请联系幼儿园办理";
            logger.warn(warn);
            return "prompt/prompt_not_in_service";
        }
        String gartenId = gartenUsers.get(0).getGartenId();
        List<GartenClass> classList = gartenServiceProxy.getAllClassesByGartenId(gartenId);
        request.setAttribute("classList", classList);
        request.setAttribute(WxConstant.WX_AUTH_INSTANCE_ID, instanceId);
        request.setAttribute(WxConstant.WX_OPEN_ID, openId);
        request.setAttribute("environment", environment);
        request.setAttribute("piwikSiteId", subscription.getPiwikId());
        return "video/account_class_list";
    }

    @RequestMapping(value = ACTION_ACCOUNT_CHILDREN, method = RequestMethod.POST)
    public String accountChildren(HttpServletRequest request) {
        String classId = request.getParameter("classId");
        String openId = request.getParameter("openId");
        logger.info("accountChildren, classId: " + classId + ", openId:" + openId);
        if (StringUtils.isEmpty(classId)) {
            logger.info("accountChildren, param invalid.");
            request.setAttribute("msg", "param invalid.");
            return "common/common_msg";
        }

        List<GartenUser> childs = gartenServiceProxy.getChildrenByClassId(classId);
        if (childs.isEmpty()) {
            request.setAttribute("className", gartenServiceProxy.getClass(classId).getName());
            return "prompt/account_class_nouser";
        }
        List<ChildAccount> childAccounts = new ArrayList<>();
        logger.info("accountChildren, childs cnt: " + childs.size());

        for (GartenUser child : childs) {
            List<GartenUser> parents = gartenServiceProxy.getParentsByChild(child.getId());
            if (parents.isEmpty())
                continue;
            ChildAccount childAccount = new ChildAccount();
            childAccount.setId(child.getId());
            childAccount.setAliasName(child.getAliasName());
            List<Account> parentAccounts = new ArrayList<>();
            for (GartenUser parent : parents) {
                Account account = gartenServiceProxy.getByBaseUserId(parent.getBaseUserId());
                if (account == null) {
                    account = new Account();
                    account.setBaseUserId(parent.getBaseUserId());
                    account.setAliasName(parent.getAliasName());
                    account = gartenServiceProxy.updateAccount(account);
                }
                parentAccounts.add(account);
            }
            childAccount.setParents(parentAccounts);
            childAccounts.add(childAccount);
        }

        request.setAttribute("classId", classId);
        request.setAttribute("className", gartenServiceProxy.getClass(classId).getName());
        request.setAttribute("openId", openId);
        request.setAttribute("childAccounts", childAccounts);
        request.setAttribute("environment", environment);
        return "video/account_child_list";
    }

    @RequestMapping(value = ACTION_ACCOUNT_PARENT, method = RequestMethod.POST)
    public String accountParent(HttpServletRequest request) {
        String childId = request.getParameter("childId");
        logger.info("accountParent, childId: " + childId);
        if (StringUtils.isEmpty(childId)) {
            logger.info("accountParent, param invalid.");
            request.setAttribute("msg", "param invalid.");
            return "common/common_msg";
        }

        List<GartenUser> parents = gartenServiceProxy.getParentsByChild(childId);
        if (parents == null || parents.isEmpty()) {
            request.setAttribute("childId", childId);
            return "prompt/account_class_nouser";
        }
        for (GartenUser parent : parents) {
            Account account = gartenServiceProxy.getByBaseUserId(parent.getBaseUserId());
            if (account == null) {
                account = new Account();
                account.setBaseUserId(parent.getBaseUserId());
                account.setAliasName(parent.getAliasName());
                gartenServiceProxy.updateAccount(account);
            }
        }

        request.setAttribute("childName", gartenServiceProxy.getUser(childId).getAliasName());
        request.setAttribute("parents", parents);
        request.setAttribute("environment", environment);
        return "video/account_parent_list";
    }
    
    /*@RequestMapping(value = ACTION_ACCOUNT_SETTING+"/{openId}", method = RequestMethod.GET)
    public String accountSetting(HttpServletRequest request,
            @PathVariable String openId) {
        logger.info("accountSetting, after auth, openId: "+ openId);
        WxOpenUser wxOpenUser = wxOpenUserService.getWxOpenUserByOpenId(openId);
        String ret = checkSubAndReg(request, wxOpenUser);
        if (!StringUtils.isEmpty(ret))
            return ret;
        WxSubscription subscription = wxServiceProxy.getSubscriptionById(wxOpenUser.getSubscriptionId());
        String instanceId = subscription.getInstanceId();

        if (!checkPermission(wxOpenUser, PermissionType.MANAGE_CAMERA)) {
            String warn = "您尚未开通该业务，请联系幼儿园办理";
            logger.warn(warn);
            return "prompt/prompt_not_in_service";
        }
        List<GartenUser> gartenUsers = gartenUserController.getAllByBaseUserId(wxOpenUser.getBaseUserId());
        
        if (gartenUsers.isEmpty()) {
            String warn = "您尚未开通该业务，请联系幼儿园办理";
            logger.warn(warn);
            return "prompt/prompt_not_in_service";
        }
        String gartenId = gartenUsers.get(0).getGartenId();
        List<Map<String, Object>> dataList = new ArrayList<>();
        List<GartenClass> classList = gartenClassController.getAllByGartenId(gartenId);
        for (GartenClass gartenClass:classList) {
            List<GartenUser> childs = gartenUserController.getChildrenByClassId(gartenClass.getId());
            //List<Account> accounts = accountService.getAccountsByClassId(gartenClass.getId());
            List<ChildAccount> childAccounts = new ArrayList<>();
            logger.info("accountSetting, childs cnt: "+ childs.size());
            Map<String, Object> data = new HashMap<>();
            data.put("name", gartenClass.getName());
            for (GartenUser child:childs) {
                ChildAccount childAccount = new ChildAccount();
                childAccount.setId(child.getId());
                childAccount.setAliasName(child.getAliasName());
                List<GartenUser> parents = gartenUserController.getParentsByChild(child.getId());
                for (GartenUser parent:parents) {
                    Account account = accountService.getByBaseUserId(parent.getBaseUserId());
                    if (account.isClose()) {
                        childAccount.setClose(true);
                        break;
                    }
                }
                childAccounts.add(childAccount);
            }
            data.put("childs", childAccounts);
            dataList.add(data);
        }
        
        request.setAttribute("dataList", dataList);
        request.setAttribute(WxConstant.WX_AUTH_INSTANCE_ID, instanceId);
        request.setAttribute(WxConstant.WX_OPEN_ID, openId);
        request.setAttribute("environment", environment);
        request.setAttribute("piwikSiteId", subscription.getPiwikId());
        return "video/account_manage_list";
    }*/

    @RequestMapping(value = ACTION_USER_CAMERA_SETTING, method = RequestMethod.POST)
    public String userCameraSetting(HttpServletRequest request) {
        String openId = request.getParameter("openId");
        String classId = request.getParameter("classId");
        String childId = request.getParameter("childId");
        logger.info("userCameraSetting, openId: " + openId + ", classId: " + classId + ", childId: " + childId);
        GartenUser child = gartenServiceProxy.getUser(childId);
        List<GartenUser> parents = gartenServiceProxy.getParentsByChild(childId);
        if (parents.isEmpty()) {
            logger.info("userCameraSetting, no parents.");
            String warn = "您尚未开通该业务，请联系幼儿园办理";
            logger.warn(warn);
            return "prompt/prompt_not_in_service";
        }

        List<UserCamera> cameras = new ArrayList<>();
        List<Camera> allCameras = new ArrayList<>();
        if (StringUtils.isEmpty(openId)) {
            allCameras = deviceServiceProxy.getAllCamerasByKinderGartenIdAndBizType(
                    gartenServiceProxy.getClass(classId).getGartenId(), BizVideoType.LIVE.name());
        } else {
            WxOpenUser wxOpenUser = wxOpenUserService.getWxOpenUserByOpenId(openId);
            allCameras = gartenServiceProxy.getCamerasRemoveDuplicate(wxOpenUser.getBaseUserId());
        }
        logger.info("userCameraSetting, all cameras cnt: " + allCameras.size());

        List<String> userCameraIds = new ArrayList<String>();
        for (GartenUser parent : parents) {
            List<Camera> cameraList = gartenServiceProxy.getCamerasRemoveDuplicate(parent.getBaseUserId());
            for (Camera camera : cameraList) {
                if (!userCameraIds.contains(camera.getId())) {
                    userCameraIds.add(camera.getId());
                }
            }
        }
        logger.info("userCameraSetting, available cameras cnt: " + userCameraIds.size());

        for (Camera camera : allCameras) {
            UserCamera userCamera = new UserCamera();
            userCamera.setId(camera.getId());
            userCamera.setName(camera.getName());
            if (userCameraIds.contains(camera.getId())) {
                userCamera.setAvailable(true);
            }
            cameras.add(userCamera);
        }
        List<Account> parentAccounts = new ArrayList<>();
        for (GartenUser parent : parents) {
            Account account = gartenServiceProxy.getByBaseUserId(parent.getBaseUserId());
            if (account == null) {
                account = new Account();
                account.setBaseUserId(parent.getBaseUserId());
                account.setAliasName(parent.getAliasName());
                account = gartenServiceProxy.updateAccount(account);
            }
            parentAccounts.add(account);
        }
        request.setAttribute("child", child);
        request.setAttribute("openId", openId);
        request.setAttribute("classId", classId);
        request.setAttribute("parents", parentAccounts);
        request.setAttribute("cameras", cameras);
        request.setAttribute("environment", environment);
        return "video/account_camera_setting";
    }

    @RequestMapping(value = "manage_cameras", method = RequestMethod.POST)
    public void manageCameras(HttpServletRequest request,
                              HttpServletResponse response) {

        //final String openId = request.getParameter(WxConstant.WX_OPEN_ID);
        final String[] cameraIds = request.getParameterValues("cameraIds");
        final String[] enableCameras = request.getParameterValues("cameraStatus");
        if (cameraIds == null || cameraIds.length <= 0) {
            MessageRender.renderHtml(response, MessageRender.FAIL);
            return;
        }
        logger.info("manageCameras, cameraIds :" + Arrays.toString(cameraIds));
        logger.info("manageCameras, enableCameras :" + Arrays.toString(enableCameras));
        List<String> cameraIdList = new ArrayList<String>();

        for (String id : cameraIds) {
            cameraIdList.add(id);
        }
        List<String> enableCameraList = new ArrayList<String>();
        if (enableCameras != null) {
            for (String id : enableCameras) {
                enableCameraList.add(id);
            }
        }

        for (String id : cameraIds) {
            Camera camera = deviceServiceProxy.getCameraById(id);
            if (camera == null)
                continue;
            if (enableCameraList.contains(id)) {
                logger.info("manageCameras, enable camera:" + id);
                if (!camera.isClose())
                    continue;
                camera.setClose(false);
            } else {
                logger.info("manageCameras, disable camera:" + id);
                if (camera.isClose())
                    continue;
                camera.setClose(true);
            }
            deviceServiceProxy.saveCamera(camera);
        }
        MessageRender.renderHtml(response, MessageRender.SUCCESS);
    }

    @RequestMapping(value = "manage_accounts", method = RequestMethod.POST)
    public void manageAccounts(HttpServletRequest request,
                               HttpServletResponse response) {

        final String openId = request.getParameter(WxConstant.WX_OPEN_ID);
        WxSubscription subscription = wxServiceProxy.getSubscriptionById(
                wxOpenUserService.getWxOpenUserByOpenId(openId).getSubscriptionId());
        final String[] childIds = request.getParameterValues("accountIds");
        final String[] enableAccounts = request.getParameterValues("accountStatus");
        if (childIds == null || childIds.length <= 0) {
            MessageRender.renderHtml(response, MessageRender.FAIL);
            return;
        }
        logger.info("manageAccounts, accountIds :" + Arrays.toString(childIds));
        logger.info("manageAccounts, enableAccounts :" + Arrays.toString(enableAccounts));

        List<String> enableAccountList = new ArrayList<String>();
        if (enableAccounts != null) {
            for (String id : enableAccounts) {
                enableAccountList.add(id);
            }
        }

        for (String id : childIds) {
            List<GartenUser> parents = gartenServiceProxy.getParentsByChild(id);
            for (GartenUser parent : parents) {
                Account account = gartenServiceProxy.getByBaseUserId(parent.getBaseUserId());
                if (account == null) {
                    account = new Account();
                    account.setBaseUserId(parent.getBaseUserId());
                    account.setAliasName(parent.getAliasName());
                }

                String msg = "";
                if (enableAccountList.contains(id)) {
                    logger.info("manageAccounts, enable parent:" + parent);
                    if (!account.isClose())
                        continue;
                    account.setClose(false);
                    msg = playLiveOpen;
                } else {
                    logger.info("manageAccounts, disable parent:" + parent);
                    if (account.isClose())
                        continue;
                    account.setClose(true);
                    msg = playLiveClosed;
                }
                gartenServiceProxy.updateAccount(account);
                WxOpenUser wxOpenUser = wxOpenUserService.getWxOpenUserByBaseUserIdAndSubscriptionId(
                        parent.getBaseUserId(), subscription.getId());
                if (wxOpenUser != null)
                    wxServiceProxy.sendCustomTextMessage(subscription.getInstanceId(), wxOpenUser.getOpenId(), msg);
            }
        }
        MessageRender.renderHtml(response, MessageRender.SUCCESS);
    }

    @RequestMapping(value = "edit_account_cameras", method = RequestMethod.POST)
    public void editAccountCameras(HttpServletRequest request,
                                   HttpServletResponse response) {
        String childId = request.getParameter("childId");
        if (StringUtils.isBlank(childId)) {
            MessageRender.renderHtml(response, MessageRender.FAIL);
            return;
        }
        logger.info("editAccountCameras, childId :" + childId);
        GartenUser child = gartenServiceProxy.getUser(childId);
        GartenInfo gartenInfo = gartenServiceProxy.getGartenInfo(
                gartenServiceProxy.get(child.getGartenId()).getGartenInfoId());
        WxSubscription subscription = wxServiceProxy.getSubscriptionById(gartenInfo.getSubscriptionId());
        final String[] accounts = request.getParameterValues("accountIds");
        final String[] enableAccounts = request.getParameterValues("accountStatus");
        logger.info("manageAccounts, accounts:" + accounts + ", enableAccounts:" + Arrays.toString(enableAccounts));
        List<String> enableAccountList = new ArrayList<String>();
        if (enableAccounts != null) {
            for (String id : enableAccounts) {
                enableAccountList.add(id);
            }
        }
        final String[] cameraIds = request.getParameterValues("cameraIds");
        final String[] enableCameras = request.getParameterValues("cameraStatus");

        logger.info("editAccountCameras, cameraIds :" + Arrays.toString(cameraIds));
        logger.info("editAccountCameras, enableCameras :" + Arrays.toString(enableCameras));
        List<String> cameraIdList = new ArrayList<String>();

        for (String id : cameraIds) {
            cameraIdList.add(id);
        }
        List<String> enableCameraList = new ArrayList<String>();
        if (enableCameras != null) {
            for (String id : enableCameras) {
                enableCameraList.add(id);
            }
        }

        for (String accountId : accounts) {
            Account account = gartenServiceProxy.getAccount(accountId);
            String msg = "";
            if (enableAccountList.contains(accountId)) {
                if (account.isClose()) {
                    logger.info("manageAccounts, enable parent:" + accountId);
                    account.setClose(false);
                    msg = playLiveOpen;
                }
            } else {
                if (!account.isClose()) {
                    logger.info("manageAccounts, disable parent:" + accountId);
                    account.setClose(true);
                    msg = playLiveClosed;
                }
            }
            if (!StringUtils.isBlank(msg)) {
                gartenServiceProxy.updateAccount(account);
                WxOpenUser wxOpenUser = wxOpenUserService.getWxOpenUserByBaseUserIdAndSubscriptionId(
                        account.getBaseUserId(), subscription.getId());
                if (wxOpenUser != null)
                    wxServiceProxy.sendCustomTextMessage(subscription.getInstanceId(), wxOpenUser.getOpenId(), msg);
            }

            for (String cameraId : cameraIds) {
                Account2Camera account2Camera = gartenServiceProxy
                        .getAccount2CameraByAccountIdAndCameraId(accountId, cameraId);
                if (enableCameraList.contains(cameraId)) {
                    if (account2Camera == null) {
                        gartenServiceProxy.addCameraForAccount(accountId, cameraId);
                    }
                } else {
                    if (account2Camera != null) {
                        gartenServiceProxy.deleteCameraForAccount(account2Camera);
                    }
                }
            }
        }
        MessageRender.renderHtml(response, MessageRender.SUCCESS);
    }

    @RequestMapping(value = ACTION_RENEW, method = RequestMethod.POST)
    public String renew(HttpServletRequest request) {
        String bizId = request.getParameter("bizId");
        logger.info("renew, bizId: " + bizId);
        String goodsLink = gartenServiceProxy.getGoodsById(bizId).getLink();
        if (StringUtils.isBlank(goodsLink)) {
        }
        logger.info("renew, goodsLink: " + goodsLink);
        if (StringUtils.isEmpty(goodsLink)) {
            logger.info("renew, no online product.");
            return "prompt/no_online_product";
        }
        return "redirect:" + goodsLink;
    }

    private String checkSubAndReg(HttpServletRequest request, WxOpenUser wxOpenUser) {
        if (wxOpenUser == null) {
            logger.info("checkSubAndReg, no this wxOpenUser, need reSubscribe.");
            request.setAttribute("msg", needReSubscribe);
            return "common/common_msg";
        }
        if (!baseUserServiceProxy.checkRegister(wxOpenUser)) {
            request.setAttribute(WxConstant.WX_OPEN_ID, wxOpenUser.getOpenId());
            request.setAttribute(Constant.GARTEN_ID, wxServiceProxy.getGartenIdByWxUser(wxOpenUser));
            logger.info("checkSubAndReg, unregister");
            return "wx/user_auth";
        }
        return "";
    }

}