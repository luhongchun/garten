package cn.smart.cloud.biz.opadmin.controller.garten.video;

import cn.smart.cloud.biz.opadmin.constant.SmartCodeMsgConstant;
import cn.smart.cloud.biz.opadmin.controller.MessageRender;
import cn.smart.cloud.biz.opadmin.entity.fr.Person;
import cn.smart.cloud.biz.opadmin.entity.garten.Garten;
import cn.smart.cloud.biz.opadmin.entity.garten.GartenClass;
import cn.smart.cloud.biz.opadmin.entity.garten.data.GartenInfo;
import cn.smart.cloud.biz.opadmin.entity.garten.user.GartenUser;
import cn.smart.cloud.biz.opadmin.entity.garten.user.GartenUser2GartenClass;
import cn.smart.cloud.biz.opadmin.entity.garten.user.GartenUserVideo;
import cn.smart.cloud.biz.opadmin.entity.video.VideoReview;
import cn.smart.cloud.biz.opadmin.entity.video.VideoReviewRequest;
import cn.smart.cloud.biz.opadmin.entity.weixin.*;
import cn.smart.cloud.biz.opadmin.gson.BizVideoType;
import cn.smart.cloud.biz.opadmin.gson.FrMessage;
import cn.smart.cloud.biz.opadmin.gson.Position;
import cn.smart.cloud.biz.opadmin.gson.UploadVodResult;
import cn.smart.cloud.biz.opadmin.gson.device.Camera;
import cn.smart.cloud.biz.opadmin.gson.device.box.BoxConfig;
import cn.smart.cloud.biz.opadmin.gson.video.UserVideo;
import cn.smart.cloud.biz.opadmin.gson.video.VideoConfig;
import cn.smart.cloud.biz.opadmin.gson.video.VideoReviewData;
import cn.smart.cloud.biz.opadmin.proxy.*;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/biz/video/closeup")
public class CloseupVideoController {

    private static final Logger logger = LoggerFactory.getLogger(CloseupVideoController.class);

    @Autowired
    Gson gson;

    @Autowired
    GartenServiceProxy gartenServiceProxy;
    @Autowired
    FaceServiceProxy faceServiceProxy;
    @Autowired
    BaseCoreServiceProxy baseServiceProxy;
    @Autowired
    BaseUserServiceProxy baseUserServiceProxy;
    @Autowired
    VideoServiceProxy videoServiceProxy;
    @Autowired
    DeviceServiceProxy deviceServiceProxy;
    @Autowired
    WxServiceProxy wxServiceProxy;
    @Autowired
    WxOpenUserServiceProxy wxOpenUserServiceProxy;

    @Autowired
    WxAuthService wxAuthService;
    @Autowired
    Environment environment;

    @Value("${server.port}")
    String svcPort;
    @Value("${service.biz.wan.url.base}")
    String serverUrl;
    @Value("${text.biz.garten.video.template.closeup.title}")
    String titleCloseupTemplate;
    @Value("${text.biz.garten.video.template.closeup.remark}")
    String remarkCloseup;

    private static final String ACTION_BASE_LOCAL = "/biz/video/closeup/";
    private static final String PREFIX_AUTH = "auth/";
    private static final String PREFIX_REDIRECT = "rdt/";
    private static final String ACTION_REVIEW_VIDEO = "action_review_video";
    private static final String ACTION_REVIEW_VIDEO_RESULT = "action_review_video_result";
    private static final String ACTION_REVIEW_VIDEO_PROMPT = "action_review_video_prompt";

    @RequestMapping(value = "notify_person_identified")
    public void notifyPersonIdentified(@RequestParam String frMessage,
                                       HttpServletRequest request,
                                       HttpServletResponse response) {
        logger.info("notifyPersonIdentified, frMessage:" + frMessage);
        Map<String, Object> resultMap = new HashMap<>();

        try {
            FrMessage message = gson.fromJson(frMessage, FrMessage.class);// throw JsonSyntaxException
            if (message != null && message.getPersonId() != null) {
                Camera camera = deviceServiceProxy.getCameraBySn(message.getCameraSn());
                Position position = deviceServiceProxy.getPositionByCameraId(camera.getId());
                String addr = position == null ? "" : position.getName();

                String childName = "";
                List<GartenUser> parents = new ArrayList<>();
                List<WxOpenUser> wxUserList = new ArrayList<>();

                if (message.getPersonId().equals("children")) {
                    String cameraId = deviceServiceProxy.getCameraBySn(message.getCameraSn()).getId();
                    String classId = gartenServiceProxy.getGartenClassConfigByCameraId(cameraId).getClassId();
                    parents = gartenServiceProxy.getParentsByClassId(classId);
                    childName = addr;
                } else {
                    Person person = faceServiceProxy.getPersonByPersonId(message.getPersonId());
                    if (person == null) {
                        logger.warn("notifyPersonIdentified, no person");
                        return;
                    }
                    GartenUser child = gartenServiceProxy.getChildByPersonId(person.getId());
                    if (child == null) {
                        logger.warn("notifyPersonIdentified, no child");
                        return;
                    }
                    parents = gartenServiceProxy.getParentsByChild(child.getId());
                    childName = child.getAliasName() == null  ? "" : child.getAliasName();
                }
                if (parents.isEmpty()) {
                    logger.warn("notifyPersonIdentified, no parents");
                    return;
                }
                GartenInfo gartenInfo = gartenServiceProxy.getGartenInfo(parents.get(0).getGartenId());
                boolean online = gartenInfo.isPayOnline();
                WxSubscription subscription = wxServiceProxy.getSubscriptionById(gartenInfo.getSubscriptionId());
                for (GartenUser p : parents) {
                    try {
                        WxOpenUser openUser = wxOpenUserServiceProxy
                                .getWxOpenUserByBaseUserIdAndSubscriptionId(p.getBaseUserId(), subscription.getId());
                        if (openUser == null
                                || !gartenServiceProxy.hasBizPermission(p.getBaseUserId(), BizVideoType.CLOSEUP, online)) {
                            logger.warn("notifyPersonIdentified, parent not registered or no permission");
                            continue;
                        }
                        wxUserList.add(openUser);
                    } catch (Exception e) {
                        logger.info("notifyPersonIdentified, e:" + e);
                    }
                }
                if (wxUserList.isEmpty()) {
                    logger.warn("notifyPersonIdentified, no wxUser");
                    return;
                }

                UserVideo userVideo = videoServiceProxy.getCloseupVideoByCameraIdWithTime(camera.getId(),
                        message.getStartTime(), message.getEndTime());
                if (userVideo == null) {
                    logger.warn("notifyPersonIdentified, userVideo is null.");
                    return;
                }
                String videoUrl = userVideo.getVideoUrl();
                String shareVideoUrl = userVideo.getVideoUrlWithHeadTrail()
                        == null ? videoUrl : userVideo.getVideoUrlWithHeadTrail();
                String instanceId = subscription.getInstanceId();
                videoUrl = generateVideoPageUrl(request, instanceId, camera.getId(), videoUrl, shareVideoUrl);
                logger.info("notifyPersonIdentified, videoUrl:" + videoUrl);
                if (videoUrl == null || videoUrl.isEmpty()) {
                    logger.error("notifyPersonIdentified, no valid video!!");
                    return;
                }

                boolean ret = pushCloseupVideo(instanceId,
                        childName, addr, message.getEndTime(), "content", "detail", videoUrl, wxUserList);
                resultMap = SmartCodeMsgConstant.createResultMapError(ret ? SmartCodeMsgConstant.SMART_ERROR_CODE_SUCCESS :
                        SmartCodeMsgConstant.SMART_ERROR_CODE_SYSTEM_BUSY);

            }
        } catch (JsonSyntaxException e) {
            resultMap = SmartCodeMsgConstant.createResultMapError(SmartCodeMsgConstant.SMART_ERROR_CODE_INVALID_PARAM);
        }

        logger.info("notifyPersonIdentified finished, resultMap:" + resultMap);
        MessageRender.renderJson(response, resultMap);
    }

    @RequestMapping(value = "upload_vod_finished")
    public void uploadVodFinished(@RequestParam String uploadVodResult,
                                  HttpServletResponse response) {
        int code = SmartCodeMsgConstant.SMART_ERROR_CODE_SUCCESS;
        Map<String, Object> resultMap = new HashMap<>();
        logger.info("uploadVodFinished param = " + uploadVodResult);
        try {
            UploadVodResult uvResult = gson.fromJson(uploadVodResult, UploadVodResult.class);// throw JsonSyntaxException
            if (uvResult != null && !(uvResult.getVideoUrl() == null || uvResult.getVideoUrl().isEmpty())) {
                String videoUrl = uvResult.getVideoUrl();
                String coverUrl = uvResult.getCoverUrl();
                String cameraId = uvResult.getCameraId();
                UserVideo uv = videoServiceProxy.createUserVideo(videoUrl, coverUrl, cameraId);
                if (!gartenServiceProxy.saveVideo(uv.getId(), uvResult))
                    code = SmartCodeMsgConstant.SMART_ERROR_CODE_INVALID_PARAM;
            } else {
                code = SmartCodeMsgConstant.SMART_ERROR_CODE_INVALID_PARAM;
            }
        } catch (JsonSyntaxException e) {
            code = SmartCodeMsgConstant.SMART_ERROR_CODE_INVALID_PARAM;
        }

        resultMap.putAll(SmartCodeMsgConstant.createResultMapError(code));
        logger.info("uploadVodFinish result = " + gson.toJson(resultMap));
        MessageRender.renderJson(response, resultMap);
    }

    @RequestMapping(value = "request_video_review")
    public String requestVideoReview(
            HttpServletRequest request,
            HttpServletResponse response) {
        logger.info("requestVideoReview");

        request.setAttribute("gartens", gartenServiceProxy.getAll());
        List<String> vtList = new ArrayList<>();
        vtList.add("记录版");
        vtList.add("精彩版");
        request.setAttribute("vtList", vtList);
        List<String> dateList = new ArrayList<>();
        dateList.add("1月");
        dateList.add("2月");
        dateList.add("3月");
        dateList.add("4月");
        dateList.add("5月");
        dateList.add("6月");
        dateList.add("7月");
        dateList.add("8月");
        dateList.add("9月");
        dateList.add("10月");
        dateList.add("11月");
        dateList.add("12月");
        dateList.add("第一学期");
        dateList.add("第二学期");
        request.setAttribute("dateList", dateList);
        return "video/review_request";
    }

    @RequestMapping(value = "notify_video_review")
    public void notifyVideoReview(
            HttpServletRequest request,
            HttpServletResponse response) {
        String classId = request.getParameter("gartenClass");
        String date = request.getParameter("date");
        String reviewerId = request.getParameter("reviewerId");
        String videoType = request.getParameter("videoType");
        String videoIds = request.getParameter("videoIds");
        logger.info("notifyVideoReview, videoIds:" + videoIds + ", class:" + classId + ", date:" + date + ", reviewerId:" + reviewerId
                + ", videoType:" + videoType);
        Map<String, Object> resultMap = new HashMap<>();
        if (isEmpty(classId)) {
            resultMap = SmartCodeMsgConstant.createResultMapError(SmartCodeMsgConstant.SMART_ERROR_CODE_INVALID_GARTEN_CLASS);
            MessageRender.renderJson(response, resultMap);
            return;
        }
        if (isEmpty(reviewerId)) {
            resultMap = SmartCodeMsgConstant.createResultMapError(SmartCodeMsgConstant.SMART_ERROR_CODE_INVALID_GARTEN_USER);
            MessageRender.renderJson(response, resultMap);
            return;
        }
        if (isEmpty(videoType)) {
            resultMap = SmartCodeMsgConstant.createResultMapError(SmartCodeMsgConstant.SMART_ERROR_CODE_INVALID_GARTEN_VIDEO_TYPE);
            MessageRender.renderJson(response, resultMap);
            return;
        }
        if (isEmpty(date)) {
            resultMap = SmartCodeMsgConstant.createResultMapError(SmartCodeMsgConstant.SMART_ERROR_CODE_INVALID_DATE);
            MessageRender.renderJson(response, resultMap);
            return;
        }
        GartenUser reviewer = gartenServiceProxy.getUser(reviewerId);
        try {
            String[] ids = videoIds.split(",");
            List<String> idList = Arrays.asList(ids);
            if (idList != null && !idList.isEmpty()) {
                VideoReviewRequest req = new VideoReviewRequest();
                req.setReviewerId(reviewerId);
                req.setReviewTime(new Date(System.currentTimeMillis()));
                req.setSubmitId("");
                String vt = BizVideoType.RECORD_EDITION.name();
                if (videoType.contains("精彩"))
                    vt = BizVideoType.WONDERFUL_EDITION.name();
                req.setVideoType(vt);
                req.setIssue(date);
                gartenServiceProxy.saveVideoReviewRequest(req);

                boolean hasVideo = false;
                for (String id : idList) {
                    if (isEmpty(id))
                        continue;
                    GartenUserVideo guv = gartenServiceProxy.getUserVideoById(id);
                    if (guv == null || isEmpty(guv.getVideoId())) {
                        logger.warn("notifyVideoReview, invalid videoId:" + id);
                        continue;
                    }
                    VideoReview old = gartenServiceProxy.getVideoReview(id);
                    if (old != null) {
                        logger.warn("notifyVideoReview, old videoId:" + id);
                        continue;
                    }
                    hasVideo = true;
                    VideoReview videoReview = new VideoReview();
                    videoReview.setVideoId(id);
                    //TODO summit user
                    videoReview.setPass(false);
                    videoReview.setDescription("");
                    videoReview.setRequestId(req.getId());
                    gartenServiceProxy.saveVideoReview(videoReview);
                    logger.info("notifyVideoReview, videoId:" + id + " saved.");
                }
                if (!hasVideo) {
                    resultMap = SmartCodeMsgConstant.createResultMapError(SmartCodeMsgConstant.SMART_ERROR_CODE_INVALID_GARTEN_VIDEO);
                    MessageRender.renderJson(response, resultMap);
                    return;
                }

                GartenInfo gartenInfo = gartenServiceProxy.getGartenInfo(reviewer.getGartenId());
                WxSubscription subscription = wxServiceProxy.getSubscriptionById(gartenInfo.getSubscriptionId());
                try {
                    List<WxOpenUser> wxUserList = new ArrayList<>();
                    WxOpenUser openUser = wxOpenUserServiceProxy.getWxOpenUserByBaseUserIdAndSubscriptionId(reviewer.getBaseUserId(),
                            subscription.getId());
                    /*if (openUser == null || !checkPermission(reviewer.getBaseUserId(), PermissionType.MANAGE_CAMERA)) {
                        logger.warn("notifyVideoReview, parent not registered or no permission");
                        continue;
                    }*/
                    if (openUser != null)
                        wxUserList.add(openUser);

                    String instanceId = subscription.getInstanceId();
                    logger.info("notifyVideoReview, instanceId:" + instanceId);

                    String pageUrl = generateReviewVideoPageUrl(req.getId());
                    String className = gartenServiceProxy.getClass(classId).getName();

                    boolean ret = pushVideoReviewMsg(instanceId, className + date + "视频", pageUrl, wxUserList);
                    resultMap = SmartCodeMsgConstant.createResultMapError(ret ? SmartCodeMsgConstant.SMART_ERROR_CODE_SUCCESS :
                            SmartCodeMsgConstant.SMART_ERROR_CODE_SYSTEM_BUSY);
                } catch (Exception e) {
                    logger.info("notifyVideoReview, e:" + e);
                }
            } else {
                logger.error("notifyVideoReview, no valid video!!");
                resultMap = SmartCodeMsgConstant.createResultMapError(SmartCodeMsgConstant.SMART_ERROR_CODE_INVALID_GARTEN_VIDEO);
                MessageRender.renderJson(response, resultMap);
                return;
            }
        } catch (JsonSyntaxException e) {
            resultMap = SmartCodeMsgConstant.createResultMapError(SmartCodeMsgConstant.SMART_ERROR_CODE_INVALID_PARAM);
        }

        logger.info("notifyVideoReview finished, resultMap:" + resultMap);
        MessageRender.renderJson(response, resultMap);
    }

    @RequestMapping(value = ACTION_REVIEW_VIDEO + "/{requestId}/{userId}")//TODO AUTH openUser
    public String reviewVideo(@PathVariable String requestId,
                              @PathVariable String userId,
                              HttpServletRequest request,
                              HttpServletResponse response) {
        logger.info("reviewVideo, requestId:" + requestId + ", userId:" + userId);
        List<VideoReview> vrList = gartenServiceProxy.getVideoReviewByRequestIdAndPass(requestId, false);
        VideoReviewRequest vrr = gartenServiceProxy.getVideoReviewRequest(requestId);
        if (vrList == null || vrList.isEmpty() || vrr == null) {
            logger.error("reviewVideo, invalid content. vrList:" + vrList + ", vrr:" + vrr);
            return "prompt/video_reviewed";
        } else {
            GartenUserVideo gartenUserVideo = gartenServiceProxy.getUserVideoById(vrList.get(0).getVideoId());
            String videoType = vrr.getVideoType();
            GartenUser2GartenClass u2c = gartenServiceProxy.getGartenUser2GartenClass(gartenUserVideo.getChildId());
            GartenClass gartenClass = gartenServiceProxy.getClass(u2c.getGartenClassId());
            String className = gartenClass.getName();
            String gartenName = gartenServiceProxy.get(gartenClass.getGartenId()).getName();
            List<VideoReviewData> vrdList = new ArrayList<>();
            for (VideoReview vr : vrList) {
                GartenUserVideo guv = gartenServiceProxy.getUserVideoById(vr.getVideoId());
                if (guv == null)
                    continue;
                UserVideo userVideo = videoServiceProxy.getUserVideo(guv.getVideoId());
                if (userVideo != null && !isEmpty(userVideo.getVideoUrl())) {
                    String name = gartenServiceProxy.getUser(guv.getChildId()).getAliasName();
                    VideoReviewData vrd = new VideoReviewData();
                    vrd.setId(guv.getId());
                    vrd.setChildName(name);
                    vrd.setVideoUrl(userVideo.getVideoUrl());
                    vrdList.add(vrd);
                }
            }

            request.setAttribute("userId", userId);
            request.setAttribute("gartenName", gartenName);
            request.setAttribute("className", className);
            request.setAttribute("videoType", videoType.equals(BizVideoType.WONDERFUL_EDITION.name()) ? "剪辑版" : "记录版");
            String issue = vrr.getIssue();
            logger.info("reviewVideo, issue:" + issue);
            request.setAttribute("issue", issue);

            request.setAttribute("videos", vrdList);
            request.setAttribute("videoCnt", vrdList.size());
            request.setAttribute("environment", environment);

            GartenInfo gartenInfo = gartenServiceProxy.getGartenInfo(gartenServiceProxy.getUser(userId).getGartenId());
            WxSubscription subscription = wxServiceProxy.getSubscriptionById(gartenInfo.getSubscriptionId());
            WxJsConfig jsConfig = wxServiceProxy.getWxJsConfig(subscription.getAppId(),
                    request.getRequestURL().toString());
            logger.info("reviewVideo, jsConfig:" + jsConfig);
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
            return "video/video_edited_review";
        }
    }

    @RequestMapping(value = ACTION_REVIEW_VIDEO_RESULT)
    public void reviewVideoResult(
            HttpServletRequest request,
            HttpServletResponse response) {
        String videoCnt = request.getParameter("videoCnt");
        String userId = request.getParameter("userId");
        final String[] videoIdArray = request.getParameterValues("videoIds");
        final String[] passArray = request.getParameterValues("passStatus");
        final String[] desArray = request.getParameterValues("desArray");

        logger.info("reviewVideoResult, videoCnt:" + videoCnt + ", userId:" + userId + ", videoIdArray:" + videoIdArray
                + ", videoIdpassArrayArray:" + passArray + ", desArray:" + desArray);

        logger.info("reviewVideoResult, videoIdArray :" + Arrays.toString(videoIdArray));
        logger.info("reviewVideoResult, passArray :" + Arrays.toString(passArray));
        logger.info("reviewVideoResult, desArray :" + Arrays.toString(desArray));
        if (videoIdArray == null || videoIdArray.length <= 0) {
            MessageRender.renderHtml(response, ACTION_BASE_LOCAL + ACTION_REVIEW_VIDEO_PROMPT + "/requestId/" + 0 + "/" + 0);
            //return;
        }
        List<String> vIdList = new ArrayList<>();
        List<String> passList = new ArrayList<>();
        Map<String, String> desMap = new HashMap<>();

        for (String id : videoIdArray) {
            vIdList.add(id);
            logger.info("reviewVideoResult, videoId :" + id);
        }
        if (passArray != null) {
            for (String p : passArray) {
                passList.add(p);
                logger.info("reviewVideoResult, pass :" + p);
            }
        }
        if (desArray != null) {
            for (String d : desArray) {
                String[] des = d.split("#");
                if (des == null || des.length < 1 || des.length > 2)
                    continue;
                if (des.length == 1)
                    desMap.put(des[0], "");
                else
                    desMap.put(des[0], des[1]);
                logger.info("reviewVideoResult, des :" + d);
            }
        }

        String requestId = gartenServiceProxy.getVideoReview(vIdList.get(0)).getRequestId();
        VideoReviewRequest vrr = gartenServiceProxy.getVideoReviewRequest(requestId);
        if (vrr != null) {
            vrr.setReviewTime(new Date(System.currentTimeMillis()));
            gartenServiceProxy.saveVideoReviewRequest(vrr);
        }

        GartenInfo gartenInfo = gartenServiceProxy.getGartenInfo(gartenServiceProxy.getUser(userId).getGartenId());
        WxSubscription subscription = wxServiceProxy.getSubscriptionById(gartenInfo.getSubscriptionId());
        for (String videoId : vIdList) {
            logger.info("reviewVideoResult, videoId:" + videoId + " description:" + desMap.get(videoId));
            VideoReview videoReview = gartenServiceProxy.getVideoReview(videoId);
            videoReview.setDescription(desMap.get(videoId));
            if (passList.contains(videoId)) {
                logger.info("reviewVideoResult, pass:" + videoId);
                videoReview.setPass(true);
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        List<WxOpenUser> wxUserList = new ArrayList<>();
                        GartenUserVideo guv = gartenServiceProxy.getUserVideoById(videoId);
                        List<GartenUser> parents = gartenServiceProxy.getParentsByChild(guv.getChildId());
                        boolean online = gartenServiceProxy.isPayOnline(parents.get(0).getGartenId());
                        for (GartenUser p : parents) {
                            try {
                                WxOpenUser openUser = wxOpenUserServiceProxy
                                        .getWxOpenUserByBaseUserIdAndSubscriptionId(p.getBaseUserId(), subscription.getId());
                                if (openUser == null || !gartenServiceProxy
                                        .hasBizPermission(p.getBaseUserId(), BizVideoType.CLOSEUP, online)) {
                                    logger.warn("reviewVideoResult, parent not registered or no permission");
                                    continue;
                                }
                                wxUserList.add(openUser);
                            } catch (Exception e) {
                                logger.info("reviewVideoResult, e:" + e);
                            }
                        }
                        if (wxUserList.isEmpty()) {
                            logger.warn("reviewVideoResult, no wxUser");
                            return;
                        }
                        //TODO notify parent new video to watch.
                        String cameraId = videoServiceProxy.getUserVideo(guv.getVideoId()).getCameraId();
                        UserVideo userVideo = videoServiceProxy.getUserVideo(guv.getVideoId());
                        String watchUrl = generateVideoPlayPageUrl(subscription.getInstanceId(), guv.getChildId(), userVideo.getVideoUrl(), cameraId);
                        String videoType = gartenServiceProxy.getVideoReviewRequest(videoReview.getRequestId()).getVideoType();
                        if (videoType.equals(BizVideoType.WONDERFUL_EDITION.name())) {
                            videoType = "精彩版视频";
                        } else {
                            videoType = "记录版视频";
                        }
                        pushVideoWatchMsg(subscription.getInstanceId(), videoType, watchUrl, System.currentTimeMillis(), wxUserList);
                    }
                }).start();
            } else {
                logger.info("reviewVideoResult, not pass:" + videoId);
            }
            gartenServiceProxy.saveVideoReview(videoReview);
        }
        //TODO notify manager review finished. some video not pass.
        logger.info("reviewVideoResult, success.");
        MessageRender.renderHtml(response, ACTION_BASE_LOCAL + ACTION_REVIEW_VIDEO_PROMPT + "/"
                + requestId + "/" + passList.size() + "/" + (vIdList.size() - passList.size()));
        //return;
    }

    @RequestMapping(value = ACTION_REVIEW_VIDEO_PROMPT + "/{requestId}/{passCnt}/{failCnt}")
    public String reviewVideoPrompt(@PathVariable String requestId,
                                    @PathVariable String passCnt,
                                    @PathVariable String failCnt,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {
        logger.info("reviewVideoPrompt, requestId:" + requestId + ", passCnt:" + passCnt + ", failCnt:" + failCnt);
        int pass = Integer.parseInt(passCnt);
        int fail = Integer.parseInt(failCnt);
        List<VideoReview> vrList = gartenServiceProxy.getVideoReviewByRequestId(requestId);
        VideoReviewRequest vrr = gartenServiceProxy.getVideoReviewRequest(requestId);
        if (vrList == null || vrList.isEmpty() || vrr == null) {
            logger.error("reviewVideo, invalid content. vrList:" + vrList + ", vrr:" + vrr);
            return "prompt/video_reviewed";
        } else {
            if (pass == 0 && fail == 0) {
                logger.error("reviewVideoPrompt, no pass and fail.");
                return "prompt/video_edited_review_result";
            } else {
                GartenUserVideo gartenUserVideo = gartenServiceProxy.getUserVideoById(vrList.get(0).getVideoId());
                String videoType = vrr.getVideoType();
                GartenUser2GartenClass u2c = gartenServiceProxy.getGartenUser2GartenClass(gartenUserVideo.getChildId());
                GartenClass gartenClass = gartenServiceProxy.getClass(u2c.getGartenClassId());
                String className = gartenClass.getName();
                String gartenName = gartenServiceProxy.get(gartenClass.getGartenId()).getName();

                request.setAttribute("gartenName", gartenName);
                request.setAttribute("className", className);
                request.setAttribute("videoType", videoType.equals(BizVideoType.WONDERFUL_EDITION.name()) ? "剪辑版" : "记录版");
                String issue = vrr.getIssue();
                logger.info("reviewVideoPrompt, issue:" + issue);
                request.setAttribute("issue", issue);

                request.setAttribute("passCnt", passCnt);
                request.setAttribute("failCnt", failCnt);
                request.setAttribute("environment", environment);

                return "video/video_edited_review_result";
            }
        }
    }

    private boolean pushCloseupVideo(String instanceId, String name, String address,
                                     long time, String content, String detail, String url, List<WxOpenUser> wxUserList) {
        boolean result = true;
        WxTemplate template = wxServiceProxy.getTemplateByInstanceIdAndType(
                instanceId, WxTemplateType.VIDEO_CLOSEUP.getName());// because a special wechat subscription is for attend for now.
        logger.info("sendAttendTemplateMsg, template:" + template + ", instanceId:" + instanceId);
        if (template != null && wxUserList != null && !wxUserList.isEmpty()) {
            WxTemplateMessage templateMessage = genCloseupVideoWxTemplateMsg(template.getTemplateId(), name, address, time, content, detail, url);
            for (WxOpenUser wxUser : wxUserList) {
                logger.info("sendAttendTemplateMsg, send templateMessage to:" + wxUser.getOpenId());
                templateMessage.setToUserOpenId(wxUser.getOpenId());
                if (!wxServiceProxy.sendTemplateMessage(instanceId, templateMessage))
                    result = false;
            }
        } else {
            result = false;
        }
        return result;
    }

    private boolean pushVideoReviewMsg(String instanceId, String desc, String url, List<WxOpenUser> wxUserList) {
        boolean result = true;
        WxTemplate template = wxServiceProxy.getTemplateByInstanceIdAndType(
                instanceId, WxTemplateType.VIDEO_REVIEW.getName());
        logger.info("pushVideoReviewMsg, template:" + template + ", instanceId:" + instanceId);
        if (template != null && wxUserList != null && !wxUserList.isEmpty()) {
            WxTemplateMessage templateMessage = genVideoReviewWxTemplateMsg(template.getTemplateId(), desc, url);
            for (WxOpenUser wxUser : wxUserList) {
                logger.info("pushVideoReviewMsg, send templateMessage to:" + wxUser.getOpenId());
                templateMessage.setToUserOpenId(wxUser.getOpenId());
                templateMessage.setUrl(url + wxUser.getOpenId());
                if (!wxServiceProxy.sendTemplateMessage(instanceId, templateMessage))
                    result = false;
            }
        } else {
            result = false;
        }
        return result;
    }

    private boolean pushVideoReviewedMsg(String instanceId, String desc, String url, List<WxOpenUser> wxUserList) {
        boolean result = true;
        WxTemplate template = wxServiceProxy.getTemplateByInstanceIdAndType(
                instanceId, WxTemplateType.VIDEO_REVIEWED.getName());
        logger.info("pushVideoReviewedMsg, template:" + template + ", instanceId:" + instanceId);
        if (template != null && wxUserList != null && !wxUserList.isEmpty()) {
            WxTemplateMessage templateMessage = genVideoReviewWxTemplateMsg(template.getTemplateId(), desc, url);
            for (WxOpenUser wxUser : wxUserList) {
                logger.info("pushVideoReviewedMsg, send templateMessage to:" + wxUser.getOpenId());
                templateMessage.setToUserOpenId(wxUser.getOpenId());
                templateMessage.setUrl(url + wxUser.getOpenId());
                if (!wxServiceProxy.sendTemplateMessage(instanceId, templateMessage))
                    result = false;
            }
        } else {
            result = false;
        }
        return result;
    }

    private boolean pushVideoWatchMsg(String instanceId, String videoType, String url, long time, List<WxOpenUser> wxUserList) {
        boolean result = true;
        WxTemplate template = wxServiceProxy.getTemplateByInstanceIdAndType(
                instanceId, WxTemplateType.VIDEO_WATCH.getName());
        logger.info("pushVideoWatchMsg, template:" + template + ", instanceId:" + instanceId);
        if (template != null && wxUserList != null && !wxUserList.isEmpty()) {
            WxTemplateMessage templateMessage = genVideoWatchWxTemplateMsg(template.getTemplateId(), videoType, time, url);
            for (WxOpenUser wxUser : wxUserList) {
                logger.info("pushVideoWatchMsg, send templateMessage to:" + wxUser.getOpenId());
                templateMessage.setToUserOpenId(wxUser.getOpenId());
                if (!wxServiceProxy.sendTemplateMessage(instanceId, templateMessage))
                    result = false;
            }
        } else {
            result = false;
        }
        return result;
    }

    private WxTemplateMessage genCloseupVideoWxTemplateMsg(String templateId,
                                                           String name, String address, long time, String content, String detail, String url) {
        WxTemplateMessage templateMessage = new WxTemplateMessage();
        templateMessage.setTemplateId(templateId);
        templateMessage.setUrl(url);

        Map<String, WxTemplateMessageData> data = new HashMap<>();
        WxTemplateMessageData first = new WxTemplateMessageData();
        WxTemplateMessageData keyword1 = new WxTemplateMessageData();
        WxTemplateMessageData keyword2 = new WxTemplateMessageData();
        WxTemplateMessageData remark = new WxTemplateMessageData();
        first.setValue(titleCloseupTemplate);
        data.put("first", first);

        keyword1.setValue(address);
        data.put("keyword1", keyword1);

        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        keyword2.setValue(format.format(new Date(time)).toString());
        data.put("keyword2", keyword2);

        remark.setValue(remarkCloseup);
        data.put("remark", remark);
        templateMessage.setData(data);

        //templateMessage.setTopColor("");
        return templateMessage;
    }

    private WxTemplateMessage genVideoReviewWxTemplateMsg(String templateId,
                                                          String contentDesc, String url) {
        WxTemplateMessage templateMessage = new WxTemplateMessage();
        templateMessage.setTemplateId(templateId);
        templateMessage.setUrl(url);

        Map<String, WxTemplateMessageData> data = new HashMap<>();
        WxTemplateMessageData first = new WxTemplateMessageData();
        WxTemplateMessageData keyword1 = new WxTemplateMessageData();
        WxTemplateMessageData keyword2 = new WxTemplateMessageData();
        WxTemplateMessageData remark = new WxTemplateMessageData();
        first.setValue("视频审核");
        data.put("first", first);

        keyword1.setValue(contentDesc);
        data.put("keyword1", keyword1);//审批事项：

        keyword2.setValue("待审批");
        data.put("keyword2", keyword2);//审批结果：

        remark.setValue(remarkCloseup);
        data.put("remark", remark);
        templateMessage.setData(data);

        //templateMessage.setTopColor("");
        return templateMessage;
    }

    private WxTemplateMessage genVideoReviewedWxTemplateMsg(String templateId,
                                                            String videoType, long time, String url) {
        WxTemplateMessage templateMessage = new WxTemplateMessage();
        templateMessage.setTemplateId(templateId);
        templateMessage.setUrl(url);

        Map<String, WxTemplateMessageData> data = new HashMap<>();
        WxTemplateMessageData first = new WxTemplateMessageData();
        WxTemplateMessageData keyword1 = new WxTemplateMessageData();
        WxTemplateMessageData keyword2 = new WxTemplateMessageData();
        WxTemplateMessageData keyword3 = new WxTemplateMessageData();
        WxTemplateMessageData remark = new WxTemplateMessageData();
        first.setValue(titleCloseupTemplate);
        data.put("first", first);

        keyword1.setValue(videoType);
        data.put("keyword1", keyword1);//服务类型：

        keyword2.setValue("制作完成");
        data.put("keyword2", keyword2);//服务状态：

        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");
        keyword3.setValue(format.format(new Date(time)).toString());
        data.put("keyword1", keyword3);//服务时间：

        remark.setValue(remarkCloseup);
        data.put("remark", remark);
        templateMessage.setData(data);

        //templateMessage.setTopColor("");
        return templateMessage;
    }

    private WxTemplateMessage genVideoWatchWxTemplateMsg(String templateId,
                                                         String videoType, long time, String url) {
        WxTemplateMessage templateMessage = new WxTemplateMessage();
        templateMessage.setTemplateId(templateId);
        templateMessage.setUrl(url);

        Map<String, WxTemplateMessageData> data = new HashMap<>();
        WxTemplateMessageData first = new WxTemplateMessageData();
        WxTemplateMessageData keyword1 = new WxTemplateMessageData();
        WxTemplateMessageData keyword2 = new WxTemplateMessageData();
        WxTemplateMessageData keyword3 = new WxTemplateMessageData();
        WxTemplateMessageData remark = new WxTemplateMessageData();
        first.setValue(titleCloseupTemplate);
        data.put("first", first);

        keyword1.setValue(videoType);
        data.put("keyword1", keyword1);//服务类型：

        keyword2.setValue("制作完成");
        data.put("keyword2", keyword2);//服务状态：

        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");
        keyword3.setValue(format.format(new Date(time)).toString());
        data.put("keyword3", keyword3);//服务时间：

        remark.setValue(remarkCloseup);
        data.put("remark", remark);
        templateMessage.setData(data);

        //templateMessage.setTopColor("");
        return templateMessage;
    }

    private String generateVideoPageUrl(HttpServletRequest request, String instanceId, String cameraId,
                                        String videoUrl, String shareVideoUrl) {
        logger.info("generateVideoPageUrl, instanceId:" + instanceId + ", cameraId:" + cameraId
                + ", videoUrl:" + videoUrl + ", shareVideoUrl:" + shareVideoUrl);
        if (isEmpty(videoUrl))
            return "";
        return serverUrl + ":" + environment.getProperty("server.port")
                + "/biz/carekids/action_closeup_video_play/" + instanceId + "/" + cameraId + "?videoUrl=" + videoUrl
                + "&shareVideoUrl=" + shareVideoUrl;
    }

    private String generateReviewVideoPageUrl(String requestId) {
        logger.info("generateReviewVideoPageUrl, requestId:" + requestId);
        if (isEmpty(requestId))
            return "";
        return serverUrl + ":" + environment.getProperty("server.port")
                + "/biz/video/closeup/" + ACTION_REVIEW_VIDEO + "/" + requestId + "/";
    }

    private String generateVideoPlayPageUrl(String instanceId, String childId, String videoUrl, String cameraId) {
        logger.info("generateVideoPlayPageUrl, videoId:" + videoUrl);
        if (isEmpty(videoUrl))
            return "";
        return serverUrl + ":" + environment.getProperty("server.port")
                + "/biz/carekids/action_video_play_vod/" + instanceId + "/" + childId + "/" + videoUrl + "/" + cameraId;
    }

    private boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    /**
     * -------------------        private              ---------------------
     */
    @Scheduled(cron = "0 2/10 * * * ?")
    public void pushVideoTimer() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);
        logger.info("时间又过去10分钟了，现在时间:" + cal.getTime());
        List<Garten> gartenList = gartenServiceProxy.getAll();
        for (Garten g : gartenList) {
            VideoConfig config = videoServiceProxy.getVideoConfigByKinderGartenIdAndBizType(g.getId(), BizVideoType.CLOSEUP.name());
            if (config == null || config.getOpenTime() == null)
                continue;
            Date pushDate = config.getOpenTime();
            if (pushDate.getHours() < 6 || pushDate.getHours() > 18)
                continue;
            if (pushDate.getHours() == hour && Math.abs(pushDate.getMinutes() - min) <= 1) {
                BoxConfig boxConfig = deviceServiceProxy.getBoxConfigByKinderGartenIdAndBizType(g.getId(), BizVideoType.CLOSEUP.name());
                final List<Camera> cameras = deviceServiceProxy.getTrustedCamerasByBoxId(boxConfig.getBoxId());
                if (cameras != null && !cameras.isEmpty()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for (Camera c : cameras) {
                                logger.info("pushVideoTimer, c:" + c);
                            }
                        }
                    }).start();
                }
            }
        }
    }

}
