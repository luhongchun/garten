/**
 * <p><h1>Copyright:</h1><strong><a href="http://www.smart-f.cn">
 * BeiJing Smart Future Technology Co.Ltd. 2015 (c)</a></strong></p>
 */
package cn.smart.cloud.biz.opadmin.gson.fr;

import org.apache.commons.lang3.builder.ToStringBuilder;

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
 * <li> 2016年7月9日 上午8:58:19    V1.0.0          jjj         first release</li>
 * </p>
 *
 * @author jjj
 * @version V1.0
 * @Title FaceAttr.java
 * @Description please add description for the class
 * @email <a href="jiangjunjie@smart-f.cn">jiangjunjie@smart-f.cn</a>
 * @date 2016年7月9日 上午8:58:19
 */
public class FaceAttr {

    private FaceAge age;
    private FaceGender gender;
    private FaceGlass glass;
    private FacePose pose;
    private FaceRace race;
    private FaceSmile smiling;

    /**
     * @return the age
     */
    public FaceAge getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(FaceAge age) {
        this.age = age;
    }

    /**
     * @return the gender
     */
    public FaceGender getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(FaceGender gender) {
        this.gender = gender;
    }

    /**
     * @return the glass
     */
    public FaceGlass getGlass() {
        return glass;
    }

    /**
     * @param glass the glass to set
     */
    public void setGlass(FaceGlass glass) {
        this.glass = glass;
    }

    /**
     * @return the pose
     */
    public FacePose getPose() {
        return pose;
    }

    /**
     * @param pose the pose to set
     */
    public void setPose(FacePose pose) {
        this.pose = pose;
    }

    /**
     * @return the race
     */
    public FaceRace getRace() {
        return race;
    }

    /**
     * @param race the race to set
     */
    public void setRace(FaceRace race) {
        this.race = race;
    }

    /**
     * @return the smiling
     */
    public FaceSmile getSmiling() {
        return smiling;
    }

    /**
     * @param smiling the smiling to set
     */
    public void setSmiling(FaceSmile smiling) {
        this.smiling = smiling;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
