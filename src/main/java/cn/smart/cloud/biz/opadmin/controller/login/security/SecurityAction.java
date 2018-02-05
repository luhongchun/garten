package cn.smart.cloud.biz.opadmin.controller.login.security;

import cn.smart.cloud.biz.opadmin.entity.UserSession;
import cn.smart.cloud.biz.opadmin.entity.user.SysUser;
import cn.smart.cloud.biz.opadmin.proxy.BaseCoreServiceProxy;
import cn.smart.cloud.biz.opadmin.proxy.GartenServiceProxy;
import cn.smart.cloud.biz.opadmin.util.Md5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/security/")
public class SecurityAction {

    private Logger logger = LoggerFactory.getLogger(SecurityAction.class);
    private final static String PAGE_MANAGE_INDEX = "manage/manage_index";
    private final static String PAGE_LOGIN = "login";
    private final static String PAGE_TIP = "common/common_tip";
    private static final String LOGIN_IDENTITY = "login_identity";
    private static final String PASSWORD = "password";

    @Autowired
    private BaseCoreServiceProxy baseServiceProxy;
    @Autowired
    private GartenServiceProxy gartenServiceProxy;

    @RequestMapping("login")
    public String login(HttpServletRequest request,
                        HttpServletResponse response,
                        HttpSession session) {
        boolean is_valid = false;
        String loginIdentity = request.getParameter(LOGIN_IDENTITY);
        logger.info("login, loginIdentity:" + loginIdentity);
        String password = request.getParameter(PASSWORD);
        logger.info("login, password:" + password);
        SysUser user = baseServiceProxy.getUserByLoginId(loginIdentity);
        logger.info("login, user1:" + user);
        if ((user != null && user.getPassword().equals(Md5Util.md5Encode(password)))) {
            is_valid = true;
        }
        logger.info("login, is_valid:" + is_valid);
        if (is_valid) {
            /*UserSession userSession = new UserSession(user, new Date());
            session.setAttribute(UserSession.USER_SESSION, userSession);
			
			request.setAttribute("user", user);*/
            // TODO: 2017/11/23 Need to merge this with loginManager method
            String managerId = gartenServiceProxy.getGartenManagerId("admin");
            request.setAttribute("managerId", managerId);
            return PAGE_MANAGE_INDEX;
        } else {
            String warn = "登录信息错误！";
            logger.warn(warn);
            request.setAttribute("msg", warn);
            return PAGE_TIP;
        }
    }

    @RequestMapping("loginManager")
    public String loginManager(HttpServletRequest request,
                               HttpServletResponse response,
                               HttpSession session) {
        String loginIdentity = request.getParameter(LOGIN_IDENTITY);
        logger.info("loginManager, loginIdentity:" + loginIdentity);
        String password = request.getParameter(PASSWORD);
        logger.info("loginManager, password:" + password);
  //      boolean is_valid = gartenServiceProxy.verifyGartenManager(loginIdentity, Md5Util.md5Encode(password));
        boolean is_valid = false;
		logger.info("loginManager, is_valid:" + is_valid);
        if (is_valid) {
            //session.setAttribute(UserSession.USER_SESSION, loginIdentity);

            //request.setAttribute("manager", manager);
            //return PAGE_GARTEN_INDEX;
            //request.setAttribute("managerId", manager.getId());
            //return "redirect:/garten/manage/garten/listSome/" + manager.getId();
            return "redirect:/garten/manage/garten/index/" + gartenServiceProxy.getGartenManagerId(loginIdentity);

			/*HttpClient client = new HttpClient(response);
			client.setParameter("managerId", manager.getId());
			try {
				client.sendByPost("/garten/manage/garten/list");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "";*/
        } else {
            String warn = "登录信息错误！";
            logger.warn(warn);
            request.setAttribute("msg", warn);
            return PAGE_TIP;
        }
    }

    @RequestMapping("logout")
    public String logout(HttpServletRequest request,
                         HttpSession session,
                         HttpServletResponse response) {
        logger.info("user logout");
        session.removeAttribute(UserSession.USER_SESSION);
        return PAGE_LOGIN;
    }
}