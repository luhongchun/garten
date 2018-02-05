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
 * <li> 2016年7月9日 上午8:10:29    V1.0.0          jjj         first release</li>
 * </p> 
 * @Title Group.java
 * @Description please add description for the class 
 * @author jjj
 * @email <a href="jiangjunjie@smart-f.cn">jiangjunjie@smart-f.cn</a>
 * @date 2016年7月9日 上午8:10:29 
 * @version V1.0
 */
public class GroupList {

    private List<GroupOfFacepp> group;
    private int response_code;

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

    /**
     * @return the response_code
     */
    public int getResponse_code() {
        return response_code;
    }

    /**
     * @param response_code the response_code to set
     */
    public void setResponse_code(int response_code) {
        this.response_code = response_code;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
