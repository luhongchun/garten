package cn.smart.cloud.biz.opadmin.entity.weixin.usergroup;

import java.util.List;

/**
 * Author: jjj
 * Date: 16-1-8
 * Time: 下午6:52
 */
public class WxMoveUsersParam {

    private List<String> openid_list;
    private String to_groupid;

    public List<String> getOpenid_list() {
        return openid_list;
    }

    public void setOpenid_list(List<String> openid_list) {
        this.openid_list = openid_list;
    }

    public String getTo_groupid() {
        return to_groupid;
    }

    public void setTo_groupid(String to_groupid) {
        this.to_groupid = to_groupid;
    }
}
