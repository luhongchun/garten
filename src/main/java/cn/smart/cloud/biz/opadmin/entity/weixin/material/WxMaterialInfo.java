package cn.smart.cloud.biz.opadmin.entity.weixin.material;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Author: jjj
 * Date: 16-1-11
 * Time: 下午10:19
 */
public class WxMaterialInfo {

    private String media_id;
    private String name;
    private long update_time;
    private String url;

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
