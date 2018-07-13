package com.android.gpstest.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by haoshenglin on 2018/6/24.
 */

public class DateUtils {


    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String date(long time) {
        String currentTime = simpleDateFormat.format(new Date(time));
        return currentTime;
    }


    // 计算时区函数，根据经度来计算
    public static int calculateTimezone(double lat, double lon) {
        int a, b, c, timezone;
        a = (int) (Math.abs(lon) + 0.5);// 对经度进行四舍五入，且取正整数
        b = a / 15; // 商
        c = a % 15; // 余数
        if ((lat >= 17.9 && lat <= 53 && lon >= 75 && lon <= 125)
                || (lat >= 40 && lat <= 53 && lon >= 125 && lon <= 135)) {// 如果经纬度处于中国版图内，则都划为东8区，为什么要这样划分详见第三节
            timezone = 8;
        } else {
            if (c > 7.5) {
                timezone = b + 1;
            } else {
                timezone = b;
            }
            if (lon > 0.0f) {
                timezone = timezone;
            } else {
                timezone = (-1) * timezone;
            }
        }
        return timezone;
    }

    // UTC时间转换为本地时间函数
    public static String UTCTOLocalTime(int timezone,
                                        long gpsTime) {
        int year, month, day, hour;
        int lastday = 0;// 月的最后一天的日期
        int lastlastday = 0;// 上月的最后一天的日期

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(gpsTime);

        year = calendar.get(Calendar.YEAR); // 已知的UTC时间
        month = calendar.get(Calendar.MONTH);// 已知的UTC时间
        day = calendar.get(Calendar.DAY_OF_MONTH);// 已知的UTC时间

        hour = calendar.get(Calendar.HOUR) + (timezone - 8); // 已知的UTC时间,默认出厂的就是第时区，也就是中国时区

        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
                || month == 10 || month == 12) {
            lastday = 31;
            if (month == 3) {
                if ((year % 400 == 0) || (year % 4 == 0 && year % 100 != 0))// 判断是否为闰年，年号能被400整除或年号能被4整除，而不能被100整除为闰年
                    lastlastday = 29;// 闰年的2月为29天，平年为28天
                else
                    lastlastday = 28;
            }
            if (month == 8)
                lastlastday = 31;
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            lastday = 30;
            lastlastday = 31;
        } else {
            lastlastday = 31;
            if ((year % 400 == 0) || (year % 4 == 0 && year % 100 != 0))// 闰年的2月为29天，平年为28天
                lastday = 29;
            else
                lastday = 28;
        }

        if (hour >= 24) {// 当算出的区时大于或等于24:00时，应减去24:00，日期加一天
            hour -= 24;
            day += 1;

            if (day > lastday) { // 当算出的日期大于该月最后一天时，应减去该月最后一天的日期，月份加上一个月
                day -= lastday;
                month += 1;

                if (month > 12) {// 当算出的月份大于12时，应减去12，年份加上一年
                    month -= 12;
                    year += 1;
                }
            }
        }
        if (hour < 0) {// 当算出的区时为负数时，应加上24:00，日期减一天
            hour += 24;
            day -= 1;
            if (day < 1) { // 当算出的日期为0时，日期变为上一月的最后一天，月份减去一个月
                day = lastlastday;
                month -= 1;
                if (month < 1) { // 当算出的月份为0时，月份变为12月，年份减去一年
                    month = 12;
                    year -= 1;
                }
            }
        }
        // 得到转换后的本地时间
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR, hour);

        String datestring = simpleDateFormat.format(calendar.getTime());
        return datestring;
    }
}
