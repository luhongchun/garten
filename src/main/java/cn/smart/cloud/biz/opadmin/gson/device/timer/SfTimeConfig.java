/**
 * <p><h1>Copyright:</h1><strong><a href="http://www.smart-f.cn">
 * BeiJing Smart Future Technology Co.Ltd. 2015 (c)</a></strong></p>
 */
package cn.smart.cloud.biz.opadmin.gson.device.timer;

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
 * <li> 2016年9月29日 下午6:29:34    V1.0.0          jjj         first release</li>
 * </p> 
 * @Title SfTimeConfig.java
 * @Description please add description for the class 
 * @author jjj
 * @email <a href="jiangjunjie@smart-f.cn">jiangjunjie@smart-f.cn</a>
 * @date 2016年9月29日 下午6:29:34 
 * @version V1.0
 */
public class SfTimeConfig {

    private int day;
    private int start;
    private int end;
    private long localId;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public long getLocalId() {
        return localId;
    }

    public void setLocalId(long localId) {
        this.localId = localId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o == this)
            return true;
        if (!(o instanceof SfTimeConfig)) {
            return false;
        }
        SfTimeConfig config = (SfTimeConfig) o;
        if (config.day == this.day && config.start == this.start
                && config.end == this.end)
            return true;
        return false;
    }

    ;

    @Override
    public int hashCode() {
        return day * 37 + start * 17 + end * 7;
    }

    ;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}