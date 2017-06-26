package com.demo.android.newlife.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期时间的工具。
 *
 * @author benjamin chen china
 * @creation 2015-5-5
 */
public class DateUtil {

    /**
     * 日期格式：yyyy-MM-dd HH:mm:ss *
     */
    public static final String DF_YYYY_MM_DD_HH_MM_SS = "yyyy.MM.dd HH:mm:ss";

    /**
     * 日期格式：yyyy-MM-dd HH:mm *
     */
    public static final String DF_YYYY_MM_DD_HH_MM = "yyyy.MM.dd HH:mm";

    public static final String DF_YYYY_MM_DD_HH = "yyyy.MM.dd HH";

    /**
     * 日期格式：yyyy-MM-dd *
     */
    public static final String DF_YYYY_MM_DD = "yyyy.MM.dd";

    /**
     * 日期格式 MM-dd 月=日
     */
    public static final String MM_DD = "MM-dd";

    /**
     * 日期格式：HH:mm:ss *
     */
    public static final String DF_HH_MM_SS = "HH:mm:ss";

    /**
     * 日期格式：HH:mm *
     */
    public static final String DF_HH_MM = "HH:mm";

    private final static long minute = 60 * 1000;
    private final static long hour = 60 * minute;
    private final static long day = 24 * hour;
    private final static long month = 31 * day;
    private final static long year = 12 * month;

    /**
     * 转换时间戳
     * @param l
     * @return
     */
    public static String getDateStr(long l) {

        return getDateFormat().format(new Date(l));
    }
    public static String getCycleTime(Date date){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }

    public static  long getD(Date date, int statu) throws ParseException {
        long endTimeChuo;
        if(statu == 1) {
            String endTimeStr = getCycleTime(date);
            long currentTim = System.currentTimeMillis();
            SimpleDateFormat formatter   =   new SimpleDateFormat("yyyy.MM.dd");
            Date curDate   =   new Date(currentTim);
            //获取当前时间
            String end  =   formatter.format(curDate);
            String entTime = end + " "+endTimeStr;
            SimpleDateFormat format =  new SimpleDateFormat("yyyy.MM.dd HH:mm");
            Date date2 = format.parse(entTime);
             endTimeChuo = date2.getTime();
            return  endTimeChuo;
        }else {
            int hours = date.getHours();
            int endHours = hours + 1;
            String endHour = String.valueOf(endHours);
            long currentTim = System.currentTimeMillis();
            SimpleDateFormat formatter   =   new SimpleDateFormat("yyyy.MM.dd");
            Date curDate   =   new Date(currentTim);
            String end  =   formatter.format(curDate);
            String entTime = end + " "+endHour+":"+"00";
            SimpleDateFormat format =  new SimpleDateFormat("yyyy.MM.dd HH:mm");
            Date date2 = format.parse(entTime);
            endTimeChuo = date2.getTime();

            return  endTimeChuo;
        }

    }
    public static SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.CHINA);
    }


    public static String getFormatTimeString(long time, String format){
        Date nowTime  =   new Date(time);
        SimpleDateFormat sdFormatter = new SimpleDateFormat(format);
        return sdFormatter.format(nowTime);
    }

    public static long getCurrentTime(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static long getStartTime(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static long getEndTime(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY,date.getHours()+1);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }


    /**
     * date转化成string
     */


    /**
     * 判断 今天 明天
     * 用于显示 消息列表中的时间
     *
     */
    public static String formatDateTimeForMessage(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        if(time==null ||"".equals(time)){
            return "";
        }
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar current = Calendar.getInstance();

        Calendar today = Calendar.getInstance();    //今天

        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH,current.get(Calendar.DAY_OF_MONTH));
        //  Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
        today.set( Calendar.HOUR_OF_DAY, 0);
        today.set( Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        Calendar yesterday = Calendar.getInstance();    //昨天

        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH,current.get(Calendar.DAY_OF_MONTH)-1);
        yesterday.set( Calendar.HOUR_OF_DAY, 0);
        yesterday.set( Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);

        current.setTime(date);

        if(current.after(today)){
            return "今天 ";
        }else if(current.before(today) && current.after(yesterday)){

            return "昨天 ";
        }else{
            return (date.getMonth()+1)+"月"+date.getDate()+"日";
        }
    }

    private static long lastClickTime;
    public synchronized  static boolean isLastClick(){
        long time = System.currentTimeMillis();
        if(time - lastClickTime < 300){
            return true;
        }
        lastClickTime = time;
        return false;
    }
    //时间戳转时分
    public static String getHourMinute(String timeStamp){
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DF_HH_MM);
        long lcc_time = Long.valueOf(timeStamp);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }
    //时间戳转化月日
    public static String getMouthDay(String timeStamp) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.MM_DD);
        long lcc_time = Long.valueOf(timeStamp);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    } //时间戳转化年月日时分秒
    public static String getDate(String timeStamp) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DF_YYYY_MM_DD_HH_MM_SS);
        long lcc_time = Long.valueOf(timeStamp);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }

}
