package cn.smart.cloud.biz.opadmin.controller.device;

import cn.smart.cloud.biz.opadmin.controller.MessageRender;
import cn.smart.cloud.biz.opadmin.entity.garten.Garten;
import cn.smart.cloud.biz.opadmin.gson.BizVideoType;
import cn.smart.cloud.biz.opadmin.gson.device.Camera;
import cn.smart.cloud.biz.opadmin.gson.device.CameraConfig;
import cn.smart.cloud.biz.opadmin.gson.device.CameraPerson;
import cn.smart.cloud.biz.opadmin.gson.device.CameraPersonInfo;
import cn.smart.cloud.biz.opadmin.gson.device.box.Box;
import cn.smart.cloud.biz.opadmin.gson.device.box.BoxConfig;
import cn.smart.cloud.biz.opadmin.gson.report.ReportMessage;
import cn.smart.cloud.biz.opadmin.gson.report.ReportMessageCamera;
import cn.smart.cloud.biz.opadmin.proxy.DeviceServiceProxy;
import cn.smart.cloud.biz.opadmin.proxy.GartenServiceProxy;
import cn.smart.cloud.biz.opadmin.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping(value = "/device/manage")
public class DeviceStatusController extends MessageRender {

    private Logger logger = LoggerFactory.getLogger(DeviceStatusController.class);

    @Autowired
    private DeviceServiceProxy deviceServiceProxy;
    @Autowired
    private GartenServiceProxy gartenServiceProxy;

    @RequestMapping(value = "box_status_list")
    public String boxStatusList(HttpServletRequest request) {
        final List<ReportMessage> reportMessageList = deviceServiceProxy.getAllReportMessage();

        request.setAttribute("reportMessageList", reportMessageList);
        return "device/box_status_list";
    }

    @RequestMapping(value = "box_status/{boxId}")
    public String boxStatus(@PathVariable String boxId, HttpServletRequest request) {
        final ReportMessage reportMessage = deviceServiceProxy.getReportMessage(boxId);
        request.setAttribute("boxId", boxId);
        request.setAttribute("reportMessage", reportMessage);
        logger.info("boxStatus:" + reportMessage);
        return "device/box_status";
    }

    @RequestMapping(value = "box_add")
    public String boxAdd(HttpServletRequest request, HttpServletResponse response) {
        List<Garten> gartenList = gartenServiceProxy.getAll();
        if (gartenList == null || gartenList.size() == 0) {
            renderText(response,"没有找到可关联的幼儿园，无法添加盒子SN");
            return "device/device_status_index";
        }
        String[] bizTypes = new String[] {
                BizVideoType.LIVE.name(),
                BizVideoType.CLOSEUP.name(),
                BizVideoType.ATTEND.name(),
                BizVideoType.RECORD_EDITION.name(),
                BizVideoType.WONDERFUL_EDITION.name()
        };
        request.setAttribute("gartenList", gartenList);
        request.setAttribute("bizTypes", bizTypes);
        return "device/box_add";
    }

    @RequestMapping(value = "box_write_sn_to_db")
    public String boxWriteSn2DB(HttpServletResponse response,
                                @RequestParam String gartenId,
                                @RequestParam String bizType,
                                @RequestParam String snList) {
        logger.info("Add box GartenId: " + gartenId);
        logger.info("Add box bizType: " + bizType);
        logger.info("Add box sn to DB: " + snList);
        String[] boxSNs = snList.split(";");
        boolean result = false;
        for (String boxSN : boxSNs) {
            Box box = new Box();
            box.setSn(boxSN);
            box.setDescription("Add by opadmin");
            box.setName("box_" + boxSN.substring(0, 6));
            box = deviceServiceProxy.saveBox(box);
            if (box == null) {
                result = false;
                break;
            }
            BoxConfig boxConfig = new BoxConfig();
            boxConfig.setBoxId(box.getId());
            boxConfig.setKinderGartenId(gartenId);
            boxConfig.setBizType(bizType);
            result = deviceServiceProxy.saveBoxConfig(boxConfig) != null;
        }
        renderText(response, result ? "添加成功！SN: " + snList : "添加失败 !");
        return "device/box_add";
    }

    @RequestMapping(value = "device_status_index")
    public String device_status_index() {
        return "device/device_status_index";
    }

    @RequestMapping(value = "camera_setup_edit/{cameraId}")
    public String cameraSetupEdit(@PathVariable String cameraId, HttpServletRequest request) {
        final Camera camera;
        camera = deviceServiceProxy.getCameraById(cameraId);
        if (camera != null) {
            camera.setTrusted(deviceServiceProxy.findByBoxIdAndCameraId(camera.getBoxId(), cameraId) != null);
            request.setAttribute("camera", camera);
        }
        return "device/camera_setup";
    }

    @RequestMapping(value = "camera_status/{cameraId}")
    public String cameraStatus(@PathVariable String cameraId, HttpServletRequest request) {
        final ReportMessageCamera cameraMessage = deviceServiceProxy.getCameraReportMessage(cameraId);
        String ip = null;
        if (cameraMessage != null) {
            ip = cameraMessage.getIp();
        }
        Camera camera = deviceServiceProxy.getCameraById(cameraId);
        if (camera != null && ip != null) {
            camera.setCameraIp(ip);
            deviceServiceProxy.updateCamera(camera);
        }
        request.setAttribute("cameraId", cameraId);
        request.setAttribute("reportMessage", cameraMessage);
        return "device/camera_status";
    }

    @RequestMapping(value = "camera_person/{cameraId}")
    public String cameraPerson(@PathVariable String cameraId, HttpServletRequest request) {
        List<CameraPerson> cameraPersonList = deviceServiceProxy.getCameraPersonInfo(cameraId);

        if (cameraPersonList != null && cameraPersonList.size() > 0) {
            String cameraName = cameraPersonList.get(0).getCameraId();

            List<CameraPersonInfo> cameraPersonInfos = new ArrayList<>();
            CameraPersonInfo cameraPersonInfo = new CameraPersonInfo();
            cameraPersonInfo.setRecordDate(cameraPersonList.get(0).getRecordDate());
            cameraPersonInfo.setCameraPersons(new ArrayList<>());
            cameraPersonInfos.add(cameraPersonInfo);

            for (CameraPerson cameraPerson : cameraPersonList) {
                if (cameraPerson.getRecordDate().compareTo(cameraPersonInfo.getRecordDate()) == 0) {
                    cameraPersonInfo.getCameraPersons().add(cameraPerson);
                } else {
                    cameraPersonInfo = new CameraPersonInfo();
                    cameraPersonInfo.setRecordDate(cameraPerson.getRecordDate());
                    cameraPersonInfo.setCameraPersons(new ArrayList<>());
                    cameraPersonInfo.getCameraPersons().add(cameraPerson);
                    cameraPersonInfos.add(cameraPersonInfo);
                }
            }
            request.setAttribute("cameraId", cameraId);
            request.setAttribute("cameraName", cameraName);
            request.setAttribute("cameraPersonInfos", cameraPersonInfos);
        }

        return "device/camera_person";
    }

    @RequestMapping(value = "camera_searching")
    public String cameraSearching(HttpServletRequest request) {
        logger.info("cameraSearching");
        final String cameraSn = request.getParameter("cameraSn");
        String cameraId = null;
        ReportMessageCamera deviceReportMessage = null;
        Camera camera = deviceServiceProxy.getCameraBySn(cameraSn);
        if (camera != null) {
            cameraId = camera.getId();
            deviceReportMessage = deviceServiceProxy.getCameraReportMessage(camera.getId());
        }
        request.setAttribute("cameraId", cameraId);
        request.setAttribute("reportMessage", deviceReportMessage);
        return "device/camera_status";
    }

    @RequestMapping(value = "box_searching")
    public String boxSearching(HttpServletRequest request) {
        logger.info("boxSearching");
        final String boxSn = request.getParameter("boxSn");
        String boxId = null;
        ReportMessage reportMessage = null;
        Box box = deviceServiceProxy.getBoxBySn(boxSn);
        if (box != null) {
            boxId = box.getId();
            reportMessage = deviceServiceProxy.getReportMessage(boxId);
        }
        request.setAttribute("boxId", boxId);
        request.setAttribute("reportMessage", reportMessage);
        return "device/box_status";
    }

    @RequestMapping(value = "camera_set_name")
    public String cameraSetName(HttpServletRequest request) {
        logger.info("cameraSetName");
        final String cameraId = request.getParameter("cameraId");

        request.setAttribute("cameraId", cameraId);
        return "device/camera_set_name";
    }

    @RequestMapping(value = "camera_status_list")
    public String cameraStatusList(HttpServletRequest request) {
        final List<ReportMessageCamera> cameraReportMessageList = deviceServiceProxy.getReportMessageCameraList();

        request.setAttribute("reportMessageList", cameraReportMessageList);
        return "device/camera_status_list";
    }

    @RequestMapping(value = "box_info_edit")
    public String boxInfoEdit(HttpServletRequest request) {
        final String boxId = request.getParameter("boxId");
        final Box box = deviceServiceProxy.getBoxById(boxId);
        logger.info("boxInfoEdit, box:" + box);
        if (box != null) {
            request.setAttribute("box", box);
            BoxConfig boxConfig = deviceServiceProxy.getBoxConfigByBoxId(boxId);
            logger.info("boxInfoEdit, boxConfig:" + boxConfig);
            request.setAttribute("boxConfig", boxConfig);
            List<Camera> cameraList = deviceServiceProxy.getAllCamerasByBoxId(boxId);
            if (!cameraList.isEmpty()) {
                Camera c = cameraList.get(0);
                CameraConfig config = deviceServiceProxy.getCameraConfigById(c.getId());
                logger.info("boxInfoEdit, cConfig:" + config);
                if (config != null)
                    request.setAttribute("cConfig", config);
            }
            /*String globalId = "";
            Maintainer maintainer = maintenanceService.getByBoxId(boxId);
			if (maintainer != null && maintainer.getGlobalUser() != null) {
				globalId = maintainer.getGlobalUser().getGlobalId();
			}
			request.setAttribute("maintainerGlobalId", globalId);*/
            request.setAttribute("maintainerGlobalId", boxConfig.getKinderGartenId());
        }
        return "device/box_info_edit";
    }

    @RequestMapping(value = "adjust_camera_config")
    public String adjustIpcConfig(HttpServletRequest request) {
        final String boxId = request.getParameter("boxId");
        final String inVolume = request.getParameter("inVolume");
        final String bps = request.getParameter("bps");
        final String fps = request.getParameter("fps");
        final String gop = request.getParameter("gop");
        final String cbrmodeStr = request.getParameter("cbrmode");
        final String imagegrade = request.getParameter("imagegrade");
        List<Camera> cameraList = deviceServiceProxy.getAllCamerasByBoxId(boxId);
        for (Camera c : cameraList) {
            CameraConfig config = deviceServiceProxy.getCameraConfigById(c.getId());
            if (config == null)
                config = getNewConfig(c.getId());
            config.setInvolume(Integer.parseInt(inVolume));
            config.setBps(Integer.parseInt(bps));
            config.setFps(Integer.parseInt(fps));
            config.setGop(Integer.parseInt(gop));
            boolean cbrmode = true;
            if (cbrmodeStr != null && cbrmodeStr.equals("0")) {
                cbrmode = false;
            }
            config.setCbrmode(cbrmode);
            config.setImagegrade(Integer.parseInt(imagegrade));
            deviceServiceProxy.updateCameraConfig(config);
        }
        return "device/box_info_edit";
    }

    private CameraConfig getNewConfig(String cameraId) {
        CameraConfig config = new CameraConfig();
        config.setCameraId(cameraId);
        config.setInvolume(31);
        config.setOutvolume(1);
        config.setImagegrade(1);
        config.setBps(96);
        config.setFps(16);
        config.setGop(16);
        config.setCbrmode(true);
        return config;
    }
}