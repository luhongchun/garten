/**
 * <p><h1>Copyright:</h1><strong><a href="http://www.smart-f.cn">
 * BeiJing Smart Future Technology Co.Ltd. 2015 (c)</a></strong></p>
 */
package cn.smart.cloud.biz.opadmin.gson.device;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
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
 * <li> 2016年9月15日 上午9:12:40    V1.0.0          jjj         first release</li>
 * </p> 
 * @Title CruiseTemplate.java
 * @Description please add description for the class 
 * @author jjj
 * @email <a href="jiangjunjie@smart-f.cn">jiangjunjie@smart-f.cn</a>
 * @date 2016年9月15日 上午9:12:40 
 * @version V1.0
 */
public class ActionTemplate {

    protected List<ShotCommand> actionList = new ArrayList<ShotCommand>();
    protected long duration;
    protected long timeForFocus;

    public List<ShotCommand> getActionList() {
        return actionList;
    }

    public void setActionList(List<ShotCommand> actionList) {
        this.actionList = actionList;
    }

    public long getDuration() {
        duration = 0;
        for (ShotCommand cmd : actionList) {
            if (cmd.getOperation() == PtzOperationEnum.ZOOM_IN
                    || cmd.getOperation() == PtzOperationEnum.ZOOM_OUT)
                duration += (cmd.getDuration() + timeForFocus);//对焦耗时
            else
                duration += cmd.getDuration();
        }
        return duration;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}