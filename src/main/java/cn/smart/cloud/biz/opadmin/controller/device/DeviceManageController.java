package cn.smart.cloud.biz.opadmin.controller.device;

import cn.smart.cloud.biz.opadmin.constant.SmartPushConstant;
import cn.smart.cloud.biz.opadmin.controller.MessageRender;
import cn.smart.cloud.biz.opadmin.entity.garten.GartenClassConfig;
import cn.smart.cloud.biz.opadmin.gson.device.*;
import cn.smart.cloud.biz.opadmin.gson.device.box.Box;
import cn.smart.cloud.biz.opadmin.gson.device.box.Box2TrustedCamera;
import cn.smart.cloud.biz.opadmin.gson.device.box.BoxConfig;
import cn.smart.cloud.biz.opadmin.gson.device.timer.SfTimeConfig;
import cn.smart.cloud.biz.opadmin.gson.device.timer.SfTimerParser;
import cn.smart.cloud.biz.opadmin.gson.report.ReportMessage;
import cn.smart.cloud.biz.opadmin.proxy.DeviceServiceProxy;
import cn.smart.cloud.biz.opadmin.proxy.GartenServiceProxy;
import cn.smart.cloud.biz.opadmin.proxy.WxServiceProxy;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping(value = "/device/manage")
public class DeviceManageController extends MessageRender {

    private Logger logger = LoggerFactory.getLogger(DeviceManageController.class);

    @Autowired
    private DeviceServiceProxy deviceServiceProxy;
    @Autowired
    private WxServiceProxy wxService;
    @Autowired
    private GartenServiceProxy gartenServiceProxy;

    @Value("${service.biz.wan.url.base}")
    String serverUrl;

    @RequestMapping(value = "has_device_offline")
    public void hasDeviceOffline() {
        final List<ReportMessage> reportMessageList = deviceServiceProxy.getOfflineBoxReportMessageList();

        boolean hasBoxAlarm = false;
        if (reportMessageList != null) {
            for (ReportMessage reportMessage : reportMessageList) {
                Box box = deviceServiceProxy.getBoxById(reportMessage.getBoxMessage().getBoxId());
                if (box != null && deviceServiceProxy.getBoxConfigByBoxId(box.getId()).isAlarmEnabled()) {
                    hasBoxAlarm = true;
                    break;
                }
            }
        }
        if (hasBoxAlarm) {
            wxService.sendDeviceFaultMessageByTemplate(String.format("%s/device/manage/box_status_list", serverUrl));
        }
    }

    @RequestMapping(value = "record_open")
    public void recordOpen() {
        logger.info("recordOpen");
        List<Box> boxList = deviceServiceProxy.getAllBoxes();
        for (Box b : boxList) {
            if (b == null)
                continue;
            BoxConfig boxConfig = deviceServiceProxy.getBoxConfigByBoxId(b.getId());
            if (boxConfig == null) {
                boxConfig = new BoxConfig();
                boxConfig.setBoxId(b.getId());
            }
            boxConfig.setRecordEnabled(true);
            deviceServiceProxy.saveBoxConfig(boxConfig);
        }
    }

    @RequestMapping(value = "record_close")
    public void recordClose() {
        logger.info("recordClose");
        List<Box> boxList = deviceServiceProxy.getAllBoxes();
        for (Box b : boxList) {
            if (b == null)
                continue;
            BoxConfig boxConfig = deviceServiceProxy.getBoxConfigByBoxId(b.getId());
            if (boxConfig == null) {
                boxConfig = new BoxConfig();
                boxConfig.setBoxId(b.getId());
            }
            boxConfig.setRecordEnabled(false);
            deviceServiceProxy.saveBoxConfig(boxConfig);
        }
    }

    @RequestMapping(value = "camera_setup")
    public void cameraSetup(HttpServletRequest request,
                            HttpServletResponse response) {
        final String cameraId = request.getParameter("cameraId");
        final String cameraName = request.getParameter("cameraName");
        final String trustedEnabledStr = request.getParameter("trustedEnabled");
        final String fixedEnabledStr = request.getParameter("fixedEnabled");
        final String sortedStr = request.getParameter("cameraSorted");
        boolean trustedEnabled = true;
        boolean fixedEnabled = true;
        int sorted = 0;
        if (trustedEnabledStr != null && trustedEnabledStr.equals("0")) {
            trustedEnabled = false;
        }
        if (fixedEnabledStr != null && fixedEnabledStr.equals("0")) {
            fixedEnabled = false;
        }
        if (sortedStr != null) {
            try {
                sorted = Integer.parseInt(sortedStr);
            } catch (NumberFormatException e) {
                renderText(response, FAIL);
            }
        }
        Camera camera = deviceServiceProxy.getCameraById(cameraId);
        if (camera != null) {
            camera.setName(cameraName);
            camera.setTrusted(trustedEnabled);
            camera.setFixed(fixedEnabled);
            if (sorted != camera.getSorted()) {
                boolean invalid = false;
                List<Camera> cameraList = deviceServiceProxy.getAllCamerasByBoxId(camera.getBoxId());
                for (Camera mCamera : cameraList) {
                    if (mCamera.getSorted() == sorted && (!mCamera.getId().equals(camera.getId()))) {
                        invalid = true;
                        break;
                    }
                }
                if (!invalid) {
                    camera.setSorted(sorted);
                }
            }
            deviceServiceProxy.updateCamera(camera);

            if (trustedEnabled) {
                Box2TrustedCamera box2TrustedCamera = new Box2TrustedCamera();
                box2TrustedCamera.setBoxId(camera.getBoxId());
                box2TrustedCamera.setCameraId(camera.getId());
                deviceServiceProxy.saveBox2TrustedCamera(box2TrustedCamera);
            } else {
                Box2TrustedCamera box2TrustedCamera = deviceServiceProxy
                        .findByBoxIdAndCameraId(camera.getBoxId(), cameraId);
                if (box2TrustedCamera != null) {
                    deviceServiceProxy.deleteBox2TrustedCamera(box2TrustedCamera.getId());
                }
            }
        }
        renderText(response, SUCCESS);
    }

    @RequestMapping(value = "camera_set_name_commit")
    public void cameraSetNameCommit(HttpServletRequest request,
                                    HttpServletResponse response) {
        logger.info("cameraSetNameCommit");
        final String cameraId = request.getParameter("cameraId");
        final String cameraName = request.getParameter("cameraName");
        logger.info("cameraSetNameCommit " + cameraId + ", " + cameraName);
        final Camera camera = deviceServiceProxy.getCameraById(cameraId);
        camera.setName(cameraName);
        deviceServiceProxy.updateCamera(camera);
        renderText(response, SUCCESS);
    }

    @RequestMapping(value = "box_info_edit_submit")
    public void boxInfoEditSubmit(HttpServletRequest request,
                                  HttpServletResponse response) {
        final String boxId = request.getParameter("boxId");
        final String boxName = request.getParameter("name");
        final String description = request.getParameter("description");
        //final String maintainerGlobalId = request.getParameter("globalId");
        final String alarmEnabledStr = request.getParameter("alarmEnabled");
        final String trustedListEnabledStr = request.getParameter("trustedListEnabled");
        boolean alarmEnabled = true;
        if (alarmEnabledStr != null && alarmEnabledStr.equals("0")) {
            alarmEnabled = false;
        }
        boolean trustedListEnabled = true;
        if (trustedListEnabledStr != null && trustedListEnabledStr.equals("0")) {
            trustedListEnabled = false;
        }
        BoxConfig boxConfig = deviceServiceProxy.getBoxConfigByBoxId(boxId);
        boxConfig.setAlarmEnabled(alarmEnabled);
        boxConfig.setTrustedListEnabled(trustedListEnabled);
        deviceServiceProxy.saveBoxConfig(boxConfig);

        Box box = deviceServiceProxy.getBoxById(boxId);
        if (box != null) {
            box.setName(boxName);
            box.setDescription(description);
            deviceServiceProxy.saveBox(box);
        }
        renderText(response, SUCCESS);
    }

    @RequestMapping(value = "restart_camera")
    public void restartCamera(HttpServletRequest request,
                              HttpServletResponse response) {
        String cameraSn = request.getParameter("cameraSn");
        logger.info("restart_camera cameraSn:" + cameraSn);
        DeviceCommandResponseBean result = new DeviceCommandResponseBean();
        result.setSuccess(deviceServiceProxy.rebootCamera(deviceServiceProxy.getCameraBySn(cameraSn).getId()));
        renderJson(response, result);
    }

    @RequestMapping(value = "restart_box")
    public void restartBox(HttpServletRequest request,
                           HttpServletResponse response) {
        String boxSn = request.getParameter("boxSn");
        logger.info("restartBox boxSn:" + boxSn);
        DeviceCommandResponseBean result = new DeviceCommandResponseBean();
        result.setSuccess(deviceServiceProxy.rebootBox(boxSn));
        renderJson(response, result);
    }

    @RequestMapping(value = "reboot_wecare")
    public void rebootWeCare(HttpServletRequest request,
                             HttpServletResponse response) {
        String boxSn = request.getParameter("boxSn");
        logger.info("rebootWeCare boxSn:" + boxSn);
        DeviceCommandResponseBean result = new DeviceCommandResponseBean();
        result.setSuccess(deviceServiceProxy.rebootWeCare(boxSn));
        renderJson(response, result);
    }

    @RequestMapping(value = "refresh_box_config")
    public void refreshBoxConfig(HttpServletRequest request,
                                 HttpServletResponse response) {
        String boxSn = request.getParameter("boxSn");
        logger.info("refresh_box_config boxSn:" + boxSn);
        DeviceCommandResponseBean result = new DeviceCommandResponseBean();
        result.setSuccess(deviceServiceProxy.refreshBox(boxSn));
        renderJson(response, result);
    }

    @RequestMapping(value = "rescan_camera")
    public void rescanCamera(HttpServletRequest request,
                             HttpServletResponse response) {
        String boxSn = request.getParameter("boxSn");
        logger.info("rescan_camera boxSn:" + boxSn);
        DeviceCommandResponseBean result = new DeviceCommandResponseBean();
        result.setSuccess(deviceServiceProxy.rescanCamera(boxSn));
        renderJson(response, result);
    }

    @RequestMapping(value = "adjust_volume")
    public void adjustCameraVolume(HttpServletRequest request,
                                   HttpServletResponse response) {
        String boxSn = request.getParameter("boxSn");
        List<Camera> cameraList = deviceServiceProxy
                .getAllCamerasByBoxId(deviceServiceProxy.getBoxBySn(boxSn).getId());
        if (!cameraList.isEmpty()) {
            Camera c = cameraList.get(0);
            CameraConfig config = deviceServiceProxy.getCameraConfigById(c.getId());
            if (config != null) {
                DeviceCommandResponseBean result = new DeviceCommandResponseBean();
                result.setSuccess(deviceServiceProxy.adjustVolume(boxSn, config.getInvolume()));
                renderJson(response, result);
            }
        }
    }

    @RequestMapping(value = "adjust_video_params")
    public void adjustCameraVideoParams(HttpServletRequest request,
                                        HttpServletResponse response) {
        String boxSn = request.getParameter("boxSn");
        List<Camera> cameraList = deviceServiceProxy.getAllCamerasByBoxId(
                deviceServiceProxy.getBoxBySn(boxSn).getId());
        if (!cameraList.isEmpty()) {
            Camera c = cameraList.get(0);
            CameraConfig config = deviceServiceProxy.getCameraConfigById(c.getId());
            if (config != null) {
                DeviceCommandResponseBean result = new DeviceCommandResponseBean();
                VideoParams params = new VideoParams();
                params.setBps(config.getBps());
                params.setFps(config.getFps());
                params.setGop(config.getGop());
                params.setCbrmode(config.isCbrmode());
                params.setImagegrade(config.getImagegrade());
                result.setSuccess(deviceServiceProxy.adjustVideoParams(boxSn, params));
                renderJson(response, result);
            }
        }
    }

    @RequestMapping(value = "gen_reboot_app_cmd")
    public void genRebootAppCmd(HttpServletRequest request,
                                HttpServletResponse response) {
        String boxSn = request.getParameter("boxSn");
        PushDeviceCommand command = new PushDeviceCommand();
        command.setDeviceType(SmartPushConstant.CommandDeviceType.APP);
        command.setDeviceId(boxSn);//TODO add package name of app to restart different app
        command.setActionType(SmartPushConstant.CommandActionType.OPERATE);
        command.setAction(SmartPushConstant.CommandOperateAction.RESTART);
        deviceServiceProxy.saveCommand(command);
        logger.info("genRebootAppCmd command: " + new Gson().toJson(command));
        BoxConfig config = deviceServiceProxy.getBoxConfigByBoxId(deviceServiceProxy.getBoxBySn(boxSn).getId());
        config.setCommandId(command.getId());
        deviceServiceProxy.saveBoxConfig(config);
        renderText(response, SUCCESS);
    }

    @RequestMapping(value = "gen_reboot_box_cmd")
    public void genRebootBoxCmd(HttpServletRequest request,
                                HttpServletResponse response) {
        String boxSn = request.getParameter("boxSn");
        PushDeviceCommand command = new PushDeviceCommand();
        command.setDeviceType(SmartPushConstant.CommandDeviceType.BOX);
        command.setDeviceId(boxSn);
        command.setActionType(SmartPushConstant.CommandActionType.OPERATE);
        command.setAction(SmartPushConstant.CommandOperateAction.REBOOT);
        deviceServiceProxy.saveCommand(command);
        logger.info("genRebootBoxCmd command: " + new Gson().toJson(command));
        BoxConfig config = deviceServiceProxy.getBoxConfigByBoxId(deviceServiceProxy.getBoxBySn(boxSn).getId());
        config.setCommandId(command.getId());
        deviceServiceProxy.saveBoxConfig(config);
        renderText(response, SUCCESS);
    }

    @RequestMapping(value = "switchCamera")
    public void switchCamera() {
        logger.info("switchCamera");
        Calendar cal = Calendar.getInstance();
        final int day = cal.get(Calendar.DAY_OF_WEEK);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);
        logger.info("现在时间:" + hour + "点" + min + "分, 本周第" + day + "天");
        final int now = hour * 100 + min;
        try {
            List<Box> boxList = deviceServiceProxy.getAllBoxes();
            if (boxList == null || boxList.isEmpty()) {
                return;
            }
            logger.info("switchCamera, all box count:" + boxList.size());
            for (Box box : boxList) {
                final String boxId = box.getId();
                final List<Camera> cameraList = deviceServiceProxy.getTrustedCamerasByBoxId(boxId);
                if (cameraList == null || cameraList.isEmpty())
                    continue;
                logger.info("switchCamera, boxId:" + boxId + ", cameras:" + cameraList.size());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (Camera camera : cameraList) {
                            switchCamera(camera, day, now);
                        }
                    }
                }).start();
            }
        } catch (Exception e) {
            logger.error("switchCamera exception : " + e);
            e.printStackTrace();
        } finally {
        }
        logger.info("switchCamera finished.");
    }

    private void switchCamera(Camera camera, int day, int now) {
        GartenClassConfig gcc = gartenServiceProxy.getGartenClassConfigByCameraId(camera.getId());
        String liveTime = gcc == null ? "" : gcc.getVideoLiveTime();
        if (!StringUtils.isEmpty(liveTime)) {
            List<SfTimeConfig> tcList = SfTimerParser.parserTimeConfig(liveTime);
            for (SfTimeConfig tc : tcList) {
                if (tc.getDay() == day) {
                    if (tc.getStart() == now) {
                        logger.info("switchCamera open c:" + camera);
                        camera.setClose(false);
                        deviceServiceProxy.updateCamera(camera);
                        break;
                    } else if (tc.getEnd() == now) {
                        logger.info("switchCamera close c:" + camera);
                        camera.setClose(true);
                        deviceServiceProxy.updateCamera(camera);
                        break;
                    }
                }
            }
        }
    }
}

