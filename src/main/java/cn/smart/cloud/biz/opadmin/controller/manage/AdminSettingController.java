package cn.smart.cloud.biz.opadmin.controller.manage;

import cn.smart.cloud.biz.opadmin.entity.user.SysUser;
import cn.smart.cloud.biz.opadmin.proxy.BaseCoreServiceProxy;
import cn.smart.cloud.biz.opadmin.util.Md5Util;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "/admin/setting")
public class AdminSettingController {

    private static final Logger logger = Logger.getLogger(AdminSettingController.class);
    private static final String REDIRECT_LOCAL = "redirect:/admin/setting";

    @Autowired
    BaseCoreServiceProxy baseCoreServiceProxy;

    @RequestMapping(value = "sysusers", method = RequestMethod.GET)
    public String sysUsers(HttpServletRequest request, Model model) {
        String keySysUserName = request.getParameter("sysUserName");
        List<SysUser> sysUserList = baseCoreServiceProxy.getSysUsers();
        model.addAttribute("sysUserName", keySysUserName);
        model.addAttribute("sysUserList", sysUserList);
        model.addAttribute("pageName", "系统管理员");

        return "setting/sysUserManage";
    }

    @RequestMapping(value = "sysuser/add", method = RequestMethod.GET)
    public String addSysUser(Model model) {
        return editSysUser("", model);
    }

    @RequestMapping(value = "sysuser/{sysUserId}", method = RequestMethod.GET)
    public String editSysUser(@PathVariable String sysUserId, Model model) {
        SysUser sysUser;
        if (StringUtils.isEmpty(sysUserId)) {
            sysUser = new SysUser();
        } else {
            sysUser = baseCoreServiceProxy.getSysUser(sysUserId);
        }
        model.addAttribute("sysUser", sysUser);
        model.addAttribute("pageName", "系统管理员信息");

        return "setting/sysUserEdit";
    }

    @RequestMapping(value = "sysuser", method = RequestMethod.POST)
    public String saveSysUser(SysUser sysUser) {
        if (StringUtils.isEmpty(sysUser.getId())) {
            sysUser.setPassword(Md5Util.md5Encode(sysUser.getPassword()));
        }
        SysUser ret = baseCoreServiceProxy.saveSysUser(sysUser);
        if (ret == null) {
            return "setting/sysUserEdit";
        }

        return REDIRECT_LOCAL + "/sysusers";
    }

    @RequestMapping(value = "sysuser/{sysUserId}", method = RequestMethod.DELETE)
    public @ResponseBody String deleteSysUser(@PathVariable String sysUserId) {
        baseCoreServiceProxy.deleteSysUser(sysUserId);
        SysUser sysUser = baseCoreServiceProxy.getSysUser(sysUserId);
        return sysUser == null ? "success" : "failed";
    }

}
