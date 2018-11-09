package com.zbcn.demo.joda;

import org.joda.time.DateTime;

/**
 * joda_time
 *
 * @author likun
 * @date 2018/10/24 15:09
 */
public class JodaTime {

    public static void main(String[] args){
        testTimeStyle();
    }

    //测试时间输出格式
    private static void testTimeStyle() {
        //输出时间格式：2018-10-24T15:30:33.574+08:00
        final DateTime today = DateTime.now().withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
        final DateTime end = today.minusDays(24);
        System.out.println(end);
    }
}
