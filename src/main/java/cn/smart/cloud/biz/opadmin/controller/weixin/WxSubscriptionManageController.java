package cn.smart.cloud.biz.opadmin.controller.weixin;

import cn.smart.cloud.biz.opadmin.constant.WxConstant;
import cn.smart.cloud.biz.opadmin.controller.MessageRender;
import cn.smart.cloud.biz.opadmin.entity.weixin.WxOpenUser;
import cn.smart.cloud.biz.opadmin.entity.weixin.WxSubscription;
import cn.smart.cloud.biz.opadmin.entity.weixin.WxSubscriptionMode;
import cn.smart.cloud.biz.opadmin.entity.weixin.WxSubscriptionType;
import cn.smart.cloud.biz.opadmin.entity.weixin.material.WxMaterialListResult;
import cn.smart.cloud.biz.opadmin.entity.weixin.material.WxMaterialRequest;
import cn.smart.cloud.biz.opadmin.entity.weixin.material.WxMaterialResult;
import cn.smart.cloud.biz.opadmin.entity.weixin.material.WxPermanentMaterialManager;
import cn.smart.cloud.biz.opadmin.entity.weixin.menu.WxIndividualMenu;
import cn.smart.cloud.biz.opadmin.entity.weixin.menu.WxMenuQueryResult;
import cn.smart.cloud.biz.opadmin.entity.weixin.usergroup.WxQueryGroupResult;
import cn.smart.cloud.biz.opadmin.gson.weixin.NoticeMessageSendResult;
import cn.smart.cloud.biz.opadmin.gson.weixin.UpdateMenuResult;
import cn.smart.cloud.biz.opadmin.gson.weixin.WxUserListResult;
import cn.smart.cloud.biz.opadmin.proxy.WxServiceProxy;
import cn.smart.cloud.biz.opadmin.util.UuidUtil;
import cn.smart.cloud.biz.opadmin.util.WxBaseUtil;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

@Controller
@RequestMapping(value = "/wx/manage/subscription")
public class WxSubscriptionManageController {

    private Logger logger = LoggerFactory.getLogger("WxSubscriptionController");
    private Gson gson = new Gson();
    private final String TOKEN_NAME = "TokenName";
    private final String SUCCESS = "success";
    private final String NONE = "NONE";
    private final String TIP = "common/common_msg";

    @Autowired
    private WxServiceProxy wxServiceProxy;

    @Value("${service.biz.wan.url.base}")
    private String SERVICE_BIZ_WAN_URL_BASE;

    public String createSubscription(HttpServletRequest request) {

        WxSubscription wxSubscription = new WxSubscription();
        wxSubscription.setToken(UUID.randomUUID().toString());
        wxSubscription.setAuthURL(SERVICE_BIZ_WAN_URL_BASE + "/wx/message/input/"
                + wxSubscription.getInstanceId() + ".xml");
        wxServiceProxy.createSubscription(wxSubscription);
        logger.info("createWxSubscription" + wxSubscription);
        return NONE;
    }

    public WxSubscription setupSubscription(Map<?, ?> params) {

        String subscriptionId = (String) params.get(WxConstant.WX_SUBSCRIPTION_ID);
        String appId = (String) params.get(WxConstant.WX_APP_ID);
        String secretId = (String) params.get(WxConstant.WX_SECRET_ID);

        WxSubscription wxSubscription = wxServiceProxy.getSubscriptionById(subscriptionId);

        wxSubscription.setAppId(appId);
        wxSubscription.setSecret(secretId);
        wxServiceProxy.saveSubscription(wxSubscription);

        //TODO wxSubscription.setAdvanced(rst);

        return wxSubscription;
    }

    public String setupSubscriptionInfo(HttpServletRequest request) {

        WxSubscription wxSubscription = this.setupSubscription(request.getParameterMap());

        logger.info("createWxSubscription" + wxSubscription);
        return NONE;
    }

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String subscriptionManageIndex() {
        return "wx/subscription/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String addSubscription(HttpServletRequest request, HttpSession session) {
        genToken(session);
        final String token = UuidUtil.randomWithoutHyphen();
        final String instanceId = UuidUtil.randomWithoutHyphen();
        final String authUrl = SERVICE_BIZ_WAN_URL_BASE + "/wx/message/input/" + instanceId + ".xml";

        WxSubscription subscription = new WxSubscription();
        subscription.setInstanceId(instanceId);
        subscription.setToken(token);
        subscription.setAuthURL(authUrl);
        subscription.setType(WxSubscriptionType.BIZ_VIDEO);
        wxServiceProxy.saveSubscription(subscription);

        logger.debug("id " + subscription.getId());
        request.setAttribute("subscriptionId", subscription.getId());
        request.setAttribute("token", token);
        request.setAttribute("authUrl", authUrl);
        return "wx/subscription/add";
    }

    @RequestMapping(value = "add_commit", method = RequestMethod.GET)
    public String addSubscriptionCommit(HttpServletRequest request, HttpSession session) {
        if (!checkAndRemoveToken(request, session)) {
            request.setAttribute("msg", "请勿重复提交");
            return "common/common_msg";
        }

        final String name = request.getParameter("name");
        final String subscriptionId = request.getParameter("subscriptionId");
        final String type = request.getParameter("type");
        final String wxId = request.getParameter("wxId");
        final String mode = request.getParameter("mode");
        final String welcomeTitle = request.getParameter("welcomeTitle");
        final String welcomePicUrl = request.getParameter("welcomePicUrl");
        final String welcomeDesc = request.getParameter("welcomeDesc");
        final String normalMessage = request.getParameter("normalMessage");
        final String appId = request.getParameter("appId");
        final String appSecret = request.getParameter("appSecret");
        final String officialWebsite = request.getParameter("officialWebsite");

        final String registerCheckStr = request.getParameter("registerCheck");
        final String piwikSiteId = request.getParameter("piwikSiteId");

        boolean registerCheck = false;
        if (registerCheckStr != null && registerCheckStr.equals("1")) {
            registerCheck = true;
        }

        WxSubscription subscription = wxServiceProxy.getSubscriptionById(subscriptionId);
        subscription.setName(name);
        subscription.setAppId(appId);
        subscription.setSecret(appSecret);
        subscription.setWxId(wxId);
        subscription.setType(WxSubscriptionType.getByOrdinal(Integer.valueOf(type)));
        subscription.setMode(WxSubscriptionMode.getByOrdinal(Integer.valueOf(mode)));

        subscription.setWelcomeTitle(welcomeTitle);
        subscription.setWelcomePicUrl(welcomePicUrl);
        subscription.setWelcomeDesc(welcomeDesc);
        subscription.setNormalMessage(normalMessage);
        subscription.setOfficialWebsite(officialWebsite);
        subscription.setPiwikId(piwikSiteId);
        subscription.setRegisterCheck(registerCheck);
        wxServiceProxy.saveSubscription(subscription);
        wxServiceProxy.createDefaultMenu(subscription);


        request.setAttribute("msg", "公众账号添加成功");
        return "common/common_msg";
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String listSubscriptions(HttpServletRequest request) {
        final List<WxSubscription> subscriptions = wxServiceProxy.getAllSubscription();
        request.setAttribute("subscriptions", subscriptions);
        return "wx/subscription/list";
    }

    @RequestMapping(value = "edit/{subscriptionId}", method = RequestMethod.GET)
    public String editSubscription(@PathVariable String subscriptionId, HttpServletRequest request, HttpSession session) {
        genToken(session);
        final WxSubscription subscription = wxServiceProxy.getSubscriptionById(subscriptionId);
        request.setAttribute("subscription", subscription);
        return "wx/subscription/edit";
    }

    @RequestMapping(value = "edit_commit", method = RequestMethod.GET)
    public String editSubscriptionCommit(HttpServletRequest request, HttpSession session) {
        if (!checkAndRemoveToken(request, session)) {
            request.setAttribute("msg", "请勿重复提交");
            return TIP;
        }

        final String name = request.getParameter("name");
        final String subscriptionId = request.getParameter("subscriptionId");
        final String type = request.getParameter("type");
        final String wxId = request.getParameter("wxId");
        final String mode = request.getParameter("mode");
        final String welcomeTitle = request.getParameter("welcomeTitle");
        final String welcomePicUrl = request.getParameter("welcomePicUrl");
        final String welcomeDesc = request.getParameter("welcomeDesc");
        final String normalMessage = request.getParameter("normalMessage");
        final String appId = request.getParameter("appId");
        final String appSecret = request.getParameter("appSecret");
        final String officialWebsite = request.getParameter("officialWebsite");
        final String piwikSiteId = request.getParameter("piwikSiteId");
        final String registerCheckStr = request.getParameter("registerCheck");
        boolean registerCheck = false;
        if (registerCheckStr != null && registerCheckStr.equals("1")) {
            registerCheck = true;
        }

        WxSubscription subscription = wxServiceProxy.getSubscriptionById(subscriptionId);
        subscription.setName(name);
        subscription.setAppId(appId);
        subscription.setWxId(wxId);
        subscription.setMode(WxSubscriptionMode.getByOrdinal(Integer.valueOf(mode)));
        subscription.setWelcomeTitle(welcomeTitle);
        subscription.setWelcomePicUrl(welcomePicUrl);
        subscription.setWelcomeDesc(welcomeDesc);
        subscription.setNormalMessage(normalMessage);
        subscription.setSecret(appSecret);
        subscription.setType(WxSubscriptionType.getByOrdinal(Integer.parseInt(type)));
        subscription.setOfficialWebsite(officialWebsite);
        subscription.setPiwikId(piwikSiteId);
        subscription.setRegisterCheck(registerCheck);

        if (subscription.getType() != WxSubscriptionType.getByOrdinal(Integer.parseInt(type))) {
            subscription.setType(WxSubscriptionType.getByOrdinal(Integer.parseInt(type)));
            wxServiceProxy.createDefaultMenu(subscription);
        }
        wxServiceProxy.saveSubscription(subscription);

        request.setAttribute("msg", "公众账号修改成功");
        return "common/common_msg";
    }

    @RequestMapping(value = "del_commit", method = RequestMethod.GET)
    public String delSubscriptionCommit(HttpServletRequest request, HttpSession session) {
        if (!checkAndRemoveToken(request, session)) {
            request.setAttribute("msg", "请勿重复提交");
            return TIP;
        }

        final String subscriptionId = request.getParameter("subscriptionId");
        wxServiceProxy.deleteSubscription(subscriptionId);

        request.setAttribute("msg", "公众账号删除成功");
        return "common/common_msg";
    }

    @RequestMapping(value = "rebuild_user", method = RequestMethod.GET)
    public void rebuildUser(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<>();
        String msg;
        int code = 0;
        String subscriptionId = request.getParameter("subscriptionId");
        WxSubscription subscription = wxServiceProxy.getSubscriptionById(subscriptionId);
        logger.info("rebuildUser, subscriptionId:" + subscriptionId + ", subscription : " + subscription);
        if (subscription != null && this.rebuildWxOpenUsers(subscription)) {
            msg = "关注者信息重建成功!";
        } else {
            code = 1;
            msg = "关注者信息重建失败!";
        }
        resultMap.put("msg", msg);
        resultMap.put("code", code);
        logger.info("rebuildUser, msg:" + msg);
        MessageRender.renderJson(response, resultMap);
    }

    @RequestMapping(value = "check_appId", method = RequestMethod.GET)
    public void checkAppId(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<>();
        int code = 0;
        String appId = request.getParameter("appId");
        String subscriptionId = request.getParameter("subscriptionId");
        logger.info("checkAppId, subscriptionId:" + subscriptionId + ", appId : " + appId);
        WxSubscription subscription = wxServiceProxy.getSubscriptionByAppId(appId);
        if (subscription != null && !subscription.getId().equals(subscriptionId)) {
            code = 1;
        }
        resultMap.put("code", code);
        MessageRender.renderJson(response, resultMap);
    }

    @RequestMapping(value = "tools", method = RequestMethod.GET)
    public String tools() {
        return "wx/subscription/tools";
    }

    @RequestMapping(value = "send_notice_message", method = RequestMethod.GET)
    public String sendNoticeMessage() {
        return "wx/subscription/tools_send_notice_message";
    }

    @RequestMapping(value = "load_subscription_list", method = RequestMethod.GET)
    public void loadSubscriptionList(HttpServletResponse response) {
        List<WxSubscription> WxSubscriptionList = wxServiceProxy.getAllSubscription();
        MessageRender.renderJson(response, WxSubscriptionList);
    }

    @RequestMapping(value = "send_notice_message_submit", method = RequestMethod.GET)
    public void sendNoticeMessageSubmit(HttpServletRequest request, HttpServletResponse response) {
        String subscriptionId = request.getParameter("subscriptionId");
        String text = request.getParameter("text");
        String isSendAll1Str = request.getParameter("isSendAll");
        boolean isSendAll = false;
        if (isSendAll1Str != null && isSendAll1Str.equals("1")) {
            isSendAll = true;
        }
        List<NoticeMessageSendResult> sendResultList = new ArrayList<>();

        if (isSendAll) {
            List<WxSubscription> subscriptions = wxServiceProxy.getAllSubscription();
            for (WxSubscription s : subscriptions) {
                sendResultList.add(wxServiceProxy.noticeAllUser(s, text));
            }
        }
        {
            sendResultList.add(wxServiceProxy.noticeAllUser(wxServiceProxy.getSubscriptionById(subscriptionId), text));
        }

        MessageRender.renderJson(response, sendResultList);
    }

    /**
     * 获取菜单信息并显示
     *
     * @return
     */
    @RequestMapping(value = "menu_info", method = RequestMethod.GET)
    public String menuInfo(HttpServletRequest request) {
        logger.info("menuInfo");
        String subscriptionId = request.getParameter("subscriptionId");
        WxMenuQueryResult result = wxServiceProxy.getAllMenu(subscriptionId);
        if (result == null || result.getMenu() == null) {
            request.setAttribute("msg", "获取最新应用信息出错！");
            return TIP;
        } else {
            List<String> menuJson = new ArrayList<>();
            menuJson.add(gson.toJson(result.getMenu()));
            List<WxIndividualMenu> indiMenuList = result.getConditionalmenu();
            if (indiMenuList != null) {
                for (WxIndividualMenu menu : indiMenuList) {
                    menuJson.add(gson.toJson(menu));
                }
            }
            request.setAttribute("menuList", menuJson);
        }
        request.setAttribute("msg", "获取最新应用信息成功！");
        return "wx/subscription/tools_manage_individual_menu";
    }

    @RequestMapping(value = "update_menu", method = RequestMethod.GET)
    public String updateMenu() {
        return "wx/subscription/tools_update_menu";
    }

    @RequestMapping(value = "update_menu_submit", method = RequestMethod.POST)
    public void updateMenuSubmit(HttpServletRequest request, HttpServletResponse response) {
        String subscriptionId = request.getParameter("subscriptionId");
        String menuJson = request.getParameter("menuJson");
        String isSendAll1Str = request.getParameter("isSendAll");
        boolean isSendAll = false;
        if (isSendAll1Str != null && isSendAll1Str.equals("1")) {
            isSendAll = true;
        }
        if (isSendAll) {
            List<UpdateMenuResult> updateMenuResult = wxServiceProxy.updateMenuForAll(menuJson);
            MessageRender.renderJson(response, updateMenuResult);
        } else {
            List<UpdateMenuResult> result = wxServiceProxy.updateMenu(subscriptionId, menuJson);
            MessageRender.renderJson(response, result);
        }
    }

    @RequestMapping(value = "manage_individual_menu", method = RequestMethod.GET)
    public String manageIndividualMenu() {
        return "wx/subscription/tools_manage_individual_menu";
    }

    @RequestMapping(value = "create_individual_menu_submit", method = RequestMethod.POST)
    public void createIndividualMenuSubmit(HttpServletRequest request,
                                           HttpServletResponse response) {
        String subscriptionId = request.getParameter("subscriptionId");
        logger.info("createIndividualMenuSubmit, subscriptionId:" + subscriptionId);
        String menuJson = request.getParameter("menuJson");
        boolean result = wxServiceProxy.createIndividualMenu(subscriptionId, menuJson);
        MessageRender.renderText(response, result ? SUCCESS : TIP);
    }


    @RequestMapping(value = "delete_individual_menu_submit", method = RequestMethod.POST)
    public void deleteIndividualMenuSubmit(HttpServletRequest request,
                                           HttpServletResponse response) {
        String subscriptionId = request.getParameter("subscriptionId");
        String menuId = request.getParameter("menuId");
        logger.info("deleteIndividualMenuSubmit, menuId:" + menuId);
        boolean result = wxServiceProxy.deleteIndividualMenu(subscriptionId, menuId);
        MessageRender.renderText(response, result ? SUCCESS : TIP);
    }

    @RequestMapping(value = "user_group_list")
    public String userGroupList(HttpServletRequest request) {
        String subscriptionId = request.getParameter("subscriptionId");
        logger.info("userGroupList, subscriptionId:" + subscriptionId);
        if (!StringUtils.isEmpty(subscriptionId)) {
            WxQueryGroupResult result = wxServiceProxy.getUserGroups(subscriptionId);
            logger.info("userGroupList, result:" + result);
            request.setAttribute("groupList", result.getWxUserGroups());
        }
        return "wx/subscription/user_group_list";
    }

    @RequestMapping(value = "group_create", method = RequestMethod.GET)
    public String userGroupCreate(HttpServletRequest request) {
        request.setAttribute("subscriptionId", request.getParameter("subscriptionId"));
        return "wx/subscription/user_group_create";
    }

    @RequestMapping(value = "group_create_submit", method = RequestMethod.POST)
    public void userGroupCreateSubmit(HttpServletRequest request,
                                      HttpServletResponse response) {
        String subscriptionId = request.getParameter("subscriptionId");
        String groupName = request.getParameter("groupName");
        logger.info("userGroupCreateSubmit, subscriptionId:" + subscriptionId + ", name:" + groupName);
        wxServiceProxy.createUserGroup(subscriptionId, groupName);
        MessageRender.renderText(response, SUCCESS);
    }

    @RequestMapping(value = "group_delete", method = RequestMethod.GET)
    public void userGroupDelete(HttpServletRequest request,
                                HttpServletResponse response) {
        String subscriptionId = request.getParameter("subscriptionId");
        String groupId = request.getParameter("groupId");
        logger.info("userGroupDelete, subscriptionId:" + subscriptionId + ", groupId:" + groupId);
        boolean suc = wxServiceProxy.deleteUserGroup(subscriptionId, groupId);
        MessageRender.renderText(response, suc ? SUCCESS : TIP);
    }

    @RequestMapping(value = "group_members_update", method = RequestMethod.GET)
    public String userGroupMoveUsers(HttpServletRequest request) {
        request.setAttribute("subscriptionId", request.getParameter("subscriptionId"));
        return "wx/subscription/user_group_move_user";
    }

    @RequestMapping(value = "group_members_update_submit", method = RequestMethod.POST)
    public void userGroupMoveUsersSubmit(HttpServletRequest request,
                                         HttpServletResponse response) {
        String subscriptionId = request.getParameter("subscriptionId");
        String groupId = request.getParameter("groupId");
        String openIds = request.getParameter("openIds");
        List<String> openIdList = new ArrayList<>();
        String[] params = openIds.split(",");
        openIdList.addAll(Arrays.asList(params));
        logger.info("userGroupMoveUsersSubmit, subscriptionId:" + subscriptionId + ", groupId:" + groupId
                + ", openIdList:" + openIdList);
        boolean suc = wxServiceProxy.moveUsersGroup(subscriptionId, groupId, openIdList);
        MessageRender.renderText(response, suc ? SUCCESS : TIP);
    }


    @RequestMapping(value = "manage_material", method = RequestMethod.GET)
    public String manageMaterial() {
        return "wx/subscription/tools_manage_material";
    }


    private File uploadFile;

    public File getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(File uploadFile) {
        this.uploadFile = uploadFile;
    }

    @RequestMapping(value = "upload_permanent_material", method = RequestMethod.GET)
    public void uploadPermanentMaterial(HttpServletRequest request,
                                        HttpServletResponse response) {
        String subscriptionId = request.getParameter("subscriptionId");
        String filePath = request.getParameter("materialId");
        logger.info("uploadPermanentMaterial, subscriptionId:" + subscriptionId + ", file:" + uploadFile);
        if (uploadFile == null) {
            uploadFile = new File(filePath);
        }
        WxMaterialResult result = WxPermanentMaterialManager.uploadMaterialMedia(wxServiceProxy
                .getAccessTokenString(subscriptionId), uploadFile);
        if (result == null || StringUtils.isEmpty(result.getMedia_id())) {
            result = WxPermanentMaterialManager.uploadMaterialMedia(wxServiceProxy
                    .getAccessTokenString(subscriptionId), uploadFile);
        }
        boolean suc = result != null && !StringUtils.isEmpty(result.getMedia_id());
        MessageRender.renderText(response, suc ? "/wx/subscription/tools_manage_material" : TIP);
    }

    /**
     * 获取永久素材信息并显示
     */
    @RequestMapping(value = "material_info", method = RequestMethod.GET)
    public String materialInfo(HttpServletRequest request) {
        logger.info("materialInfo");
        String subscriptionId = request.getParameter("subscriptionId");
        String type = request.getParameter("type");
        WxMaterialRequest wxMaterialRequest = new WxMaterialRequest();
        wxMaterialRequest.setType(type);
        wxMaterialRequest.setOffset(0);
        wxMaterialRequest.setCount(50);
        WxMaterialListResult result = WxPermanentMaterialManager.getMaterialList(subscriptionId, wxMaterialRequest);
        if (result == null || result.getTotal_count() <= 0) {
            request.setAttribute("msg", "无素材 或 获取素材列表出错！");
            return TIP;
        } else {
            request.setAttribute("materialList", result.getItem());
            request.setAttribute("msg", "获取素材列表成功！");
            return "wx/subscription/tools_manage_material";
        }
    }

    private void genToken(HttpSession session) {
        session.setAttribute(TOKEN_NAME, UuidUtil.randomWithoutHyphen());
    }

    private boolean checkAndRemoveToken(HttpServletRequest request, HttpSession session) {
        final String sessionToken = (String) session.getAttribute(TOKEN_NAME);
        session.removeAttribute(TOKEN_NAME);
        final String requestToken = request.getParameter(TOKEN_NAME);
        return !StringUtils.isEmpty(sessionToken) && sessionToken.equals(requestToken);
    }

    public boolean rebuildWxOpenUsers(WxSubscription subscription) {
        WxUserListResult userListResult = WxBaseUtil
                .getUserList(wxServiceProxy.getAccessTokenString(subscription.getId()));
        logger.info("rebuildWxOpenUsers, userListResult: " + userListResult);
        if (userListResult != null && userListResult.getData() == null) {
            userListResult = WxBaseUtil
                    .getUserList(wxServiceProxy.getAccessTokenString(subscription.getId(), false));
        }
        buildWxOpenUser(subscription, userListResult);
        // 10000 users could get once
        while (userListResult.getCount() < userListResult.getTotal() && userListResult.getNext_openid() != null) {
            userListResult = WxBaseUtil.getUserList(wxServiceProxy
                    .getAccessTokenString(subscription.getId()), userListResult.getNext_openid());
            if (userListResult != null && userListResult.getData() == null) {
                userListResult = WxBaseUtil.getUserList(wxServiceProxy
                        .getAccessTokenString(subscription.getId()), userListResult.getNext_openid());
            }
            buildWxOpenUser(subscription, userListResult);
        }
        logger.info("rebuildWxOpenUsers, finish.");
        return true;
    }

    private void buildWxOpenUser(WxSubscription subscription, WxUserListResult userListResult) {
        if (userListResult != null && userListResult.getData() != null) {
            for (String openId : userListResult.getData().getOpenid()) {
                WxOpenUser wxOpenUser = wxServiceProxy.getOpenUserByOpenId(openId);
                if (wxOpenUser == null) {
                    wxOpenUser = new WxOpenUser();
                    wxOpenUser.setOpenId(openId);
                    wxOpenUser.setSubscriptionId(subscription.getId());
                    wxOpenUser.setSubscribe(true);
                    wxServiceProxy.createOpenUser(wxOpenUser);
                }
            }
        }
    }
}