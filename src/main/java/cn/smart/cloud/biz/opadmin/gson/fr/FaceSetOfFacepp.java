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
 * <li> 2016年7月9日 上午8:10:41    V1.0.0          jjj         first release</li>
 * </p>
 *
 * @author jjj
 * @version V1.0
 * @Title FaceSet.java
 * @Description please add description for the class
 * @email <a href="jiangjunjie@smart-f.cn">jiangjunjie@smart-f.cn</a>
 * @date 2016年7月9日 上午8:10:41
 */
public class FaceSetOfFacepp {

    private String faceset_id;
    private String faceset_name;
    private String tag;

    /**
     * @return the faceset_id
     */
    public String getFaceset_id() {
        return faceset_id;
    }

    /**
     * @param faceset_id the faceset_id to set
     */
    public void setFaceset_id(String faceset_id) {
        this.faceset_id = faceset_id;
    }

    /**
     * @return the faceset_name
     */
    public String getFaceset_name() {
        return faceset_name;
    }

    /**
     * @param faceset_name the faceset_name to set
     */
    public void setFaceset_name(String faceset_name) {
        this.faceset_name = faceset_name;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
