package cn.smart.cloud.biz.opadmin.controller.garten;

import cn.smart.cloud.biz.opadmin.entity.garten.Garten;
import cn.smart.cloud.biz.opadmin.proxy.GartenServiceProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/garten/manage/class")
public class GartenClassManageController {

    private static final Logger logger = LoggerFactory.getLogger(GartenManageController.class);

    @Value("${text.tip.success}")
    String tipSuccess;
    @Value("${text.tip.failed}")
    String tipFailed;

    @Autowired
    private GartenServiceProxy gartenServiceProxy;

    @RequestMapping(value = "index")
    public String gartenClassManageIndex() {
        return "/manage/garten/class/index";
    }

    @RequestMapping(value = "add")
    public String addGartenClass(HttpServletRequest request) {
        List<Garten> gartenList = gartenServiceProxy.getAll();
        request.setAttribute("gartenList", gartenList);
        return "/manage/garten/class/add";
    }

    @RequestMapping(value = "add_commit")
    public String addGartenClassCommit(HttpServletRequest request) {
        try {
            //gartenClassController.save(null);
            request.setAttribute("tip", tipSuccess);
        } catch (Exception e) {
            logger.error("addGartenClassCommit exception", e);
            request.setAttribute("tip", tipFailed);
        }
        return "redirect:/garten/manage/class/add";
    }

    @RequestMapping(value = "list")
    public String listGartenClass() {
        return "/manage/garten/class/list";
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String editGartenClass(HttpServletRequest request) {
        String id = request.getParameter("classId");
        String mId = request.getParameter("mId");
        //request.setAttribute("gartenCls", gartenClassController.get(id));
        request.setAttribute("classId", id);
        request.setAttribute("children", gartenServiceProxy.getChildrenByClassId(id));
        logger.info("editGartenClass mId:" + mId);
        request.setAttribute("mId", mId);
        return "/manage/garten/children/list";
    }

    @RequestMapping(value = "edit_commit")
    public String editGartenClassCommit(HttpServletRequest request) {
        try {
            //gartenClassController.save(null);
            request.setAttribute("tip", tipSuccess);
            return "redirect:/garten/manage/class/list";
        } catch (Exception e) {
            logger.error("editGartenClassCommit exception", e);
            request.setAttribute("tip", tipFailed);
            return "/manage/garten/class/edit";
        }
    }
}
