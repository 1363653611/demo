package com.zbcn.demo.joda;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;

import java.time.Instant;

/**
 * joda_time
 *
 * @author likun
 * @date 2018/10/24 15:09
 */
public class JodaTime {

    public static void main(String[] args){

        //testTimeStyle();
//        System.out.println(DateTime.now());
//        System.out.println(DateTime.now().plusDays(1).minusDays(30));
        //test();
//        testDays();
//        test();
//        test();
//        testplusDays();
//        testInstant();
//        String[] split = "contacts.bankCode".split(".");
//        String[] split1 = StringUtils.split("contacts.bankCode", ".");
        //testTime();
        testDays();
    }


    private static void testInstant(){
        Instant now = Instant.now();
        System.out.println(now);
    }

    //测试时间输出格式
    private static void testTimeStyle() {
        //输出时间格式：2018-10-24T15:30:33.574+08:00
        final DateTime today = DateTime.now().withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0).plusDays(1);
        final DateTime end = today.minusDays(60);
        System.out.println(end);
        

    }

    private static void testTime(){
        DateTime auditTime = new DateTime("2018-10-27T18:06:22.000+08:00");
        final DateTime today = DateTime.now().withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0).plusDays(1);
        Days days = Days.daysBetween(auditTime, today);
        System.out.println(days.getDays());
    }

    private static void test(){
        //查询，符合条件的打标(当天算一天)
        //DateTime auditTime =
        //final DateTime today = DateTime.now().withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0).plusDays(1);
        final DateTime today = new DateTime("2018-12-21T00:00:00.000+08:00");
        final DateTime removeTipTime = today.minusDays(40);
        System.out.println(removeTipTime);
    }

    /**
     * 两个时间中间的时间差
     */
    private static void testDays(){
//        DateTime.now().withTimeAtStartOfDay();
//        System.out.println( "startDay:"+DateTime.now().withTimeAtStartOfDay());
        final DateTime today = DateTime.now().withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0).plusDays(1);
//        System.out.println(today);
//        DateTime auditTime = DateTime.now().withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0).minusDays(9);
//        System.out.println(auditTime);
        DateTime dateTime = today.minusDays(40);
//        int days = Days.daysBetween(auditTime,today).getDays();
        System.out.println(dateTime);
    }

    public static void testplusDays(){
//        DateTime now = DateTime.now().withTimeAtStartOfDay().plusDays(1);
//        System.out.println("开始时间："+now);
//        //DateTime dateTime = DateTime.now().withTimeAtStartOfDay();
//        DateTime dateTime = new DateTime("2018-11-21T14:59:38.000+08:00").withTimeAtStartOfDay();
        final DateTime
                today = DateTime.now().withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0).plusDays(1);
        System.out.println("开始时间："+today);
        DateTime auditTime = new DateTime("2018-11-21T00:00:00.000Z").toDateTime(DateTimeZone.forID("Asia/Shanghai"));
        System.out.println("结束时间："+auditTime);
        int days = Days.daysBetween(auditTime, today).getDays();
        System.out.println(days);

    }
}
