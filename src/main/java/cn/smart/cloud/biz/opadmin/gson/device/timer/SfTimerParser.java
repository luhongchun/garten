/**
 * <p><h1>Copyright:</h1><strong><a href="http://www.smart-f.cn">
 * BeiJing Smart Future Technology Co.Ltd. 2015 (c)</a></strong></p>
 */
package cn.smart.cloud.biz.opadmin.gson.device.timer;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
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
 * <li> 2016年9月29日 下午3:54:36    V1.0.0          jjj         first release</li>
 * </p> 
 * @Title SfTimerParser.java
 * @Description please add description for the class 
 * @author jjj
 * @email <a href="jiangjunjie@smart-f.cn">jiangjunjie@smart-f.cn</a>
 * @date 2016年9月29日 下午3:54:36 
 * @version V1.0
 */
public class SfTimerParser {

    private static Logger logger = Logger.getLogger(SfTimerParser.class);

    public static List<SfTimeConfig> parserTimeConfig(String pushTimes) {
        if (StringUtils.isBlank(pushTimes)) {
            return null;
        }
        String[] dayAndTime = pushTimes.split(";");
        if (dayAndTime == null || dayAndTime.length < 1) {
            logger.error("parserTimeConfig, invalid dayAndTime");
            return null;
        }
        String tString = "";
        if (dayAndTime.length == 1) {
            tString = dayAndTime[0];
        } else if (dayAndTime.length == 2) {
            tString = dayAndTime[1];
        } else {
            logger.error("parserTimeConfig, config string length invalid.");
        }
        List<Integer> validDayList = getValidDay(pushTimes);
        return parseTime(validDayList, tString);
    }

    private static List<SfTimeConfig> parseTime(List<Integer> validDayList, String tString) {
        List<SfTimeConfig> times = new ArrayList<>();
        String[] pushTime = tString.split(",");
        for (String t : pushTime) {
            if (t.contains("-") && t.length() >= 7) {
                String[] pp = t.split("-");
                try {
                    int start = Integer.parseInt(pp[0]);
                    int end = Integer.parseInt(pp[1]);
                    if (end < 2400 && end - start > 10) {
                        for (int d : validDayList) {
                            SfTimeConfig config = new SfTimeConfig();
                            config.setDay(d);
                            config.setStart(start);
                            config.setEnd(end);
                            times.add(config);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return times;
    }

    public static List<Integer> getValidDay(String pushTimes) {
        List<Integer> validDayList = new ArrayList<>();
        if (StringUtils.isBlank(pushTimes)) {
            logger.error("getValidDay, invalid pushTimes");
            return validDayList;
        }
        String[] dayAndTime = pushTimes.split(";");
        if (dayAndTime == null || dayAndTime.length < 1) {
            logger.error("getValidDay, invalid dayAndTime");
            return validDayList;
        }
        if (dayAndTime.length == 1) {
            validDayList.add(1);
            validDayList.add(2);
            validDayList.add(3);
            validDayList.add(4);
            validDayList.add(5);
            validDayList.add(6);
            validDayList.add(7);
        } else if (dayAndTime.length == 2) {
            String dString = dayAndTime[0];
            String[] days = dString.split(" ");
            for (String day : days) {
                try {
                    int dayIndex = Integer.parseInt(day);
                    if (dayIndex > 0 && dayIndex < 8)
                        validDayList.add(dayIndex);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return validDayList;
    }

    public static boolean isValidDay(String pushTimes) {
        if (StringUtils.isBlank(pushTimes)) {
            logger.error("isValidDay, invalid pushTimes");
            return false;
        }
        String[] dayAndTime = pushTimes.split(";");
        if (dayAndTime == null || dayAndTime.length < 1) {
            logger.error("isValidDay, invalid dayAndTime");
            return false;
        }
        if (dayAndTime.length == 1)
            return true;
        if (dayAndTime.length == 2) {
            @SuppressWarnings("deprecation")
            int d = new Date().getDay();
            String dString = dayAndTime[0];
            String[] days = dString.split(" ");
            for (String day : days) {
                if (day.equals("7"))
                    day = "0";
                if (Integer.parseInt(day) == d) {
                    return true;
                }

            }
        }
        return false;
    }
}