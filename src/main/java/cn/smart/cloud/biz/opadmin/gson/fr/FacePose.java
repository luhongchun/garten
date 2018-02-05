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
 * <li> 2016年7月9日 上午9:10:58    V1.0.0          jjj         first release</li>
 * </p>
 *
 * @author jjj
 * @version V1.0
 * @Title FacePose.java
 * @Description please add description for the class
 * @email <a href="jiangjunjie@smart-f.cn">jiangjunjie@smart-f.cn</a>
 * @date 2016年7月9日 上午9:10:58
 */
public class FacePose {
    private Angle pitch_angle;
    private Angle roll_angle;
    private Angle yaw_angle;

    /**
     * @return the pitch_angle
     */
    public Angle getPitch_angle() {
        return pitch_angle;
    }

    /**
     * @param pitch_angle the pitch_angle to set
     */
    public void setPitch_angle(Angle pitch_angle) {
        this.pitch_angle = pitch_angle;
    }

    /**
     * @return the roll_angle
     */
    public Angle getRoll_angle() {
        return roll_angle;
    }

    /**
     * @param roll_angle the roll_angle to set
     */
    public void setRoll_angle(Angle roll_angle) {
        this.roll_angle = roll_angle;
    }

    /**
     * @return the yaw_angle
     */
    public Angle getYaw_angle() {
        return yaw_angle;
    }

    /**
     * @param yaw_angle the yaw_angle to set
     */
    public void setYaw_angle(Angle yaw_angle) {
        this.yaw_angle = yaw_angle;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
