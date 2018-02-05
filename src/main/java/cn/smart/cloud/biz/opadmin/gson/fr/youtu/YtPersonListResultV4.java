package cn.smart.cloud.biz.opadmin.gson.fr.youtu;

import cn.smart.cloud.biz.opadmin.gson.fr.FrResult;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class YtPersonListResultV4 extends FrResult {

    private YtPersonListResult data;

    public YtPersonListResult getData() {
        return data;
    }

    public void setData(YtPersonListResult data) {
        this.data = data;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
