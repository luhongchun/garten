package cn.smart.cloud.biz.opadmin.proxy;

import cn.smart.cloud.biz.opadmin.constant.WxConstant;
import cn.smart.cloud.biz.opadmin.entity.weixin.*;
import cn.smart.cloud.biz.opadmin.entity.weixin.menu.WxMenuQueryResult;
import cn.smart.cloud.biz.opadmin.entity.weixin.usergroup.WxQueryGroupResult;
import cn.smart.cloud.biz.opadmin.gson.weixin.NoticeMessageSendResult;
import cn.smart.cloud.biz.opadmin.gson.weixin.UpdateMenuResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WxServiceProxy {

    private Logger logger = LoggerFactory.getLogger(WxServiceProxy.class);
    private static final String BASE_URL_SEND_MSG =
            ServiceConstant.MSVC_INSTANCE_NAME_WECHAT_SERVER + "wx/message/output/";
    private static final String BASE_URL_WX_TEMPLATE =
            ServiceConstant.MSVC_INSTANCE_NAME_WECHAT_SERVER + "wx/message/template/";
    private static final String BASE_URL_SEND_TEMPLATE =
            ServiceConstant.MSVC_INSTANCE_NAME_WECHAT_SERVER + "wx/message/template/send/";
    private static final String BASE_URL_WX_JS_CONFIG =
            ServiceConstant.MSVC_INSTANCE_NAME_WECHAT_SERVER + "wx/jsconfig/";
    private static final String BASE_URL_WX_SUBSCRIPTION =
            ServiceConstant.MSVC_INSTANCE_NAME_WECHAT_SERVER + "wx/subscription/";
    private static final String BASE_URL_WX_SCENE =
            ServiceConstant.MSVC_INSTANCE_NAME_WECHAT_SERVER + "wx/scene/";
    private static final String BASE_URL_WX_MENU =
            ServiceConstant.MSVC_INSTANCE_NAME_WECHAT_SERVER + "wx/menu/";
    private static final String BASE_URL_WX_USER_GROUP =
            ServiceConstant.MSVC_INSTANCE_NAME_WECHAT_SERVER + "wx/usergroup/";
    private static final String BASE_URL_WX_MESSAGE =
            ServiceConstant.MSVC_INSTANCE_NAME_WECHAT_SERVER + "wx/message/";
    private static final String BASE_URL_WX_OPEN_USER =
            ServiceConstant.MSVC_INSTANCE_NAME_WECHAT_SERVER + "wx/openuser/";
    private static final String BASE_URL_WX_AUTH =
            ServiceConstant.MSVC_INSTANCE_NAME_WECHAT_SERVER + "wx/auth/";
    private static Map<String, WxSubscription> scps = new HashMap<>();

    @Autowired
    private SfRestTemplate rstl;
    @Autowired
    private VideoServiceProxy videoServiceProxy;

    public String getAccessTokenString(String subscriptionId) {
        return getAccessTokenString(subscriptionId, true);
    }

    public String getAccessTokenString(String subscriptionId, boolean useCached) {
        return rstl.get(BASE_URL_WX_AUTH + "getAccessTokenString/" + subscriptionId + "/" + useCached,
                String.class);
    }

    //============================ WxSubscription Related ===================
    public WxSubscription createSubscription(WxSubscription wxSubscription) {
        return rstl.post(BASE_URL_WX_SUBSCRIPTION + "create", wxSubscription, WxSubscription.class);
    }

    public WxSubscription saveSubscription(WxSubscription wxSubscription) {
        return rstl.post(BASE_URL_WX_SUBSCRIPTION + "save", wxSubscription, WxSubscription.class);
    }

    public void deleteSubscription(String id) {
        rstl.get(BASE_URL_WX_SUBSCRIPTION + "delete/" + id, null);
    }

    public List<WxSubscription> getAllSubscription() {
        return Arrays.asList(rstl.get(BASE_URL_WX_SUBSCRIPTION + "getAll", WxSubscription[].class));
    }

    public WxSubscription getSubscriptionById(String id) {
        if (scps.containsKey(id)) {
            return scps.get(id);
        }
        WxSubscription entity = rstl.get(BASE_URL_WX_SUBSCRIPTION + "get/" + id, WxSubscription.class);
        scps.put(id, entity);
        return entity;
    }

    public WxSubscription getSubscriptionByAppId(String appId) {
        return rstl.get(BASE_URL_WX_SUBSCRIPTION + "getByAppId/" + appId, WxSubscription.class);
    }

    public WxSubscription getSubscriptionByInstanceId(String instanceId) {
        WxSubscription wxSubscription = rstl.get(BASE_URL_WX_SUBSCRIPTION + "getByInstanceId/"
                + instanceId, WxSubscription.class);
        logger.info("getSubscriptionByInstanceId, sub:" + wxSubscription);
        return wxSubscription;
    }

    public List<WxSubscription> getSubscriptionByBizType(WxSubscriptionType type) {
        WxSubscription[] result = rstl.get(BASE_URL_WX_SUBSCRIPTION + "getAllByType/" + type.ordinal(),
                WxSubscription[].class);
        return Arrays.asList(result);
    }

    //============================ WxSubscription Related ===================
    public void createDefaultMenu(WxSubscription subscription) {
        rstl.post(BASE_URL_WX_MENU + "createDefaultMenu", subscription, null);
    }

    public WxMenuQueryResult getAllMenu(String subscriptionId) {
        return rstl.get(BASE_URL_WX_MENU + "queryAll/" + subscriptionId, WxMenuQueryResult.class);
    }

    public List<UpdateMenuResult> updateMenuForAll(String json) {
        return Arrays.asList(rstl.post(BASE_URL_WX_MENU + "updateMenuForAll", json, UpdateMenuResult[].class));
    }

    public List<UpdateMenuResult> updateMenu(String subscriptionId, String json) {
        return Arrays.asList(rstl.post(BASE_URL_WX_MENU + "updateMenu/" + subscriptionId,
                json, UpdateMenuResult[].class));
    }

    public boolean createIndividualMenu(String subscriptionId, String json) {
        return rstl.post(BASE_URL_WX_MENU + "createIndividualMenu/" + subscriptionId, json, Boolean.class);
    }

    public boolean deleteIndividualMenu(String subscriptionId, String menuId) {
        return rstl.get(BASE_URL_WX_MENU + "deleteIndividualMenu/" + subscriptionId + "/" + menuId, Boolean.class);
    }

    //============================ UserGroup Related ===================
    public WxQueryGroupResult getUserGroups(String subscriptionId) {
        return rstl.get(BASE_URL_WX_USER_GROUP + "getUserGroups/" + subscriptionId, WxQueryGroupResult.class);
    }

    public boolean createUserGroup(String subscriptionId, String groupName) {
        return rstl.get(BASE_URL_WX_USER_GROUP + "createUserGroup/" + subscriptionId + "/" + groupName,
                Boolean.class);
    }

    public boolean deleteUserGroup(String subscriptionId, String groupId) {
        return rstl.get(BASE_URL_WX_USER_GROUP + "deleteUserGroup/" + subscriptionId + "/" + groupId,
                Boolean.class);
    }

    public boolean moveUsersGroup(String subscriptionId, String groupId, List<String> openIdList) {
        return rstl.post(BASE_URL_WX_USER_GROUP + "moveUsersGroup/" + subscriptionId + "/" + groupId,
                openIdList, Boolean.class);
    }

    //============================ Message Related ===================
    public NoticeMessageSendResult noticeAllUser(WxSubscription s, String text) {
        return rstl.post(BASE_URL_WX_MESSAGE + "noticeAllUser/" + text, s, NoticeMessageSendResult.class);
    }

    //============================ OpenUser Related ===================
    public WxOpenUser getOpenUserByOpenId(String openId) {
        return rstl.get(BASE_URL_WX_OPEN_USER + "getByOpenId/" + openId, WxOpenUser.class);
    }

    public void createOpenUser(WxOpenUser wxOpenUser) {
        rstl.post(BASE_URL_WX_OPEN_USER + "create", wxOpenUser, null);
    }

    //============================ Template Related ===================
    public WxTemplate getTemplateByInstanceIdAndType(String instanceId, String type) {
        WxTemplate result = rstl.get(BASE_URL_WX_TEMPLATE
                + "getByInstanceIdAndType/" + instanceId + "/" + type, WxTemplate.class);
        return result;
    }

    public WxTemplate getTemplateById(String id) {
        return rstl.get(BASE_URL_WX_TEMPLATE + "get/" + id, WxTemplate.class);
    }

    public boolean sendCustomMessage(String instanceId, WxCustomMessage message) {
        logger.info("sendCustomMessage, message:" + message);
        return rstl.post(
                BASE_URL_SEND_MSG + instanceId,
                message, Boolean.class);
    }

    public boolean sendCustomTextMessage(String instanceId, String openId, String content) {
        logger.info("sendCustomTextMessage, content:" + content);
        WxCustomMessage message = new WxCustomMessage();
        message.setMsgtype(WxConstant.MSG_TYPE_TEXT);
        WxCustomMessage.Text text = new WxCustomMessage.Text();
        text.setContent(content);
        message.setText(text);
        message.setTouser(openId);
        return this.sendCustomMessage(instanceId, message);
    }

    public boolean sendTemplateMessage(String instanceId, WxTemplateMessage message) {
        logger.info("sendTemplateMessage, message:" + message);
        return rstl.post(
                BASE_URL_SEND_TEMPLATE + instanceId,
                message, Boolean.class);
    }

    private boolean sendTemplateMessage(WxTemplate template, String instanceId, WxTemplateMessage templateMessage,
                                        List<String> openIdList) {
        boolean result = true;
        logger.info("sendAttendTemplateMsg, template:" + template);
        if (template != null && openIdList != null && !openIdList.isEmpty()) {
            for (String openId : openIdList) {
                templateMessage.setToUserOpenId(openId);
                logger.info("sendAttendTemplateMsg, send templateMessage:" + templateMessage);
                if (!sendTemplateMessage(instanceId, templateMessage))
                    result = false;
            }
        }
        return result;
    }

    public WxJsConfig getWxJsConfig(String appId, String pageUrl) {
        logger.info("getWxJsConfig, appId:" + appId + ", pageUrl:" + pageUrl);
        WxJsConfig result = rstl.post(
                BASE_URL_WX_JS_CONFIG + "getByAppIdAndPageUrl/" + appId,
                pageUrl, WxJsConfig.class);
        return result;
    }

    public WxScene getWxScene(String sceneId) {
        logger.info("getWxScene, sceneId:" + sceneId);
        WxScene result = rstl.get(
                BASE_URL_WX_SCENE + "getBySceneId/" + sceneId, WxScene.class);
        return result;
    }

    public String createWxQrcodeUrl(String instanceId, long sceneId, int expireSeconds) {
        return "";
    }

    public String getGartenIdByWxUser(WxOpenUser wxOpenUser) {
        String gartenId = "";
        if (wxOpenUser == null || StringUtils.isBlank(wxOpenUser.getSceneId()))
            return gartenId;
        WxScene wxScene = getWxScene(wxOpenUser.getSceneId());
        return wxScene == null ? gartenId : wxScene.getBizSceneId();
    }

    public boolean sendDeviceFaultMessageByTemplate(String boxOfflineUrl) {
        logger.info("sendDeviceFaultMessageByTemplate");
        boolean result = false;
        List<String> openIdList = Arrays.asList(videoServiceProxy.getDeviceExceptionAlarmOpenIds());
        if (openIdList.isEmpty()) return false;

        WxTemplateMessage templateMessage = new WxTemplateMessage();
        templateMessage.setUrl(boxOfflineUrl);

        Map<String, WxTemplateMessageData> data = new HashMap<>();
        WxTemplateMessageData first = new WxTemplateMessageData();
        WxTemplateMessageData keyword1 = new WxTemplateMessageData();
        WxTemplateMessageData keyword2 = new WxTemplateMessageData();
        WxTemplateMessageData keyword3 = new WxTemplateMessageData();
        WxTemplateMessageData remark = new WxTemplateMessageData();
        first.setValue("盒子可能掉线啦");
        data.put("first", first);
        String content = "盒子可能掉线啦";
        if (content != null && content.length() > 16) {
            content = content.substring(0, 16) + "...";
        }
        keyword1.setValue(content);
        data.put("keyword1", keyword1);
        keyword2.setValue("盒子可能掉线啦");
        data.put("keyword2", keyword2);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        keyword3.setValue(format.format(new Date()).toString());
        data.put("keyword3", keyword3);
        remark.setValue("请点击详情进行处理");
        data.put("remark", remark);

        templateMessage.setData(data);
        String templateId = videoServiceProxy.getDeviceExceptionAlarmTemplate();
        WxTemplate template = getTemplateById(templateId);
        templateMessage.setTemplateId(template.getTemplateId());
        WxSubscription subscription = getSubscriptionById(template.getSubscriptionId());
        if (subscription != null) {
            sendTemplateMessage(template, subscription.getInstanceId(), templateMessage, openIdList);
            result = true;
        }
        return result;
    }
}