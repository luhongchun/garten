package cn.smart.cloud.biz.opadmin.gson.fr.youtu;

import cn.smart.cloud.biz.opadmin.gson.fr.FrResult;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class YtFaceResult extends FrResult {

    private YtFace face_info;

    public YtFace getFace_info() {
        return face_info;
    }

    public void setFace_info(YtFace face_info) {
        this.face_info = face_info;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}