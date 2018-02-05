package cn.smart.cloud.biz.opadmin.gson.fr.youtu;

import cn.smart.cloud.biz.opadmin.gson.fr.FrResult;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class YtDetectResult extends FrResult {

    private List<YtFace> face;
    private int image_width;
    private int image_height;
    private String session_id;

    public List<YtFace> getFace() {
        return face;
    }

    public void setFace(List<YtFace> face) {
        this.face = face;
    }

    public int getImage_width() {
        return image_width;
    }

    public void setImage_width(int image_width) {
        this.image_width = image_width;
    }

    public int getImage_height() {
        return image_height;
    }

    public void setImage_height(int image_height) {
        this.image_height = image_height;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}