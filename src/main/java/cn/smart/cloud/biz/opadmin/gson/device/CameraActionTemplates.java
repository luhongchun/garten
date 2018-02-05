/**
 * <p><h1>Copyright:</h1><strong><a href="http://www.smart-f.cn">
 * BeiJing Smart Future Technology Co.Ltd. 2015 (c)</a></strong></p>
 */
package cn.smart.cloud.biz.opadmin.gson.device;

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
 * <li> 2016年9月17日 上午9:30:02    V1.0.0          jjj         first release</li>
 * </p> 
 * @Title CruiseTemplate.java
 * @Description please add description for the class 
 * @author jjj
 * @email <a href="jiangjunjie@smart-f.cn">jiangjunjie@smart-f.cn</a>
 * @date 2016年9月17日 上午9:30:02 
 * @version V1.0
 */
public class CameraActionTemplates {

    private String sn;
    private long timeForZoomOneStep;
    private List<ActionCombinationTemplate> actions;
    private String pushTime;

    public CameraActionTemplates(String cameraSn, long timeForZoomOneStep) {
        this.sn = cameraSn;
        this.timeForZoomOneStep = timeForZoomOneStep;
    }

    public String getSn() {
        return sn;
    }

    public long getTimeForZoomOneStep() {
        return timeForZoomOneStep;
    }

    public List<ActionCombinationTemplate> getActions() {
        return actions;
    }

    public void setActions(List<ActionCombinationTemplate> actions) {
        this.actions = actions;
    }

    public String getPushTime() {
        return pushTime;
    }

    public void setPushTime(String pushTime) {
        this.pushTime = pushTime;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}