package com.zbcn.demo.java8.date;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

public class DateDemon {

    public static void main(String[] args) {
        //timeTest();
        //TimeZone();
        //localTimeTest();
        create();
        week();
        convert();
        convert2();
        convert3();
    }

    public static void timeTest() {
        Clock clock = Clock.systemDefaultZone();
        long millis = clock.millis();

        Instant instant = clock.instant();

        java.util.Date from = Date.from(instant);
        System.out.println(millis);
        System.out.println(instant);
        System.out.println(from);
    }

    /**
     * 在新API中时区使用ZoneId来表示。时区可以很方便的使用静态方法of来获取到。
     * 时区定义了到UTS时间的时间差，在Instant时间点对象到本地日期对象之间转换的时候是极其重要的
     */
    public static void TimeZone() {
        System.out.println(ZoneId.getAvailableZoneIds());
        ZoneId zone1 = ZoneId.of("Europe/Berlin");
        ZoneId zone2 = ZoneId.of("Brazil/East");
        System.out.println(zone1.getRules());
        System.out.println(zone2.getRules());
    }

    /**
     * LocalTime 定义了一个没有时区信息的时间，例如 晚上10点，或者 17:30:15。
     * 下面的例子使用前面代码创建的时区创建了两个本地时间。
     * 之后比较时间并以小时和分钟为单位计算两个时间的时间差：
     */
    public static void localTimeTest() {
        ZoneId zone1 = ZoneId.of("Europe/Berlin");
        ZoneId zone2 = ZoneId.of("Brazil/East");
        LocalTime now = LocalTime.now(zone1);
        LocalTime now1 = LocalTime.now(zone2);

        //相差多少小时
        long hours = ChronoUnit.HOURS.between(now, now1);
        //相差分钟
        long miutes = ChronoUnit.MINUTES.between(now, now1);
        System.out.println(hours);
        System.out.println(miutes);
    }

    private static void create(){
        LocalTime of = LocalTime.of(23, 59, 59);
        System.out.println(of);

        //时间格式化
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(Locale.getDefault());
        LocalTime parse = LocalTime.parse("12.13.52", dateTimeFormatter);
        System.out.println(parse);

        LocalDate xmas = LocalDate.parse("24.12.2014", dateTimeFormatter);
        System.out.println(xmas);
    }


    private static void week(){
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
        LocalDate yesterday = today.minus(1, ChronoUnit.DAYS);
        System.out.println(today);
        System.out.println(tomorrow);
        System.out.println(yesterday);
        LocalDate independenceDay = LocalDate.of(2014, Month.JULY, 4);

        DayOfWeek dayOfWeek = independenceDay.getDayOfWeek();
        System.out.println(dayOfWeek);
    }

    /**
     * LocalDateTime 同时表示了时间和日期，相当于前两节内容合并到一个对象上了。
     * LocalDateTime和LocalTime还有LocalDate一样，都是不可变的。
     * LocalDateTime提供了一些能访问具体字段的方法。
     */
    public static void convert(){
        LocalDateTime sylvester = LocalDateTime.of(2014, Month.DECEMBER, 31, 23, 59, 59);

        DayOfWeek dayOfWeek = sylvester.getDayOfWeek();
        System.out.println(dayOfWeek);      // WEDNESDAY

        Month month = sylvester.getMonth();
        System.out.println(month);          // DECEMBER

        long minuteOfDay = sylvester.getLong(ChronoField.MINUTE_OF_DAY);
        System.out.println(minuteOfDay);
    }


    /**
     * 只要附加上时区信息，就可以将其转换为一个时间点Instant对象，
     * Instant时间点对象可以很容易的转换为老式的java.util.Date。
     */
    public static void convert2(){
        LocalDateTime sylvester = LocalDateTime.of(2014, Month.DECEMBER, 31, 23, 59, 59);
        Instant instant = sylvester
                .atZone(ZoneId.systemDefault())
                .toInstant();

        Date legacyDate = Date.from(instant);
        System.out.println(legacyDate);     // Wed Dec 31 23:59:59 CET 2014
    }

    /**
     * 格式化LocalDateTime和格式化时间和日期一样的，除了使用预定义好的格式外，我们也可以自己定义格式
     * Exception in thread "main" java.time.format.DateTimeParseException:
     * Text 'Nov 03,2014 - 07:13' could not be parsed at index 0
     */
    public static void convert3(){
        //取系统时间
        DateTimeFormatter formatter =
                DateTimeFormatter
                        .ofPattern("MMM dd, yyyy - HH:mm");

        LocalDateTime parsed = LocalDateTime.parse("十一月 03, 2014 - 07:13", formatter);
        String string = formatter.format(parsed);
        System.out.println(string);
        //指定时区
        DateTimeFormatter formatter2 =
                DateTimeFormatter
                        .ofPattern("MMM dd, yyyy - HH:mm",Locale.ENGLISH);

        LocalDateTime parse2 = LocalDateTime.parse("Nov 03, 2014 - 07:13", formatter2);
        String string2 = formatter.format(parsed);
        System.out.println(string2);     // Nov 03, 2014 - 07:13
    }


}
