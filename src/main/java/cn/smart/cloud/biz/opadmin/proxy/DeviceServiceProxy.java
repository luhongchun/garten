package cn.smart.cloud.biz.opadmin.proxy;

import cn.smart.cloud.biz.opadmin.gson.Position;
import cn.smart.cloud.biz.opadmin.gson.device.*;
import cn.smart.cloud.biz.opadmin.gson.device.box.Box;
import cn.smart.cloud.biz.opadmin.gson.device.box.Box2TrustedCamera;
import cn.smart.cloud.biz.opadmin.gson.device.box.BoxConfig;
import cn.smart.cloud.biz.opadmin.gson.report.ReportMessage;
import cn.smart.cloud.biz.opadmin.gson.report.ReportMessageCamera;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DeviceServiceProxy {

    private static final Logger logger = LoggerFactory.getLogger(DeviceServiceProxy.class);

    private static final String BASE_URL_DEVICE_BOX =
            ServiceConstant.MSVC_INSTANCE_NAME_DEVICE_SERVICE + "device/box";

    private static final String BASE_URL_DEVICE_BOX_CONFIG =
            ServiceConstant.MSVC_INSTANCE_NAME_DEVICE_SERVICE + "device/boxconfig";

    private static final String BASE_URL_DEVICE_DEVICE_TOKEN =
            ServiceConstant.MSVC_INSTANCE_NAME_DEVICE_SERVICE + "device/devicetoken";

    private static final String BASE_URL_DEVICE_CAMERA =
            ServiceConstant.MSVC_INSTANCE_NAME_DEVICE_SERVICE + "device/camera";

    private static final String BASE_URL_DEVICE_CAMERA_CONFIG =
            ServiceConstant.MSVC_INSTANCE_NAME_DEVICE_SERVICE + "device/cameraconfig";

    private static final String BASE_URL_DEVICE_BADGE =
            ServiceConstant.MSVC_INSTANCE_NAME_DEVICE_SERVICE + "device/badge";

    private static final String BASE_URL_DEVICE_BADGE_DETECTOR =
            ServiceConstant.MSVC_INSTANCE_NAME_DEVICE_SERVICE + "device/badgedetector";

    private static final String BASE_URL_DEVICE_BADGE_TRACE =
            ServiceConstant.MSVC_INSTANCE_NAME_DEVICE_SERVICE + "device/badgetrace";

    private static final String BASE_URL_DEVICE_POSITION =
            ServiceConstant.MSVC_INSTANCE_NAME_DEVICE_SERVICE + "device/position";

    private static final String BASE_URL_DEVICE_REPORT =
            ServiceConstant.MSVC_INSTANCE_NAME_DEVICE_SERVICE + "device/report";

    private static final String BASE_URL_DEVICE_COMMAND =
            ServiceConstant.MSVC_INSTANCE_NAME_DEVICE_SERVICE + "device/command";

    private static final String BASE_URL_DEVICE_MANAGE =
            ServiceConstant.MSVC_INSTANCE_NAME_DEVICE_SERVICE + "device/manage";

    @Autowired
    private SfRestTemplate restTpl;
    @Autowired
    private Gson gson;

    public DeviceToken getDeviceTokenByToken(String accessToken) {
        return restTpl.get(BASE_URL_DEVICE_DEVICE_TOKEN + "/getByAccessToken/" + accessToken, DeviceToken.class);
    }

    //============================ Box Related =======================
    public Box getBoxById(String boxId) {
        return restTpl.get(BASE_URL_DEVICE_BOX + "/get/" + boxId, Box.class);
    }

    public Box getBoxBySn(String sn) {
        return restTpl.get(BASE_URL_DEVICE_BOX + "/getBySn/" + sn, Box.class);
    }

    public List<Box> getAllBoxes() {
        return Arrays.asList(restTpl.get(BASE_URL_DEVICE_BOX + "/getAll", Box[].class));
    }

    public Box saveBox(Box box) {
        return restTpl.post(BASE_URL_DEVICE_BOX + "/save", box, Box.class);
    }

    public BoxConfig getBoxConfigByBoxId(String boxId) {
        return restTpl.get(BASE_URL_DEVICE_BOX_CONFIG + "/getByBoxId/" + boxId, BoxConfig.class);
    }

    public BoxConfig getBoxConfigByKinderGartenIdAndBizType(String kinderGartenId, String bizType) {
        return restTpl.get(BASE_URL_DEVICE_BOX_CONFIG + "/getByKinderGartenIdAndBizType/"
                + kinderGartenId + "/" + bizType, BoxConfig.class);
    }

    public BoxConfig getBoxConfigByDetectorSn(String detectorSn) {
        return restTpl.get(BASE_URL_DEVICE_BOX_CONFIG + "/getByDetectorSn/" + detectorSn, BoxConfig.class);
    }

    public BoxConfig saveBoxConfig(BoxConfig config) {
        return restTpl.post(BASE_URL_DEVICE_BOX_CONFIG + "/save", config, BoxConfig.class);
    }

    //========================== Badge Related =======================
    public Badge getBadgeById(String badgeId) {
        logger.info("getBadgeById, badgeId:" + badgeId);
        return restTpl.get(BASE_URL_DEVICE_BADGE + "/get/" + badgeId, Badge.class);
    }

    public Badge getBadgeBySn(String sn) {
        logger.info("getBadgeBySn, sn:" + sn);
        return restTpl.get(BASE_URL_DEVICE_BADGE + "/getBySn/" + sn, Badge.class);
    }

    public BadgeDetector getBadgeDetectorBySn(String sn) {
        logger.info("getBadgeDetectorsBySn, sn:" + sn);
        return restTpl.get(BASE_URL_DEVICE_BADGE_DETECTOR + "/getBySn/" + sn, BadgeDetector.class);
    }

    public List<BadgeDetector> getBadgeDetectorsByBoxId(String boxId) {
        logger.info("getBadgeDetectorsByBoxId, boxId:" + boxId);
        return Arrays.asList(restTpl.get(BASE_URL_DEVICE_BADGE_DETECTOR + "/getAllByBoxId/" + boxId,
                BadgeDetector[].class));
    }

    public Position getBadgeDetectorPositionBySn(String sn) {
        logger.info("getBadgeDetectorPositionBySn, sn:" + sn);
        return restTpl.get(BASE_URL_DEVICE_BADGE_DETECTOR + "/getDetectorPositionBySn/" + sn, Position.class);
    }

    public Badge saveBadgeTrace(BadgeTrace trace) {
        logger.info("saveBadgeTrace, trace:" + trace);
        return restTpl.post(BASE_URL_DEVICE_BADGE_TRACE + "/save/", trace, Badge.class);
    }

    //========================= Report Related =======================
    public ReportMessage getReportMessage(String boxId) {
        return restTpl.get(BASE_URL_DEVICE_REPORT + "/getReportMessage/" + boxId, ReportMessage.class);
    }

    public List<ReportMessage> getAllReportMessage() {
        return Arrays.asList(restTpl.get(BASE_URL_DEVICE_REPORT + "/getAll", ReportMessage[].class));
    }

    public ReportMessageCamera getCameraReportMessage(String cameraId) {
        return restTpl.get(BASE_URL_DEVICE_REPORT + "/getCameraReportMessage/" + cameraId,
                ReportMessageCamera.class);
    }

    public List<CameraPerson> getCameraPersonInfo(String cameraId) {
        return Arrays.asList(restTpl.get(BASE_URL_DEVICE_REPORT + "/getCameraPersonInfo/" + cameraId,
                CameraPerson[].class));
    }

    public List<ReportMessageCamera> getReportMessageCameraList() {
        return Arrays.asList(restTpl.get(BASE_URL_DEVICE_REPORT + "/getAllCameraReportMessage",
                ReportMessageCamera[].class));
    }

    public List<ReportMessage> getOfflineBoxReportMessageList() {
        return Arrays.asList(restTpl.get(BASE_URL_DEVICE_REPORT + "/getOfflineBoxReportMessageList",
                ReportMessage[].class));
    }

    //========================= Camera Related =======================
    public Camera getCameraBySn(String sn) {
        return restTpl.get(BASE_URL_DEVICE_CAMERA + "/getBySn/" + sn, Camera.class);
    }

    public Camera getCameraById(String id) {
        return restTpl.get(BASE_URL_DEVICE_CAMERA + "/get/" + id, Camera.class);
    }

    public CameraConfig getCameraConfigById(String id) {
        return restTpl.get(BASE_URL_DEVICE_CAMERA_CONFIG + "/get/" + id, CameraConfig.class);
    }

    public CameraConfig updateCameraConfig(CameraConfig config) {
        return restTpl.post(BASE_URL_DEVICE_CAMERA_CONFIG + "/update", config, CameraConfig.class);
    }

    // TODO: 2017/11/23 This method need to test online
    public Camera updateCamera(Camera camera) {
        return restTpl.post(BASE_URL_DEVICE_CAMERA + "/update", camera, Camera.class);
    }

    public Camera saveCamera(Camera c) {
        return restTpl.post(BASE_URL_DEVICE_CAMERA + "/update", c, Camera.class);
    }

    public List<Camera> getTrustedCamerasByBoxId(String boxId) {
        return Arrays.asList(restTpl.get(BASE_URL_DEVICE_CAMERA + "/getTrustedByBoxId/" + boxId, Camera[].class));
    }

    public List<Camera> getAllCamerasByBoxId(String boxId) {
        return Arrays.asList(restTpl.get(BASE_URL_DEVICE_CAMERA + "/getAllByBoxId/" + boxId, Camera[].class));
    }

    public List<Camera> getAllCamerasByKinderGartenIdAndBizType(String kinderGartenId, String bizType) {
        logger.info("getAllCamerasByKinderGartenIdAndBizType, kinderGartenId:" + kinderGartenId
                + ", bizType:" + bizType);
        return Arrays.asList(restTpl.get(BASE_URL_DEVICE_CAMERA + "/getAllByKinderGartenIdAndBizType/"
                + kinderGartenId + "/" + bizType, Camera[].class));
    }

    public Box2TrustedCamera findByBoxIdAndCameraId(String boxId, String cameraId) {
        return restTpl.get(BASE_URL_DEVICE_CAMERA + "/findByBoxIdAndCameraId/" + boxId + "/" + cameraId,
                Box2TrustedCamera.class);
    }

    public Box2TrustedCamera saveBox2TrustedCamera(Box2TrustedCamera entity) {
        return restTpl.post(BASE_URL_DEVICE_CAMERA + "/saveBox2TrustedCamera", entity, Box2TrustedCamera.class);
    }

    public void deleteBox2TrustedCamera(String id) {
        restTpl.get(BASE_URL_DEVICE_CAMERA + "/deleteBox2TrustedCamera/" + id, null);
    }

    public CameraActionTemplates getProgrammeByCameraId(String id) {
        logger.info("getProgrammeByCameraId, id:" + id);
        String result = restTpl.get(BASE_URL_DEVICE_CAMERA + "/getProgrammeByCameraId/" + id, String.class);
        return gson.fromJson(result, CameraActionTemplates.class);
    }

    public boolean batchUpdateCameras(List<Camera> changeCameraList) {
        return false;
    }

    //========================= Command Related =======================
    public PushDeviceCommand saveCommand(PushDeviceCommand command) {
        return restTpl.post(BASE_URL_DEVICE_COMMAND + "/save", command, PushDeviceCommand.class);
    }

    public Position getPositionByDetectorId(String detectorId) {
        logger.info("getPositionByDetectorId, detectorId:" + detectorId);
        return restTpl.get(BASE_URL_DEVICE_POSITION + "/getByDetectorId/" + detectorId, Position.class);
    }

    public Position getPositionByCameraId(String cameraId) {
        logger.info("getPositionByCameraId, cameraId:" + cameraId);
        return restTpl.get(BASE_URL_DEVICE_POSITION + "/getByCameraId/" + cameraId, Position.class);
    }

    public boolean rebootCamera(String cameraId) {
        return restTpl.get(BASE_URL_DEVICE_MANAGE + "/rebootCamera/" + cameraId, Boolean.class);
    }

    public boolean rebootBox(String boxSn) {
        return restTpl.get(BASE_URL_DEVICE_MANAGE + "/rebootBox/" + boxSn, Boolean.class);
    }

    public boolean rebootWeCare(String boxSn) {
        return restTpl.get(BASE_URL_DEVICE_MANAGE + "/rebootWeCare/" + boxSn, Boolean.class);
    }

    public boolean refreshBox(String boxSn) {
        return restTpl.get(BASE_URL_DEVICE_MANAGE + "/refreshBox/" + boxSn, Boolean.class);
    }

    public boolean rescanCamera(String boxSn) {
        return restTpl.get(BASE_URL_DEVICE_MANAGE + "/rescanCamera/" + boxSn, Boolean.class);
    }

    public boolean adjustVolume(String boxSn, int involume) {
        return restTpl.get(BASE_URL_DEVICE_MANAGE + "/adjustVolume/" + boxSn + "/" + involume, Boolean.class);
    }

    public boolean adjustVideoParams(String boxSn, VideoParams params) {
        return restTpl.post(BASE_URL_DEVICE_MANAGE + "/adjustVideoParams/" + boxSn, params, Boolean.class);
    }
}