package com.redis.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by https://github.com/kuangcp on 17-6-21  上午10:07
 * 自定义时间工具类
 */
public class MythTime {

  private static SimpleDateFormat simpleDateFormat;

  public static String getTime() {
    Date date = new Date();
    simpleDateFormat = new SimpleDateFormat("HH:mm:ss:MM");
    return simpleDateFormat.format(date);
  }

  public static String getDateTime() {
    Date date = new Date();
    simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:MM");
    return simpleDateFormat.format(date);
  }

  public static String getDate() {
    Date date = new Date();
    simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    return simpleDateFormat.format(date);
  }

}
