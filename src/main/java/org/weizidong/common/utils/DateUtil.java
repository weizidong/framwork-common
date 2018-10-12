package org.weizidong.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期操作工具类
 *
 * @author WeiZiDong
 */
public class DateUtil {
  private DateUtil() {
  }

  /**
   * 格式: yyyy年MM月
   */
  public static final String P_DATE_CN = "yyyy年MM月";
  /**
   * 格式: yyyy年MM月dd日 HH时mm分ss秒
   */
  public static final String P_DATETIME_CN = "yyyy年MM月dd日 HH时mm分ss秒";
  /**
   * 格式: yyyy-MM-dd
   **/
  public static final String P_DATE = "yyyy-MM-dd";
  /**
   * 格式: yyyy-MM-dd HH:mm
   **/
  public static final String P_DATETIME = "yyyy-MM-dd HH:mm";
  /**
   * 格式: yyyy-MM-dd HH:mm:ss
   **/
  public static final String P_TIMESTAMP = "yyyy-MM-dd HH:mm:ss";
  /**
   * 格式: yyyyMMdd
   **/
  public static final String P_DATE_1 = "yyyyMMdd";
  /**
   * 格式: yyyyMMddHHmm
   **/
  public static final String P_DATETIME_1 = "yyyyMMddHHmm";
  /**
   * 格式: yyyyMMddHHmmss
   **/
  public static final String P_TIMESTAMP_1 = "yyyyMMddHHmmss";
  /**
   * 格式: yyyy/MM/dd
   **/
  public static final String P_DATE_2 = "yyyy/MM/dd";
  /**
   * 格式: yyyy/MM/dd/HH/mm
   **/
  public static final String P_DATETIME_2 = "yyyy/MM/dd/HH/mm";
  /**
   * 格式: yyyy/MM/dd/HH/mm/ss
   **/
  public static final String P_TIMESTAMP_2 = "yyyy/MM/dd/HH/mm/ss";
  /**
   * 格式: HH:mm
   **/
  public static final String P_TIME = "HH:mm";
  /**
   * 格式: HH:mm:ss
   **/
  public static final String P_TIME_1 = "HH:mm:ss";

  /**
   * 转换成日历
   *
   * @param date 当前时间
   * @return 转换后的日历
   */
  public static Calendar toCalendar(Date date) {
    Calendar c = Calendar.getInstance();
    c.clear();
    c.setTime(date);
    return c;
  }

  /**
   * 转换成日历
   *
   * @param dateStr 日期字符串
   * @param pattern 日期格式
   * @return 转换后的日历
   */
  public static Calendar toCalendar(String dateStr, String pattern) {
    return toCalendar(toDate(dateStr, pattern));
  }

  /**
   * 指定格式的字符串转换成时间
   *
   * @param dateStr 日期字符串
   * @param pattern 日期格式
   * @return 转换后的时间
   */
  public static Date toDate(String dateStr, String pattern) {
    try {
      return new SimpleDateFormat(pattern).parse(StringUtils.defaultIfBlank(dateStr, ""));
    } catch (ParseException e) {
      LogUtil.error(DateUtil.class, e, "指定格式的字符串转换成时间失败！");
    }
    return null;
  }


  /**
   * 将一种格式的日期字符串转换成另外一种格式的日期字符串
   *
   * @param dateStr       日期字符串
   * @param sourcePattern 源格式
   * @param targetPattern 目标格式
   * @return 转换后的日期字符串
   */
  public static String transfer(String dateStr, String sourcePattern, String targetPattern) {
    return format(toDate(dateStr, sourcePattern), targetPattern);
  }

  /**
   * 将日期格式化
   *
   * @param date    日期
   * @param pattern 日期格式
   * @return 格式化后的字符串
   */
  public static String format(Date date, String pattern) {
    return new SimpleDateFormat(pattern).format(date);
  }


  /**
   * 计算两个日期之间的时间间隔
   *
   * @param start 开始时间
   * @param end   结束时间
   * @param type  单位（Calendar.YEAR、Calendar.MONTH、Calendar.WEEK_OF_YEAR、Calendar.DAY_OF_YEAR、Calendar.HOUR、Calendar.MINUTE、Calendar.SECOND）
   * @return 时间间隔(end比start小, 结果为负数)
   */
  public static long diff(Date start, Date end, int type) {
    long diffTime = end.getTime() - start.getTime();
    switch (type) {
      case Calendar.YEAR:
        return toCalendar(start).get(Calendar.YEAR) - toCalendar(end).get(Calendar.YEAR);
      case Calendar.MONTH:
        return toCalendar(start).get(Calendar.MONTH) - toCalendar(end).get(Calendar.MONTH);
      case Calendar.WEEK_OF_YEAR:
        return diffTime / 3600000 / 24 / 7;
      case Calendar.DAY_OF_YEAR:
        return diffTime / 3600000 / 24;
      case Calendar.HOUR:
        return diffTime / 3600000;
      case Calendar.MINUTE:
        return diffTime / 60000;
      case Calendar.SECOND:
        return diffTime / 1000;
      default:
        return diffTime;
    }
  }

  /**
   * 获取指定时间之后多长间隔的时间
   *
   * @param date  指定时间
   * @param times 时间间隔
   * @param type  时间单位类型，Calendar.*
   * @return 多长间隔之后的时间
   */
  public static Date getAfter(Date date, int times, int type) {
    Calendar calendar = toCalendar(date);
    calendar.set(type, calendar.get(type) + times);
    return calendar.getTime();
  }


  /**
   * 获取指定时间之前多长间隔的时间
   *
   * @param date  指定时间
   * @param times 时间间隔
   * @param type  时间单位类型，Calendar.*
   * @return 多长间隔之前的时间
   */
  public static Date getBefore(Date date, int times, int type) {
    Calendar calendar = toCalendar(date);
    calendar.set(type, calendar.get(type) - times);
    return calendar.getTime();
  }

  /**
   * 获取指定时间在当年的哪一周
   *
   * @param date 指定时间
   * @return 第多少周
   */
  public static int getWeekOfYear(Date date) {
    Calendar calendar = toCalendar(date);
    return calendar.get(Calendar.WEEK_OF_YEAR);
  }

  /**
   * 获取当前日期是星期几
   */
  public static Integer getDayOfWeek(Date date) {
    Calendar calendar = toCalendar(date);
    int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
    if (w <= 0) {
      w = 7;
    }
    return w;
  }

  /**
   * 取出指定时间所在周的第一天的日期数据
   *
   * @param date           指定时间
   * @param firstDayOfWeek 一周从哪天开始,0:星期天，1：星期1...
   */
  public static Date getStartOfWeek(Date date, int firstDayOfWeek) {
    Calendar cal = toCalendar(date);
    cal.setFirstDayOfWeek(firstDayOfWeek + 1);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    return cal.getTime();
  }

  /**
   * 取出指定时间所在周的最后一天的日期数据
   *
   * @param date           指定时间
   * @param firstDayOfWeek 一周从哪天开始,0:星期天，1：星期1...
   */
  public static Date getEndOfWeek(Date date, int firstDayOfWeek) {
    Calendar cal = toCalendar(date);
    cal.setFirstDayOfWeek(firstDayOfWeek + 1);
    cal.set(Calendar.HOUR_OF_DAY, 23);
    cal.set(Calendar.MINUTE, 59);
    cal.set(Calendar.SECOND, 59);
    cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
    return cal.getTime();
  }

  /**
   * 取出指定时间所在月的第一天的日期数据
   *
   * @param date 指定时间
   */
  public static Date getStartOfMonth(Date date) {
    Calendar cal = toCalendar(date);
    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    return cal.getTime();
  }

  /**
   * 取出指定时间所在月的最后一天的日期数据
   *
   * @param date 指定时间
   */
  public static Date getEndOfMonth(Date date) {
    Calendar cal = toCalendar(date);
    cal.set(Calendar.HOUR_OF_DAY, 23);
    cal.set(Calendar.MINUTE, 59);
    cal.set(Calendar.SECOND, 59);
    cal.set(Calendar.MILLISECOND, 999);
    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
    return cal.getTime();
  }

  /**
   * 取出指定时间的开始时间
   *
   * @param date 指定时间
   */
  public static Date getStart(Date date, int type) {
    Calendar cal = toCalendar(date);
    if (type == Calendar.YEAR) {
      cal.set(Calendar.MONTH, cal.getActualMinimum(Calendar.MONTH));

      cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));

      cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));

      cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));

      cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));

      cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));
    } else if (type == Calendar.MONTH) {
      cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));

      cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));

      cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));

      cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));

      cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));
    } else if (type == Calendar.DATE) {
      cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));

      cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));

      cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));

      cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));
    } else if (type == Calendar.HOUR) {
      cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));

      cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));

      cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));
    } else if (type == Calendar.MINUTE) {
      cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));

      cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));
    } else {
      cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));
    }
    return cal.getTime();
  }

  /**
   * 取出指定时间的结束时间
   *
   * @param date 指定时间
   */
  public static Date getEnd(Date date, int type) {
    Calendar cal = toCalendar(date);
    if (type == Calendar.YEAR) {
      cal.set(Calendar.MONTH, cal.getActualMaximum(Calendar.MONTH));

      cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));

      cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));

      cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));

      cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));

      cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND));
    } else if (type == Calendar.MONTH) {
      cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));

      cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));

      cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));

      cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));

      cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND));
    } else if (type == Calendar.DATE) {
      cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));

      cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));

      cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));

      cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND));
    } else if (type == Calendar.HOUR) {
      cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));

      cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));

      cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND));
    } else if (type == Calendar.MINUTE) {
      cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));

      cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND));
    } else {
      cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND));
    }
    return cal.getTime();
  }

  /**
   * 获取当前系统时间
   *
   * @return 格式化后的系统时间
   */
  public static String getSystemTime() {
    return format(new Date(), P_TIMESTAMP);
  }


  /**
   * 返回指定日期的季度
   *
   * @param date 指定日期
   * @return 当前所在季度
   */
  public static int getQuarterOfYear(Date date) {
    Calendar calendar = toCalendar(date);
    return calendar.get(Calendar.MONTH) / 3 + 1;
  }


  /**
   * 计算年龄
   *
   * @param birthDay 生日
   * @return 年龄
   */
  public static int getAge(Date birthDay) {
    int age = 0;
    Calendar born = Calendar.getInstance();
    Calendar now = Calendar.getInstance();
    if (birthDay != null) {
      now.setTime(new Date());
      born.setTime(birthDay);
      if (born.after(now)) {
        throw new IllegalArgumentException("出生日期不能超过当前日期");
      }
      age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
      int nowDayOfYear = now.get(Calendar.DAY_OF_YEAR);
      int bornDayOfYear = born.get(Calendar.DAY_OF_YEAR);
      if (nowDayOfYear < bornDayOfYear) {
        age -= 1;
      }
    }
    return age;
  }

  /**
   * 获得本天的开始时间，即2012-01-01 00:00:00
   *
   * @return 当天的开始时间
   */
  public static Date getCurrentDayStartTime() {
    return getStart(new Date(), Calendar.DATE);
  }

  /**
   * 获得本天的结束时间，即2012-01-01 23:59:59
   *
   * @return 当天的结束时间
   */

  public static Date getCurrentDayEndTime() {
    return getEnd(new Date(), Calendar.DATE);
  }

  /**
   * 前一天
   *
   * @param date 指定时间
   * @return 指定时间前一天
   */
  public static Date getPrevDay(Date date) {
    return getBefore(date, 1, Calendar.DATE);
  }

  /**
   * 后一天
   *
   * @param date 指定时间
   * @return 指定时间后一天
   */
  public static Date getNextDay(Date date) {
    return getAfter(date, 1, Calendar.DATE);
  }

  /**
   * 获取所在周的星期一
   *
   * @param date 指定时间
   * @return 指定时间的周一
   */
  public static Date getMonday(Date date) {
    return getStartOfWeek(date, 1);
  }

  /**
   * 获取所在周的星期天
   *
   * @param date 指定时间
   * @return 指定时间的周日
   */
  public static Date getSunday(Date date) {
    return getEndOfWeek(date, 1);
  }

  /**
   * 判断给定的时间是否是在今天以前
   *
   * @param date 指定时间
   * @return 在今天之前为true，否则为false
   */
  public static boolean isBeforeToday(Date date) {
    Calendar current = toCalendar(date);
    // 今天
    Calendar today = Calendar.getInstance();
    today.set(Calendar.HOUR_OF_DAY, 0);
    today.set(Calendar.MINUTE, 0);
    today.set(Calendar.SECOND, 0);
    today.set(Calendar.MILLISECOND, 0);
    return current.before(today);
  }

  /**
   * Date 转 LocalDate
   */
  public static LocalDate toLocalDate(Date date) {
    if (date == null) {
      return LocalDate.now();
    }
    Instant instant = date.toInstant();
    ZoneId zoneId = ZoneId.systemDefault();
    return instant.atZone(zoneId).toLocalDate();
  }

  /**
   * LocalDate 转 Date
   */
  public static Date toDate(LocalDate date) {
    if (date == null) {
      return new Date();
    }
    ZoneId zoneId = ZoneId.systemDefault();
    ZonedDateTime zdt = date.atStartOfDay(zoneId);
    return Date.from(zdt.toInstant());
  }

  /**
   * 设置系统时间
   */
  public static void setSystemTime(Date time) {
    String osName = System.getProperty("os.name");
    String dateStr = format(time, P_DATE);
    String timeStr = format(time, P_TIME_1);
    try {
      if (osName.matches("^(?i)Windows.*$")) {
        // Window 系统
        // 设置日期 格式：yyyy-MM-dd
        Runtime.getRuntime().exec(" cmd /c date " + dateStr);
        // 设置时间 格式 HH:mm:ss
        Runtime.getRuntime().exec(" cmd /c time " + timeStr);
      } else if (osName.matches("^(?i)Linux.*$")) {
        // Linux 系统
        // 设置日期时间 格式：yyyy-MM-dd HH:mm:ss
        Runtime.getRuntime().exec("date -s " + "\"" + dateStr + " " + timeStr + "\"");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
