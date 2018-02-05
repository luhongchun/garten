package cn.smart.cloud.biz.opadmin.controller.garten.video;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/biz/video/manage")
public class VideoManageController {

    private static final Logger logger = LoggerFactory.getLogger(VideoManageController.class);

    @RequestMapping(value = "/")
    public String videoManage(HttpServletRequest request, HttpServletResponse response) {
        logger.info("videoManage");
        return "video/manage";
    }

}