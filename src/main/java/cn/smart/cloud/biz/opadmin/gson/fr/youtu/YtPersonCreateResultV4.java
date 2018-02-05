package cn.smart.cloud.biz.opadmin.gson.fr.youtu;

import cn.smart.cloud.biz.opadmin.gson.fr.FrResult;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class YtPersonCreateResultV4 extends FrResult {

    private YtPersonCreateResult data;

    public YtPersonCreateResult getData() {
        return data;
    }

    public void setData(YtPersonCreateResult data) {
        this.data = data;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
