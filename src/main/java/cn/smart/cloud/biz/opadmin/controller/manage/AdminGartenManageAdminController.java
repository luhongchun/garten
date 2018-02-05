package cn.smart.cloud.biz.opadmin.controller.manage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import cn.smart.cloud.biz.opadmin.entity.BaseRole;
import cn.smart.cloud.biz.opadmin.entity.BaseUser;
import cn.smart.cloud.biz.opadmin.entity.SexType;
import cn.smart.cloud.biz.opadmin.entity.garten.Garten;
import cn.smart.cloud.biz.opadmin.entity.garten.GartenClass;
import cn.smart.cloud.biz.opadmin.entity.garten.data.ChildInfo;
import cn.smart.cloud.biz.opadmin.entity.garten.data.GartenInfo;
import cn.smart.cloud.biz.opadmin.entity.garten.data.GartenUserInfo;
import cn.smart.cloud.biz.opadmin.entity.garten.data.TeacherInfo;
import cn.smart.cloud.biz.opadmin.entity.garten.user.GartenUser;
import cn.smart.cloud.biz.opadmin.entity.garten.user.GartenUser2GartenClass;
import cn.smart.cloud.biz.opadmin.entity.garten.user.GartenUser2Role;

@Controller
@RequestMapping("/admin/garten")
public class AdminGartenManageAdminController extends BaseAdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminGartenManageAdminController.class);
    private static final String REDIRECT_LOCAL = "redirect:/admin/garten";

    @Value("${text.tip.success}")
    String tipSuccess;
    @Value("${text.tip.failed}")
    String tipFailed;

    @RequestMapping(value = "classes/gartenid/{gartenId}", method = RequestMethod.GET)
    public @ResponseBody String getClassesByGartenId(@PathVariable String gartenId) {
        List<GartenClass> list = gartenServiceProxy.getAllClassesByGartenId(gartenId);
        StringBuilder jsonStr = new StringBuilder("[");
        for (GartenClass gartenClass : list) {
            jsonStr.append("{\"text\":\"").append(gartenClass.getName()).append("\",")
                    .append("\"value\":\"").append(gartenClass.getId()).append("\"},");
        }
        if (list.size() > 0) {
            jsonStr.delete(jsonStr.length() - 1, jsonStr.length());
        }
        jsonStr.append("]");

        return jsonStr.toString();
    }

    @RequestMapping(value = "students/classid/{classId}", method = RequestMethod.GET)
    public @ResponseBody String getStudentsByGartenId(@PathVariable String classId) {
        List<GartenUser> list = gartenServiceProxy.getChildrenByClassId(classId);
        StringBuilder jsonStr = new StringBuilder("[");
        for (GartenUser user : list) {
            jsonStr.append("{\"text\":\"").append(user.getAliasName()).append("\",")
                    .append("\"value\":\"").append(user.getId()).append("\"},");
        }
        if (list.size() > 0) {
            jsonStr.delete(jsonStr.length() - 1, jsonStr.length());
        }
        jsonStr.append("]");

        return jsonStr.toString();
    }

    //======================= Garten Manage ==========================

    @RequestMapping(value = "gartens", method = RequestMethod.GET)
    public String listGarten(HttpSession session, HttpServletRequest request, Model model) {
        String gartenName = request.getParameter("gartenName");
        List<Garten> gartenList = getGatensByManagerId(session);
        model.addAttribute("gartenList", gartenList);
        model.addAttribute("gartenName", gartenName);
        return "garten/gartenManage";
    }

    @RequestMapping(value = "garten/add", method = RequestMethod.GET)
    public String addGarten(Model model) {
        return editGarten("", new Garten(), new GartenInfo(), model);
    }

    @RequestMapping(value = "garten/{gartenId}", method = RequestMethod.GET)
    public String editGarten(@PathVariable String gartenId, Garten garten, GartenInfo gartenInfo, Model model) {
        if (!StringUtils.isEmpty(gartenId)) {
            garten = gartenServiceProxy.get(gartenId);
            if (garten == null || StringUtils.isEmpty(garten.getGartenInfoId())) {
                gartenInfo = new GartenInfo();
            } else {
                gartenInfo = gartenServiceProxy.getGartenInfo(garten.getGartenInfoId());
            }
        }
        model.addAttribute("garten", garten);
        model.addAttribute("gartenInfo", gartenInfo);
        return "garten/gartenEdit";
    }

    @RequestMapping(value = "garten", method = RequestMethod.POST)
    public String saveGarten(Garten garten, GartenInfo gartenInfo) {
        logger.info("garten: " + garten);
        GartenInfo gni = gartenServiceProxy.saveGartenInfo(gartenInfo);
        if (gni == null) {
            logger.error("Save GartenInfo failed!");
            return "garten/gartenEdit";
        }
        garten.setGartenInfoId(gni .getId());
        Garten gn = gartenServiceProxy.save(garten);
        if (gn == null) {
            logger.error("Save Garten failed!");
            return "garten/gartenEdit";
        }
        return REDIRECT_LOCAL + "/gartens";
    }

    @RequestMapping(value = "garten/{gartenId}", method = RequestMethod.DELETE)
    public @ResponseBody String deleteGarten(@PathVariable String gartenId) {
        logger.info("deleteGarten: gartenId: " + gartenId);
        return "success";
    }

    //======================= Class Manage ==========================

    @RequestMapping(value = "classes", method = RequestMethod.GET)
    public String listClass(HttpSession session, HttpServletRequest request, Model model) {
        String gartenId = request.getParameter("gartenid");
        List<Garten> gartenList = getGatensByManagerId(session);
        gartenId = StringUtils.isEmpty(gartenId)
                ? (gartenList.size() > 0 ? gartenList.get(0).getId() : "-1") : gartenId;
        model.addAttribute("gartenList", gartenList);
        model.addAttribute("gartenSeletedId", gartenId);
        List<GartenClass> classList = gartenServiceProxy.getAllClassesByGartenId(gartenId);
        model.addAttribute("classList", classList); 
        ArrayList<Integer> studentList=new ArrayList<Integer>();
        @SuppressWarnings("unused")
        int i = 0;
       for (GartenClass u2c:classList) {
           classList = gartenServiceProxy.getAllClassesByGartenId(gartenId);
           int   childList = gartenServiceProxy.getChildrenByClassId(u2c.getId()).size();   
          studentList.add(childList);
          i++;
        }
       model.addAttribute("studentList", studentList); 
       model.addAttribute("count", studentList); 
       model.addAttribute("pageName", "班级管理");
        return "garten/classManage";
    }

    @RequestMapping(value = "class/add", method = RequestMethod.GET)
    public String addClass(HttpSession session, Model model) {
        return editClass(session, "", model);
    }

    @RequestMapping(value = "class/{classId}", method = RequestMethod.GET)
    public String editClass(HttpSession session, @PathVariable String classId, Model model) {
        List<Garten> gartenList = getGatensByManagerId(session);
        GartenClass gartenClass;
        String gartenId;
        if (StringUtils.isEmpty(classId)) {
            gartenClass = new GartenClass();
            gartenId = "-1";
        } else {
            gartenClass = gartenServiceProxy.getClass(classId);
            gartenId = gartenClass.getGartenId();
        }
        model.addAttribute("gartenList", gartenList);
        model.addAttribute("gartenClass", gartenClass);
        model.addAttribute("gartenSeletedId", gartenId);

        return "garten/classEdit";
    }

    @RequestMapping(value = "class", method = RequestMethod.POST)
    public String saveClass(GartenClass gartenClass) {
        GartenClass ret = gartenServiceProxy.saveClass(gartenClass);
        if (ret == null) {
            return "garten/classEdit";
        }

        return REDIRECT_LOCAL + "/classes?gartenid=" + gartenClass.getGartenId();
    }

    //======================= Teacher Manage ==========================

    @RequestMapping(value = "teachers", method = RequestMethod.GET)
    public String listTeachers(HttpSession session, HttpServletRequest request, Model model) {
        String gartenId = request.getParameter("selectI0");
        String classId = request.getParameter("selectI1"); 
        logger.info("listTeachers , gartenId:"+gartenId+"classId , classId:"+classId);
        List<Garten> gartenList = getGatensByManagerId(session);
        List<GartenClass> classList;
        List<TeacherInfo> teacherAccounts = new ArrayList<>();
        if (!StringUtils.isEmpty(classId) && !classId.equalsIgnoreCase("-1")) {
            classList = gartenServiceProxy.getAllClassesByGartenId(gartenId);
        } else {
            if (StringUtils.isEmpty(gartenId) || gartenId.equalsIgnoreCase("-1")) {
                gartenId = gartenList == null || gartenList.size() == 0 ? "-1" : gartenList.get(0).getId();
            }
            classList = gartenServiceProxy.getAllClassesByGartenId(gartenId);
            classId = classList == null || classList.size() == 0 ? "-1" : classList.get(0).getId();
        }
        
        //List<GartenUser> teacherList = gartenServiceProxy.getTeachersByGartenId(gartenId);
        List<GartenUserInfo> teacherList = gartenServiceProxy.getTeachersInfoByGartenId(gartenId);
        logger.info("listTeachers, teacherList"+teacherList);
        for (GartenUserInfo u : teacherList) {
            logger.info("listTeachers, u" + u);
            TeacherInfo teacherInfo = new TeacherInfo();
            teacherInfo.setId(u.getId());
            teacherInfo.setName(u.getName());
            teacherInfo.setClassName(u.getClassName());
            if (u.getSex().equals(SexType.FEMALE.name())) {
                teacherInfo.setSex("女");
            } else {
                teacherInfo.setSex("男");
            }
            teacherInfo.setPhoneNum(u.getPhoneNum());
            teacherAccounts.add(teacherInfo);
        }
        logger.info("listTeachers, teacherAccounts:"+teacherAccounts);
        model.addAttribute("teacher", new TeacherInfo());
        model.addAttribute("gartenSeletedId", gartenId);
        model.addAttribute("classSelectedId", classId);
        model.addAttribute("gartenList", gartenList);
        model.addAttribute("classList", classList);
        model.addAttribute("teacherList", teacherAccounts);
        model.addAttribute("pageName", "老师管理");
      //  model.addAttribute("selectList", assembleSelectList(gartenList, gartenId,null, "-1"));
        model.addAttribute("selectList", assembleSelectList(gartenList, gartenId, classList, classId));

        return "garten/teacherManage";
    }

    @PostMapping(value = "teacher/add")
    public @ResponseBody String addTeacher(HttpServletRequest request, HttpSession session, Model model) {
        String id = request.getParameter("id");
        String gartenId = request.getParameter("gartenId");
        String classId = request.getParameter("gartenClassId");
         String name      = request.getParameter("name");
         String sexString = request.getParameter("sex");
         String phoneNum  = request.getParameter("phoneNum");
         String status = request.getParameter("status");
         logger.info("id:"+id +"gartenId:"+gartenId + "classId:"+classId + "name:"+name 
                 + "sexString:"+sexString +"phoneNum:"+phoneNum+"status:"+status);
         if (StringUtils.isEmpty(gartenId)||StringUtils.isEmpty(classId)) {
             return "请填写班级！";
       }
         if (StringUtils.isEmpty(name)) {
               return "请填写姓名！";
         }
         SexType sex;
         if(sexString.equals("男"))
         {
             System.out.println(sexString);
             sex =SexType.MALE;
         }else{
             sex =SexType.FEMALE;
         } 
       
         if(status.equals("1")) {
             logger.info("老师修改");
              try {
                  GartenUser teacher = gartenServiceProxy.getUser(id);
                  teacher.setAliasName(name);
                  gartenServiceProxy.saveUser(teacher);
                  BaseUser buTeacher = baseUserServiceProxy.get(teacher.getBaseUserId());
                  buTeacher.setSex(sex);
                  buTeacher.setName(name);
                  buTeacher.setPhoneNum(phoneNum);
                  baseUserServiceProxy.update(buTeacher);
                  
                  List<BaseRole> roles = gartenServiceProxy.getAllGartenRoleByGartenUserId(teacher.getId());
                  BaseRole brTeacher = baserCoreServiceProxy.getBaseRoleById(roles.get(0).getId());
                  brTeacher.setName(name);
                  baserCoreServiceProxy.updateBaseRole(brTeacher);
                  
                  GartenUser2GartenClass u2c =gartenServiceProxy.getGartenUser2GartenClass(teacher.getId());
                  u2c.setGartenUserId(teacher.getId());
                  u2c.setGartenClassId(classId);
                  gartenServiceProxy.saveU2C(u2c);
                  request.setAttribute("tip", tipSuccess);
              } catch (Exception e) {
                  logger.info("editSubmit exception:" + e);
                  request.setAttribute("tip", tipFailed);
              }
         }else {
             logger.info("新增老师");
             try {
            	 GartenUserInfo gartenUserInfo = new GartenUserInfo();
            	 gartenUserInfo.setClassId(classId);
            	 gartenUserInfo.setName(name);
            	 gartenUserInfo.setPhoneNum(phoneNum);
            	 gartenUserInfo.setSex(sex.name());
            	 gartenServiceProxy.createTeacher(gartenUserInfo);
            //     gartenServiceProxy.createTeacher(classId, name, phoneNum, sex.name());
                 //createTeacher(gartenId, classId,name, phoneNum, input_sex);
                 request.setAttribute("tip", tipSuccess);
             } catch (Exception e) {
                 logger.error("addSubmit exception:" + e);
                 request.setAttribute("tip", tipSuccess);
             }
         }
        return "success";
    }

    @RequestMapping(value = "teacher/{teacherId}", method = RequestMethod.GET)
    public String editTeacher(HttpSession session, @PathVariable String teacherId, Model model) {
        GartenUser teacher;
        if (StringUtils.isEmpty(teacherId)) {
            teacher = new GartenUser();
        } else {
            teacher = gartenServiceProxy.getUser(teacherId);
        }

        model.addAttribute("gartenSeletedId", "-1");
        model.addAttribute("gartenList", getGatensByManagerId(session));
        model.addAttribute("teacher", teacher);
        model.addAttribute("pageName", "老师信息");
        return "garten/teacherEdit";
    }

    @RequestMapping(value = "teacher", method = RequestMethod.POST)
    public String saveTeacher(GartenUser teacher) {
        GartenUser ret = gartenServiceProxy.saveUser(teacher);
        if (ret == null) {
            return "garten/teacherEdit";
        }

        return REDIRECT_LOCAL + "/teachers?gartenId=" + teacher.getGartenId();
    }

    //======================= Student Manage ==========================

    @RequestMapping(value = "students", method = RequestMethod.GET)
    public String listStudents(HttpSession session, HttpServletRequest request, Model model) {
        String gartenId = request.getParameter("selectI0");
        String classId = request.getParameter("selectI1");
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
        List<ChildInfo> childAccounts = gartenServiceProxy.getChildrenInfoByClassId(classId);//new ArrayList<>();
        for (ChildInfo info:childAccounts) {
            String sexType = info.getSex();
            if(sexType.equals( SexType.FEMALE.name())) {
                info.setSex("女");
            }else{
                info.setSex("男");
            }
        }
        model.addAttribute("gartenSeletedId", gartenId);
        model.addAttribute("gartenList", gartenList);
        model.addAttribute("classSelectedId", classId);
        model.addAttribute("classList", classList);
        model.addAttribute("childAccounts", childAccounts);
        model.addAttribute("childAccount", new ChildInfo());
        model.addAttribute("student", new GartenUser());
        model.addAttribute("pageName", "学生管理");
        model.addAttribute("selectList", assembleSelectList(gartenList, gartenId, classList, classId));
        return "garten/studentManage";
    }

    @RequestMapping(value = "student/{studentId}", method = RequestMethod.GET)
    public @ResponseBody String editStudent(HttpSession session, HttpServletRequest request, 
            @PathVariable String studentId, Model model) {
        GartenUser student;
        if (StringUtils.isEmpty(studentId)) {
            student = new GartenUser();
        } else {
            student = gartenServiceProxy.getUser(studentId);
        }
        String classId = request.getParameter("select1");
        model.addAttribute("gartenSeletedId", "-1");
        model.addAttribute("gartenList", getGatensByManagerId(session));
        model.addAttribute("classSelectedId", classId);
        model.addAttribute("student", new GartenUser());
        model.addAttribute("student", student);
        model.addAttribute("pageName", "学生信息");
        return new Gson().toJson(student);
    }

    @PostMapping(value = "student")
    public @ResponseBody String saveStudent(HttpServletRequest request, HttpSession session,
            @ModelAttribute GartenUser entity, @ModelAttribute BaseRole baseRole,
            @ModelAttribute GartenUser2GartenClass u2c, @ModelAttribute BaseUser baseuser) {
        String childId = entity.getId();
        String name = entity.getAliasName();
        String sexString = request.getParameter("sex1");
        SexType input_sex;
        if (sexString.equals("男")) {
            System.out.println(sexString);
            input_sex = SexType.MALE;
        } else {
            input_sex = SexType.FEMALE;
        }
        if (StringUtils.isBlank(name)) {
            return "名字未填写！";
        }
        if (StringUtils.isBlank(childId)) {
            childId = gartenServiceProxy.createStudent(name, input_sex.name(), u2c.getGartenClassId());
            if (StringUtils.isBlank(childId)) {
                return "保存失败！";
            }
        } else {
            logger.info("修改学生");
            GartenUser child = gartenServiceProxy.getUser(childId);
            child.setAliasName(name);
            GartenUser ret = gartenServiceProxy.saveUser(child);
            if (ret == null) {
                return "修改失败！";
            }
            List<GartenUser2Role> roles = gartenServiceProxy.getAllU2RByGartenUserId(childId);
            BaseRole baserole = baserCoreServiceProxy.getBaseRoleById(roles.get(0).getBaseRoleId());
            baserole.setName(name);
            baserCoreServiceProxy.updateBaseRole(baserole);
            BaseUser buChild1 = baseUserServiceProxy.get(child.getBaseUserId());
            buChild1.setSex(input_sex);
            buChild1.setName(name);
            baseUserServiceProxy.update(buChild1);
        }
        return "success";
    }

    //======================= Patent Manage ==========================

    @RequestMapping(value = "parents/{studentId}", method = RequestMethod.GET)
    public String getParents(HttpSession session, HttpServletRequest request, @PathVariable String studentId,Model model) {
        GartenUser  child = gartenServiceProxy.getUser(studentId);
        logger.info("getParents, id:"+studentId+", child:"+child);
        ChildInfo student = new ChildInfo();
        student.setId(child.getId());
        student.setName(child.getAliasName());
        GartenUser2GartenClass classAll = gartenServiceProxy.getGartenUser2GartenClass(child.getId());
        GartenClass  class_student = gartenServiceProxy.getClass(classAll.getGartenClassId());
        student.setClassName(class_student.getName());
        List<GartenUserInfo> parentAccounts = gartenServiceProxy.getParentsInfoByChildId(studentId);//new ArrayList<>();
        model.addAttribute("student", student);
        model.addAttribute("parentList", parentAccounts);
        model.addAttribute("parent", new ChildInfo());
        model.addAttribute("pageName", "家长管理");

        return "garten/parentManage";
    }

    @PostMapping(value = "parent/add")
    public @ResponseBody String saveParent(HttpServletRequest request, HttpSession session,
            @ModelAttribute GartenUser entity, @ModelAttribute BaseRole baseRole) {
        String childId = request.getParameter("id");
        //String parentId = request.getParameter("parentId");
        String childName = request.getParameter("name");
        String parentName = request.getParameter("select_parent");
        String phoneNum = request.getParameter("PhoneNum");
        //String status = request.getParameter("status");
        logger.info("saveParent, childId:"+childId+", name:"+parentName+", phone:"+phoneNum);
        logger.info("新增家长");
        try {
            String sex = SexType.MALE.name();
            GartenUser parent = gartenServiceProxy.createParent(childId, childName + parentName, phoneNum, sex);
            logger.info("saveParent, result: " + parent);
        } catch (Exception e) {
            return "保存失败";
        }
    
        return "success";
    }

    @PostMapping(value = "parent/edit")
    public @ResponseBody String editParent(HttpServletRequest request, HttpSession session,
            @ModelAttribute GartenUser entity, @ModelAttribute BaseRole baseRole) {
        String parentId = request.getParameter("id");
        String phoneNum = request.getParameter("PhoneNum");
        logger.info("editParent, parentId:"+parentId+", phoneNum:"+phoneNum);
        try {
            logger.info("修改家长");
            BaseUser buParent = baseUserServiceProxy.get(gartenServiceProxy.getUser(parentId).getBaseUserId());
            buParent.setPhoneNum(phoneNum);
            baseUserServiceProxy.update(buParent);
        } catch (Exception e) {
            return "保存失败";
        }
    
        return "success";
    }

    public String date(String date) {
        long date1= Long.parseLong(date);
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm");//将毫秒级long值转换成日期格式
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(date1);
        String dateStr = dateformat.format(gc.getTime());
        return dateStr;
    }

    /*public boolean createTeacher(String gartenId, String gartenclass, String teacherName,
            String phoneNum, SexType sex) {
        try {
            BaseUser buTeacherOld = baseUserServiceProxy.getByPhoneNum(phoneNum);
            if (buTeacherOld != null) {
                
            }
            
            BaseUser buTeacher = new BaseUser();
            buTeacher.setGlobalId(UuidUtil.randomWithMd5Encrypted());
            buTeacher.setName(teacherName);
            buTeacher.setPhoneNum(phoneNum);
            buTeacher.setSex(sex);
            buTeacher = baseUserServiceProxy.create(buTeacher);

            BaseRole brTeacher = new BaseRole();
            brTeacher.setName(teacherName);
            brTeacher.setRoleType(RoleType.TEACHER);
            brTeacher = baserCoreServiceProxy.createBaseRole(brTeacher);
            
            GartenUser teacher = new GartenUser();
            teacher.setAliasName(teacherName);
            teacher.setBaseUserId(buTeacher.getId());
            teacher.setGartenId(gartenId);
            teacher = gartenServiceProxy.createUser(teacher);
            gartenServiceProxy.bindUserAndRole(teacher, brTeacher);
            
            GartenUser2GartenClass u2c = new GartenUser2GartenClass();
            u2c.setGartenUserId(teacher.getId());
            u2c.setGartenClassId(gartenclass);
            gartenServiceProxy.saveU2C(u2c);
            
            GartenUser2Role u2r = new GartenUser2Role();
            u2r.setGartenUserId(teacher.getId());
            u2r.setBaseRoleId(brTeacher.getId());
            gartenServiceProxy.saveGartenUser2Role(u2r);
        } catch (Exception e) {
            return false;
        }
        return true;
    }*/
}

