package cn.smart.cloud.biz.opadmin.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtil {
    private static final String SERVER_DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    private static boolean isToday(Date date) {
        Calendar current = Calendar.getInstance();
        Calendar today = Calendar.getInstance();    //今天

        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        //  Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        current.setTime(date);
        return current.after(today);
    }

    /**
     * @param dateTime style "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
     */
    public static String getFormatDate(String dateTime, String formatStyle) {
        if (dateTime == null || dateTime.isEmpty()) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(SERVER_DATE_TIME_PATTERN, Locale.getDefault());
        ParsePosition pos = new ParsePosition(0);
        Date cTime = formatter.parse(dateTime, pos);
        if (cTime == null) {
            return "";
        }

        formatter = new SimpleDateFormat(formatStyle, Locale.CHINA);
        return formatter.format(cTime);
    }

    public static long formatServerDate(String dataTime) {
        if (dataTime == null || dataTime.isEmpty()) {
            return 0;
        } else {
            return formatServerTime(dataTime).getTime();
        }
    }

    /**
     * @param dateTime style "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
     */
    public static Date formatServerTime(String dateTime) {
        SimpleDateFormat formatter = new SimpleDateFormat(SERVER_DATE_TIME_PATTERN, Locale.getDefault());
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(dateTime, pos);
    }

    private static Date formatBirthday(String dateTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(dateTime, pos);
    }

    public static String formatToServerDate(long millSeconds) {
        Date date = new Date(millSeconds);
        SimpleDateFormat formatter = new SimpleDateFormat(SERVER_DATE_TIME_PATTERN, Locale.getDefault());
        return formatter.format(date);
    }

    public static int daysBetween(String startDate, String stopDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(startDate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(stopDate));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * @param birthday style "yyyy-MM-dd"
     */
    public static int getAge(String birthday) {
        Date birthDay = formatBirthday(birthday);
        Calendar cal = Calendar.getInstance();

        if (birthDay == null || cal.before(birthDay)) {
            return 0;
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            } else {
                age--;
            }
        }
        return age;
    }

    private static int[] getYearMonthDay(String birthday) {
        Date date = formatBirthday(birthday);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return new int[]{
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH) + 1,
                cal.get(Calendar.DAY_OF_MONTH)
        };
    }

    public static String formatDate(long time, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        return simpleDateFormat.format(new Date(time));
    }

    public static Date formatDate(String dateTime, String formatStyle) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatStyle, Locale.getDefault());
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(dateTime.trim(), pos);
    }
}
