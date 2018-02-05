package cn.smart.cloud.biz.opadmin.controller.garten;

import cn.smart.cloud.biz.opadmin.entity.garten.Garten;
import cn.smart.cloud.biz.opadmin.proxy.GartenServiceProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/garten/manage/garten")
public class GartenManageController {

    private static final Logger logger = LoggerFactory.getLogger(GartenManageController.class);
    private static final String REDIRECT_LOCAL = "redirect:/garten/manage/garten/";
    private final static String MANAGE_INDEX = "index";

    @Value("${text.tip.success}")
    String tipSuccess;
    @Value("${text.tip.failed}")
    String tipFailed;

    @Autowired
    private GartenServiceProxy gartenServiceProxy;

    @RequestMapping(value = "index/{managerId}")
    public String gartenManageIndex(HttpServletRequest request,
                                    @PathVariable String managerId) {
        logger.info("gartenManageIndex, mId:" + managerId);
        return REDIRECT_LOCAL + "listSome/" + managerId;
    }

    @RequestMapping(value = "add")
    public String addGarten() {
        return "manage/garten/add";
    }

    @RequestMapping(value = "add_commit")
    public String addGartenCommit(HttpServletRequest request) {
        try {
            gartenServiceProxy.save(null);
            request.setAttribute("tip", tipSuccess);
        } catch (Exception e) {
            logger.error("addGartenCommit exception", e);
            request.setAttribute("tip", tipSuccess);
        }
        return "redirect:add";
    }

    @RequestMapping(value = "listSome/{managerId}")
    public String listGartenWithManagerId(HttpServletRequest request,
                                          @PathVariable String managerId, RedirectAttributes ra) {
        logger.info("listGartenWithManagerId, mId:" + managerId);
        ra.addFlashAttribute("managerId", managerId);//for hide
        return REDIRECT_LOCAL + "list";
    }

    @RequestMapping(value = "list")
    public String listGarten(HttpServletRequest request,
                             @ModelAttribute("managerId") String managerId, RedirectAttributes ra) {
        logger.info("listGarten, mId:" + managerId);
        List<Garten> gartens = gartenServiceProxy.getGartensByManagerId(managerId);
        if (gartens == null || gartens.isEmpty() || gartens.size() != 1) {
            request.setAttribute("gartenList", gartens);
            request.setAttribute("mId", managerId);
            return "manage/garten/list";
        } else {
            String gartenId = gartens.get(0).getId();
            ra.addFlashAttribute("gartenId", gartenId);
            ra.addFlashAttribute("mId", managerId);
            return REDIRECT_LOCAL + "manage";
        }
    }

    @RequestMapping(value = "manage")
    public String manage(HttpServletRequest request,
                         @ModelAttribute("gartenId") String gartenId,
                         @ModelAttribute("mId") String mId) {
        logger.info("manage, gartenId:" + gartenId + ", mId:" + mId);
        Garten garten = gartenServiceProxy.get(gartenId);
        request.setAttribute("garten", garten);
        request.setAttribute("mId", mId);
        return "manage/garten/manage";
        //return MANAGE_INDEX;
    }

    @RequestMapping(value = "info", method = RequestMethod.POST)
    public String info(HttpServletRequest request) {
        String gartenId = request.getParameter("gartenId");
        String mId = request.getParameter("mId");
        logger.info("info, gartenId:" + gartenId);
        Garten garten = gartenServiceProxy.get(gartenId);
        request.setAttribute("mId", mId);
        request.setAttribute("garten", garten);
        request.setAttribute("gartenInfo", gartenServiceProxy.get(garten.getGartenInfoId()));
        request.setAttribute("gartenClasses", gartenServiceProxy.getAllClassesByGartenId(gartenId));
        //request.setAttribute("teachers", gartenUserController.getTeachersByGartenId(gartenId));
        return "manage/garten/info";
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String editGarten(HttpServletRequest request) {
        String gartenId = request.getParameter("gartenId");
        String mId = request.getParameter("mId");
        logger.info("info, gartenId:" + gartenId + ", mId:" + mId);
        Garten garten = gartenServiceProxy.get(gartenId);
        request.setAttribute("mId", mId);
        request.setAttribute("garten", garten);
        request.setAttribute("gartenInfo", gartenServiceProxy.get(garten.getGartenInfoId()));
        request.setAttribute("gartenClasses", gartenServiceProxy.getAllClassesByGartenId(gartenId));
        //request.setAttribute("teachers", gartenUserController.getTeachersByGartenId(gartenId));
        return "manage/garten/edit";
    }

    @RequestMapping(value = "edit_commit")
    public String editGartenCommit(HttpServletRequest request) {
        String gartenId = request.getParameter("garten.id");
        logger.info("editGartenCommit gartenId:" + gartenId);
        try {
            gartenServiceProxy.save(null);
            request.setAttribute("tip", tipSuccess);
        } catch (Exception e) {
            logger.error("editGartenCommit exception", e);
            request.setAttribute("tip", tipFailed);
        }
        return "redirect:garten/manage/garten/list";
    }
}
