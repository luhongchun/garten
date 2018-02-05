package cn.smart.cloud.biz.opadmin.gson.fr.youtu;

import cn.smart.cloud.biz.opadmin.gson.fr.FrResult;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class YtPersonResultV4 extends FrResult {

    private YtPersonResult data;

    public YtPersonResult getData() {
        return data;
    }

    public void setData(YtPersonResult data) {
        this.data = data;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
