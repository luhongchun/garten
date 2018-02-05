package cn.smart.cloud.biz.opadmin.entity.weixin.usergroup;

import cn.smart.cloud.biz.opadmin.entity.weixin.WxCommonResult;
import cn.smart.cloud.biz.opadmin.util.GsonUtil;
import cn.smart.cloud.biz.opadmin.util.WxURIFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

/**
 * Author: jjj
 * Date: 16-9-23
 * Time: 下午10:11
 */
public class WxUserGroupManager {


    @Autowired
    private static RestTemplate restTemplate = new RestTemplate();

    public static WxGroupParam createUserGroup(String token, WxGroupParam group) {

        return restTemplate.postForObject(WxURIFactory.getWxCreateGroupURI(token), group, WxGroupParam.class);
    }

    public static WxQueryGroupResult getUserGroups(String token) {
        String rString = restTemplate.postForObject(WxURIFactory.getWxGetGroupURI(token), "", String.class);
        return GsonUtil.fromJsonForWechat(rString, WxQueryGroupResult.class);
    }

    public static WxCommonResult getGroupIdUserIn(String token, WxOpenId wxOpenId) {

        return restTemplate.postForObject(WxURIFactory.getWxGetGroupIdURI(token), wxOpenId, WxCommonResult.class);
    }

    public static WxCommonResult updateUserGroup(String token, WxGroupParam group) {

        return restTemplate.postForObject(WxURIFactory.getWxUpdateGroupURI(token), group, WxCommonResult.class);
    }

    public static WxCommonResult moveUserGroup(String token, WxMoveUserParam param) {

        return restTemplate.postForObject(WxURIFactory.getWxUpdateGroupMemberURI(token), param, WxCommonResult.class);
    }

    public static WxCommonResult moveUsersGroup(String token, WxMoveUsersParam param) {

        return restTemplate.postForObject(WxURIFactory.getWxUpdateGroupMembersURI(token), param, WxCommonResult.class);
    }

    public static WxCommonResult deleteUserGroup(String token, WxGroupParam param) {

        return restTemplate.postForObject(WxURIFactory.getWxDeleteGroupURI(token), param, WxCommonResult.class);
    }

}