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
 * <p>
 * <p>
 * <h1>Reviewer:</h1>
 * <a href="mailto:jiangjunjie@smart-f.cn">jjj</a>
 * </p>
 * <p>
 * <p>
 * <h1>History Trace:</h1>
 * <li> 2016年7月9日 上午8:10:08    V1.0.0          jjj         first release</li>
 * </p>
 *
 * @author jjj
 * @version V1.0
 * @Title Person.java
 * @Description please add description for the class
 * @email <a href="jiangjunjie@smart-f.cn">jiangjunjie@smart-f.cn</a>
 * @date 2016年7月9日 上午8:10:08
 */
public class PersonOfFacepp {

    private String person_id;
    private String person_name;
    private String tag;
    private List<FaceOfFacepp> face;
    private List<GroupOfFacepp> group;

    /**
     * @return the person_id
     */
    public String getPerson_id() {
        return person_id;
    }

    /**
     * @param person_id the person_id to set
     */
    public void setPerson_id(String person_id) {
        this.person_id = person_id;
    }

    /**
     * @return the person_name
     */
    public String getPerson_name() {
        return person_name;
    }

    /**
     * @param person_name the person_name to set
     */
    public void setPerson_name(String person_name) {
        this.person_name = person_name;
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
     * @return the group
     */
    public List<GroupOfFacepp> getGroup() {
        return group;
    }

    /**
     * @param group the group to set
     */
    public void setGroup(List<GroupOfFacepp> group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
