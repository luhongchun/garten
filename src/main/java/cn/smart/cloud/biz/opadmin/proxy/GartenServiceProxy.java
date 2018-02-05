package cn.smart.cloud.biz.opadmin.proxy;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.smart.cloud.biz.opadmin.entity.AccountBizInfo;
import cn.smart.cloud.biz.opadmin.entity.AttendCardEntity;
import cn.smart.cloud.biz.opadmin.entity.AttendCardGartenUserEntity;
import cn.smart.cloud.biz.opadmin.entity.AttendCardRecordEntity;
import cn.smart.cloud.biz.opadmin.entity.BaseRole;
import cn.smart.cloud.biz.opadmin.entity.GartenManager;
import cn.smart.cloud.biz.opadmin.entity.account.Account;
import cn.smart.cloud.biz.opadmin.entity.account.Account2Camera;
import cn.smart.cloud.biz.opadmin.entity.garten.Garten;
import cn.smart.cloud.biz.opadmin.entity.garten.Garten2Goods;
import cn.smart.cloud.biz.opadmin.entity.garten.GartenClass;
import cn.smart.cloud.biz.opadmin.entity.garten.GartenClassConfig;
import cn.smart.cloud.biz.opadmin.entity.garten.Goods;
import cn.smart.cloud.biz.opadmin.entity.garten.data.ChildInfo;
import cn.smart.cloud.biz.opadmin.entity.garten.data.GartenInfo;
import cn.smart.cloud.biz.opadmin.entity.garten.data.GartenUserInfo;
import cn.smart.cloud.biz.opadmin.entity.garten.user.GartenUser;
import cn.smart.cloud.biz.opadmin.entity.garten.user.GartenUser2GartenClass;
import cn.smart.cloud.biz.opadmin.entity.garten.user.GartenUser2Role;
import cn.smart.cloud.biz.opadmin.entity.garten.user.GartenUserRelation;
import cn.smart.cloud.biz.opadmin.entity.garten.user.GartenUserVideo;
import cn.smart.cloud.biz.opadmin.entity.video.VideoReview;
import cn.smart.cloud.biz.opadmin.entity.video.VideoReviewRequest;
import cn.smart.cloud.biz.opadmin.gson.BizVideoType;
import cn.smart.cloud.biz.opadmin.gson.UploadVodResult;
import cn.smart.cloud.biz.opadmin.gson.device.Camera;
import cn.smart.cloud.biz.opadmin.gson.video.VideoData;

@Service
public class GartenServiceProxy extends BaseProxy {

    private static final String URL = ServiceConstant.MSVC_INSTANCE_NAME_GARTEN_SERVICE
            .substring(0, ServiceConstant.MSVC_INSTANCE_NAME_GARTEN_SERVICE.length() - 1);

    public void getGartenMangerByLoginId(String baseUserId) {
        // TODO: 2017/11/21 Need to add api for Garten service after refactoring
    }

    //============================ Garten Related ===================

    public Garten save(Garten garten) {
        return restPost("/save", garten, Garten.class);
    }

    public Garten get(String id) {
        return restGet("/get/" + id, Garten.class);
    }

    public GartenInfo getGartenInfo(String gartenInfoId) {
        return restGet("/info/get/" + gartenInfoId, GartenInfo.class);
    }

    public GartenInfo saveGartenInfo(GartenInfo gartenInfo) {
        return restPost("/info/save", gartenInfo, GartenInfo.class);
    }

    public String getGartenManagerId(String loginId) {
        return restGet("/manager/getGartenManagerId/" + loginId, String.class);
    }
    public GartenManager verifyGartenManager(String loginId, String password) {
    	System.out.println("loginId:"+loginId+",password:"+password);
        return restGet("/manager/verifyGartenManager/" + loginId + "/" + password, GartenManager.class);
    }
    public List<Garten> getGartensByManagerId(String managerId) {
        return Arrays.asList(restGet("/manager/getGartensByManagerId/" + managerId, Garten[].class));
    }

    public List<Garten> getAll() {
        return Arrays.asList(restGet("/getAll", Garten[].class));
    }

    //=========== User(child/teacher/parent) Related ===============

    public GartenUser createUser(GartenUser user) {
        return saveUser(user);
    }

    public GartenUser saveUser(GartenUser user) {
        return restPost("/user/save", user, GartenUser.class);
    }

    public GartenUser getUser(String userId) {
        return restGet("/user/get/" + userId, GartenUser.class);
    }

    public List<GartenUser> getAllByBaseUserId(String baseUserId) {
        return Arrays.asList(restGet("/user/getAllByBaseUserId/" + baseUserId, GartenUser[].class));
    }

    public List<GartenUser> getChildrenByParent(String parentId) {
        return Arrays.asList(restGet("/user/getChildrenByParent/" + parentId, GartenUser[].class));
    }

    public void deleteGartenUser(String gartenUserId) {
        restGet("/user/delete/" + gartenUserId, null);
    }

    public void deleteParent(String gartenUserId) {
        restDel("/user/parent/" + gartenUserId);
    }

    public void deleteChild(String gartenUserId) {
        restDel("/user/child/" + gartenUserId);
    }

    public List<GartenUser> getChildrenByClassId(String classId) {
    	//System.out.println("getAllClassesByGartenId, id:"+classId);
        return Arrays.asList(restGet("/user/getChildrenByClassId/" + classId, GartenUser[].class));
    }

    public List<ChildInfo> getChildrenInfoByClassId(String classId) {
        return Arrays.asList(restGet("/manage/child/getChildrenInfoByClassId/" + classId, ChildInfo[].class));
    }

    public String createStudent(String name, String sex, String classId) {
        return restGet("/manage/child/createStudent/" +name+"/"+sex+"/"+classId, String.class);
    }

    public List<GartenUser> getParentsByClassId(String classId) {
        return Arrays.asList(restGet("/user/getParentsByClassId/" + classId, GartenUser[].class));
    }

    public List<GartenUserInfo> getParentsInfoByChildId(String childId) {
        return Arrays.asList(restGet("/manage/parent/getParentsInfoByChildId/" + childId, GartenUserInfo[].class));
    }

    public GartenUser createParent(String childId, String parentName, String phoneNum, String sex) {
        return restGet("/manage/parent/createParent/" + childId+"/"+parentName+"/"+phoneNum+"/"+sex, GartenUser.class);
    }

    public GartenUser getChildByPersonId(String personId) {
        return restGet("/user/getChildByPersonId/" + personId, GartenUser.class);

    }

    public GartenUserRelation saveRelation(GartenUserRelation relation) {
        return restPost("/user/saveRelation", relation, GartenUserRelation.class);
    }

    public List<GartenUser> getTeachersByGartenId(String gartenId) {
        return Arrays.asList(restGet("/user/getTeachersByGartenId/" + gartenId, GartenUser[].class));
    }

    public List<GartenUserInfo> getTeachersInfoByGartenId(String gartenId) {
        return Arrays.asList(restGet("/manage/teacher/getTeachersInfoByGartenId/" + gartenId, GartenUserInfo[].class));
    }
/*
    public GartenUser createTeacher(String  classId, String teacherName, String phoneNum, String sex) {
        return restGet("/manage/teacher/createTeacher/" + classId+"/"+teacherName+"/"+phoneNum+"/"+sex, GartenUser.class);
    }
    */
    public GartenUser createTeacher(GartenUserInfo gartenUser) {
        System.out.println("createTeacher, gartenUser:"+gartenUser);
           return restPost("/manage/teacher/createTeacher", gartenUser, GartenUser.class);
    }
    public List<GartenUser> getParentsByGartenId(String gartenId) {
        return Arrays.asList(restGet("/user/getParentsByGartenId/" + gartenId, GartenUser[].class));
    }

    public void deleteFromRelations(String gartenUserId) {
        restGet("/user/deleteFromRelations/" + gartenUserId, null);
    }

    public GartenUser getByBaseUserIdAndGartenId(String baseUserId, String gartenId) {
        return restGet("/user/getByBaseUserIdAndGartenId/" + baseUserId + "/" + gartenId, GartenUser.class);
    }

    public List<GartenUser2Role> getAllRelationByGartenUserId(String userId) {
        return Arrays.asList(restGet("/user/getAllRelationByGartenUserId/" + userId, GartenUser2Role[].class));
    }

    //============================ Roles Related ===================
    public GartenUser2Role saveGartenUser2Role(GartenUser2Role gartenUser2Role) {
        return restPost("/role/save", gartenUser2Role, GartenUser2Role.class);
    }

    public void bindUserAndRole(GartenUser user, BaseRole baseRole) {
        restGet("/role/bindUserAndRole/" + user.getId() + "/" + baseRole.getId(), null);
    }

    public List<BaseRole> getAllGartenRoleByGartenUserId(String userId) {
        return Arrays.asList(restGet("/role/getAllGartenRoleByGartenUserId/" + userId, BaseRole[].class));
    }

    public  List<GartenUser2Role> getAllU2RByGartenUserId(String parentId) {
        return Arrays.asList(restGet("/role/getAllU2RByGartenUserId/" + parentId, GartenUser2Role[].class));
    }

    public void deleteUser2Role(String u2rId) {
        restGet("/role/deleteUser2Role/" + u2rId, null);
    }

    //============================ Class Related ===================
    public GartenClass getByGartenUserId(String childId) {
        return restGet("/class/getByGartenUser/" + childId, GartenClass.class);
    }

    public List<GartenClass> getAllClassesByGartenId(String gartenId) {
    	//System.out.println("getAllClassesByGartenId, id:"+gartenId);
        return Arrays.asList(restGet("/class/getAllByGartenId/" + gartenId, GartenClass[].class));
    }

    public GartenClass saveClass(GartenClass gartenClass) {
        return restPost("/class/save", gartenClass, GartenClass.class);
    }

    public GartenUser2GartenClass saveU2C(GartenUser2GartenClass u2c) {
        return restPost("/class/saveU2C", u2c, GartenUser2GartenClass.class);
    }

    public void deleteU2CByUserId(String gartenUserId) {
        restGet("/class/deleteU2CByUserId/" + gartenUserId, null);
    }

    public List<GartenUser> getParentsByChild(String childId) {
        return Arrays.asList(restGet("/user/getParentsByChild/" + childId, GartenUser[].class));
    }

    public GartenClass getClass(String classId) {
        return restGet("/class/get/" + classId, GartenClass.class);
    }

    public GartenClassConfig getGartenClassConfigByCameraId(String cameraId) {
        return restGet("/class/getByCameraId/" + cameraId, GartenClassConfig.class);
    }

    public GartenUser2GartenClass getGartenUser2GartenClass(String gartenUserId) {
        return restGet("/class/getU2CByUserId/" + gartenUserId, GartenUser2GartenClass.class);
    }

    //============================ Garten Goods ======================
    public Goods getGoodsById(String goodsId) {
        return restGet("/goods/getGoodsById/" + goodsId, Goods.class);
    }

    public Garten2Goods getGarten2GoodsByTypeAndGartenId(String name, String gartenId) {
        return restGet("/goods/getByTypeAndGartenId/" + name + "/" + gartenId, Garten2Goods.class);
    }

    public Goods getGoodsByTypeAndGartenId(String type, String gartenId) {
        return restGet("/goods/gGoodsByTypeAndGartenId/" + type + "/" + gartenId, Goods.class);
    }

    public List<Garten2Goods> getAllGoodsByGartenId(String gartenId) {
        return Arrays.asList(restGet("/goods/getAllByGartenId/" + gartenId, Garten2Goods[].class));
    }

    //============================ User Video =======================
    public GartenUserVideo getUserVideoById(String id) {
        return restGet("/user_video/getById/" + id, GartenUserVideo.class);
    }

    public List<GartenUserVideo> getUserVideoAllByChildId(String childId) {
        return Arrays.asList(restGet("/user_video/getAllByChildId/" + childId, GartenUserVideo[].class));
    }

    public GartenUserVideo saveUserVideo(GartenUserVideo userVideo) {
        return restPost("/user_video/save", userVideo, GartenUserVideo.class);
    }

    public void deleteUserVideo(String id) {
        restGet("/user_video/delete/" + id, null);
    }

    public boolean saveVideo(String id, UploadVodResult vod) {
        return restPost("/user_video/saveVideo/" + id, vod, Boolean.class);
    }

    public VideoReview getVideoReview(String videoId) {
        return restGet("/user_video/getVideoReview/" + videoId, VideoReview.class);
    }

    public List<VideoReview> getVideoReviewByRequestId(String requestId) {
        return Arrays.asList(restGet("/user_video/getVideoReviewByRequestId/" + requestId, VideoReview[].class));
    }

    public List<VideoReview> getVideoReviewByRequestIdAndPass(String requestId, boolean isPass) {
        return Arrays.asList(restGet("/user_video/getVideoReviewByRequestIdAndPass/" + requestId + "/" + isPass,
                VideoReview[].class));
    }

    public GartenUserVideo getByQrcodeSn(String sn) {
        return restGet("/user_video/getByQrcodeSn/" + sn, GartenUserVideo.class);
    }

    public VideoReview saveVideoReview(VideoReview videoReview) {
        return restPost("/user_video/saveVideoReview", videoReview, VideoReview.class);
    }

    public VideoReviewRequest saveVideoReviewRequest(VideoReviewRequest videoReviewRequest) {
        return restPost("/user_video/saveVideoReviewRequest", videoReviewRequest, VideoReviewRequest.class);
    }

    public VideoReviewRequest getVideoReviewRequest(String requestId) {
        return restGet("/user_video/getVideoReviewRequest/" + requestId, VideoReviewRequest.class);
    }

    public VideoData getVideoData(String childId, String videoId) {
        return restGet("/user_video/getVideoData/" + childId + "/" + videoId, VideoData.class);
    }

    public List<VideoData> getChildVideoEdited(String childId) {
        return Arrays.asList(restGet("/user_video/getChildVideoEdited/" + childId, VideoData[].class));
    }

    //============================ Account ============================

    public boolean checkPermissionBizByGarten(String gartenId, BizVideoType bizVideoType) {
        return restGet("/account/permission/check/" + gartenId + "/" + bizVideoType.ordinal(), Boolean.class);
    }

    public Account getByBaseUserId(String baseUserId) {
        return restGet("/account/baseuser/" + baseUserId, Account.class);
    }

    public Account updateAccount(Account account) {
        return restPost("/account/baseuser", account, Account.class);
    }

    public Account initAccount(GartenUser parent) {
        return restPost("/account/init", parent, Account.class);
    }

    public boolean destroyAccount(GartenUser parent) {
        return restGet("/account/destroy/" + parent.getBaseUserId(), Boolean.class);
    }

    public boolean hasBizPermission(String baseUserId, BizVideoType closeup, boolean online) {
        return restGet("/account/permission/" + baseUserId + "/" + closeup.ordinal() + "/" + online,
                Boolean.class);
    }

    public List<Camera> getCamerasRemoveDuplicate(String baseUserId) {
        return Arrays.asList(restGet("/account/cameras/remove/duplicate/" + baseUserId, Camera[].class));
    }

    public List<AccountBizInfo> getBizInfo(String baseUserId) {
        return Arrays.asList(restGet("/account/bizinfo/" + baseUserId, AccountBizInfo[].class));
    }

    public boolean isBizExpired(Account account, BizVideoType type, boolean payOnline) {
        return restGet("/account/biz/expired/" + account.getBaseUserId() + "/"
                + account.getId() + "/" + type.ordinal() + "/" + payOnline, Boolean.class);
    }

    public Account getAccount(String accountId) {
        return restGet("/account/" + accountId, Account.class);
    }

    public Account2Camera getAccount2CameraByAccountIdAndCameraId(String accountId, String cameraId) {
        return restGet("/account/account2camera/" + accountId + "/" + cameraId, Account2Camera.class);
    }

    public Account2Camera addCameraForAccount(String accountId, String cameraId) {
        return restPost("/account/account2camera/" + accountId + "/" + cameraId,
                accountId, Account2Camera.class);
    }

    public void deleteCameraForAccount(Account2Camera account2Camera) {
        restPost("/account/account2camera/delete", account2Camera, null);
    }

    //============================ Others ============================
    public boolean isPayOnline(String gartenId) {
        return restGet("/info/isPayOnline/" + gartenId, Boolean.class);
    }

    public String genStorePathForVideoQrcode(String childId) {
        return restGet("/user_video/genStorePathForVideoQrcode/" + childId, String.class);
    }

    //============================ Garten Attend ============================

    public List<AttendCardGartenUserEntity> getAllAttendCards() {
        return Arrays.asList(restTpl.get(URL + "/garten/attend/cards", AttendCardGartenUserEntity[].class));
    }

    public List<AttendCardGartenUserEntity> getAllAttendCardsByGartenUserId(String childId) {
        return Arrays.asList(restTpl
                .get(URL + "/garten/attend/cards/gartenuserid" + childId, AttendCardGartenUserEntity[].class));
    }

    public List<AttendCardGartenUserEntity> getAllAttendCardsByState(int state) {
        return Arrays.asList(restTpl.get(URL + "/garten/attend/cards/state/" + state, AttendCardGartenUserEntity[].class));
    }

    public List<AttendCardGartenUserEntity> getAllAttendCardsByGartenAndState(String gartenId, int state) {
        return Arrays.asList(restTpl.get(URL + "/garten/attend/cards/gartenidstate/" + gartenId + "/" + state, AttendCardGartenUserEntity[].class));
    }

    public AttendCardGartenUserEntity getAttendCardByCaredId(String cardId) {
        return restTpl.get(URL + "/garten/attend/card/cardid/" + cardId, AttendCardGartenUserEntity.class);
    }

    public AttendCardEntity saveAttendCard(AttendCardEntity card) {
        return restTpl.post(URL + "/garten/attend/card", card, AttendCardEntity.class);
    }

    public void deleteAttendCard(String cardId) {
        restTpl.delete(URL + "/garten/attend/card/cardid/" + cardId);
    }

    //------------------------ Garten Attend Record -----------------------

    public List<AttendCardGartenUserEntity> getAllRecords() {
        return Arrays.asList(restTpl.get(URL + "/garten/attend/records", AttendCardGartenUserEntity[].class));
    }

    public List<AttendCardGartenUserEntity> getAllRecordsByCardId(String cardId) {
        return Arrays.asList(restTpl.get(URL + "/garten/attend/records/cardid/" + cardId,
                AttendCardGartenUserEntity[].class));
    }

    public List<AttendCardGartenUserEntity> getAllRecordsByGartenClassId(String classId) {
        return Arrays.asList(restTpl.get(URL + "/garten/attend/records/" + classId,//UriUtil.encode(name),
                AttendCardGartenUserEntity[].class));
    }

    public List<AttendCardGartenUserEntity> getAllRecordsByDate(long startDate, long endDate) {
        return Arrays.asList(restTpl.get(URL + "/garten/attend/records/start/" + startDate + "/end/" + endDate,
                AttendCardGartenUserEntity[].class));
    }

    public AttendCardRecordEntity saveRecord(AttendCardRecordEntity record) {
        return restTpl.post(URL + "/garten/attend/record", record, AttendCardRecordEntity.class);
    }

    public List<AttendCardGartenUserEntity> getAllRecordsByDateAndClassId(long startDate, long endDate, String classId) {
        return Arrays.asList(restTpl.get(URL + "/garten/attend/records/start/" + startDate + "/end/" + endDate
                +"/"+classId, AttendCardGartenUserEntity[].class));
    }

    public void deleteAttendCardRecord(long recordId) {
        restTpl.delete(URL + "/garten/attend/record/" + recordId);
    }
}