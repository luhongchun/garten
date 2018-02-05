package cn.smart.cloud.biz.opadmin.controller.manage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import cn.smart.cloud.biz.opadmin.constant.Constant.RoleType;
import cn.smart.cloud.biz.opadmin.entity.AttendCardEntity;
import cn.smart.cloud.biz.opadmin.entity.AttendCardGartenUserEntity;
import cn.smart.cloud.biz.opadmin.entity.AttendCardRecordEntity;
import cn.smart.cloud.biz.opadmin.entity.garten.Garten;
import cn.smart.cloud.biz.opadmin.entity.garten.GartenClass;
import cn.smart.cloud.biz.opadmin.entity.garten.user.GartenUser;
import cn.smart.cloud.biz.opadmin.util.DateTimeUtil;

@Controller
@RequestMapping("/admin/garten/attend")
public class AdminAttendManageAdminController extends BaseAdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminGartenManageAdminController.class);
    //private static final String REDIRECT_LOCAL = "redirect:/admin/garten/attend";
    private static final String[] stateList = new String[]{"未绑定", "已绑定", "废弃"};
    private static final String[] roleList = new String[]{"学生", "家长", "老师"};

    @RequestMapping(value = "cards", method = RequestMethod.GET)
    public String getCards(HttpSession session, HttpServletRequest request, Model model) {
        List<AttendCardGartenUserEntity> list = new ArrayList<>();
        String cardState = request.getParameter("state");
        String cardNo = request.getParameter("cardNo");
    	String gartenId = request.getParameter("gartenid");
		List<Garten> gartenList = getGatensByManagerId(session);
        logger.info("getCards, gartenList:"+gartenList+", gartenId:"+gartenId);
    	if (StringUtils.isBlank(gartenId)) {
            if (!gartenList.isEmpty()) {
            	gartenId = gartenList.get(0).getId();
            }
    	}
        logger.info("getCards, gartenId:"+gartenId);
    	if (!StringUtils.isBlank(gartenId)) {
            if (StringUtils.isEmpty(cardState) || "-1".equals(cardState)) {
            	cardState = "1";
            	list = gartenServiceProxy.getAllAttendCardsByGartenAndState(gartenId, Integer.parseInt(cardState));
            	model.addAttribute("stateSelectedIndex", 1);
            } else {
                if (StringUtils.isEmpty(cardNo)) {
                    list = gartenServiceProxy.getAllAttendCardsByGartenAndState(gartenId, Integer.parseInt(cardState));
                    model.addAttribute("stateSelectedIndex", Integer.parseInt(cardState));
                } else {
                    if (isNumeric(cardNo) == true) {
                        logger.info("getCards, 卡号查找:"+cardNo);
                        AttendCardGartenUserEntity entity = new AttendCardGartenUserEntity();
                        entity = gartenServiceProxy.getAttendCardByCaredId(cardNo);
                        list.add(entity);
                    }
                    List<AttendCardGartenUserEntity> list1 = new ArrayList<>();
                    if (list.isEmpty()) {
                    } else {
                        for (AttendCardGartenUserEntity u : list) {
                            if (u.getState() == Integer.parseInt(cardState)) {
                                list1.add(u);
                            }
                        }
                    }
                    list = list1;
                    model.addAttribute("stateSelectedIndex", Integer.parseInt(cardState));
                }
            }
        }
        logger.info("getCards, list:"+list+", roleList:"+roleList);
        List<List> selectList = assembleInitSelectList(session);
        model.addAttribute("gartenList", gartenList);
        model.addAttribute("gartenSeletedId", gartenId);
        model.addAttribute("stateList", stateList);
        model.addAttribute("roleList", roleList);
        model.addAttribute("cardList", list);
        model.addAttribute("card", new AttendCardGartenUserEntity());
        model.addAttribute("selectList", selectList);
        model.addAttribute("pageName", "考勤卡管理");

        return "garten/attend/cardManage";
    }

    @GetMapping("/card/cardid/{cardid}")
    public @ResponseBody String getCardByCardId(HttpSession session, @PathVariable String cardid, Model model) {
        AttendCardGartenUserEntity entity = gartenServiceProxy.getAttendCardByCaredId(cardid);

        List<List> selectList = assembleSelectList(session,
                entity.getGartenId(), entity.getGartenClassId(), entity.getGartenUserId());

        model.addAttribute("selectList", selectList);

        return new Gson().toJson(entity);
    }

    @PostMapping(value = "card")
    public @ResponseBody String saveCard(HttpServletRequest request) {
        String id = request.getParameter("id");//entity.getId();
        String cardId = request.getParameter("cardId");//entity.getCardId();
        String userId = request.getParameter("gartenUserId");
        if (StringUtils.isEmpty(cardId) || cardId.length() > 10 || !cardId.matches("[0-9]+")) {
            return "卡号错误！";
        }

        AttendCardEntity cardEntity = new AttendCardEntity();
        if (!StringUtils.isBlank(id))
            cardEntity.setId(id);
        cardEntity.setCardId(cardId);
        cardEntity.setGartenUserId(userId);
        cardEntity.setState(StringUtils.isBlank(userId)?0:1);
        cardEntity = gartenServiceProxy.saveAttendCard(cardEntity);
        if (cardEntity == null) {
            return "保存失败！";
        }
        return "success";
    }

    @GetMapping(value = "records")
    public String getRecords(HttpSession session, HttpServletRequest request, Model model) {
        String gartenId = request.getParameter("select0");
        String classId = request.getParameter("select1");
        List<Garten> gartenList = getGatensByManagerId(session);
        List<GartenClass> classList;
        if (!StringUtils.isEmpty(classId) && !classId.equalsIgnoreCase("-1")) {
            classList = gartenServiceProxy.getAllClassesByGartenId(gartenId);
        } else {
            if (StringUtils.isEmpty(gartenId) || gartenId.equalsIgnoreCase("-1")) {
                gartenId = gartenList == null || gartenList.size() == 0 ? "-1" : gartenList.get(0).getId();
            }
            classList = gartenServiceProxy.getAllClassesByGartenId(gartenId);
            classId = classList == null || classList.size() == 0 ? "-1" : classList.get(0).getId();
        }
        String dateRange = request.getParameter("daterange");
        logger.info("getRecords, classId: " + classId + ", daterange: " + dateRange);
        if (StringUtils.isEmpty(classId)) {
            //TODO return error
        }
        List<AttendCardGartenUserEntity> recordList;
        if (StringUtils.isEmpty(dateRange)) {
            Calendar calendar = Calendar.getInstance();
            String endDate = DateTimeUtil.formatDate(calendar.getTimeInMillis(), "yyyy/MM/dd");
            //calendar.add(Calendar.MONTH, -1);
            String startDate = DateTimeUtil.formatDate(calendar.getTimeInMillis(), "yyyy/MM/dd");
            dateRange = startDate + " - " + endDate;
        }
        logger.info("getRecords, daterange: " + dateRange);

        String[] dateArray = dateRange.split("-");
        if (dateArray.length == 2) {
            Date startDate = DateTimeUtil.formatDate(dateArray[0], "yyyy/MM/dd");
            Date endDate = DateTimeUtil.formatDate(dateArray[1], "yyyy/MM/dd");
            /*Calendar start = Calendar.getInstance();
            start.setTime(startDate);
            start.set(Calendar.DATE, start.get(Calendar.DATE) - 1);*/
            Calendar end = Calendar.getInstance();
            end.setTime(endDate);
            end.set(Calendar.DATE, end.get(Calendar.DATE) + 1);
            recordList = gartenServiceProxy.getAllRecordsByDateAndClassId(
                    startDate.getTime(), end.getTimeInMillis(), classId);
        } else {
            recordList = gartenServiceProxy.getAllRecordsByGartenClassId(classId);
        }
    
        Map<String, AttendCardGartenUserEntity> entry = new HashMap<>();
        Map<String, AttendCardGartenUserEntity> exit = new HashMap<>();
        List<AttendCardGartenUserEntity> rList = new ArrayList<>();
        for (AttendCardGartenUserEntity r:recordList) {
            if (!entry.containsKey(r.getCardId()))
                entry.put(r.getCardId(), r);
            else
                exit.put(r.getCardId(), r);
        }
        for (String cardId:entry.keySet()) {
            AttendCardGartenUserEntity r = entry.get(cardId);
            r.setRoleType(RoleType.CHILD.ordinal());
            if (exit.containsKey(cardId))
                r.setDateExit(exit.get(cardId).getDate());
            rList.add(r);
        }
        
        logger.info("getRecords, recordList:"+recordList+", roleList:"+roleList);
        model.addAttribute("gartenSeletedId", gartenId);
        model.addAttribute("gartenList", gartenList);
        model.addAttribute("classSelectedId", classId);
        model.addAttribute("classList", classList);
        model.addAttribute("selectList", assembleSelectList(gartenList, gartenId, classList, classId));
        model.addAttribute("recordList", rList);
        model.addAttribute("roleList", roleList);
        model.addAttribute("record", new AttendCardGartenUserEntity());
        model.addAttribute("classId", classId);
        model.addAttribute("dateRange", dateRange);
        model.addAttribute("pageName", "考勤记录");

        return "garten/attend/recordManage";
    }

    @PostMapping(value = "record")
    public @ResponseBody String saveRecord(HttpServletRequest request) {
        String cardId = request.getParameter("cardId");
        logger.info("saveRecord, cardId:" + cardId);
        if (StringUtils.isBlank(cardId))
            return "输入错误";
        AttendCardRecordEntity recordEntity = new AttendCardRecordEntity();
        recordEntity.setCardId(cardId);
        recordEntity = gartenServiceProxy.saveRecord(recordEntity);
        logger.info("saveRecord, recordEntity:" + recordEntity);
        return "success";
    }

    private List<List> assembleInitSelectList(HttpSession session) {
        List<List> selectList = new ArrayList<>();

        List<Garten> gartens = getGatensByManagerId(session);
        if (gartens == null || gartens.size() == 0) {
            return selectList;
        }

        List<GartenClass> gartenClasses = gartenServiceProxy.getAllClassesByGartenId(gartens.get(0).getId());
        if (gartenClasses == null || gartenClasses.size() == 0) {
            return assembleSelectList(gartens, "", null, "", null, "");
        }

        List<GartenUser> gartenUsers = gartenServiceProxy.getChildrenByClassId(gartenClasses.get(0).getId());
        if (gartenUsers == null || gartenUsers.size() == 0) {
            return assembleSelectList(gartens, "", gartenClasses, "", null, "");
        }

        return assembleSelectList(gartens, "", gartenClasses, "", gartenUsers, "");
    }

    private List<List> assembleSelectList(HttpSession session,
            String selectedGartenId, String selectedClassId, String selectedUserId) {
        List<Garten> gartens = getGatensByManagerId(session);
        List<GartenClass> classes = StringUtils.isEmpty(selectedGartenId) ? new ArrayList<GartenClass>()
                : gartenServiceProxy.getAllClassesByGartenId(selectedGartenId);
        List<GartenUser> users = StringUtils.isEmpty(selectedUserId) ? new ArrayList<GartenUser>()
                : gartenServiceProxy.getChildrenByClassId(selectedClassId);
        return assembleSelectList(gartens, selectedGartenId, classes, selectedClassId, users, selectedUserId);
    }

    private boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*$");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ) {
            return false;
        }
        return true;
     }
}
