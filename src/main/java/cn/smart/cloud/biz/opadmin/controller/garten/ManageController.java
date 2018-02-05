package cn.smart.cloud.biz.opadmin.controller.garten;

import cn.smart.cloud.biz.opadmin.constant.Constant;
import cn.smart.cloud.biz.opadmin.constant.WxConstant;
import cn.smart.cloud.biz.opadmin.entity.garten.Garten;
import cn.smart.cloud.biz.opadmin.entity.garten.GartenClass;
import cn.smart.cloud.biz.opadmin.gson.BizVideoType;
import cn.smart.cloud.biz.opadmin.gson.device.Camera;
import cn.smart.cloud.biz.opadmin.proxy.DeviceServiceProxy;
import cn.smart.cloud.biz.opadmin.proxy.GartenServiceProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "/garten/manage")
public class ManageController {

    private static final Logger logger = LoggerFactory.getLogger(ManageController.class);

    @Autowired
    DeviceServiceProxy deviceServiceProxy;
    @Autowired
    GartenServiceProxy gartenServiceProxy;

    @RequestMapping(value = "action_class_account", method = RequestMethod.POST)
    public String accountClass(HttpServletRequest request) {
        String gartenId = request.getParameter("gartenId");
        logger.info("accountClass, gartenId: " + gartenId);
        if (StringUtils.isEmpty(gartenId)) {
            logger.info("accountClass, param invalid.");
            request.setAttribute("msg", "param invalid.");
            return "common/common_msg";
        }
        Garten garten = gartenServiceProxy.get(gartenId);
        if (garten == null) {
            request.setAttribute(Constant.GARTEN_ID, gartenId);
            logger.info("accountClass, no garten.");
            request.setAttribute("msg", "no garten.");
            return "common/common_msg";
        }

        List<GartenClass> classList = gartenServiceProxy.getAllClassesByGartenId(gartenId);
        request.setAttribute("classList", classList);
        request.setAttribute(WxConstant.WX_OPEN_ID, "");
        return "video/account_class_list";
    }

    @RequestMapping(value = "action_camera_setting", method = RequestMethod.POST)
    public String cameraSetting(HttpServletRequest request) {
        String gartenId = request.getParameter("gartenId");
        logger.info("cameraSetting, gartenId: " + gartenId);
        if (StringUtils.isEmpty(gartenId)) {
            logger.info("accountClass, param invalid.");
            request.setAttribute("msg", "param invalid.");
            return "common/common_msg";
        }
        Garten garten = gartenServiceProxy.get(gartenId);
        if (garten == null) {
            request.setAttribute(Constant.GARTEN_ID, gartenId);
            logger.info("cameraSetting, no garten.");
            request.setAttribute("msg", "no garten.");
            return "common/common_msg";
        }

        List<Camera> cameras = deviceServiceProxy.getAllCamerasByKinderGartenIdAndBizType(gartenId, BizVideoType.LIVE.name());
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
        request.setAttribute("gartenId", gartenId);
        return "video/camera_manage_list";
    }
}
