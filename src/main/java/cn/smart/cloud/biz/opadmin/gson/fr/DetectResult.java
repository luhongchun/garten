/**
 * <p><h1>Copyright:</h1><strong><a href="http://www.smart-f.cn">
 * BeiJing Smart Future Technology Co.Ltd. 2015 (c)</a></strong></p>
 */
package cn.smart.cloud.biz.opadmin.gson.fr;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * <p><h1>Copyright:</h1><strong><a href="http://www.smart-f.cn">
 * BeiJing Smart Future Technology Co.Ltd. 2015 (c)</a></strong></p> 
 *
 * <p>
 * <h1>Reviewer:</h1> 
 * <a href="mailto:jiangjunjie@smart-f.cn">jjj</a>
 * </p>
 *
 * <p>
 * <h1>History Trace:</h1>
 * <li> 2016年7月9日 上午8:51:43    V1.0.0          jjj         first release</li>
 * </p> 
 * @Title DetectResult.java
 * @Description please add description for the class 
 * @author jjj
 * @email <a href="jiangjunjie@smart-f.cn">jiangjunjie@smart-f.cn</a>
 * @date 2016年7月9日 上午8:51:43 
 * @version V1.0
 */
public class DetectResult {

    private List<FaceOfFacepp> face;
    private String session_id;
    private String img_id;
    private int img_height;
    private int img_width;
    private String url;

    /**
     * @return the face
     */
    public List<FaceOfFacepp> getFace() {
        return face;
    }

    /**
     * @param face the face to set
     */
    public void setFace(List<FaceOfFacepp> face) {
        this.face = face;
    }

    /**
     * @return the session_id
     */
    public String getSession_id() {
        return session_id;
    }

    /**
     * @param session_id the session_id to set
     */
    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    /**
     * @return the img_id
     */
    public String getImg_id() {
        return img_id;
    }

    /**
     * @param img_id the img_id to set
     */
    public void setImg_id(String img_id) {
        this.img_id = img_id;
    }

    /**
     * @return the img_height
     */
    public int getImg_height() {
        return img_height;
    }

    /**
     * @param img_height the img_height to set
     */
    public void setImg_height(int img_height) {
        this.img_height = img_height;
    }

    /**
     * @return the img_width
     */
    public int getImg_width() {
        return img_width;
    }

    /**
     * @param img_width the img_width to set
     */
    public void setImg_width(int img_width) {
        this.img_width = img_width;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
