package cn.smart.cloud.biz.opadmin.gson.fr.youtu;

import cn.smart.cloud.biz.opadmin.gson.fr.FrResult;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class YtFaceListResult extends FrResult {

    private List<String> face_ids;

    public List<String> getFace_ids() {
        return face_ids;
    }

    public void setFace_ids(List<String> face_ids) {
        this.face_ids = face_ids;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
