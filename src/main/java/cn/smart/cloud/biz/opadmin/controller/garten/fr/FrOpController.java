package cn.smart.cloud.biz.opadmin.controller.garten.fr;

import cn.smart.cloud.biz.opadmin.controller.MessageRender;
import cn.smart.cloud.biz.opadmin.entity.fr.*;
import cn.smart.cloud.biz.opadmin.gson.fr.*;
import cn.smart.cloud.biz.opadmin.proxy.FaceOnlineServiceProxy;
import cn.smart.cloud.biz.opadmin.proxy.FaceServiceProxy;
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
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/operate/facepp/")
public class FrOpController {

    private Logger logger = LoggerFactory.getLogger(FrOpController.class);
    private static final String RESULT_CODE = "response_code";
    private static final int RESULT_CODE_OK = 200;

    @Autowired
    FaceServiceProxy faceServiceProxy;
    @Autowired
    FaceOnlineServiceProxy faceOnlineServiceProxy;

    @RequestMapping(value = "fr_test/{api}")
    public String frTest(@PathVariable String api,
                         HttpServletRequest request) {
        return "";
    }

    @RequestMapping(value = "fr_group_list")
    public String frGroupList(HttpServletRequest request) {
        /*GroupList groupList = faceOnlineServiceProxy.infoGetGroupList();
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
        GroupList groupList = faceOnlineServiceProxy.infoGetGroupList();
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

                    if (s.getGroup_name().equals(l.getGroupName())
                            && s.getTag().equals(l.getTag())) {
                        logger.info("frGroupSync, same content.");
                    } else {
                        l.setGroupName(s.getGroup_name());
                        l.setTag(s.getTag());
                        logger.info("frGroupSync, update group:" + l);
                        faceServiceProxy.saveGroup(l);
                    }
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
                GroupOfFacepp groupOfFacepp = faceOnlineServiceProxy.groupGetInfo(group.getGroupId());
                List<PersonOfFacepp> pList = groupOfFacepp.getPerson();
                for (PersonOfFacepp p : pList) {
                    logger.info("frGroupSync, new person:" + p);
                    Person person = new Person();
                    person.setPersonId(p.getPerson_id());
                    person.setPersonName(p.getPerson_name());
                    person.setTag(p.getTag());
                    faceServiceProxy.savePerson(person);
                    Person2Group person2Group = new Person2Group();
                    person2Group.setGroupId(group.getId());
                    person2Group.setPersonId(person.getId());
                    faceServiceProxy.savePerson2Group(person2Group);
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
            personList.add(faceServiceProxy.getPerson(p2g.getPersonId()));
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
        GroupOfFacepp groupOfFacepp = faceOnlineServiceProxy.groupCreate(groupName);
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

    @RequestMapping(value = "fr_group_edit/{id}")
    public String frGroupEdit(@PathVariable String id,
                              HttpServletRequest request) {
        Group group = faceServiceProxy.getGroup(id);
        request.setAttribute("group", group);
        logger.info("frGroupEdit, group:" + group);
        return "operate/fr_group_edit";
    }

    @RequestMapping(value = "fr_group_save")
    public void frGroupSave(
            HttpServletRequest request,
            HttpServletResponse response) {
        String groupId = request.getParameter("groupId");
        String groupName = request.getParameter("groupName");
        String tag = request.getParameter("tag");
        logger.info("frGroupSave, groupId:" + groupId);
        //TODO make the change on face recognize system
        Group group = faceServiceProxy.getGroupByGroupId(groupId);
        ;
        JSONObject result = faceOnlineServiceProxy.groupSetInfo(groupId, groupName, tag);
        try {
            if (result.getInt(RESULT_CODE) == RESULT_CODE_OK) {
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
    public String frPersonInfo(@PathVariable String id,
                               HttpServletRequest request) {
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
        logger.info("frPersonCreateSubmit, groupId:" + groupId + ", personName:" + personName);
        Person person = null;
        PersonOfFacepp personOfFacepp = faceOnlineServiceProxy.personCreate("", personName, "");
        if (personOfFacepp != null && !StringUtils.isBlank(personOfFacepp.getPerson_id())) {
            logger.info("frPersonCreateSubmit, create person success.");
            person = new Person();
            person.setPersonId(personOfFacepp.getPerson_id());
            person.setPersonName(personOfFacepp.getPerson_name());
            person.setTag(personOfFacepp.getTag());
            faceServiceProxy.savePerson(person);
            ArrayList<String> personIds = new ArrayList<>();
            personIds.add(person.getPersonId());
            JSONObject result = faceOnlineServiceProxy.groupAddPersons(faceServiceProxy.getGroup(groupId).getGroupId(), personIds);
            try {
                if (result.getInt(RESULT_CODE) == RESULT_CODE_OK) {
                    logger.info("frPersonCreateSubmit, add person to group success.");
                    Person2Group p2g = new Person2Group();
                    p2g.setGroupId(groupId);
                    p2g.setPersonId(person.getId());
                    faceServiceProxy.savePerson2Group(p2g);
                } else {
                    logger.info("frPersonCreateSubmit, add person to group failed.");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            logger.warn("frPersonCreateSubmit, failed.");
        }
        //return "redirect:/operate/fr_group_info/"+groupId;
        MessageRender.renderJson(response, "/operate/fr_group_info/" + groupId);
    }

    @RequestMapping(value = "fr_person_edit/{id}")
    public String frPersonEdit(@PathVariable String id,
                               HttpServletRequest request) {
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
        JSONObject result = faceOnlineServiceProxy.personSetInfo(personId, personName, tag);
        try {
            if (result.getInt(RESULT_CODE) == RESULT_CODE_OK) {
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MessageRender.renderJson(response, "/operate/fr_person_info/" + (person == null ? "" : person.getId()));
    }

    @RequestMapping(value = "fr_face_info/{id}/{personId}")
    public String frFaceList(@PathVariable String id,
                             @PathVariable String personId,
                             HttpServletRequest request) {
        request.setAttribute("face", faceServiceProxy.getPerson(id));
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
        Face2Person face2Person = null;
        ArrayList<String> faceIds = new ArrayList<>();
        faceIds.add(faceServiceProxy.getFace(id).getFaceId());
        JSONObject result = faceOnlineServiceProxy.personAddFace(
                faceServiceProxy.getPerson(personId).getPersonId(), faceIds);
        try {
            if (result.getInt(RESULT_CODE) == RESULT_CODE_OK) {
                logger.info("frFaceCreateSubmit, add face to person success.");
                face2Person = new Face2Person();
                face2Person.setFaceId(id);
                face2Person.setPersonId(personId);
                faceServiceProxy.saveFace2Person(face2Person);
            } else {
                logger.warn("frFaceCreateSubmit, add face to person failed.");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //MessageRender.renderJson(response, "/operate/fr_person_info/"+personId);
        return "redirect:/operate/fr_person_info/" + personId;
    }

    @RequestMapping(value = "fr_face_delete/{id}/{personId}")
    public String frFaceDelete(@PathVariable String id,
                               @PathVariable String personId,
                               HttpServletRequest request) {
        logger.info("frFaceDelete, id:" + id);
        JSONObject result = faceOnlineServiceProxy.personRemoveFace(
                faceServiceProxy.getPerson(personId).getPersonId(), faceServiceProxy.getFace(id).getFaceId());
        try {
            if (result.getInt(RESULT_CODE) == RESULT_CODE_OK) {
                logger.info("frFaceCreateSubmit, delete face from person success.");
                faceServiceProxy.deleteFace2Person(faceServiceProxy.getFace2PersonByFaceId(id).getId());
                Face face = new Face();
                face.setId(id);
                faceServiceProxy.deleteFace(face);
            } else {
                logger.warn("frFaceCreateSubmit, delete face from person failed.");
            }
        } catch (JSONException e) {
            e.printStackTrace();
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
        DetectResult result = null;
        if (imgUrl.startsWith("http://")) {
            result = faceOnlineServiceProxy.detectionDetectByUrl(imgUrl);
        } else {
            result = faceOnlineServiceProxy.detectionDetectByPath(imgUrl);
        }

        List<FaceOfFacepp> faceList = result.getFace();
        if (faceList != null && !faceList.isEmpty()) {
            logger.warn("frFaceDetect, success, cnt:" + faceList.size());
            FaceOfFacepp facepp = faceList.get(0);
            face = new Face();
            face.setFaceId(facepp.getFace_id());
            face.setImgId(result.getImg_id());
            face.setTag(facepp.getTag());
            face.setUrl(result.getUrl());
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
        faceOnlineServiceProxy.trainIdentify(faceServiceProxy.getGroup(id).getGroupId());
        return "redirect:/operate/fr_group_info/" + id;
    }

    private void syncPerson(GroupOfFacepp syncGroup, Group localGroup) {//TODO handle table child class
        GroupOfFacepp groupOfFacepp = faceOnlineServiceProxy.groupGetInfo(syncGroup.getGroup_id());
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
                Person person = new Person();
                person.setPersonId(s.getPerson_id());
                person.setPersonName(s.getPerson_name());
                person.setTag(s.getTag());
                faceServiceProxy.savePerson(person);
                logger.info("syncPerson, new person:" + person);
                Person2Group person2Group = new Person2Group();
                person2Group.setGroupId(localGroup.getId());
                person2Group.setPersonId(person.getId());
                faceServiceProxy.savePerson2Group(person2Group);
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
        PersonOfFacepp personOfFacepp = faceOnlineServiceProxy.personGetInfo(syncPerson.getPerson_id());
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
                    if (s.getTag().equals(l.getTag())) {
                        //same
                    } else {
                        l.setTag(s.getTag());
                        logger.info("syncFace, update face:" + l);
                        faceServiceProxy.saveFace(l);
                    }
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
                face.setTag(s.getTag());
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
                faceServiceProxy.deleteFace2Person(faceServiceProxy.getFaceByFaceId(l.getId()).getId());
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

}