package cn.smart.cloud.biz.opadmin.entity.weixin.material;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Author: jjj
 * Date: 16-1-11
 * Time: 上午12:16
 */
public class WxMaterialResult {

    private String media_id;

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
