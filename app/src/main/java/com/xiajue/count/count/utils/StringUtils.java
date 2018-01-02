package com.xiajue.count.count.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xiaJue on 2017/12/26.
 */

public class StringUtils {
    public static String formatDate(long ss) {
        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
        return formatter.format(ss*1000);
    }

    /**
     * 格式化时间
     */
    public static String formatDateString(long milliseconds, String... formats) {
        SimpleDateFormat format;
        if (formats != null && formats.length > 0) {
            format = new SimpleDateFormat(formats[0]);
        } else {
            format = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        }
        Date d1 = new Date(milliseconds);
        return format.format(d1);
    }
}
