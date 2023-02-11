package cn.jackbin.SimpleRecord.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 日期工具类
 * @date: 2020/10/27 22:20
 **/
public class DateUtil {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final long MS = 1L;
    private static final long SECOND = MS * 1000;
    private static final long MIN = SECOND * 60;
    private static final long HOUR = MIN * 60;
    private static final long DAY = HOUR * 24;

    /**
     * 获取当前时间
     */
    public static String getCurrentTime() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return sf.format(date);
    }

    /**
     * 距离下一个纪念日还有几天
     *
     * @param anniversary 纪念日日期
     * @return {@link int}
     * @author Eli.Gui 2023-02-11 10:11:43
     */
    public static int beforeForAnniversary(Date anniversary) {
        int day = 0;
        Calendar currentDate = Calendar.getInstance();
        Calendar anniversaryDate = Calendar.getInstance();
        anniversaryDate.setTime(anniversary);
        anniversaryDate.set(Calendar.YEAR, currentDate.get(Calendar.YEAR));
        long currentInMillis = currentDate.getTimeInMillis();
        long birthDayInMillis = anniversaryDate.getTimeInMillis();
        long l = birthDayInMillis - currentInMillis;
        if (l <= -DAY) {
            anniversaryDate.add(Calendar.YEAR, 1);
            birthDayInMillis = anniversaryDate.getTimeInMillis();
            l = birthDayInMillis - currentInMillis;
        }
        long longDay = l / DAY;
        day = (int) (longDay) + 1;
        if (0L >= l && l > -DAY) {
            day = 0;
        }
        return day;
    }

    public static void main(String[] args) throws ParseException {
        String dateStr = "2019-01-12";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = sdf.parse(dateStr);
        int before = DateUtil.beforeForAnniversary(parse);
        System.out.println(before);
    }


    /**
     * 添加月份
     */
    public static Date addMonth(Date sourceDate, int month) {
        Calendar c = Calendar.getInstance();
        c.setTime(sourceDate);
        c.add(Calendar.MONTH, month);
        return c.getTime();
    }

    /**
     * 比较年月
     */
    public static boolean compareYearAndMonth(Date date1, Date date2) {
        //传入日期
        Calendar calder1 = Calendar.getInstance();
        calder1.setTime(date1);//设置时间
        int year1 = calder1.get(Calendar.YEAR);//获取年份
        int month1 = calder1.get(Calendar.MONTH);//获取月份
        Calendar calder2 = Calendar.getInstance();
        calder2.setTime(date1);//设置时间
        int year2 = calder2.get(Calendar.YEAR);//获取年份
        int month2 = calder2.get(Calendar.MONTH);//获取月份
        return year1 == year2 && month1 == month2;
    }

    public static Date getYearAndMonth(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        try {
            return sdf.parse(sdf.format(sdf));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 以周分割时间段
     *
     * @param startTime     开始时间戳(毫秒)
     * @param endTime       结束时间戳(毫秒)
     * @param beginDateList 开始段时间戳 和 结束段时间戳 一一对应
     * @param endDateList   结束段时间戳 和 开始段时间戳 一一对应
     */
    public static void getIntervalTimeByWeek(Long startTime, Long endTime, List<Long> beginDateList, List<Long> endDateList) {
        Date startDate = new Date(startTime);
        Date endDate = new Date(endTime);
        SimpleDateFormat sdw = new SimpleDateFormat("E");
        Calendar calendar = Calendar.getInstance();
        String begin = sdw.format(startDate);
        calendar.setTime(startDate);
        beginDateList.add(calendar.getTimeInMillis());
        if ("星期一".equals(begin)) {
            addTimeStamp(beginDateList, endDateList, startDate, endDate, sdw, calendar);
        } else {
            if ("星期日".equals(sdw.format(startDate))) {
                Calendar special = Calendar.getInstance();
                special.setTime(startDate);
                special.set(Calendar.HOUR_OF_DAY, 23);
                special.set(Calendar.MINUTE, 59);
                special.set(Calendar.SECOND, 59);
                endDateList.add(special.getTime().getTime());
            }
            addTimeStamp(beginDateList, endDateList, startDate, endDate, sdw, calendar);
        }
    }

    private static void addTimeStamp(List<Long> beginDateList, List<Long> endDateList, Date startDate, Date endDate, SimpleDateFormat sdw, Calendar calendar) {
        while (startDate.getTime() < endDate.getTime()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            startDate = calendar.getTime();
            if ("星期一".equals(sdw.format(startDate))) {
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                beginDateList.add(calendar.getTimeInMillis());
            } else if ("星期日".equals(sdw.format(startDate)) || startDate.getTime() >= endDate.getTime()) {
                if (startDate.getTime() <= endDate.getTime()) {
                    calendar.set(Calendar.HOUR_OF_DAY, 23);
                    calendar.set(Calendar.MINUTE, 59);
                    calendar.set(Calendar.SECOND, 59);
                    endDateList.add(calendar.getTimeInMillis());
                } else {
                    endDateList.add(endDate.getTime());
                }
            }
        }
    }

    /**
     * 按照月份分割一段时间
     *
     * @param startTime 开始时间戳(毫秒)
     * @param endTime   结束时间戳(毫秒)
     */
    public static List<Long> getIntervalTimeByMonth(Date startTime, Date endTime) {
        List<Long> dateList = new ArrayList<>();
        Date startDate = startTime;
        Date endDate = endTime;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        dateList.add(calendar.getTimeInMillis());
        while (calendar.getTimeInMillis() < endDate.getTime()) {
            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.add(Calendar.DATE, -1);
            calendar.set(Calendar.HOUR_OF_DAY, 13);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            if (calendar.getTimeInMillis() >= endDate.getTime()) {
                dateList.add(endTime.getTime());
                break;
            }
            calendar.add(Calendar.DATE, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            dateList.add(calendar.getTimeInMillis());
        }
        return dateList;
    }

}
