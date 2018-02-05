package cn.smart.cloud.biz.opadmin.proxy;

import cn.smart.cloud.biz.opadmin.entity.fr.*;
import cn.smart.cloud.biz.opadmin.gson.fr.FrResult;
import cn.smart.cloud.biz.opadmin.gson.fr.GroupList;
import cn.smart.cloud.biz.opadmin.gson.fr.GroupOfFacepp;
import cn.smart.cloud.biz.opadmin.gson.fr.PersonOfFacepp;
import cn.smart.cloud.biz.opadmin.gson.fr.youtu.*;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FaceServiceProxy extends BaseProxy {

    public Face getFaceByGartenUserId(String childId) {
        return restGet("/fr/system/getFaceByGartenUserId/" + childId, Face.class);
    }

    // TODO: 2017/11/22 The new method have to test
    public YtPersonResult updateFrFace(String childId, File imageFile, String imageUrl) {
        Map<String, String> param = new HashMap<>();
        param.put("faceUrl", imageUrl);
        return restPostFile("/fr/system/updateFrFace/" + childId, imageFile, YtPersonResult.class, param);
    }

    // TODO: 2017/11/22 The new method have to test
    public YtPersonResult updateFaceData(String childId, YtPersonResult result, Face face) {
        return restPost("/fr/system/updateFaceData/"
                + result.getFace_ids().get(0) + "/"
                + result.getGroup_ids().get(0) + "/"
                + result.getPerson_id() + "/"
                + result.getPerson_name(), face, YtPersonResult.class);
    }

    //============================ Face Related ============================
    public Face getFace(String id) {
        return restGet("/fr/system/getFace/" + id, Face.class);
    }

    public Face getFaceByFaceId(String faceId) {
        return restGet("/fr/system/getFaceByFaceId/" + faceId, Face.class);
    }

    public void deleteFace(Face face) {
        restGet("/fr/system/deleteFace/" + face.getId(), null);
    }

    public Face saveFace(Face face) {
        return restPost("/fr/system/saveFace", face, Face.class);
    }

    public Face2Person saveFace2Person(Face2Person face2Person) {
        return restPost("/fr/system/saveFace2Person", face2Person, Face2Person.class);
    }

    public GroupOfFacepp getFaceGroupInfo(String groupName) {
        return restGet("/fr/system/getFaceGroupInfo/" + groupName, GroupOfFacepp.class);
    }

    public Face2Person getFace2PersonByFaceId(String faceId) {
        return restGet("/fr/system/getFace2PersonByFaceId/" + faceId, Face2Person.class);
    }

    public List<Face2Person> getFace2PersonByPersonId(String personId) {
        return Arrays.asList(restGet("/fr/system/getFace2PersonByPersonId/" + personId, Face2Person[].class));
    }

    public void deleteFace2Person(String id) {
        restGet("/fr/system/deleteFace2Person/" + id, null);
    }

    //============================ Person Related ============================
    public String genPersonId() {
        return restGet("/fr/system/getPersonId", String.class);
    }

    public Person getPerson(String id) {
        return restGet("/fr/system/getPerson/" + id, Person.class);
    }

    public Person getPersonByPersonId(String personId) {
        return restGet("/fr/system/getPersonByPersonId/" + personId, Person.class);
    }

    public Person getPersonByPersonName(String personName) {
        return restGet("/fr/system/getPersonByPersonName/" + personName, Person.class);
    }

    public Person savePerson(Person person) {
        return restPost("/fr/system/savePerson", person, Person.class);
    }

    public void deletePerson(String id) {
        restGet("/fr/system/deletePerson/" + id, null);
    }

    public boolean clearPerson(String childId) {
        return restGet("/fr/system/clearPerson/" + childId, boolean.class);
    }

    public Child2Person saveChild2Person(Child2Person child2Person) {
        return restPost("/fr/system/saveChild2Person", child2Person, Child2Person.class);
    }

    //============================ Group Related ============================
    public GroupOfFacepp createGroup(String groupName) {
        return restGet("/fr/system/createGroup/" + groupName, GroupOfFacepp.class);
    }

    public Group getGroup(String groupId) {
        return restGet("/fr/system/getGroup/" + groupId, Group.class);
    }

    public Group getGroupByGroupId(String groupId) {
        return restGet("/fr/system/getGroupByGroupId/" + groupId, Group.class);
    }

    public Group getGroupByName(String groupName) {
        return restGet("/fr/system/getGroupByName/" + groupName, Group.class);
    }

    public List<Group> getAllGroup() {
        return Arrays.asList(restGet("/fr/system/getAllGroup/", Group[].class));
    }

    public Group saveGroup(Group group) {
        return restPost("/fr/system/saveGroup", group, Group.class);
    }

    public void deleteGroup(Group group) {
        restGet("/fr/system/deleteGroup/" + group.getId(), null);
    }

    //============================ Person2Group Related ============================
    public Person2Group savePerson2Group(Person2Group person2Group) {
        return restPost("/fr/system/savePerson2Group", person2Group, Person2Group.class);
    }

    public List<Person2Group> getPerson2GroupByGroupId(String groupId) {
        return Arrays.asList(restGet("/fr/system/getPerson2GroupByGroupId/" + groupId, Person2Group[].class));
    }

    public Person2Group getPerson2GroupByPersonIdAndGroupId(String personId, String groupId) {
        return restGet("/fr/system/getPerson2GroupByPersonIdAndGroupId/" + personId + "/" + groupId,
                Person2Group.class);
    }


    public void deletePerson2Group(String id) {
        restGet("/fr/system/deletePerson2Group/" + id, null);
    }

    //======================== Group2GartenClass Related ===========================
    public Group2GartenClass getGroup2GartenClass(String classId) {
        return restGet("/fr/group2class/getByClassId" + classId, Group2GartenClass.class);
    }

    public void deleteGroup2GartenClass(String id) {
        restGet("/fr/group2class/delete/" + id, null);
    }

    public Group2GartenClass saveGroup2GartenClass(Group2GartenClass group2GartenClass) {
        return restPost("/fr/group2class/save", group2GartenClass, Group2GartenClass.class);
    }

    //======================== Face recognized Related ===========================
    public YtIdentifyResult recognitionIdentifyByPath(String frGroupId, String absolutePath) {
        return restGet("/fr/system/recognitionIdentifyByPath/" + frGroupId + "/" + absolutePath,
                YtIdentifyResult.class);
    }

    public YtPersonCreateResult personCreateByPath(String groupId, String personId, String personName,
                                                   String imgPath, String tag) {
        Map<String, String> params = new HashMap<>();
        params.put("groupId", groupId);
        params.put("personId", personId);
        params.put("personName", personName);
        params.put("imgPath", imgPath);
        params.put("tag", tag);
        return restPost("/fr/system/personCreateByPath", params, YtPersonCreateResult.class);
    }

    public GroupList infoGetGroupList() {
        return restGet("/fr/system/infoGetGroupList", GroupList.class);
    }

    public List<PersonOfFacepp> infoGetPersonList(String groupId) {
        return Arrays.asList(restGet("/fr/system/infoGetPersonList/" + groupId, PersonOfFacepp[].class));
    }

    public FaceAddResult personAddFace(String personId, String imgPath) {
        return restGet("/fr/system/personAddFace/" + personId + "/" + imgPath, FaceAddResult.class);
    }

    public YtPersonCreateResult personAddFaces(String groupId, String personId, String personName,
                                               String imgUrl, String tag) {
        return restGet("/fr/system/personAddFaces/" + groupId + "/" + personId + "/" + personName
                + "/" + imgUrl + "/" + tag, YtPersonCreateResult.class);
    }

    public JSONObject groupSetInfo(String groupId, String groupName, String tag) {
        return restGet("/fr/system/groupSetInfo/" + groupId + "/" + groupName + "/" + tag, JSONObject.class);
    }

    public FrResult personSetInfo(String personId, String personName, String tag) {
        return restGet("/fr/system/personSetInfo/" + personId + "/" + personName + "/" + tag, FrResult.class);
    }

    public FrResult personRemoveFace(String personId, String faceId) {
        return restGet("/fr/system/personRemoveFace/" + personId + "/" + faceId, FrResult.class);
    }

    public PersonOfFacepp personGetInfo(String personId) {
        return restGet("/fr/system/personGetInfo/" + personId, PersonOfFacepp.class);
    }

    public YtDetectResult detectionDetectByUrl(String imgUrl) {
        return restGet("/fr/system/detectionDetectByUrl/" + imgUrl, YtDetectResult.class);
    }

    public YtDetectResult detectionDetectByPath(String imgUrl) {
        return restGet("/fr/system/detectionDetectByPath/" + imgUrl, YtDetectResult.class);
    }

    public String trainIdentify(String groupId) {
        return restGet("/fr/system/trainIdentify/" + groupId, String.class);
    }
}
