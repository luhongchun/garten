package cn.smart.cloud.biz.opadmin.controller.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class ManagementController {

    private Logger logger = LoggerFactory.getLogger(ManagementController.class);

    private final static String PAGE_LOGIN = "login";
    private final static String PAGE_LOGIN_GARTEN = "manage/garten/login_for_garten";

    @RequestMapping("login")
    public String login(HttpServletRequest request,
                        HttpServletResponse response,
                        HttpSession session) {
        logger.info("login");
        return PAGE_LOGIN;
    }

    @RequestMapping("opadmin/login")
    public String loginGarten(HttpServletRequest request,
                              HttpServletResponse response,
                              HttpSession session) {
        logger.info("loginGarten");
        return PAGE_LOGIN_GARTEN;
    }


    @RequestMapping("version")
    public String version(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpSession session) {
        return "manage/version";
    }
}