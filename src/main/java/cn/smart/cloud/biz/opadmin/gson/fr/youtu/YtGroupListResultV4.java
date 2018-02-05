package cn.smart.cloud.biz.opadmin.gson.fr.youtu;

import cn.smart.cloud.biz.opadmin.gson.fr.FrResult;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class YtGroupListResultV4 extends FrResult {

    private YtGroupListResult data;

    public YtGroupListResult getData() {
        return data;
    }

    public void setData(YtGroupListResult data) {
        this.data = data;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}