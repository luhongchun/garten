package cn.smart.cloud.biz.opadmin.controller.garten.video;

import cn.smart.cloud.biz.opadmin.constant.Constant;
import cn.smart.cloud.biz.opadmin.constant.GlobalConfig;
import cn.smart.cloud.biz.opadmin.constant.WxConstant;
import cn.smart.cloud.biz.opadmin.controller.MessageRender;
import cn.smart.cloud.biz.opadmin.entity.BaseRole;
import cn.smart.cloud.biz.opadmin.entity.account.Account;
import cn.smart.cloud.biz.opadmin.entity.garten.Garten;
import cn.smart.cloud.biz.opadmin.entity.garten.Goods;
import cn.smart.cloud.biz.opadmin.entity.garten.data.GartenInfo;
import cn.smart.cloud.biz.opadmin.entity.garten.user.GartenUser;
import cn.smart.cloud.biz.opadmin.entity.garten.user.GartenUser2Role;
import cn.smart.cloud.biz.opadmin.entity.weixin.WxJsConfig;
import cn.smart.cloud.biz.opadmin.entity.weixin.WxOpenUser;
import cn.smart.cloud.biz.opadmin.entity.weixin.WxSubscription;
import cn.smart.cloud.biz.opadmin.gson.BizVideoType;
import cn.smart.cloud.biz.opadmin.gson.Pager;
import cn.smart.cloud.biz.opadmin.gson.SelectedVideoOneday;
import cn.smart.cloud.biz.opadmin.gson.device.Camera;
import cn.smart.cloud.biz.opadmin.gson.video.M3u8;
import cn.smart.cloud.biz.opadmin.gson.video.VideoConfig;
import cn.smart.cloud.biz.opadmin.gson.video.VideoData;
import cn.smart.cloud.biz.opadmin.proxy.*;
import cn.smart.cloud.biz.opadmin.util.UriUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/biz/carekids")
public class CareKidsController {

    private static final Logger logger = LoggerFactory.getLogger(CareKidsController.class);

    @Autowired
    private GartenServiceProxy gartenServiceProxy;
    @Autowired
    private VideoServiceProxy videoServiceProxy;
    @Autowired
    private DeviceServiceProxy deviceServiceProxy;
    @Autowired
    private BaseUserServiceProxy baseUserServiceProxy;
    @Autowired
    private BaseCoreServiceProxy baseServiceProxy;
    @Autowired
    private WxServiceProxy wxServiceProxy;
    @Autowired
    private WxOpenUserServiceProxy wxOpenUserService;
    @Autowired
    WxAuthService wxAuthService;
    @Autowired
    private Environment environment;

    @Value("${server.port}")
    String svcPort;
    @Value("${service.biz.wan.url.base}")
    String serverUrl;
    @Value("${text.biz.garten.name}")
    String gartenName;
    @Value("${text.biz.garten.video.play.live.title}")
    String titlePlayLive;
    @Value("${text.biz.garten.video.play.live.des}")
    String descPlayLive;
    @Value("${text.biz.garten.video.play.live.cover}")
    String coverPlayLiveForShare;
    @Value("${text.biz.garten.video.play.closeup.title}")
    String titlePlayCloseup;
    @Value("${text.biz.garten.video.play.closeup.des}")
    String descPlayCloseup;
    @Value("${text.biz.garten.video.arreas}")
    String accountArreas;
    @Value("${text.biz.garten.video.denied}")
    String notInService;
    @Value("${text.biz.wx.subscribe}")
    String needReSubscribe;

    private static final String PREFIX_AUTH = "auth/";
    private static final String PREFIX_REDIRECT = "rdt/";
    private static final String ACTION_KID_VIDEO = "action_kid_video";
    private static final String ACTION_KID_VIDEO_ONEDAY = "action_kid_video_oneday";
    private static final String ACTION_USER_CAMERA_LIST = "action_user_camera_list";
    private static final String ACTION_SHOW_VIDEO = "action_show_video";
    private static final String ACTION_SHARE_VIDEO = "action_share_video";
    public static final String ACTION_VIDEO_PLAY = "action_video_play";
    public static final String ACTION_VIDEO_PLAY_GET = "action_video_play_get";
    public static final String ACTION_VIDEO_PLAY_VOD = "action_video_play_vod";
    private static final String ACTION_CLOSEUP_VIDEO_PLAY = "action_closeup_video_play";
    private static final String ACTION_VIDEO_HISTORY_CAMERA_LIST = "action_video_history_camera_list";
    private static final String PRE_REDIRECT_LOCAL = "redirect:/biz/carekids/";

    private static final String VIDEO_URL_HEAD = "http";

    @RequestMapping(value = PREFIX_AUTH + ACTION_KID_VIDEO + "/{instanceId}")
    public String kidVideoAuth(@PathVariable String instanceId) {
        logger.info("kidVideoAuth, instanceId:" + instanceId);
        final String redirectUrl = String.format(GlobalConfig.FORMAT_REDIRECT_URL, instanceId, UriUtil.encode(serverUrl + ":"
                + environment.getProperty("server.port") + "/biz/carekids/" + PREFIX_REDIRECT + ACTION_KID_VIDEO));

        String url = wxAuthService.getOAuthUri(instanceId);
        String codeUrl = url.replace(WxConstant.WX_MOCK_REDIRECT_URI, UriUtil.encode(redirectUrl));
        logger.info("kidVideoAuth, codeUrl:" + codeUrl);

        return "redirect:" + codeUrl;
    }
    
    /*@RequestMapping(value = PREFIX_AUTH + ACTION_USER_CAMERA_LIST + "/{instanceId}")
    public String userCameraListAuth(@PathVariable String instanceId) {
        logger.info("userCameraListAuth, instanceId:" + instanceId);
        final String redirectUrl = String.format(FORMAT_REDIRECT_URL, instanceId, UriUtil.encode(serverUrl + ":"
                + environment.getProperty("server.port")+"/biz/carekids/"+ACTION_USER_CAMERA_LIST));
        
        String url = wxAuthService.getOAuthUri(instanceId);
        String codeUrl = url.replace(WxConstant.WX_MOCK_REDIRECT_URI, UriUtil.encode(redirectUrl));//不编码时传递到get_base_auth_result的url为null
        logger.info("userCameraListAuth, codeUrl:"+codeUrl);
        
        return "redirect:"+codeUrl;
    }*/

    @RequestMapping(value = PREFIX_AUTH + ACTION_VIDEO_HISTORY_CAMERA_LIST + "/{instanceId}")
    public String videoHistoryCameraListAuth(@PathVariable String instanceId) {
        logger.info("videoHistoryCameraListAuth, instanceId:" + instanceId);
        final String redirectUrl = String.format(GlobalConfig.FORMAT_REDIRECT_URL, instanceId, UriUtil.encode(serverUrl + ":"
                + environment.getProperty("server.port") + "/biz/carekids/" + PREFIX_REDIRECT + ACTION_VIDEO_HISTORY_CAMERA_LIST));

        String url = wxAuthService.getOAuthUri(instanceId);
        String codeUrl = url.replace(WxConstant.WX_MOCK_REDIRECT_URI, UriUtil.encode(redirectUrl));
        logger.info("videoHistoryCameraListAuth, codeUrl:" + codeUrl);

        return "redirect:" + codeUrl;
    }

    @RequestMapping(value = PREFIX_REDIRECT + ACTION_KID_VIDEO + "/{openId}", method = RequestMethod.GET)
    public String redirectKidVideo(@PathVariable String openId, RedirectAttributes ra) {
        logger.info("redirectKidVideo, openId:" + openId);
        ra.addFlashAttribute(WxConstant.WX_OPEN_ID, openId);
        return PRE_REDIRECT_LOCAL + ACTION_KID_VIDEO;
    }

    @RequestMapping(value = PREFIX_REDIRECT + ACTION_VIDEO_HISTORY_CAMERA_LIST + "/{openId}", method = RequestMethod.GET)
    public String redirectVideoHistoryCameraList(@PathVariable String openId, RedirectAttributes ra) {
        logger.info("redirectVideoHistoryCameraList, openId:" + openId);
        ra.addFlashAttribute(WxConstant.WX_OPEN_ID, openId);
        return PRE_REDIRECT_LOCAL + ACTION_VIDEO_HISTORY_CAMERA_LIST;
    }

    @RequestMapping(value = ACTION_KID_VIDEO)
    public String kidVideoEdited(HttpServletRequest request,
                                 @ModelAttribute(WxConstant.WX_OPEN_ID) String openId) {
        logger.info("kidVideoEdited, after auth, openId: " + openId);
        requestValid(request);

        WxOpenUser wxOpenUser = wxOpenUserService.getWxOpenUserByOpenId(openId);
        String ret = checkSubAndReg(request, wxOpenUser);
        if (!StringUtils.isEmpty(ret))
            return ret;
        WxSubscription subscription = wxServiceProxy.getSubscriptionById(wxOpenUser.getSubscriptionId());
        String baseUserId = wxOpenUser.getBaseUserId();
        List<GartenUser> userList = gartenServiceProxy.getAllByBaseUserId(baseUserId);
        if (userList.isEmpty()) {
            String warn = notInService;
            logger.warn("kidVideoEdited, no user. " + warn);
            return "prompt/prompt_not_in_service";
        }
        String gartenId = userList.get(0).getGartenId();
        boolean online = gartenServiceProxy.isPayOnline(gartenId);

        boolean bizLive = gartenServiceProxy.hasBizPermission(baseUserId, BizVideoType.LIVE, online);
        boolean bizCloseup = gartenServiceProxy.hasBizPermission(baseUserId, BizVideoType.CLOSEUP, online);
        if (!bizLive && !bizCloseup) {
            String warn = notInService;
            logger.warn("kidVideoEdited, no biz. " + warn);
            return "prompt/prompt_not_in_service";
        } else if (bizCloseup) {//默认显示特写列表
            List<VideoData> videoList = new ArrayList<>();
            String sceneId = wxOpenUser.getSceneId();
            GartenUser parent = null;
            if (!StringUtils.isEmpty(sceneId)) {
                gartenId = wxServiceProxy.getWxScene(sceneId).getBizSceneId();
                logger.info("kidVideoEdited, scene gartenId: " + gartenId);
                parent = gartenServiceProxy.getByBaseUserIdAndGartenId(baseUserId, gartenId);
                if (parent == null) {
                    String warn = notInService;
                    logger.warn("kidVideoEdited, no parent user. " + warn);
                    return "prompt/prompt_not_in_service";
                }
            } else {
                parent = userList.get(0);
            }
            List<GartenUser> children = gartenServiceProxy.getChildrenByParent(parent.getId());
            if (children != null && !children.isEmpty()) {
                for (GartenUser child : children) {
                    if (child != null) {
                        videoList.addAll(gartenServiceProxy.getChildVideoEdited(child.getId()));
                    }
                }
            }
            if (!videoList.isEmpty()) {
                request.setAttribute("live", bizLive);
                request.setAttribute("find", bizCloseup);
                request.setAttribute("videoList", videoList);
                request.setAttribute(WxConstant.WX_AUTH_INSTANCE_ID, subscription.getInstanceId());
                request.setAttribute(WxConstant.WX_OPEN_ID, WxConstant.WX_OPEN_ID);
                return "video/video_edited_list";
            } else if (!bizLive) {
                return "prompt/prompt_no_selected_video";
            }
        }

        String bizRet = checkAccountBiz(request, baseUserId, BizVideoType.LIVE,
                gartenServiceProxy.isPayOnline(gartenId));
        if (!StringUtils.isEmpty(bizRet))
            return bizRet;

        List<Camera> cameras = gartenServiceProxy.getCamerasRemoveDuplicate(baseUserId);
        if (cameras == null || cameras.isEmpty())
            return "prompt/prompt_no_camera";
        logger.info("kidVideoEdited, camera cnt: " + cameras.size());

        for (Camera c : cameras) {
            if (c == null)
                continue;
            if (c.getServiceType() != Constant.CameraServiceType.SERVICE
                    || c.isClose())
                c.setId("");
        }
        request.setAttribute("cameras", cameras);
        request.setAttribute(WxConstant.WX_AUTH_INSTANCE_ID, subscription.getInstanceId());
        request.setAttribute(WxConstant.WX_OPEN_ID, openId);
        request.setAttribute("environment", environment);
        request.setAttribute("piwikSiteId", subscription.getPiwikId());
        return "video/user_camera_list";
    }

    @RequestMapping(value = ACTION_KID_VIDEO_ONEDAY, method = RequestMethod.POST)
    public String kidVideoOneday(HttpServletRequest request) {
        String openId = request.getParameter(WxConstant.WX_OPEN_ID);
        String time = request.getParameter("time");
        logger.info("kidVideoOneday, openId: " + openId + ", time: " + time);
        requestValid(request);
        WxOpenUser wxOpenUser = wxOpenUserService.getWxOpenUserByOpenId(openId);
        String ret = checkSubAndReg(request, wxOpenUser);
        if (!StringUtils.isEmpty(ret))
            return ret;
        String baseUserId = wxOpenUser.getBaseUserId();
        WxSubscription subscription = wxServiceProxy.getSubscriptionById(wxOpenUser.getSubscriptionId());
        String instanceId = subscription.getInstanceId();
        List<GartenUser> userList = gartenServiceProxy.getAllByBaseUserId(baseUserId);
        if (userList.isEmpty()) {
            String warn = notInService;
            logger.warn("kidVideoOneday, " + warn);
            return "prompt/prompt_not_in_service";
        }
        String gartenId = userList.get(0).getGartenId();
        String bizRet = checkAccountBiz(request, baseUserId, BizVideoType.CLOSEUP,
                gartenServiceProxy.isPayOnline(gartenId));
        if (!StringUtils.isEmpty(bizRet))
            return bizRet;

        List<Camera> cameras = gartenServiceProxy.getCamerasRemoveDuplicate(wxOpenUser.getBaseUserId());
        if (cameras == null || cameras.isEmpty())
            return "prompt/prompt_no_camera";
        logger.info("kidVideoOneday, camera cnt: " + cameras.size());


        SelectedVideoOneday videoOneday = new SelectedVideoOneday();
        List<String> cameraIds = new ArrayList<>();
        for (Camera c : cameras) {
            if (c == null)
                continue;
            if (c.getServiceType() == Constant.CameraServiceType.SERVICE && !c.isClose()) {
                cameraIds.add(c.getId());
            }
        }
        videoOneday = videoServiceProxy.getWonderfulVideoOneDay(cameraIds, Long.parseLong(time));
        if (videoOneday.getAll().isEmpty()) {
            return "prompt/prompt_no_selected_video";
        }
        request.setAttribute("videoOneday", videoOneday);
        request.setAttribute(WxConstant.WX_AUTH_INSTANCE_ID, instanceId);
        request.setAttribute(WxConstant.WX_OPEN_ID, openId);
        return "video/video_wonderful_oneday";
    }

    @RequestMapping(value = ACTION_USER_CAMERA_LIST, method = RequestMethod.POST)
    public String userCameraList(HttpServletRequest request) {
        final String openId = request.getParameter(WxConstant.WX_OPEN_ID);
        logger.info("userCameraList, after auth, openId: " + openId);
        requestValid(request);
        WxOpenUser wxOpenUser = wxOpenUserService.getWxOpenUserByOpenId(openId);
        String ret = checkSubAndReg(request, wxOpenUser);
        if (!StringUtils.isEmpty(ret))
            return ret;
        WxSubscription subscription = wxServiceProxy.getSubscriptionById(wxOpenUser.getSubscriptionId());
        String instanceId = subscription.getInstanceId();
        String baseUserId = wxOpenUser.getBaseUserId();
        List<GartenUser> userList = gartenServiceProxy.getAllByBaseUserId(baseUserId);
        if (userList.isEmpty()) {
            String warn = notInService;
            logger.warn("userCameraList, " + warn);
            return "prompt/prompt_not_in_service";
        }
        String gartenId = userList.get(0).getGartenId();
        boolean online = gartenServiceProxy.isPayOnline(gartenId);
        String bizRet = checkAccountBiz(request, baseUserId, BizVideoType.LIVE, online);
        if (!StringUtils.isEmpty(bizRet))
            return bizRet;

        List<Camera> cameras = gartenServiceProxy.getCamerasRemoveDuplicate(baseUserId);
        if (cameras == null || cameras.isEmpty())
            return "prompt/prompt_no_camera";
        logger.info("userCameraList, camera cnt: " + cameras.size());
        for (Camera c : cameras) {
            if (c == null)
                continue;
            if (c.getServiceType() != Constant.CameraServiceType.SERVICE
                    || c.isClose())
                c.setId("");
        }
        boolean bizCloseup = gartenServiceProxy.hasBizPermission(baseUserId, BizVideoType.CLOSEUP, online);
        request.setAttribute("live", true);
        request.setAttribute("find", bizCloseup);
        request.setAttribute("cameras", cameras);
        request.setAttribute(WxConstant.WX_AUTH_INSTANCE_ID, instanceId);
        request.setAttribute(WxConstant.WX_OPEN_ID, openId);
        request.setAttribute("environment", environment);
        request.setAttribute("piwikSiteId", subscription.getPiwikId());
        return "video/user_camera_list";
    }

    @RequestMapping(value = ACTION_SHOW_VIDEO)
    public String showVideo(HttpServletRequest request) {
        final String instanceId = request.getParameter(WxConstant.WX_AUTH_INSTANCE_ID);
        final String openId = request.getParameter(WxConstant.WX_OPEN_ID);
        final String cameraId = request.getParameter("cameraId");
        String videoUrl = request.getParameter("videoUrl");
        if (!StringUtils.isEmpty(videoUrl) && !videoUrl.startsWith(VIDEO_URL_HEAD))
            videoUrl = "";
        String shareVideoUrl = request.getParameter("shareVideoUrl");
        logger.info("showVideo, instanceId:" + instanceId + ", openId:" + openId
                + ", cameraId:" + cameraId + ", videoUrl:" + videoUrl + ", shareVideoUrl:" + shareVideoUrl);
        requestValid(request);
        if (StringUtils.isEmpty(openId) || StringUtils.isEmpty(cameraId)) {
            String warn = notInService;
            logger.warn("showVideo, " + warn);
            return "prompt/prompt_not_in_service";
        }
        videoUrl = UriUtil.decode(videoUrl);
        shareVideoUrl = UriUtil.decode(shareVideoUrl);
        fillVideoPlayAttr(request, instanceId, openId, null, cameraId, videoUrl, shareVideoUrl, false, BizVideoType.LIVE);
        logger.info("showVideo, after fill request.");
        return "video/record_video_play";
    }

    @RequestMapping(value = ACTION_SHARE_VIDEO + "/{instanceId}/{cameraId}/{type}")
    public String shareVideo(@PathVariable String instanceId,
                             @PathVariable String cameraId,
                             @PathVariable String type,
                             HttpServletRequest request) {
        final String videoUrl = request.getParameter("videoUrl");
        logger.info("shareVideo, instanceId:" + instanceId + ", cameraId:" + cameraId + ", videoUrl:" + videoUrl);
        return "prompt/prompt_not_in_service";
        /*requestValid(request);
        fillLiveVideoPlayAllAttr(request, instanceId, null, cameraId, videoUrl, null, true, BizVideoType.valueOf(type));
        logger.info("shareVideo, after fill request.");
        return "video/record_video_play";*/
    }

    @RequestMapping(value = ACTION_VIDEO_PLAY, method = RequestMethod.POST)
    public String recordVideoPlay(HttpServletRequest request) {
        final String instanceId = request.getParameter(WxConstant.WX_AUTH_INSTANCE_ID);
        final String openId = request.getParameter(WxConstant.WX_OPEN_ID);
        final String cameraId = request.getParameter("cameraId");
        final String videoUrl = request.getParameter("videoUrl");
        final String shareVideoUrl = request.getParameter("shareVideoUrl");
        logger.info("recordVideoPlay, instanceId:" + instanceId + ", openId:" + openId
                + ", cameraId:" + cameraId + ", videoUrl:" + videoUrl + ", shareVideoUrl:" + shareVideoUrl);
        requestValid(request);
        fillVideoPlayAttr(request, instanceId, openId, null, cameraId, videoUrl, shareVideoUrl, false, BizVideoType.LIVE);
        logger.info("recordVideoPlay, after fill request.");
        return "video/record_video_play";
    }

    @RequestMapping(value = ACTION_VIDEO_PLAY_GET + "/{instanceId}/{openId}/{cameraId}", method = RequestMethod.GET)
    public String recordVideoPlayGet(HttpServletRequest request,
                                     @PathVariable String instanceId,
                                     @PathVariable String openId,
                                     @PathVariable String cameraId) {
        final String videoUrl = request.getParameter("videoUrl");
        final String shareVideoUrl = request.getParameter("shareVideoUrl");
        logger.info("recordVideoPlayGet, instanceId:" + instanceId + ", openId:" + openId
                + ", cameraId:" + cameraId + ", videoUrl:" + videoUrl + ", shareVideoUrl:" + shareVideoUrl);
        requestValid(request);
        fillVideoPlayAttr(request, instanceId, openId, null, cameraId, videoUrl, shareVideoUrl, false, BizVideoType.LIVE);
        logger.info("recordVideoPlayGet, after fill request.");
        return "video/record_video_play";
    }

    @RequestMapping(value = ACTION_VIDEO_PLAY_VOD + "/{instanceId}/{childId}/{videoId}/{cameraId}")
    public String recordVideoPlayVod(HttpServletRequest request,
                                     @PathVariable String instanceId,
                                     @PathVariable String childId,
                                     @PathVariable String videoId,
                                     @PathVariable String cameraId) {
        //final String videoId = request.getParameter("videoId");
        logger.info("recordVideoPlayVod, childId:" + childId + ", videoId:" + videoId);
        VideoData videoData = gartenServiceProxy.getVideoData(childId, videoId);
        requestValid(request);
        request.setAttribute("videoId", videoId);
        request.setAttribute("environment", environment);
        String gartenId = gartenServiceProxy.getUser(childId).getGartenId();
        fillVideoPlayAttr(request, instanceId, null, gartenId, cameraId, null, null, false, videoData, BizVideoType.LIVE);
        return "video/record_video_play_vod";
    }

    @RequestMapping(value = ACTION_CLOSEUP_VIDEO_PLAY + "/{instanceId}/{cameraId}")
    public String recordCloseupVideoPlay(@PathVariable String instanceId,
                                         @PathVariable String cameraId,
                                         HttpServletRequest request) {
        final String videoUrl = request.getParameter("videoUrl");
        final String shareVideoUrl = request.getParameter("shareVideoUrl");
        logger.info("recordCloseupVideoPlay, instanceId:" + instanceId + ", cameraId:" + cameraId
                + ", videoUrl:" + videoUrl + ", shareVideoUrl:" + shareVideoUrl);
        requestValid(request);
        fillVideoPlayAttr(request, instanceId, null, null, cameraId, videoUrl, shareVideoUrl, true, BizVideoType.CLOSEUP);
        logger.info("recordCloseupVideoPlay, after fill request.");
        return "video/record_video_play";
    }

    @RequestMapping(value = ACTION_VIDEO_HISTORY_CAMERA_LIST)
    public String videoHistoryCameraList(HttpServletRequest request,
                                         @ModelAttribute(WxConstant.WX_OPEN_ID) String openId) {
        logger.info("videoHistoryCameraList, after auth, openId: " + openId);
        requestValid(request);
        WxOpenUser wxOpenUser = wxOpenUserService.getWxOpenUserByOpenId(openId);
        String ret = checkSubAndReg(request, wxOpenUser);
        if (!StringUtils.isEmpty(ret))
            return ret;
        WxSubscription subscription = wxServiceProxy.getSubscriptionById(wxOpenUser.getSubscriptionId());
        String instanceId = subscription.getInstanceId();
        String baseUserId = wxOpenUser.getBaseUserId();
        List<GartenUser> userList = gartenServiceProxy.getAllByBaseUserId(baseUserId);
        if (userList.isEmpty()) {
            String warn = notInService;
            logger.warn("videoHistoryCameraList, " + warn);
            return "prompt/prompt_not_in_service";
        }
        String gartenId = userList.get(0).getGartenId();
        boolean online = gartenServiceProxy.isPayOnline(gartenId);
        String bizRet = checkAccountBiz(request, baseUserId, BizVideoType.LIVE, online);
        if (!StringUtils.isEmpty(bizRet))
            return bizRet;

        List<Camera> cameras = gartenServiceProxy.getCamerasRemoveDuplicate(baseUserId);
        if (cameras == null || cameras.isEmpty())
            return "prompt/prompt_no_camera";

        logger.info("videoHistoryCameraList, camera cnt: " + cameras.size());
        for (Camera c : cameras) {
            if (c == null)
                continue;
            if (c.getServiceType() != Constant.CameraServiceType.SERVICE || c.isClose())
                c.setId("");
        }

        request.setAttribute("cameras", cameras);
        request.setAttribute(WxConstant.WX_OPEN_ID, openId);
        request.setAttribute(WxConstant.WX_AUTH_INSTANCE_ID, instanceId);
        request.setAttribute("environment", environment);
        request.setAttribute("piwikSiteId", subscription.getPiwikId());
        return "video/video_history_camera_list";
    }

    @RequestMapping(value = "get_one_camera_video_list")
    public String getOneCameraVideoList(HttpServletRequest request) {
        final String instanceId = request.getParameter(WxConstant.WX_AUTH_INSTANCE_ID);
        final String openId = request.getParameter(WxConstant.WX_OPEN_ID);
        final String cameraId = request.getParameter("cameraId");
        logger.info("getOneCameraVideoList, openId:" + openId + ", camera:" + cameraId);
        requestValid(request);
        Camera camera = deviceServiceProxy.getCameraById(cameraId);
        request.setAttribute("camera", camera);
        request.setAttribute(WxConstant.WX_OPEN_ID, openId);
        if (instanceId != null && !instanceId.isEmpty()) {
            final WxSubscription subscription = wxServiceProxy.getSubscriptionByInstanceId(instanceId);
            if (subscription != null) {
                request.setAttribute(WxConstant.WX_AUTH_INSTANCE_ID, subscription.getInstanceId());
                request.setAttribute("piwikSiteId", subscription.getPiwikId());
            }
        }
        return "video/video_history_one_camera";
    }

    @RequestMapping(value = "get_camera_video_data/{cameraId}")
    public void getCameraVideoData(HttpServletRequest request,
                                   @PathVariable String cameraId,
                                   HttpServletResponse response) {
        String pageSizeStr = request.getParameter("pageSize");
        //String offsetStr = request.getParameter("offset");
        String pageNumberStr = request.getParameter("pageNumber");
        String order = request.getParameter("sortOrder");
        logger.info("getCameraVideoData, camera:" + cameraId + ", page:" + pageNumberStr
                + ", size:" + pageSizeStr + ", order:" + order);
        requestValid(request);
        if (StringUtils.isEmpty(pageNumberStr) || StringUtils.isEmpty(pageSizeStr)) {
            return;
        }
        int pageNO = 0;
        int pageSize = 20;
        if (pageSizeStr != null) {
            pageSize = Integer.parseInt(pageSizeStr);
        }
        if (pageNumberStr != null) {
            pageNO = Integer.parseInt(pageNumberStr);
        }
        logger.info("getCameraVideoData, cameraId=" + cameraId + ", pageNO=" + pageNO + ", pageSize=" + pageSize);
        Pager<M3u8> pager = videoServiceProxy.getM3u8PageByDuration(cameraId, pageNO - 1, pageSize, 60 * 60);
        logger.info("getCameraVideoData, pager:" + pager);
        MessageRender.renderJson(response, pager);
    }

    /**
     * -------------------        private              ---------------------
     */
    private void fillVideoPlayAttr(HttpServletRequest request, String instanceId, String openId, String gartenId,
                                   String cameraId, String videoUrl, String shareVideoUrl, boolean isShare, BizVideoType type) {
        fillVideoPlayAttr(request, instanceId, openId, gartenId, cameraId, videoUrl, shareVideoUrl, isShare, null, type);
    }

    private void fillVideoPlayAttr(HttpServletRequest request, String instanceId, String openId, String gartenId,
                                   String cameraId, String videoUrl, String shareVideoUrl, boolean isShare, VideoData videoData, BizVideoType type) {
        GartenUser gUser = null;
        if (!StringUtils.isEmpty(openId)) {
            String baseUserId = wxOpenUserService.getWxOpenUserByOpenId(openId).getBaseUserId();
            List<GartenUser> userList = gartenServiceProxy.getAllByBaseUserId(baseUserId);
            if (!userList.isEmpty()) {
                gUser = userList.get(0);
                if (StringUtils.isEmpty(gartenId))
                    gartenId = gUser.getGartenId();
            }
        }
        logger.info("fillVideoPlayAttr, gUser:" + gUser + ", gartenId:" + gartenId);
        if (!isShare) {
            request.setAttribute(WxConstant.WX_OPEN_ID, openId);// for get next video
            request.setAttribute("cameraId", cameraId);// for get next video
            request.setAttribute("time", System.currentTimeMillis());// for show next button
            if (!StringUtils.isEmpty(openId)) {
                boolean isStaff = false;
                //BoxConfig config = deviceServiceProxy.getBoxConfigByBoxId(deviceServiceProxy.getCameraById(cameraId).getBoxId());
                //String gartenId = config==null?"":config.getKinderGartenId();
                //GartenUser gUser = gartenUserController.getByBaseUserIdAndGartenId(baseUserId, gartenId);
                if (gUser != null) {
                    List<GartenUser2Role> u2rList = gartenServiceProxy.getAllRelationByGartenUserId(gUser.getId());
                    for (GartenUser2Role u2r : u2rList) {
                        BaseRole role = baseServiceProxy.getBaseRoleById(u2r.getBaseRoleId());
                        if (role.getRoleType() == Constant.RoleType.HEADMASTER ||
                                role.getRoleType() == Constant.RoleType.TEACHER) {
                            isStaff = true;
                            break;
                        }
                    }
                }
                request.setAttribute("isStaff", isStaff);
            }
        } else {
            request.setAttribute("time", 0);
            request.setAttribute("isStaff", true);
        }

        request.setAttribute("videoUrl", videoUrl);
        /*if (!StringUtils.isEmpty(videoUrl))
            request.setAttribute("shareUrl", generateShareVideoUrl(instanceId, cameraId, videoUrl, shareVideoUrl, type));*/

        request.setAttribute(WxConstant.WX_AUTH_INSTANCE_ID, instanceId);
        request.setAttribute("environment", environment);
        if (instanceId != null && !instanceId.isEmpty()) {
            fillConfigAttr(request, instanceId, gartenId, videoData, type);
        }
    }

    private void fillConfigAttr(HttpServletRequest request, String instanceId,
                                String gartenId, VideoData videoData, BizVideoType videoType) {
        final WxSubscription subscription = wxServiceProxy.getSubscriptionByInstanceId(instanceId);
        if (subscription == null)
            return;
        request.setAttribute("piwikSiteId", subscription.getPiwikId());

        //initialize by default
        String title = titlePlayLive;
        String desc = descPlayLive;
        String imgUrl = coverPlayLiveForShare;

        if (videoType == BizVideoType.CLOSEUP) {
            title = titlePlayCloseup;
            desc = descPlayCloseup;
        }

        if (!StringUtils.isEmpty(gartenId)) {
            Garten kindergarten = gartenServiceProxy.get(gartenId);
            GartenInfo gartenInfo = gartenServiceProxy.getGartenInfo(kindergarten.getGartenInfoId());
            if (kindergarten != null) {
                final String kindergartenName = kindergarten.getName();
                final String kindergartenTel = gartenInfo.getTel();
                request.setAttribute("kindergartenName", kindergartenName);
                request.setAttribute("kindergartenTel", kindergartenTel);
                request.setAttribute("logo", gartenInfo.getLogoUrl());
                request.setAttribute("slogan", gartenInfo.getSlogan());

                VideoConfig config = videoServiceProxy.getVideoConfigByKinderGartenIdAndBizType(kindergarten.getId(),
                        videoType.name());
                if (config != null) {
                    if (!StringUtils.isEmpty(config.getTitle())) {
                        title = config.getTitle();
                    }
                    if (!StringUtils.isEmpty(config.getDescription())) {
                        desc = config.getDescription();
                    } else {
                        if (!StringUtils.isEmpty(kindergartenName))
                            desc = kindergartenName + desc;
                        else {
                            desc = gartenName + desc;
                        }
                    }
                    if (!StringUtils.isEmpty(config.getCoverUrl())) {
                        imgUrl = config.getCoverUrl();
                    }
                }
            }
        }

        if (videoData != null) {
            if (!StringUtils.isEmpty(videoData.getVideoDesc()))
                title = videoData.getVideoDesc();
            if (!StringUtils.isEmpty(videoData.getVideoDescShare()))
                desc = videoData.getVideoDescShare();
            if (!StringUtils.isEmpty(videoData.getCoverUrl()))
                imgUrl = videoData.getCoverUrl();
            logger.info("fillConfigAttr, t:" + title + ", d:" + desc + ", i:" + imgUrl);
        }

        request.setAttribute("title", title);
        request.setAttribute("desc", desc);

        if (videoType == BizVideoType.LIVE || videoType == BizVideoType.CLOSEUP) {
            fillWxJsConfig(request, imgUrl, subscription);
        }
    }

    //TODO abstract
    private void fillWxJsConfig(HttpServletRequest request, String imgUrl, WxSubscription subscription) {
        request.setAttribute("imgUrl", imgUrl);
        String pageUrl = request.getRequestURL().toString();
        String qs = request.getQueryString();
        if (!StringUtils.isEmpty(qs)) {
            pageUrl = pageUrl + "?" + qs;
        }
        WxJsConfig jsConfig = wxServiceProxy.getWxJsConfig(subscription.getAppId(),
                pageUrl);
        logger.info("fillWxJsConfig, jsConfig:" + jsConfig);
        if (jsConfig != null) {
            request.setAttribute("debug", jsConfig.isDebug());
            request.setAttribute("appId", jsConfig.getAppId());
            request.setAttribute("timestamp", jsConfig.getTimestamp());
            request.setAttribute("nonceStr", jsConfig.getNonceStr());
            request.setAttribute("signature", jsConfig.getSignature());
            List<String> apiList = jsConfig.getJsApiList();
            if (apiList != null) {
                String[] apiArray = new String[apiList.size()];
                apiList.toArray(apiArray);
                request.setAttribute("jsApiList", apiArray);
            }
        }
    }

    private void fillConfigGoodsUrl(HttpServletRequest request, String baseUserId, BizVideoType type) {
        String gartenId = gartenServiceProxy.getAllByBaseUserId(baseUserId).get(0).getGartenId();
        GartenInfo gartenInfo = gartenServiceProxy.getGartenInfo(gartenServiceProxy.get(gartenId).getGartenInfoId());
        Goods goods = gartenServiceProxy.getGoodsByTypeAndGartenId(type.name(), gartenId);
        request.setAttribute("kindergartenTel", gartenInfo.getTel());
        String renewUrl = goods != null ? goods.getLink() : "";
        renewUrl = gartenInfo.isPayOnline() ? renewUrl : "";
        request.setAttribute("renewUrl", renewUrl);
    }

    private String generateShareVideoUrl(String instanceId, String cameraId, String videoUrl, String shareVideoUrl,
                                         BizVideoType type) {
        String shareUrl = serverUrl + ":" + svcPort
                + "/biz/carekids/" + ACTION_SHARE_VIDEO
                + "/" + instanceId
                + "/" + cameraId
                + "/" + type.name()
                + ".action?videoUrl=" + UriUtil.encode(shareVideoUrl == null ? videoUrl : shareVideoUrl);
        logger.info("generateShareVideoUrl, shareUrl:" + shareUrl);
        return shareUrl;
    }

    //响应慢时也会触发此方法
    public String userCameraListFallback(HttpServletRequest request) {
        return "prompt/prompt_service_slowly";
    }

    private boolean requestValid(HttpServletRequest request) {
        String referer = request.getHeader("referer");
        if (referer != null && referer.endsWith("*smart-f.cn")) {
            return true;
        }
        return false;
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

    private String checkAccountBiz(HttpServletRequest request, String baseUserId, BizVideoType type,
                                   boolean payOnline) {
        Account account = gartenServiceProxy.getByBaseUserId(baseUserId);
        if (account == null) {
            String warn = notInService;
            logger.warn("checkAccountBiz, " + warn);
            return "prompt/prompt_not_in_service";
        }
        if (account.isClose() || gartenServiceProxy.isBizExpired(account, type, payOnline)) {
            String warn = accountArreas;
            logger.warn("checkAccountBiz, " + warn);
            fillConfigGoodsUrl(request, baseUserId, type);
            return "prompt/live_video_arreas";
        }
        return "";
    }

}