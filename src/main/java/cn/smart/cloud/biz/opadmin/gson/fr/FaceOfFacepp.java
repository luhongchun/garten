/**
 * <p><h1>Copyright:</h1><strong><a href="http://www.smart-f.cn">
 * BeiJing Smart Future Technology Co.Ltd. 2015 (c)</a></strong></p>
 */
package cn.smart.cloud.biz.opadmin.gson.fr;

import cn.smart.cloud.biz.opadmin.gson.Position;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * <p><h1>Copyright:</h1><strong><a href="http://www.smart-f.cn">
 * BeiJing Smart Future Technology Co.Ltd. 2015 (c)</a></strong></p>
 * <p>
 * <p>
 * <h1>Reviewer:</h1>
 * <a href="mailto:jiangjunjie@smart-f.cn">jjj</a>
 * </p>
 * <p>
 * <p>
 * <h1>History Trace:</h1>
 * <li> 2016年7月9日 上午8:52:14    V1.0.0          jjj         first release</li>
 * </p>
 *
 * @author jjj
 * @version V1.0
 * @Title Face.java
 * @Description please add description for the class
 * @email <a href="jiangjunjie@smart-f.cn">jiangjunjie@smart-f.cn</a>
 * @date 2016年7月9日 上午8:52:14
 */
public class FaceOfFacepp {

    private FaceAttr attribute;
    private String face_id;
    private List<FaceSetOfFacepp> faceset;
    //private String img_id;
    private List<PersonOfFacepp> person;
    private Position position;
    private String tag;
    //private String url;

    /**
     * @return the attribute
     */
    public FaceAttr getAttribute() {
        return attribute;
    }

    /**
     * @param attribute the attribute to set
     */
    public void setAttribute(FaceAttr attribute) {
        this.attribute = attribute;
    }

    /**
     * @return the face_id
     */
    public String getFace_id() {
        return face_id;
    }

    /**
     * @param face_id the face_id to set
     */
    public void setFace_id(String face_id) {
        this.face_id = face_id;
    }

    /**
     * @return the faceset
     */
    public List<FaceSetOfFacepp> getFaceset() {
        return faceset;
    }

    /**
     * @param faceset the faceset to set
     */
    public void setFaceset(List<FaceSetOfFacepp> faceset) {
        this.faceset = faceset;
    }
    /**
     * @return the img_id
     */
//    public String getImg_id() {
//        return img_id;
//    }
    /**
     * @param img_id the img_id to set
     */
//    public void setImg_id(String img_id) {
//        this.img_id = img_id;
//    }

    /**
     * @return the person
     */
    public List<PersonOfFacepp> getPerson() {
        return person;
    }

    /**
     * @param person the person to set
     */
    public void setPerson(List<PersonOfFacepp> person) {
        this.person = person;
    }

    /**
     * @return the position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * @return the tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * @param tag the tag to set
     */
    public void setTag(String tag) {
        this.tag = tag;
    }
    /**
     * @return the url
     */
//    public String getUrl() {
//        return url;
//    }

    /**
     * @param url the url to set
     */
//    public void setUrl(String url) {
//        this.url = url;
//    }
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}