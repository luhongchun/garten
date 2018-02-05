package cn.smart.cloud.biz.opadmin.controller.garten;

import cn.smart.cloud.biz.opadmin.constant.Constant;
import cn.smart.cloud.biz.opadmin.entity.BaseRole;
import cn.smart.cloud.biz.opadmin.entity.BaseUser;
import cn.smart.cloud.biz.opadmin.entity.SexType;
import cn.smart.cloud.biz.opadmin.entity.fr.Face;
import cn.smart.cloud.biz.opadmin.entity.garten.GartenClass;
import cn.smart.cloud.biz.opadmin.entity.garten.user.GartenUser;
import cn.smart.cloud.biz.opadmin.entity.garten.user.GartenUser2GartenClass;
import cn.smart.cloud.biz.opadmin.entity.garten.user.GartenUser2Role;
import cn.smart.cloud.biz.opadmin.entity.garten.user.GartenUserVideo;
import cn.smart.cloud.biz.opadmin.gson.BizVideoType;
import cn.smart.cloud.biz.opadmin.gson.UploadVodResult;
import cn.smart.cloud.biz.opadmin.gson.fr.youtu.YtPersonResult;
import cn.smart.cloud.biz.opadmin.gson.video.UserVideo;
import cn.smart.cloud.biz.opadmin.proxy.*;
import cn.smart.cloud.biz.opadmin.util.TecentVodSignature;
import cn.smart.cloud.biz.opadmin.util.UuidUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/garten/manage/child")
public class GartenChildController {

    private static final Logger logger = LoggerFactory.getLogger(GartenChildController.class);
    private static final String REDIRECT_LOCAL = "redirect:/garten/manage/child/";
    private static final String NAME_PREFIX_FACE = "face-";

    @Value("${text.tip.success}")
    String tipSuccess;
    @Value("${text.tip.failed}")
    String tipFailed;

    @Autowired
    private GartenServiceProxy gartenServiceProxy;
    @Autowired
    private FaceServiceProxy faceServiceProxy;

    @Autowired
    private BaseUserServiceProxy baseUserServiceProxy;
    @Autowired
    private BaseCoreServiceProxy baseServiceProxy;
    @Autowired
    private VideoServiceProxy videoServiceProxy;
    @Autowired
    private TecentVodSignature vodSignature;

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(HttpServletRequest request) {
        String classId = request.getParameter("classId");
        String mId = request.getParameter("mId");
        request.setAttribute("classId", classId);
        request.setAttribute("mId", mId);
        request.setAttribute("sex", SexType.MALE);
        return "manage/garten/children/add";
    }

    @RequestMapping(value = "add_submit", method = RequestMethod.POST)
    public String addSubmit(HttpServletRequest request) {
        String name = request.getParameter("name");
        String classId = request.getParameter("classId");
        String mId = request.getParameter("mId");
        String sexString = request.getParameter("sex");
        logger.info("addSubmit, n:" + name + ", c:" + classId + ", mId:" + mId + ", s:" + sexString);
        if (StringUtils.isEmpty(name)) {
            return "redirect:list/" + classId + "/" + mId;
        }
        SexType sex = SexType.MALE;
        if (SexType.FEMALE.name().equals(sexString)) {
            sex = SexType.FEMALE;
        }

        if (createStudent(name, sex, classId).isEmpty()) {
            request.setAttribute("tip", tipSuccess);
        } else {
            request.setAttribute("tip", tipFailed);
        }
        return "redirect:list/" + classId + "/" + mId;
    }

    @RequestMapping(value = "list/{classId}/{mId}")
    public String list(HttpServletRequest request,
                       @PathVariable String classId, @PathVariable String mId, RedirectAttributes ra) {
        logger.info("list classId:" + classId + ", mId:" + mId);
        ra.addFlashAttribute("classId", classId);//for hide
        ra.addFlashAttribute("mId", mId);
        return REDIRECT_LOCAL + "list";
    }

    @RequestMapping(value = "list")
    public String listChildren(HttpServletRequest request,
                               @ModelAttribute("classId") String classId,
                               @ModelAttribute("mId") String mId) {
        logger.info("listChildren classId:" + classId + ", mId:" + mId);
        request.setAttribute("classId", classId);
        request.setAttribute("children", gartenServiceProxy.getChildrenByClassId(classId));
        request.setAttribute("mId", mId);
        return "manage/garten/children/list";
    }

    @RequestMapping(value = "edit/{childId}/{mId}")
    public String edit(HttpServletRequest request,
                       @PathVariable String childId, @PathVariable String mId, RedirectAttributes ra) {
        logger.info("edit childId:" + childId + ", mId:" + mId);
        if (StringUtils.isEmpty(childId))
            return "/garten/manage/garten/index";
        ra.addFlashAttribute("childId", childId);//for hide
        ra.addFlashAttribute("mId", mId);
        return REDIRECT_LOCAL + "edit";
    }

    @RequestMapping(value = "edit")
    public String edit(HttpServletRequest request,
                       @ModelAttribute("childId") String childId,
                       @ModelAttribute("mId") String mId) {
        //String childId = request.getParameter("childId");
        logger.info("edit childId:" + childId + ", mId:" + mId);
        if (StringUtils.isEmpty(childId))
            return "/garten/manage/garten/index";
        GartenUser child = gartenServiceProxy.getUser(childId);
        if (child == null)
            return "/garten/manage/garten/index";
        Face face = faceServiceProxy.getFaceByGartenUserId(childId);
        request.setAttribute("child", child);
        request.setAttribute("faceImage", face == null ? "" : face.getUrl());
        request.setAttribute("sex", baseUserServiceProxy.get(child.getBaseUserId()).getSex());
        request.setAttribute("classId", gartenServiceProxy.getByGartenUserId(childId).getId());
        request.setAttribute("mId", mId);
        List<GartenUser> parents = gartenServiceProxy.getParentsByChild(childId);
        request.setAttribute("parents", parents);
        boolean bizCloseup = gartenServiceProxy.checkPermissionBizByGarten(child.getGartenId(), BizVideoType.CLOSEUP);
        request.setAttribute("bizCloseup", bizCloseup);
        if (bizCloseup) {
            request.setAttribute("videos", gartenServiceProxy.getUserVideoAllByChildId(childId));
        }

        if (!StringUtils.isEmpty(child.getGartenId())) {
            List<GartenClass> classes = gartenServiceProxy.getAllClassesByGartenId(child.getGartenId());
            request.setAttribute("gClasses", classes);
        }
        return "manage/garten/children/edit";
    }

    @RequestMapping(value = "edit_submit", method = RequestMethod.POST)
    public String editSubmit(HttpServletRequest request) {
        String id = request.getParameter("childId");
        String name = request.getParameter("name");
        String classId = request.getParameter("classId");
        String mId = request.getParameter("mId");
        String sex = request.getParameter("sex");
        //String live      = request.getParameter("live");
        //String closeup   = request.getParameter("closeup");
        logger.info("editSubmit, n:" + name + ", classId:" + classId + ", mId:" + mId + ", s:" + sex
                /*+", live:"+live + ", closeup:"+closeup*/);
        if (StringUtils.isEmpty(name)) {
            return "redirect:/garten/manage/child/list/" + classId + "/" + mId;
        }
        try {
            GartenUser child = gartenServiceProxy.getUser(id);
            child.setAliasName(name);
            gartenServiceProxy.saveUser(child);
            BaseUser buChild = baseUserServiceProxy.get(child.getBaseUserId());
            buChild.setSex(SexType.valueOf(sex));
            buChild.setName(name);
            baseUserServiceProxy.update(buChild);
            List<BaseRole> roles = gartenServiceProxy.getAllGartenRoleByGartenUserId(child.getId());
            BaseRole brChild = baseServiceProxy.getBaseRoleById(roles.get(0).getId());
            brChild.setName(name);
            baseServiceProxy.updateBaseRole(brChild);
            
            /*List<GartenUser> parents = gartenUserController.getParentsByChild(id);
            for (GartenUser p:parents) {
                BaseUserCapacity capacity = baseServiceProxy.getUserCapacity(p.getBaseUserId());
                if (capacity == null) {
                    capacity = new BaseUserCapacity();
                    capacity.setBaseUserId(p.getBaseUserId());
                }
                    
                capacity.setRegisteredVideoLive(!StringUtils.isEmpty(live));
                capacity.setRegisteredVideoCloseup(!StringUtils.isEmpty(closeup));
                baseServiceProxy.updateUserCapacity(capacity);
            }*/
            request.setAttribute("tip", tipSuccess);
        } catch (Exception e) {
            logger.info("editSubmit exception:" + e);
            request.setAttribute("tip", tipFailed);
        }
        return "redirect:/garten/manage/child/list/" + classId + "/" + mId;
    }

    @RequestMapping(value = "del_submit", method = RequestMethod.POST)
    public String delSubmit(HttpServletRequest request) {
        String childId = request.getParameter("childId");
        String classId = request.getParameter("classId");
        String mId = request.getParameter("mId");
        logger.info("delSubmit childId:" + childId + ", classId:" + classId + ", mId:" + mId);
        logger.info("delSubmit ret:" + deleteStudent(childId));
        return "redirect:/garten/manage/child/list/" + classId + "/" + mId;
    }

    @RequestMapping("/video_upload")
    public String uploadVideoPage(Model model) {
        logger.info("uploadVideoPage");
        return "manage/garten/children/upload";
        //return "manage/garten/children/uploadVideo";
    }

    @RequestMapping("/video_upload/get_signature")
    @ResponseBody
    public Map<String, Object> getSignature(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        String s = vodSignature.getUploadSignature();
        result.put("success", true);
        result.put("signature", s);
        logger.info("getSignature, f:" + request.getParameter("f") + ", s:" + s);
        return result;
    }

    @RequestMapping(value = "/video_add", method = RequestMethod.POST)
    public String gartenUserVideoAddPage(HttpServletRequest request) {
        String childId = request.getParameter("childId");
        String mId = request.getParameter("mId");
        request.setAttribute("childId", childId);
        request.setAttribute("mId", mId);
        return "manage/garten/children/video";
    }

    @RequestMapping(value = "/video_edit", method = RequestMethod.POST)
    public String gartenUserVideoEdit(HttpServletRequest request) {
        String id = request.getParameter("videoId");
        String mId = request.getParameter("mId");
        GartenUserVideo v = gartenServiceProxy.getUserVideoById(id);
        if (v != null) {
            request.setAttribute("childId", v.getChildId());
            request.setAttribute("videoId", v.getId());
            String videoId = v.getVideoId();
            if (StringUtils.isEmpty(videoId) || videoId.startsWith("http://"))
                request.setAttribute("videoUrl", videoId);
            else {
                UserVideo uv = videoServiceProxy.getUserVideo(v.getVideoId());
                request.setAttribute("videoUrl", uv == null ? "" : uv.getVideoUrl());
                request.setAttribute("coverUrl", uv == null ? "" : uv.getCoverUrl());
            }
            request.setAttribute("id", v.getId());
            request.setAttribute("type", v.getType());
            request.setAttribute("videoDesc", v.getVideoDesc());
            request.setAttribute("videoDescShare", v.getVideoDescShare());
            request.setAttribute("qrcodeSn", v.getQrcodeSn());
            request.setAttribute("qrcodeUrl", v.getQrcodeUrl());
            request.setAttribute("mId", mId);
        }
        return "manage/garten/children/video";
    }

    @RequestMapping(value = "/video_submit", method = RequestMethod.POST)
    public String gartenUserVideoSubmit(Model model, HttpServletRequest request) {
        String id = request.getParameter("id");
        String mId = request.getParameter("mId");
        String childId = request.getParameter("childId");
        String videoUrl = request.getParameter("videoUrl");
        String coverUrl = request.getParameter("coverUrl");
        String cameraId = request.getParameter("cameraId");
        String type = request.getParameter("type");
        String videoDesc = request.getParameter("videoDesc");
        String videoDescShare = request.getParameter("videoDescShare");
        logger.info("gartenUserVideoSubmit, id:" + id + ", v:" + videoUrl + ", c:" + coverUrl + ", mId:" + mId);
        UserVideo uv = null;
        if (!StringUtils.isEmpty(videoUrl)) {
            uv = videoServiceProxy.getUserVideoByUrl(videoUrl);
        }
        if (uv == null) {
            uv = videoServiceProxy.createUserVideo(videoUrl, coverUrl, cameraId);
            UploadVodResult vod = new UploadVodResult();
            vod.setChildId(childId);
            //vod.setCameraId(cameraId);
            //vod.setVideoUrl(videoUrl);
            //vod.setCoverUrl(coverUrl);
            vod.setVideoDesc(videoDesc);
            vod.setVideoDescShare(videoDescShare);
            vod.setVideoType(type);
            gartenServiceProxy.saveVideo(uv.getId(), vod);
        } else {
            uv.setCoverUrl(coverUrl);
            uv = videoServiceProxy.saveUserVideo(uv);
            GartenUserVideo v = gartenServiceProxy.getUserVideoById(id);
            if (v != null) {
                v.setVideoDesc(videoDesc);
                v.setVideoDescShare(videoDescShare);
                gartenServiceProxy.saveUserVideo(v);
            }
        }
        return "redirect:/garten/manage/child/edit/" + childId + "/" + mId;
    }

    @RequestMapping(value = "/video_remove", method = RequestMethod.POST)
    public String gartenUserVideoRemove(HttpServletRequest request) {
        String id = request.getParameter("videoId");
        String mId = request.getParameter("mId");
        logger.info("gartenUserVideoRemove, id:" + id + ", mId:" + mId);
        GartenUserVideo guv = gartenServiceProxy.getUserVideoById(id);
        if (guv != null) {
            videoServiceProxy.deleteUserVideo(guv.getVideoId());
            gartenServiceProxy.deleteUserVideo(id);
        }
        String childId = request.getParameter("childId");
        return "redirect:/garten/manage/child/edit/" + childId + "/" + mId;
    }

    @RequestMapping(value = "face_add", method = RequestMethod.POST)
    public String faceImageUpdate(@RequestParam MultipartFile file, HttpServletRequest request)
            throws Exception {
        String childId = request.getParameter("childId");
        String mId = request.getParameter("mId");
        logger.info("faceImageUpdate, file:" + file.getOriginalFilename() + ", childId:" + childId + ", mId:" + mId);
        if (!file.isEmpty()) {
            if (request instanceof MultipartHttpServletRequest) {
                InputStream inStream = file.getInputStream();
                byte[] data = readInputStream(inStream);
                File imageFile = new File("./" + NAME_PREFIX_FACE
                        + RandomStringUtils.randomAlphanumeric(5) + Constant.MEDIA_FORMAT_JPG);
                FileOutputStream outStream = new FileOutputStream(imageFile);
                outStream.write(data);
                outStream.close();
                String path = gartenServiceProxy.getUser(childId).getGartenId() + "/" + childId;
                String faceUrl = videoServiceProxy.uploadFaceImage(imageFile, path);
                logger.info("faceImageUpdate, result:" + faceUrl + ", fileSize:" + file.getSize());
                YtPersonResult result = faceServiceProxy.updateFrFace(childId, imageFile, "");
                if (result != null && result.getCode() == 0) {
                    Face face = new Face();
                    face.setBytes(file.getSize());
                    face.setUrl(faceUrl);
                    faceServiceProxy.updateFaceData(childId, result, face);
                    logger.info("faceImageUpdate, personResult:" + result + ", face:" + face);
                }
                imageFile.delete();
            }
        }
        return "redirect:/garten/manage/child/edit/" + childId + "/" + mId;
    }

    private static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

    protected String createStudent(String name, SexType sex, String classId) {
        String childId = "";
        BaseUser buChild = new BaseUser();
        buChild.setGlobalId(UuidUtil.randomWithMd5Encrypted());
        buChild.setName(name);
        buChild.setSex(sex);
        buChild = baseUserServiceProxy.create(buChild);
        logger.info("createStudent, baseUser:" + buChild);

        BaseRole brChild = new BaseRole();
        brChild.setName(name);
        brChild.setRoleType(Constant.RoleType.CHILD);
        brChild = baseServiceProxy.createBaseRole(brChild);
        logger.info("createStudent, buRole:" + brChild);

        GartenUser child = new GartenUser();
        child.setAliasName(name);
        child.setBaseUserId(buChild.getId());
        child.setGartenId(gartenServiceProxy.getClass(classId).getGartenId());
        child = gartenServiceProxy.createUser(child);
        if (child != null) {
            childId = child.getGartenId();
            gartenServiceProxy.bindUserAndRole(child, brChild);
            logger.info("createStudent, child:" + child);

            GartenUser2GartenClass u2c = new GartenUser2GartenClass();
            u2c.setGartenUserId(child.getId());
            u2c.setGartenClassId(classId);
            gartenServiceProxy.saveU2C(u2c);
            logger.info("createStudent, u2c:" + u2c);
        } else {
            logger.error("Create garten user child failed !");
        }
        return childId;
    }

    protected boolean deleteStudent(String childId) {
        if (StringUtils.isEmpty(childId)) {
            return false;
        }
        if (!gartenServiceProxy.getParentsByChild(childId).isEmpty()) {
            logger.error("deleteStudent, please delete parents first.");
            return false;
        }
        GartenUser child = gartenServiceProxy.getUser(childId);
        logger.info("deleteStudent, childId:" + childId + ", buId:" + child.getBaseUserId());

        gartenServiceProxy.deleteChild(childId);
        if (gartenServiceProxy.getUser(childId) != null) {
            logger.error("deleteStudent, delete child failed.");
            return false;
        }

        baseUserServiceProxy.delete(child.getBaseUserId());
        logger.info("deleteStudent, finish.");
        faceServiceProxy.clearPerson(childId);
        return true;
    }

}
