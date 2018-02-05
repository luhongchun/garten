package cn.smart.cloud.biz.opadmin.controller.garten.fr;

import cn.smart.cloud.biz.opadmin.constant.Constant;
import cn.smart.cloud.biz.opadmin.controller.MessageRender;
import cn.smart.cloud.biz.opadmin.entity.BaseRole;
import cn.smart.cloud.biz.opadmin.entity.BaseUser;
import cn.smart.cloud.biz.opadmin.entity.fr.*;
import cn.smart.cloud.biz.opadmin.entity.garten.GartenClass;
import cn.smart.cloud.biz.opadmin.entity.garten.user.GartenUser;
import cn.smart.cloud.biz.opadmin.entity.garten.user.GartenUser2GartenClass;
import cn.smart.cloud.biz.opadmin.entity.garten.user.GartenUser2Role;
import cn.smart.cloud.biz.opadmin.gson.fr.*;
import cn.smart.cloud.biz.opadmin.gson.fr.youtu.FaceAddResult;
import cn.smart.cloud.biz.opadmin.gson.fr.youtu.YtDetectResult;
import cn.smart.cloud.biz.opadmin.gson.fr.youtu.YtFace;
import cn.smart.cloud.biz.opadmin.gson.fr.youtu.YtPersonCreateResult;
import cn.smart.cloud.biz.opadmin.proxy.BaseCoreServiceProxy;
import cn.smart.cloud.biz.opadmin.proxy.BaseUserServiceProxy;
import cn.smart.cloud.biz.opadmin.proxy.FaceServiceProxy;
import cn.smart.cloud.biz.opadmin.proxy.GartenServiceProxy;
import cn.smart.cloud.biz.opadmin.util.UuidUtil;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/operate/")
public class YtFrOpController {

    private Logger logger = LoggerFactory.getLogger(YtFrOpController.class);
    private static final String RESULT_CODE = "codeDesc";
    private static final String RESULT_CODE_OK = "Success";
    private static final String PROCESS_NAME_NONE = "ex";
    private static final String PROCESS_NAME_NEW = "add";
    private static final String PROCESS_NAME_DEL = "del";
    private static final String PROCESS_NAME_EDIT = "edit";

    @Autowired
    FaceServiceProxy faceServiceProxy;
    @Autowired
    GartenServiceProxy gartenServiceProxy;
    @Autowired
    BaseCoreServiceProxy baseServiceProxy;
    @Autowired
    BaseUserServiceProxy baseUserServiceProxy;

    @RequestMapping(value = "fr_test/{api}")
    public String frTest(@PathVariable String api, HttpServletRequest request) {
        return "";
    }

    @RequestMapping(value = "fr_group_list")
    public String frGroupList(HttpServletRequest request) {
        /*GroupList groupList = frService.infoGetGroupList();
        List<GroupOfFacepp> gList = groupList.getGroup();
		for (GroupOfFacepp g:gList) {
			logger.info("frGroupList, g:"+g);
		}*/
        List<Group> groupList = faceServiceProxy.getAllGroup();
        request.setAttribute("groupList", groupList);
        return "operate/fr_group_list";
    }

    @RequestMapping(value = "fr_group_sync")
    public String frGroupSync(HttpServletRequest request) {
        logger.info("frGroupSync");
        GroupList groupList = faceServiceProxy.infoGetGroupList();
        List<GroupOfFacepp> syncList = groupList.getGroup();
        for (GroupOfFacepp s : syncList) {
            logger.info("frGroupSync, s:" + s);
        }

        //TODO one by one organization
        List<Group> localGroupList = faceServiceProxy.getAllGroup();
        for (Group l : localGroupList) {
            logger.info("frGroupSync, l:" + l);
        }

        for (GroupOfFacepp s : syncList) {
            boolean handled = false;
            for (Group l : localGroupList) {
                if (l.getGroupId().equals(s.getGroup_id())) {
                    //old
                    logger.info("frGroupSync, old group.");
                    handled = true;

					/*if (s.getGroup_name().equals(l.getGroupName())
							&& s.getTag().equals(l.getTag())) {
						logger.info("frGroupSync, same content.");
					} else {
						l.setGroupName(s.getGroup_name());
						l.setTag(s.getTag());
						logger.info("frGroupSync, update group:"+l);
						personGroupRepository.save(l);
					}*/
                    //person
                    syncPerson(s, l);
                    break;
                }
            }

            if (handled) {
                continue;
            } else {
                //new
                Group group = new Group();
                group.setGroupId(s.getGroup_id());
                group.setGroupName(s.getGroup_name());
                group.setTag(s.getTag());
                logger.info("frGroupSync, new group:" + group);
                faceServiceProxy.saveGroup(group);
                //new person
                List<PersonOfFacepp> pList = faceServiceProxy.infoGetPersonList(group.getGroupId());
                for (PersonOfFacepp p : pList) {
                    logger.info("frGroupSync, new person:" + p);
                    savePersonAndRelatedData(p.getPerson_id(), group.getId());
                }
            }
        }

        for (Group l : localGroupList) {
            boolean expired = true;
            for (GroupOfFacepp s : syncList) {
                if (l.getGroupId().equals(s.getGroup_id())) {
                    expired = false;
                    break;
                }
            }
            if (expired) {
                logger.info("frGroupSync, expired group:" + l);
                //expired
                deleteExpiredGroup(l);
            }
        }
        logger.info("frGroupSync finished.");
        return "redirect:/operate/fr_group_list";
    }

    @RequestMapping(value = "fr_group_sync/{id}")
    public String frOneGroupSync(@PathVariable String id, HttpServletRequest request) {
        logger.info("frOneGroupSync, id:" + id);

        Group localGroup = faceServiceProxy.getGroup(id);
        GroupOfFacepp syncGroup = faceServiceProxy.getFaceGroupInfo(localGroup.getGroupId());
        if (localGroup.getGroupId().equals(syncGroup.getGroup_id())) {
            syncPerson(syncGroup, localGroup);
        }
        logger.info("frGroupSync finished.");
        return "redirect:/operate/fr_group_list";
    }

    @RequestMapping(value = "fr_group_info/{id}")
    public String frGroupInfo(@PathVariable String id,
                              HttpServletRequest request) {
        logger.info("frGroupInfo, id:" + id);
        Group group = faceServiceProxy.getGroup(id);
        List<Person> personList = new ArrayList<>();
        List<Person2Group> p2gList = faceServiceProxy.getPerson2GroupByGroupId(id);
        for (Person2Group p2g : p2gList) {
            personList.add(faceServiceProxy.getPersonByPersonId(p2g.getPersonId()));
        }
        request.setAttribute("group", group);
        request.setAttribute("personList", personList);
        return "operate/fr_group_info";
    }

    @RequestMapping(value = "fr_group_create")
    public String frGroupCreate(
            HttpServletRequest request) {
        logger.info("frGroupCreate");
        return "operate/fr_group_create";
    }

    @RequestMapping(value = "fr_group_create_submit")
    public void frGroupCreateSubmit(
            HttpServletRequest request,
            HttpServletResponse response) {
        String groupName = request.getParameter("groupName");
        logger.info("frGroupCreateSubmit, groupName:" + groupName);
        Group group = null;
        GroupOfFacepp groupOfFacepp = faceServiceProxy.createGroup(groupName);
        if (groupOfFacepp != null && !StringUtils.isBlank(groupOfFacepp.getGroup_id())) {
            logger.info("frGroupCreateSubmit, create group success.");
            group = new Group();
            group.setGroupId(groupOfFacepp.getGroup_id());
            group.setGroupName(groupOfFacepp.getGroup_name());
            group.setTag(groupOfFacepp.getTag());
            faceServiceProxy.saveGroup(group);
        } else {
            logger.warn("frGroupCreateSubmit, failed.");
        }
        MessageRender.renderJson(response, "/operate/fr_group_list");
    }

    @RequestMapping(value = "fr_group_create_with_persons")
    public String frGroupCreateWithPersons(
            HttpServletRequest request) {
        logger.info("frGroupCreateWithPersons");
        return "operate/fr_group_create_with_persons";
    }

    @RequestMapping(value = "fr_group_create_with_persons_submit")
    public void frGroupCreateWithPersonsSubmit(
            HttpServletRequest request,
            HttpServletResponse response) {
        //String gartenId = request.getParameter("gartenId");
        String classId = request.getParameter("classId");
        String groupName = request.getParameter("groupName");
        String dataPath = request.getParameter("dataPath");
        logger.info("frGroupCreateWithPersonsSubmit, name:" + groupName
                + ", classId:" + classId + ", path:" + dataPath);
        if (StringUtils.isBlank(classId) || StringUtils.isBlank(dataPath)) {
            logger.info("frGroupCreateWithPersonsSubmit, param invalid.");
            MessageRender.renderJson(response, "/operate/fr_group_list");
            return;
        }
        GartenClass gartenClass = gartenServiceProxy.getClass(classId);
        if (gartenClass == null) {
            logger.info("frGroupCreateWithPersonsSubmit, class not exist");
            MessageRender.renderJson(response, "/operate/fr_group_list");
            return;
        }
        String gartenId = gartenClass.getGartenId();
        if (StringUtils.isBlank(groupName)) {
            groupName = gartenClass.getName();
        }
        logger.info("frGroupCreateWithPersonsSubmit, gartenId:" + gartenId + ", groupName:" + groupName);

        Group group = null;
        String groupIdInFr = groupName;
        boolean groupCreated = false;
        if (faceServiceProxy.getGroupByName(groupName) != null) {
            logger.info("frGroupCreateWithPersonsSubmit, group exist. use it.");
            group = faceServiceProxy.getGroupByName(groupName);
            groupCreated = true;
        }
        File dataDir = new File(dataPath);
        if (dataDir.isDirectory()) {
            File[] personDirs = dataDir.listFiles();
            if (personDirs == null || personDirs.length <= 0) {
                logger.warn("frGroupCreateWithPersonsSubmit, no data in dataPath.");
            } else {
                for (File personDir : personDirs) {
                    if (!personDir.isDirectory() || personDir.listFiles().length <= 0) {
                        logger.warn("frGroupCreateWithPersonsSubmit, no data in personPath:"
                                + personDir.getAbsolutePath());
                        continue;
                    }
                    String personName = personDir.getName();
                    if (personName.startsWith(PROCESS_NAME_NONE)) {
                        logger.info("frGroupCreateWithPersonsSubmit, " + personName + " is old, conitnue.");
                        continue;
                    }
                    if (personName.startsWith(PROCESS_NAME_DEL)) {
                        logger.info("frGroupCreateWithPersonsSubmit, " + personName + " need delete.");
                        continue;//TODO delete data
                    }
                    if (personName.startsWith(PROCESS_NAME_EDIT)) {
                        personName = personName.replace(PROCESS_NAME_EDIT, "");
                        logger.info("frGroupCreateWithPersonsSubmit, edit " + personName);
                        String childId = initGartenChild(personName, gartenId, classId);
                        Child2Person child2Person = new Child2Person();
                        child2Person.setGartenUserId(childId);
                        Person p = faceServiceProxy.getPersonByPersonName(personName);
                        child2Person.setPersonId(p == null ? personName : p.getId());

                        faceServiceProxy.saveChild2Person(child2Person);
                        logger.info("initGartenChild, created child2Person:" + child2Person);
                        continue;
                    }
                    if (!personName.startsWith(PROCESS_NAME_NEW)) {
                        logger.warn("frGroupCreateWithPersonsSubmit, " + personName + " unsupport prefix.");
                        continue;
                    }
                    personName = personName.replace(PROCESS_NAME_NEW, "");
                    Person person = null;
                    String personId = UuidUtil.randomWithMd5Encrypted();
                    boolean personCreated = false;
                    File[] pictures = personDir.listFiles();
                    for (File pic : pictures) {
                        String imgPath = pic.getAbsolutePath();
                        logger.info("frGroupCreateWithPersonsSubmit p:" + imgPath);
                        if (!imgPath.endsWith("jpg") && !imgPath.endsWith("jpeg")
                                && !imgPath.endsWith("png"))
                            continue;
                        String face_id = "";
                        if (!personCreated) {// add face when create person
                            YtPersonCreateResult result = faceServiceProxy.personCreateByPath(groupIdInFr, personId, personName, imgPath, "");
                            if (result == null || result.getCode() != Constant.FR_RESULT_SUC) {
                                logger.info("frGroupCreateWithPersonsSubmit, add person to group failed.");
                            } else {
                                logger.info("frGroupCreateWithPersonsSubmit, create person succeed.");
                                if (!groupCreated) {
                                    groupCreated = true;
                                    group = new Group();
                                    group.setGroupId(groupIdInFr);
                                    group.setGroupName(groupIdInFr);
                                    group.setTag("");
                                    faceServiceProxy.saveGroup(group);
                                    Group2GartenClass group2GartenClass = new Group2GartenClass();
                                    group2GartenClass.setClassId(classId);
                                    group2GartenClass.setGroupId(group.getId());
                                    faceServiceProxy.saveGroup2GartenClass(group2GartenClass);
                                }

                                String childId = initGartenChild(personName, gartenId, classId);

                                person = new Person();
                                person.setPersonId(personId);
                                person.setPersonName(personName);
                                person.setTag("");
                                faceServiceProxy.savePerson(person);

                                Person2Group p2g = new Person2Group();
                                p2g.setGroupId(group.getId());
                                p2g.setPersonId(person.getId());
                                faceServiceProxy.savePerson2Group(p2g);

                                Child2Person child2Person = new Child2Person();
                                child2Person.setGartenUserId(childId);
                                child2Person.setPersonId(person.getId());
                                faceServiceProxy.saveChild2Person(child2Person);

                                personCreated = true;
                                face_id = result.getFace_id();
                            }
                        } else {// add face after create person
                            FaceAddResult result = faceServiceProxy.personAddFace(person.getPersonId(),
                                    imgPath);
                            if (result.getCode() == Constant.FR_RESULT_SUC) {
                                logger.info("frGroupCreateWithPersonsSubmit, success.");
                                face_id = result.getFace_ids().get(0);
                            } else {
                                logger.info("frGroupCreateWithPersonsSubmit, failed.");
                            }
                        }
                        if (StringUtils.isBlank(face_id)) {
                            logger.info("frGroupCreateWithPersonsSubmit, add face to person, no face id.");
                            continue;
                        }
                        logger.info("frGroupCreateWithPersonsSubmit, add face to person.");
                        Face face = new Face();
                        face.setFaceId(face_id);
                        face.setImgId("");
                        face.setTag("");
                        face.setUrl(imgPath);
                        faceServiceProxy.saveFace(face);

                        Face2Person face2Person = new Face2Person();
                        face2Person.setFaceId(face.getId());
                        face2Person.setPersonId(person.getId());
                        faceServiceProxy.saveFace2Person(face2Person);
                    }
                }
            }
        } else {
            logger.warn("frGroupCreateWithPersonsSubmit, dataPath error.");
        }
        logger.info("frGroupCreateWithPersonsSubmit, finished.");
        MessageRender.renderJson(response, "/operate/fr_group_list");
    }

    @RequestMapping(value = "fr_group_edit/{id}")
    public String frGroupEdit(@PathVariable String id, HttpServletRequest request) {
        Group group = faceServiceProxy.getGroup(id);
        request.setAttribute("group", group);
        logger.info("frGroupEdit, group:" + group);
        return "operate/fr_group_edit";
    }

    @RequestMapping(value = "fr_group_save")
    public void frGroupSave(HttpServletRequest request, HttpServletResponse response) {
        String groupId = request.getParameter("groupId");
        String groupName = request.getParameter("groupName");
        String tag = request.getParameter("tag");
        logger.info("frGroupSave, groupId:" + groupId);
        //TODO make the change on face recognize system
        Group group = faceServiceProxy.getGroupByGroupId(groupId);
        ;
        JSONObject result = faceServiceProxy.groupSetInfo(groupId, groupName, tag);
        try {
            if (result.getString(RESULT_CODE).equals(RESULT_CODE_OK)) {
                logger.info("frGroupSave, success.");
                if (group == null)
                    group = new Group();
                group.setGroupId(groupId);
                group.setGroupName(groupName);
                group.setTag(tag);
                faceServiceProxy.saveGroup(group);
            } else {
                logger.info("frGroupSave, failed.");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MessageRender.renderJson(response, "/operate/fr_group_info/" + (group == null ? "" : group.getId()));
    }

    @RequestMapping(value = "group_search")
    public String frGroupSearch(HttpServletRequest request) {
        logger.info("frGroupSearch");
        String group = "";
        request.setAttribute("group", group);
        return "operate/fr_person";
    }

    @RequestMapping(value = "fr_person_info/{id}")
    public String frPersonInfo(@PathVariable String id, HttpServletRequest request) {
        request.setAttribute("person", faceServiceProxy.getPerson(id));
        List<Face> faceList = new ArrayList<>();
        List<Face2Person> f2pList = faceServiceProxy.getFace2PersonByPersonId(id);
        for (Face2Person f2p : f2pList) {
            faceList.add(faceServiceProxy.getFace(f2p.getFaceId()));
        }
        request.setAttribute("faceList", faceList);
        return "operate/fr_person_info";
    }

    @RequestMapping(value = "fr_person_create/{groupId}")
    public String frPersonCreate(@PathVariable String groupId,
                                 HttpServletRequest request) {
        logger.info("frPersonCreate, groupId:" + groupId);
        request.setAttribute("groupId", groupId);
        return "operate/fr_person_create";
    }

    @RequestMapping(value = "fr_person_create_submit")
    public void frPersonCreateSubmit(
            HttpServletRequest request,
            HttpServletResponse response) {
        String groupId = request.getParameter("groupId");
        String personName = request.getParameter("personName");
        String imgUrl = request.getParameter("imgUrl");
        logger.info("frPersonCreateSubmit, groupId:" + groupId + ", personName:" + personName
                + ", imgUrl:" + imgUrl);
        Person person = null;
        String personId = UuidUtil.randomWithMd5Encrypted();
        YtPersonCreateResult result = faceServiceProxy
                .personAddFaces(faceServiceProxy.getGroup(groupId).getGroupId(),
                        personId, personName, imgUrl, "");
        if (result.getCode() == Constant.FR_RESULT_SUC) {
            logger.info("frPersonCreateSubmit, add person to group success.");
            person = new Person();
            person.setPersonId(personId);
            person.setPersonName(personName);
            person.setTag("");
            faceServiceProxy.savePerson(person);

            Person2Group p2g = new Person2Group();
            p2g.setGroupId(groupId);
            p2g.setPersonId(person.getId());
            faceServiceProxy.savePerson2Group(p2g);

            logger.info("frPersonCreateSubmit, add face to person.");
            Face face = new Face();
            face.setFaceId(result.getFace_id());
            face.setImgId("");
            face.setTag("");
            face.setUrl(imgUrl);
            faceServiceProxy.saveFace(face);

            Face2Person face2Person = new Face2Person();
            face2Person.setFaceId(face.getId());
            face2Person.setPersonId(person.getId());
            faceServiceProxy.saveFace2Person(face2Person);
        } else {
            logger.info("frPersonCreateSubmit, add person to group failed.");
        }
        MessageRender.renderJson(response, "/operate/fr_group_info/" + groupId);
    }

    @RequestMapping(value = "fr_person_edit/{id}")
    public String frPersonEdit(@PathVariable String id, HttpServletRequest request) {
        Person person = faceServiceProxy.getPerson(id);
        request.setAttribute("person", person);
        logger.info("frPersonEdit, person:" + person);
        return "operate/fr_person_edit";
    }

    @RequestMapping(value = "fr_person_save")
    public void frPersonSave(
            HttpServletRequest request,
            HttpServletResponse response) {
        String personId = request.getParameter("personId");
        String personName = request.getParameter("personName");
        String tag = request.getParameter("tag");
        logger.info("frPersonSave, personId:" + personId);

        Person person = faceServiceProxy.getPersonByPersonId(personId);
        FrResult result = faceServiceProxy.personSetInfo(personId, personName, tag);
        if (result.getCode() == Constant.FR_RESULT_SUC) {
            logger.info("frPersonSave, success.");
            if (person == null)
                person = new Person();
            person.setPersonId(personId);
            person.setPersonName(personName);
            person.setTag(tag);
            faceServiceProxy.savePerson(person);
        } else {
            logger.info("frPersonSave, failed.");
        }
        MessageRender.renderJson(response, "/operate/fr_person_info/" + (person == null ? "" : person.getId()));
    }

    @RequestMapping(value = "fr_person_add_face")
    public void frPersonAddFace(
            HttpServletRequest request,
            HttpServletResponse response) {
        String personId = request.getParameter("personId");
        String imgPath = request.getParameter("imgUrl");
        logger.info("frPersonAddFace, personId:" + personId + ", imgPath:" + imgPath);
        FaceAddResult result = faceServiceProxy.personAddFace(faceServiceProxy.getPerson(personId).getPersonId(),
                imgPath);
        if (result.getCode() == Constant.FR_RESULT_SUC) {
            logger.info("frPersonAddFace, success.");
            Face face = new Face();
            face.setFaceId(result.getFace_ids().get(0));
            face.setImgId("");
            face.setTag("");
            face.setUrl(imgPath);
            faceServiceProxy.saveFace(face);

            Face2Person face2Person = new Face2Person();
            face2Person.setFaceId(face.getId());
            face2Person.setPersonId(personId);
            faceServiceProxy.saveFace2Person(face2Person);
        } else {
            logger.info("frPersonAddFace, failed.");
        }
        MessageRender.renderJson(response, "/operate/fr_person_info/" + personId);
    }

    @RequestMapping(value = "fr_face_info/{id}/{personId}")
    public String frFaceList(@PathVariable String id,
                             @PathVariable String personId,
                             HttpServletRequest request) {
        request.setAttribute("face", faceServiceProxy.getFace(id));
        request.setAttribute("personId", personId);
        return "operate/fr_face_info";
    }

    @RequestMapping(value = "fr_face_create/{personId}")
    public String frFaceCreate(@PathVariable String personId,
                               HttpServletRequest request) {
        logger.info("frFaceCreate, personId:" + personId);
        return "operate/fr_face_create";
    }

    @RequestMapping(value = "fr_face_create_submit/{personId}/{id}")
    public String frFaceCreateSubmit(@PathVariable String personId,
                                     @PathVariable String id,
                                     HttpServletRequest request,
                                     HttpServletResponse response) {
        logger.info("frFaceCreateSubmit, faceId:" + id + ", personId:" + personId);
        if (StringUtils.isBlank(personId) || StringUtils.isBlank(id)) {
            return "redirect:/operate/fr_person_info/" + personId;
        }
        //TODO FIX
		/*Face2Person face2Person = null;
		JSONObject result = frService.personAddFace(
				personRepository.findOne(personId).getPersonId(), faceRepository.findOne(id).getUrl());
		try {
			if (result.getString(RESULT_CODE).equals(RESULT_CODE_OK)) {
				logger.info("frFaceCreateSubmit, add face to person success.");
				face2Person = new Face2Person();
				face2Person.setFaceId(id);
				face2Person.setPersonId(personId);
				face2PersonRepository.save(face2Person);
			} else {
				logger.warn("frFaceCreateSubmit, add face to person failed.");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}*/
        return "redirect:/operate/fr_person_info/" + personId;
    }

    @RequestMapping(value = "fr_face_delete/{id}/{personId}")
    public String frFaceDelete(@PathVariable String id,
                               @PathVariable String personId,
                               HttpServletRequest request) {
        logger.info("frFaceDelete, id:" + id);
        FrResult result = faceServiceProxy.personRemoveFace(faceServiceProxy
                .getPersonByPersonId(personId).getPersonId(), faceServiceProxy.getFace(id).getFaceId());
        if (result.getCode() == Constant.FR_RESULT_SUC) {
            logger.info("frFaceDelete, delete face from person success.");
            faceServiceProxy.deleteFace2Person(faceServiceProxy.getFace2PersonByFaceId(id).getId());
            Face face = new Face();
            face.setId(id);
            faceServiceProxy.deleteFace(face);
        } else {
            logger.warn("frFaceDelete, delete face from person failed.");
        }
        return "redirect:/operate/fr_person_info/" + personId;
    }

    @RequestMapping(value = "fr_face_detect")
    public void frFaceDetect(
            HttpServletRequest request,
            HttpServletResponse response) {
        String personId = request.getParameter("personId");
        String imgUrl = request.getParameter("imgUrl");
        logger.info("frFaceDetect, personId:" + personId + ", imgUrl:" + imgUrl);
        Face face = null;
        YtDetectResult result = null;
        if (imgUrl.startsWith("http://")) {
            result = faceServiceProxy.detectionDetectByUrl(imgUrl);
        } else {
            result = faceServiceProxy.detectionDetectByPath(imgUrl);
        }

        List<YtFace> faceList = result.getFace();
        if (faceList != null && !faceList.isEmpty()) {
            logger.warn("frFaceDetect, success, cnt:" + faceList.size());
            YtFace ytFace = faceList.get(0);
            face = new Face();
            face.setFaceId(ytFace.getFace_id());
            face.setImgId("");
            face.setTag("");
            face.setUrl(imgUrl);
            faceServiceProxy.saveFace(face);
        } else {
            logger.warn("frFaceDetect, no face.");
        }
        if (face == null) {
            MessageRender.renderJson(response, "/operate/fr_person_info/" + personId);
            return;
        }

        MessageRender.renderJson(response, "/operate/fr_face_create_submit/" + personId + "/" + face.getId());
    }

    @RequestMapping(value = "fr_train_identify/{id}")
    public String frTrainIdentify(@PathVariable String id,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {
        logger.info("frTrainIdentify, id:" + id);
        faceServiceProxy.trainIdentify(faceServiceProxy.getGroup(id).getGroupId());
        return "redirect:/operate/fr_group_info/" + id;
    }

    private void syncPerson(GroupOfFacepp syncGroup, Group localGroup) {//TODO handle table child class
        logger.info("syncPerson");
        GroupOfFacepp groupOfFacepp = faceServiceProxy.getFaceGroupInfo(syncGroup.getGroup_id());
        List<PersonOfFacepp> syncList = groupOfFacepp.getPerson();

        List<Person2Group> p2gList = faceServiceProxy.getPerson2GroupByGroupId(localGroup.getId());
        List<Person> localPersonList = new ArrayList<>();
        for (Person2Group p2g : p2gList) {
            localPersonList.add(faceServiceProxy.getPerson(p2g.getPersonId()));
        }

        for (PersonOfFacepp s : syncList) {
            boolean handled = false;
            for (Person l : localPersonList) {
                if (l.getPersonId().equals(s.getPerson_id())) {
                    handled = true;
                    //old
                    logger.info("syncPerson, s:" + s + ", l:" + l);
                    if (s.getPerson_name().equals(l.getPersonName())
                            && s.getTag().equals(l.getTag())) {
                        //same
                        logger.info("syncPerson, same content.");
                    } else {
                        l.setPersonName(s.getPerson_name());
                        l.setTag(s.getTag());
                        logger.info("syncPerson, update person:" + l);
                        faceServiceProxy.savePerson(l);
                    }
                    //face
                    syncFace(s, l);
                    break;
                }
            }

            if (handled) {
                continue;
            } else {
                //new
                savePersonAndRelatedData(s.getPerson_id(), localGroup.getId());
            }
        }

        for (Person l : localPersonList) {
            boolean expired = true;
            for (PersonOfFacepp s : syncList) {
                if (l.getPersonId().equals(s.getPerson_id())) {
                    expired = false;
                    break;
                }
            }
            if (expired) {
                logger.info("syncPerson, delete person:" + l);
                //expired
                deleteExpiredPerson(l.getId(), localGroup.getId());
            }
        }
    }

    private void syncFace(PersonOfFacepp syncPerson, Person localPerson) {
        PersonOfFacepp personOfFacepp = syncPerson;//frService.personGetInfo(syncPerson.getPerson_id());
        List<FaceOfFacepp> syncList = personOfFacepp.getFace();

        List<Face2Person> f2pList = faceServiceProxy.getFace2PersonByPersonId(localPerson.getId());
        List<Face> localFaceList = new ArrayList<>();
        for (Face2Person f2p : f2pList) {
            localFaceList.add(faceServiceProxy.getFace(f2p.getFaceId()));
        }

        for (FaceOfFacepp s : syncList) {
            boolean handled = false;
            for (Face l : localFaceList) {
                if (l.getFaceId().equals(s.getFace_id())) {
                    handled = true;
                    //old
					/*if (s.getTag().equals(l.getTag())) {
						//same
					} else {
						l.setTag(s.getTag());
						logger.info("syncFace, update face:"+l);
						faceRepository.save(l);
					}*/
                    break;
                }
            }

            if (handled) {
                continue;
            } else {
                //new
                Face face = new Face();
                face.setFaceId(s.getFace_id());
                face.setImgId("");
                face.setTag("");
                faceServiceProxy.saveFace(face);
                logger.info("syncFace, new face:" + face);
                Face2Person face2Person = new Face2Person();
                face2Person.setPersonId(localPerson.getId());
                face2Person.setFaceId(face.getId());
                faceServiceProxy.saveFace2Person(face2Person);
            }
        }

        for (Face l : localFaceList) {
            boolean expired = true;
            for (FaceOfFacepp s : syncList) {
                if (l.getFaceId().equals(s.getFace_id())) {
                    expired = false;
                    break;
                }
            }
            if (expired) {
                logger.info("syncFace, delete face:" + l);
                //expired
                faceServiceProxy.deleteFace2Person(faceServiceProxy.getFace2PersonByFaceId(l.getId()).getId());
                faceServiceProxy.deleteFace(l);
            }
        }
    }

    private void deleteExpiredGroup(Group group) {//TODO handle table child class
        List<Person2Group> p2gList = faceServiceProxy.getPerson2GroupByGroupId(group.getId());
        for (Person2Group p2g : p2gList) {
            logger.info("deleteExpiredGroup, delete p2g:" + p2g);
            faceServiceProxy.deletePerson2Group(p2g.getId());
        }
        logger.info("deleteExpiredGroup, delete group:" + group);
        faceServiceProxy.deleteGroup(group);
    }

    private void deleteExpiredPerson(String personId, String groupId) {//TODO handle table child2person
        List<Face2Person> f2pList = faceServiceProxy.getFace2PersonByPersonId(personId);
        for (Face2Person f2p : f2pList) {
            logger.info("deleteExpiredPerson, delete f2p:" + f2p);
            faceServiceProxy.deleteFace(faceServiceProxy.getFace(f2p.getFaceId()));
            faceServiceProxy.deleteFace2Person(f2p.getId());
        }
        faceServiceProxy.deletePerson2Group(faceServiceProxy
                .getPerson2GroupByPersonIdAndGroupId(personId, groupId).getId());
        faceServiceProxy.deletePerson(personId);
    }

    private void savePersonAndRelatedData(String personId, String groupId) {
        logger.info("savePersonAndRelatedData");
        Person person = new Person();
        person.setPersonId(personId);
        PersonOfFacepp personOfFacepp = faceServiceProxy.personGetInfo(personId);
        logger.info("savePersonAndRelatedData, new person:" + personOfFacepp);
        person.setPersonName(personOfFacepp.getPerson_name());
        person.setTag(personOfFacepp.getTag());
        faceServiceProxy.savePerson(person);
        Person2Group person2Group = new Person2Group();
        person2Group.setGroupId(groupId);
        person2Group.setPersonId(person.getId());
        faceServiceProxy.savePerson2Group(person2Group);
        List<FaceOfFacepp> faceList = personOfFacepp.getFace();
        for (FaceOfFacepp faceOfFacepp : faceList) {
            logger.info("savePersonAndRelatedData, new face:" + faceOfFacepp);
            Face face = new Face();
            face.setFaceId(faceOfFacepp.getFace_id());
            face.setImgId("");
            face.setTag("");
            faceServiceProxy.saveFace(face);
            Face2Person face2Person = new Face2Person();
            face2Person.setPersonId(person.getId());
            face2Person.setFaceId(face.getId());
            faceServiceProxy.saveFace2Person(face2Person);
        }
    }

    @RequestMapping(value = "fr_biz_data_init")
    public void frBizDataInit(HttpServletRequest request,
                              HttpServletResponse response) {
        //int result = -1;
        String gInfo = request.getParameter("gartenName");
        String dataPath = request.getParameter("dataPath");
        logger.info("frBizDataInit, gartenName:" + gInfo + ", path:" + dataPath);

        if (StringUtils.isBlank(gInfo) || StringUtils.isBlank(dataPath)) {//TODO add pattern match
            logger.error("frBizDataInit, param invalid.");
            MessageRender.renderJson(response, "/operate/fr_group_list");
            return;
        }
        String[] gInfoStrings = gInfo.split("_");
        String gartenName = gInfoStrings[0];
        String gartenId = gInfoStrings[1];

        if (gartenServiceProxy.get(gartenId) != null) {
            logger.info("frBizDataInit, group exist.");
            MessageRender.renderJson(response, "/operate/fr_group_list");
            return;
        }
        Group group = null;
        String groupIdInFr = gartenName;
        boolean groupCreated = false;
        File dataDir = new File(dataPath);
        if (dataDir.isDirectory()) {
            File[] personDirs = dataDir.listFiles();
            if (personDirs == null || personDirs.length <= 0) {
                logger.warn("frBizDataInit, no data in dataPath.");
            } else {
                for (File personDir : personDirs) {
                    if (!personDir.isDirectory() || personDir.listFiles().length <= 0) {
                        logger.warn("frBizDataInit, no data in personPath:"
                                + personDir.getAbsolutePath());
                        continue;
                    }
                    Person person = null;
                    String personName = personDir.getName();
                    String personId = UuidUtil.randomWithMd5Encrypted();
                    boolean personCreated = false;
                    File[] pictures = personDir.listFiles();
                    for (File pic : pictures) {
                        String imgPath = pic.getAbsolutePath();
                        logger.info("frBizDataInit p:" + imgPath);
                        if (!imgPath.endsWith("jpg") && !imgPath.endsWith("jpeg")
                                && !imgPath.endsWith("png"))
                            continue;
                        String face_id = "";
                        if (!personCreated) {// add face when create person
                            YtPersonCreateResult result = faceServiceProxy.personCreateByPath(groupIdInFr, personId, personName, imgPath, "");
                            if (result == null || result.getCode() != Constant.FR_RESULT_SUC) {
                                logger.info("frBizDataInit, add person to group failed.");
                            } else {
                                logger.info("frBizDataInit, create person succeed.");
                                if (!groupCreated) {
                                    groupCreated = true;
                                    group = new Group();
                                    group.setGroupId(groupIdInFr);
                                    group.setGroupName(groupIdInFr);
                                    group.setTag("");
                                    faceServiceProxy.saveGroup(group);
                                }

                                person = new Person();
                                person.setPersonId(personId);
                                person.setPersonName(personName);
                                person.setTag("");
                                faceServiceProxy.savePerson(person);

                                Person2Group p2g = new Person2Group();
                                p2g.setGroupId(group.getId());
                                p2g.setPersonId(person.getId());
                                faceServiceProxy.savePerson2Group(p2g);

                                personCreated = true;
                                face_id = result.getFace_id();
                            }
                        } else {// add face after create person
                            FaceAddResult result = faceServiceProxy.personAddFace(person.getPersonId(),
                                    imgPath);
                            if (result.getCode() == Constant.FR_RESULT_SUC) {
                                logger.info("frBizDataInit, success.");
                                face_id = result.getFace_ids().get(0);
                            } else {
                                logger.info("frBizDataInit, failed.");
                            }
                        }
                        if (StringUtils.isBlank(face_id)) {
                            logger.info("frBizDataInit, add face to person, no face id.");
                            continue;
                        }
                        logger.info("frBizDataInit, add face to person.");
                        Face face = new Face();
                        face.setFaceId(face_id);
                        face.setImgId("");
                        face.setTag("");
                        face.setUrl(imgPath);
                        faceServiceProxy.saveFace(face);

                        Face2Person face2Person = new Face2Person();
                        face2Person.setFaceId(face.getId());
                        face2Person.setPersonId(person.getId());
                        faceServiceProxy.saveFace2Person(face2Person);
                    }
                }
            }
        } else {
            logger.warn("frBizDataInit, dataPath error.");
        }
        logger.info("frBizDataInit, finished.");
        MessageRender.renderJson(response, "/operate/fr_group_list");
    }

    private String initGartenChild(String personName, String gartenId, String classId) {
        BaseUser baseUser = new BaseUser();
        baseUser.setName(personName + "小朋友");
        baseUser = baseUserServiceProxy.create(baseUser);
        logger.info("initGartenChild, created baseUser:" + baseUser);

        BaseRole baseRole = new BaseRole();
        baseRole.setName(personName);
        baseRole.setRoleType(Constant.RoleType.CHILD);
        baseRole = baseServiceProxy.createBaseRole(baseRole);
        logger.info("initGartenChild, created baseRole:" + baseRole);

        GartenUser child = new GartenUser();
        child.setAliasName(personName);
        child.setBaseUserId(baseUser.getId());
        child.setGartenId(gartenId);
        child = gartenServiceProxy.saveUser(child);
        logger.info("initGartenChild, created child:" + child);

        GartenUser2Role gartenUser2Role = new GartenUser2Role();
        gartenUser2Role.setBaseRoleId(baseRole.getId());
        gartenUser2Role.setGartenUserId(child.getId());
        gartenServiceProxy.saveGartenUser2Role(gartenUser2Role);
        logger.info("initGartenChild, created gartenUser2Role:" + gartenUser2Role);

        GartenUser2GartenClass user2GartenClass = new GartenUser2GartenClass();
        user2GartenClass.setGartenClassId(classId);
        user2GartenClass.setGartenUserId(child.getId());
        gartenServiceProxy.saveU2C(user2GartenClass);
        return child.getId();
    }

}