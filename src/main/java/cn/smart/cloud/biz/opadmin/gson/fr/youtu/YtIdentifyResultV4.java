package cn.smart.cloud.biz.opadmin.gson.fr.youtu;

import cn.smart.cloud.biz.opadmin.gson.fr.FrResult;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class YtIdentifyResultV4 extends FrResult {

    private String session_id;
    private YtIdentifyResult data;
    private int time_ms;
    private int group_size;

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public YtIdentifyResult getData() {
        return data;
    }

    public void setData(YtIdentifyResult data) {
        this.data = data;
    }

    public int getTime_ms() {
        return time_ms;
    }

    public void setTime_ms(int time_ms) {
        this.time_ms = time_ms;
    }

    public int getGroup_size() {
        return group_size;
    }

    public void setGroup_size(int group_size) {
        this.group_size = group_size;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
