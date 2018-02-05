package cn.smart.cloud.biz.opadmin.entity.weixin;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

public class WxSubscription {

    private String id;

    private String appId;
    private String secret;
    private String token;
    private String authURL;

    private String name;
    private String wxId;
    private String instanceId;

    private boolean advanced;
    private boolean bindSuccess;

    private WxSubscriptionMode mode;
    private WxSubscriptionType type;

    private String owner;

    private Date pushTime;
    private String pushCameraIds;

    private boolean registerCheck;
    private String normalMessage;
    private String welcomeTitle;
    private String welcomeDesc;
    private String welcomePicUrl;
    private String guideUrl;
    private String guideText;
    private String officialWebsite;
    private String piwikId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAuthURL() {
        return authURL;
    }

    public void setAuthURL(String authURL) {
        this.authURL = authURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
    }

    public WxSubscriptionMode getMode() {
        return mode;
    }

    public void setMode(WxSubscriptionMode mode) {
        this.mode = mode;
    }

    public boolean isAdvanced() {
        return advanced;
    }

    public void setAdvanced(boolean advanced) {
        this.advanced = advanced;
    }

    public WxSubscriptionType getType() {
        return type;
    }

    public void setType(WxSubscriptionType type) {
        this.type = type;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean isBindSuccess() {
        return bindSuccess;
    }

    public void setBindSuccess(boolean bindSuccess) {
        this.bindSuccess = bindSuccess;
    }

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    public String getPushCameraIds() {
        return pushCameraIds;
    }

    public void setPushCameraIds(String pushCameraIds) {
        this.pushCameraIds = pushCameraIds;
    }

    public boolean isRegisterCheck() {
        return registerCheck;
    }

    public void setRegisterCheck(boolean registerCheck) {
        this.registerCheck = registerCheck;
    }

    public String getNormalMessage() {
        return normalMessage;
    }

    public void setNormalMessage(String normalMessage) {
        this.normalMessage = normalMessage;
    }

    public String getWelcomeTitle() {
        return welcomeTitle;
    }

    public void setWelcomeTitle(String welcomeTitle) {
        this.welcomeTitle = welcomeTitle;
    }

    public String getWelcomeDesc() {
        return welcomeDesc;
    }

    public void setWelcomeDesc(String welcomeDesc) {
        this.welcomeDesc = welcomeDesc;
    }

    public String getWelcomePicUrl() {
        return welcomePicUrl;
    }

    public void setWelcomePicUrl(String welcomePicUrl) {
        this.welcomePicUrl = welcomePicUrl;
    }

    public String getGuideUrl() {
        return guideUrl;
    }

    public void setGuideUrl(String guideUrl) {
        this.guideUrl = guideUrl;
    }

    public String getGuideText() {
        return guideText;
    }

    public void setGuideText(String guideText) {
        this.guideText = guideText;
    }

    public String getOfficialWebsite() {
        return officialWebsite;
    }

    public void setOfficialWebsite(String officialWebsite) {
        this.officialWebsite = officialWebsite;
    }

    public String getPiwikId() {
        return piwikId;
    }

    public void setPiwikId(String piwikId) {
        this.piwikId = piwikId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
