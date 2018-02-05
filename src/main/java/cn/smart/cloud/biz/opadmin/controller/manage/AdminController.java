package cn.smart.cloud.biz.opadmin.controller.manage;

import cn.smart.cloud.biz.opadmin.entity.GartenManager;
import cn.smart.cloud.biz.opadmin.entity.user.SysUser;
import cn.smart.cloud.biz.opadmin.proxy.BaseCoreServiceProxy;
import cn.smart.cloud.biz.opadmin.util.Md5Util;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    Logger logger = Logger.getLogger(AdminController.class);

    @Autowired
    BaseCoreServiceProxy baseCoreServiceProxy;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String loginPost(HttpServletRequest request, HttpSession session) {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        logger.info("userName: " + userName + ", password: " + password);
   //   SysUser user = baseCoreServiceProxy.getUserByLoginId(userName);
        GartenManager user =  baseCoreServiceProxy.verifyGartenManager(userName,password);
        logger.info("GartenManager,user:"+user);
        if ((user != null && user.getPassword().equals(Md5Util.md5Encode(password)))) {
            session.setAttribute("user", user);
            logger.info("user_s,user_u:"+user);
            return "redirect:dashboard";
        } else {
            request.setAttribute("error", "Password error!");
        }
        String lastRequestUrl = (String) request.getAttribute("lastRequestUrl");
        return StringUtils.isEmpty(lastRequestUrl) ? "login" : lastRequestUrl;
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        logger.info("Remove session attribute user.");
        session.removeAttribute("user");
        return "login";
    }

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String register() {
        return "register";
    }

    @RequestMapping(value = "dashboard", method = RequestMethod.GET)
    public String dashboard() {
        return "dashboard";
    }
}
