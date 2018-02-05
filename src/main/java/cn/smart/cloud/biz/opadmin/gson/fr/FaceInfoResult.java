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
 * <li> 2016年7月10日 下午6:00:25    V1.0.0          jjj         first release</li>
 * </p>
 *
 * @author jjj
 * @version V1.0
 * @Title FaceInfoResult.java
 * @Description please add description for the class
 * @email <a href="jiangjunjie@smart-f.cn">jiangjunjie@smart-f.cn</a>
 * @date 2016年7月10日 下午6:00:25
 */
public class FaceInfoResult {

    private List<FaceOfFacepp> face_info;
    private int response_code;

    /**
     * @return the face_info
     */
    public List<FaceOfFacepp> getFace_info() {
        return face_info;
    }

    /**
     * @param face_info the face_info to set
     */
    public void setFace_info(List<FaceOfFacepp> face_info) {
        this.face_info = face_info;
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
