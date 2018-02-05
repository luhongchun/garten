/**
 * <p><h1>Copyright:</h1><strong><a href="http://www.smart-f.cn">
 * BeiJing Smart Future Technology Co.Ltd. 2015 (c)</a></strong></p>
 */
package cn.smart.cloud.biz.opadmin.entity.fr;

import cn.smart.cloud.biz.opadmin.entity.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

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
 * <li> 2016年7月9日 上午8:52:14    V1.0.0          jjj         first release</li>
 * </p> 
 * @Title Face.java
 * @Description please add description for the class 
 * @author jjj
 * @email <a href="jiangjunjie@smart-f.cn">jiangjunjie@smart-f.cn</a>
 * @date 2016年7月9日 上午8:52:14 
 * @version V1.0
 */
public class Face extends BaseEntity {

    private String faceId;
    private String imgId;
    private String url;
    private long bytes;
    private String tag;

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getBytes() {
        return bytes;
    }

    public void setBytes(long bytes) {
        this.bytes = bytes;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
