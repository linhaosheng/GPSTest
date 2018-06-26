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
}
