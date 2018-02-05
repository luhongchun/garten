package cn.smart.cloud.biz.opadmin.entity.weixin.usergroup;

import cn.smart.cloud.biz.opadmin.entity.weixin.WxCommonResult;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Author: jjj
 * Date: 16-1-8
 * Time: 上午12:44
 */
public class WxQueryGroupResult extends WxCommonResult {

    private List<WxUserGroup> groups;

    public List<WxUserGroup> getWxUserGroups() {
        return groups;
    }

    public void setWxUserGroups(List<WxUserGroup> groups) {
        this.groups = groups;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}