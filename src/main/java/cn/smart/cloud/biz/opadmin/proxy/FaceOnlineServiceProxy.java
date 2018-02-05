package cn.smart.cloud.biz.opadmin.proxy;

import cn.smart.cloud.biz.opadmin.gson.fr.DetectResult;
import cn.smart.cloud.biz.opadmin.gson.fr.GroupList;
import cn.smart.cloud.biz.opadmin.gson.fr.GroupOfFacepp;
import cn.smart.cloud.biz.opadmin.gson.fr.PersonOfFacepp;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class FaceOnlineServiceProxy extends BaseProxy {

    public GroupList infoGetGroupList() {
        return null;
    }

    public GroupOfFacepp groupGetInfo(String groupId) {
        return null;
    }

    public GroupOfFacepp groupCreate(String groupName) {
        return null;
    }

    public JSONObject groupSetInfo(String groupId, String groupName, String tag) {
        return null;
    }

    public PersonOfFacepp personCreate(String s, String personName, String s1) {
        return null;
    }

    public JSONObject groupAddPersons(String groupId, ArrayList<String> personIds) {
        return null;
    }

    public JSONObject personSetInfo(String personId, String personName, String tag) {
        return null;
    }

    public JSONObject personAddFace(String personId, ArrayList<String> faceIds) {
        return null;
    }

    public JSONObject personRemoveFace(String personId, String faceId) {
        return null;
    }

    public DetectResult detectionDetectByUrl(String imgUrl) {
        return null;
    }

    public DetectResult detectionDetectByPath(String imgUrl) {
        return null;
    }

    public void trainIdentify(String groupId) {

    }

    public PersonOfFacepp personGetInfo(String person_id) {
        return null;
    }
}
