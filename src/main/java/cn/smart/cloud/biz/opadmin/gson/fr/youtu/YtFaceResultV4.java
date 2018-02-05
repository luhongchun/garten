package cn.smart.cloud.biz.opadmin.gson.fr.youtu;

import cn.smart.cloud.biz.opadmin.gson.fr.FrResult;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class YtFaceResultV4 extends FrResult {

    private YtFaceResult data;

    public YtFaceResult getData() {
        return data;
    }

    public void setData(YtFaceResult data) {
        this.data = data;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}