package com.sinoif.esb.utils;

import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

public final class DateUtils {

    /**
     * 日期标准格式
     */
    public static final String FORMAT_YMD = "yyyy-MM-dd";
    public static final String FORMAT_HMS = "HH:mm:ss";
    public static final String FORMAT_YMDHMS = String.format("%s %s", FORMAT_YMD, FORMAT_HMS);

    public enum Format {

        FORMAT_YMD("yyyy-MM-dd"),

        FORMAT_HMS("HH:mm:ss"),

        FORMAT_YMDHMS(String.format("%s %s", Format.FORMAT_YMD.getFormat(), Format.FORMAT_HMS.getFormat()));

        private String format;

        public String getFormat() {
            return this.format;
        }

        Format(String format) {
            this.format = format;
        }
    }

    /**
     * 指定日期所在的当天的开始于结束
     *
     * @param inputDate
     * @param offsetDay 偏移单位:天,0不偏移
     * @param future    true-未来、false-过去
     */
    public static Tuple2<String, String> day(LocalDateTime inputDate, Integer offsetDay, Boolean future) {
        Tuple2<LocalDateTime, LocalDateTime> day = day(inputDate.plusDays(offsetDay * (future ? 1 : -1)).toLocalDate());
        String dayStart = formatLocalDateTimeToString(day.getT1(), Format.FORMAT_YMDHMS);
        String dayEnd = formatLocalDateTimeToString(day.getT2(), Format.FORMAT_YMDHMS);
        return Tuples.of(dayStart, dayEnd);
    }

    /**
     * 指定日期所在的当周的开始于结束
     *
     * @param inputDate
     * @param offsetWeek 偏移单位:周,0不偏移
     * @param future     true-未来、false-过去
     */
    public static Tuple2<String, String> week(LocalDateTime inputDate, Integer offsetWeek, Boolean future) {
        Tuple2<LocalDate, LocalDate> week = week(inputDate.plusWeeks(offsetWeek * (future ? 1 : -1)).toLocalDate());
        String weekStart = formatLocalDateTimeToString(day(week.getT1()).getT1(), Format.FORMAT_YMDHMS);
        String weekEnd = formatLocalDateTimeToString(day(week.getT2()).getT2(), Format.FORMAT_YMDHMS);
        return Tuples.of(weekStart, weekEnd);
    }

    /**
     * 指定日期所在的当月的开始于结束
     *
     * @param inputDate
     * @param offsetMonth 偏移单位:月,0不偏移
     * @param future      true-未来、false-过去
     */
    public static Tuple2<String, String> month(LocalDateTime inputDate, Integer offsetMonth, Boolean future) {
        Tuple2<LocalDate, LocalDate> month = month(inputDate.plusMonths(offsetMonth * (future ? 1 : -1)).toLocalDate());
        String monthStart = formatLocalDateTimeToString(day(month.getT1()).getT1(), Format.FORMAT_YMDHMS);
        String monthEnd = formatLocalDateTimeToString(day(month.getT2()).getT2(), Format.FORMAT_YMDHMS);
        return Tuples.of(monthStart, monthEnd);
    }

    /**
     * 格式化时间:LocalDateTime->String
     *
     * @param inputDate
     * @param format
     * @return
     */
    public static String formatLocalDateTimeToString(LocalDateTime inputDate, Format format) {
        return DateTimeFormatter.ofPattern(format.getFormat()).format(inputDate);
    }

    /**
     * 格式化时间:String->LocalDateTime
     *
     * @param inputDate
     * @param format
     * @return
     */
    public static LocalDateTime formatStringToLocalDateTime(String inputDate, Format format) {
        return LocalDateTime.parse(inputDate, DateTimeFormatter.ofPattern(format.getFormat()));
    }

    /**
     * 格式化时间:LocalDate->String
     *
     * @param inputDate
     * @param format
     * @return
     */
    public static String formatLocalDateToString(LocalDate inputDate, Format format) {
        return DateTimeFormatter.ofPattern(format.getFormat()).format(inputDate);
    }

    /**
     * 格式化时间:String->LocalDate
     *
     * @param inputDate
     * @param format
     * @return
     */
    public static LocalDate formatStringToLocalDate(String inputDate, Format format) {
        return LocalDate.parse(inputDate, DateTimeFormatter.ofPattern(format.getFormat()));
    }

    /**
     * 格式化时间:LocalDate->Date
     *
     * @param inputDate
     * @return
     */
    public static Date formatLocalDateToDate(LocalDate inputDate) {
        return Date.from(inputDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 格式化时间:Date->LocalDate
     *
     * @param inputDate
     * @return
     */
    public static LocalDate formatDateToLocalDate(Date inputDate) {
        return LocalDateTime.ofInstant(inputDate.toInstant(), ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * 格式化时间:String->Date
     *
     * @param inputDate
     * @return
     */
    public static Date formatStringToDate(String inputDate, Format format) {
        return formatLocalDateToDate(formatStringToLocalDate(inputDate,format));
    }

    /**
     * 指定一个日期获取当天开始时间和结束时间
     *
     * @param inputDate
     * @return
     */
    public static Tuple2<LocalDateTime, LocalDateTime> day(LocalDate inputDate) {
        LocalDateTime dayStart = LocalDateTime.of(inputDate, LocalTime.MIN);
        LocalDateTime dayEnd = LocalDateTime.of(inputDate, LocalTime.MAX);
        return Tuples.of(dayStart, dayEnd);
    }

    /**
     * 指定一个日期获取所在周开始时间和结束时间
     *
     * @param inputDate
     * @return
     */
    public static Tuple2<LocalDate, LocalDate> week(LocalDate inputDate) {
        TemporalAdjuster FIRST_OF_WEEK = TemporalAdjusters.ofDateAdjuster(localDate -> localDate.minusDays(localDate.getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue()));
        LocalDate weekStart = inputDate.with(FIRST_OF_WEEK);
        TemporalAdjuster LAST_OF_WEEK = TemporalAdjusters.ofDateAdjuster(localDate -> localDate.plusDays(DayOfWeek.SUNDAY.getValue() - localDate.getDayOfWeek().getValue()));
        LocalDate weekEnd = inputDate.with(LAST_OF_WEEK);
        return Tuples.of(weekStart, weekEnd);
    }

    /**
     * 指定一个日期获取所在月开始时间和结束时间
     *
     * @param inputDate
     * @return
     */
    public static Tuple2<LocalDate, LocalDate> month(LocalDate inputDate) {
        LocalDate monthStart = LocalDate.of(inputDate.getYear(), inputDate.getMonth(), 1);
        LocalDate monthEnd = inputDate.with(TemporalAdjusters.lastDayOfMonth());
        return Tuples.of(monthStart, monthEnd);
    }
}
