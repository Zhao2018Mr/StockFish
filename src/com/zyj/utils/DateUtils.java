package com.zyj.utils;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class DateUtils {

    public static LocalTime nineLocalTime= LocalTime.of(9,0);
    public static LocalTime elevenLocalTime= LocalTime.of(11,10);
    public static LocalTime thirteenLocalTime= LocalTime.of(13,0);
    public static LocalTime fifteenLocalTime= LocalTime.of(15,0);



    /**
     * 判断当前时间是否在此范围里
     * @return
     */
    public static boolean getBetweenNowDateTime(){
        LocalTime now = LocalTime.now();
        if(now.isAfter(nineLocalTime)&&now.isBefore(elevenLocalTime)){
            return true;
        }
        if(now.isAfter(thirteenLocalTime)&&now.isBefore(fifteenLocalTime)){
            return true;
        }
        return false;
    }

}
