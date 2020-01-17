package com.sinoif.esb.query.resource;

import com.google.common.collect.Lists;
import com.sinoif.esb.query.model.dto.IndexStatisticalFigureDTO;
import com.sinoif.esb.query.model.dto.IndexStatisticsDTO;
import com.sinoif.esb.query.remote.IndexStatisticsRemoteService;
import com.sinoif.esb.query.service.IndexStatisticsService;
import com.sinoif.esb.utils.DateUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.util.function.Tuple2;

import java.time.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 远程接口-首页统计
 * 主要是用于远程调用
 */
public class IndexStatisticsRemoteResource implements IndexStatisticsRemoteService {

    @Autowired
    private IndexStatisticsService indexStatisticsService;

    @Override
    public IndexStatisticsDTO statistics(Date currentTime) {
        IndexStatisticsDTO statistics = new IndexStatisticsDTO();
        if (Objects.isNull(currentTime)) {
            return statistics;
        }
        LocalDateTime current = LocalDateTime.ofInstant(currentTime.toInstant(), ZoneId.systemDefault());

        /**处理统计时间**/
        //今天
        Tuple2<String, String> toDay = DateUtils.day(current, 0, false);
        //本周
        Tuple2<String, String> thisWeek = DateUtils.week(current, 0, false);
        //本月
        Tuple2<String, String> thisMonth = DateUtils.month(current, 0, false);

        //上周
        Tuple2<String, String> lastWeek = DateUtils.week(current, 1, false);
        //上月
        Tuple2<String, String> lastMonth = DateUtils.month(current, 1, false);

        //上上周
        Tuple2<String, String> twoWeek = DateUtils.week(current, 2, false);
        //上上月
        Tuple2<String, String> twoMonth = DateUtils.month(current, 2, false);


        /***统计不同时间的数据***/
        /**统计:今天(在线接口：当月活动的接口数量)**/
        Long onLineNumber = indexStatisticsService.intervalStatisticsInterface(DateUtils.formatStringToLocalDateTime(toDay.getT1(), DateUtils.Format.FORMAT_YMDHMS), DateUtils.formatStringToLocalDateTime(toDay.getT2(), DateUtils.Format.FORMAT_YMDHMS));

        /**统计:今天(瞬时数据：当天全部接口日志数量)**/
        Long instantNumber = indexStatisticsService.intervalStatisticsLog(DateUtils.formatStringToLocalDateTime(toDay.getT1(), DateUtils.Format.FORMAT_YMDHMS), DateUtils.formatStringToLocalDateTime(toDay.getT2(), DateUtils.Format.FORMAT_YMDHMS));

        /**统计:所有(总数据量：历史所有数量统计)**/
        Long totalNumber = indexStatisticsService.intervalStatisticsLog(LocalDateTime.of(1970, 1, 1, 0, 0, 0), LocalDateTime.now());

        /**统计:本周**/
        Long thisWeekNumber = indexStatisticsService.intervalStatisticsLog(DateUtils.formatStringToLocalDateTime(thisWeek.getT1(), DateUtils.Format.FORMAT_YMDHMS), DateUtils.formatStringToLocalDateTime(thisWeek.getT2(), DateUtils.Format.FORMAT_YMDHMS));

        /**统计:本月**/
        Long thisMonthNumber = indexStatisticsService.intervalStatisticsLog(DateUtils.formatStringToLocalDateTime(thisMonth.getT1(), DateUtils.Format.FORMAT_YMDHMS), DateUtils.formatStringToLocalDateTime(thisMonth.getT2(), DateUtils.Format.FORMAT_YMDHMS));

        /**统计:上周**/
        Long lastWeekNumber = indexStatisticsService.intervalStatisticsLog(DateUtils.formatStringToLocalDateTime(lastWeek.getT1(), DateUtils.Format.FORMAT_YMDHMS), DateUtils.formatStringToLocalDateTime(lastWeek.getT2(), DateUtils.Format.FORMAT_YMDHMS));

        /**统计:上月**/
        Long lastMonthNumber = indexStatisticsService.intervalStatisticsLog(DateUtils.formatStringToLocalDateTime(lastMonth.getT1(), DateUtils.Format.FORMAT_YMDHMS), DateUtils.formatStringToLocalDateTime(lastMonth.getT2(), DateUtils.Format.FORMAT_YMDHMS));

        /**统计:上上周**/
        Long twoWeekNumber = indexStatisticsService.intervalStatisticsLog(DateUtils.formatStringToLocalDateTime(twoWeek.getT1(), DateUtils.Format.FORMAT_YMDHMS), DateUtils.formatStringToLocalDateTime(twoWeek.getT2(), DateUtils.Format.FORMAT_YMDHMS));

        /**统计:上上月**/
        Long twoMonthNumber = indexStatisticsService.intervalStatisticsLog(DateUtils.formatStringToLocalDateTime(twoMonth.getT1(), DateUtils.Format.FORMAT_YMDHMS), DateUtils.formatStringToLocalDateTime(twoMonth.getT2(), DateUtils.Format.FORMAT_YMDHMS));


        return statistics
                .setOnLineNumber(onLineNumber)
                .setInstantNumber(instantNumber)
                .setTotalNumber(totalNumber)
                .setThisWeekTotalNumber(thisWeekNumber)
                .setThisWeekFloatNumber(floatNumber(thisWeekNumber, lastWeekNumber))
                .setThisMonthTotalNumber(thisMonthNumber)
                .setThisMonthFloatNumber(floatNumber(thisMonthNumber, lastMonthNumber))
                .setLastWeekTotalNumber(lastWeekNumber)
                .setLastWeekFloatNumber(floatNumber(lastWeekNumber, twoWeekNumber))
                .setLastMonthTotalNumber(lastMonthNumber)
                .setLastMonthFloatNumber(floatNumber(lastMonthNumber, twoMonthNumber));
    }

    /**
     * 计算浮动
     *
     * @param now
     * @param before
     * @return
     */
    private Double floatNumber(Long now, Long before) {
        if (before <= 0) {
            return 0D;
        }
        //浮动方向 1:正数、-1:负数
        Integer floatDirection = now >= before ? NumberUtils.INTEGER_ONE : NumberUtils.INTEGER_MINUS_ONE;
        return Double.valueOf((Double.valueOf(now) / Double.valueOf(before)) * 100 * floatDirection);
    }

    @Override
    public List<IndexStatisticalFigureDTO> statisticalFigure(Date beginTime, Date endTime) {
        if (Objects.isNull(beginTime) || Objects.isNull(endTime)) {
            return Lists.newArrayList();
        }
        LocalDateTime begin = LocalDateTime.ofInstant(beginTime.toInstant(), ZoneId.systemDefault());
        LocalDateTime end = LocalDateTime.ofInstant(endTime.toInstant(), ZoneId.systemDefault());
        return indexStatisticsService.intervalStatisticalFigure(begin, end);
    }
}
