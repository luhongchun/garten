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
 * <li> 2016年7月13日 上午1:16:18    V1.0.0          jjj         first release</li>
 * </p> 
 * @Title ShotCommand.java
 * @Description please add description for the class 
 * @author jjj
 * @email <a href="jiangjunjie@smart-f.cn">jiangjunjie@smart-f.cn</a>
 * @date 2016年7月13日 上午1:16:18 
 * @version V1.0
 */
public class ShotCommand {

    private PtzOperationEnum operation;
    private int speed;
    private long duration;

    public ShotCommand(PtzOperationEnum operation, int speed, long duration) {
        this.operation = operation;
        this.speed = speed;
        this.duration = duration;
    }

    /**
     * @return the operation
     */
    public PtzOperationEnum getOperation() {
        return operation;
    }

    /**
     * @param operation the operation to set
     */
    public void setOperation(PtzOperationEnum operation) {
        this.operation = operation;
    }

    /**
     * @return the speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * @param speed the speed to set
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * @return the duration
     */
    public long getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}