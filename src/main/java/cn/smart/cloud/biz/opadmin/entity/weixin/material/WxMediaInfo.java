package cn.smart.cloud.biz.opadmin.entity.weixin.material;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Author: jjj
 * Date: 16-1-11
 * Time: 上午12:14
 */
public class WxMediaInfo {

    private String title;
    private String introduction;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
