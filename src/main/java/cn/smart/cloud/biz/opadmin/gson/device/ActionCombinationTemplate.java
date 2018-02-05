/**
 * <p><h1>Copyright:</h1><strong><a href="http://www.smart-f.cn">
 * BeiJing Smart Future Technology Co.Ltd. 2015 (c)</a></strong></p>
 */
package cn.smart.cloud.biz.opadmin.gson.device;

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
 * <li> 2016年9月15日 上午9:12:40    V1.0.0          jjj         first release</li>
 * </p> 
 * @Title CruiseTemplate.java
 * @Description please add description for the class 
 * @author jjj
 * @email <a href="jiangjunjie@smart-f.cn">jiangjunjie@smart-f.cn</a>
 * @date 2016年9月15日 上午9:12:40 
 * @version V1.0
 */
public class ActionCombinationTemplate {

    protected ActionTemplate mainAction;
    protected ActionTemplate triggerAction;
    protected long intervalTriggerAction;//两个triggerAction至少间隔的时间

    public ActionCombinationTemplate(ActionTemplate mainAction, ActionTemplate triggerAction) {
        this(mainAction, triggerAction, 10000);
    }

    /**
     * @param mainAction
     * @param triggerAction
     * @param intervalTriggerAction
     * @param timeForFocus
     */
    public ActionCombinationTemplate(ActionTemplate mainAction, ActionTemplate triggerAction,
                                     long intervalTriggerAction) {
        this.mainAction = mainAction;
        this.triggerAction = triggerAction;
        this.intervalTriggerAction = intervalTriggerAction;
    }

    public ActionTemplate getMainAction() {
        return mainAction;
    }

    public ActionTemplate getTriggerAction() {
        return triggerAction;
    }

    public long getIntervalTriggerAction() {
        return intervalTriggerAction;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    /*public long getMainActionDuration() {
        return calDuration(mainAction);
    }

    public long getTriggerActionDuration() {
        return calDuration(triggerAction);
    }
    
    private long calDuration(ActionTemplate action) {
        long duration = 0;
        if (action == null)
            return duration;
        for (ShotCommand cmd:action.getActionList()) {
            if (cmd.getOperation() == PtzOperationEnum.ZOOM_IN
                    || cmd.getOperation() == PtzOperationEnum.ZOOM_OUT)
                duration += (cmd.getDuration()+timeForFocus);//对焦耗时
            else
                duration +=cmd.getDuration();
        }
        return duration;
    }*/

}