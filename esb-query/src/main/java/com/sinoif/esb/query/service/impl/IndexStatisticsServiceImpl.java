package com.sinoif.esb.query.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sinoif.esb.query.constants.Constants;
import com.sinoif.esb.query.model.dto.IndexStatisticalFigureDTO;
import com.sinoif.esb.query.service.IndexStatisticsService;
import com.sinoif.esb.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 袁毅雄
 * @description 首页统计
 * @date 2019/11/7
 */
@Service
public class IndexStatisticsServiceImpl implements IndexStatisticsService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Long intervalStatisticsLog(LocalDateTime begin, LocalDateTime end) {
        if (Objects.isNull(begin) || Objects.isNull(end) || begin.isAfter(end)) {
            return 0L;
        }
        Criteria criteria = dymQuery(begin, end);
        Query query = new Query().addCriteria(criteria);
        return mongoTemplate.count(query, Constants.MongoDbConstants.MONGO_COLLECTION);
    }

    @Override
    public Long intervalStatisticsInterface(LocalDateTime begin, LocalDateTime end) {
        if (Objects.isNull(begin) || Objects.isNull(end) || begin.isAfter(end)) {
            return 0L;
        }
        Criteria criteria = dymQuery(begin, end);
        Query query = new Query().addCriteria(criteria);
        List<JSONObject> jsons = mongoTemplate.find(query, JSONObject.class, Constants.MongoDbConstants.MONGO_COLLECTION);
        if (CollectionUtils.isEmpty(jsons)) {
            return 0L;
        }
        Integer size = jsons.stream().map(json -> json.getLong("interface_id")).collect(Collectors.toSet()).size();
        return Long.valueOf(Objects.nonNull(size) ? size : 0L);
    }

    @Override
    public List<IndexStatisticalFigureDTO> intervalStatisticalFigure(LocalDateTime begin, LocalDateTime end) {
        if (Objects.isNull(begin) || Objects.isNull(end) || begin.isAfter(end)) {
            return Lists.newArrayList();
        }
        Criteria criteria = dymQuery(begin, end);
        Query query = new Query().addCriteria(criteria);
        List<JSONObject> jsons = mongoTemplate.find(query, JSONObject.class, Constants.MongoDbConstants.MONGO_COLLECTION);
        Map<String, Integer> mongodbResult = Maps.newHashMap();
        if (!CollectionUtils.isEmpty(jsons)) {
            //统计每天的数据
            mongodbResult.putAll(
                    jsons.stream().map(
                            json -> Tuples.of(
                                    json.getString("_id"),
                                    DateUtils.formatLocalDateTimeToString(
                                            DateUtils.formatStringToLocalDateTime(json.getString("invoke_time"), DateUtils.Format.FORMAT_YMDHMS), DateUtils.Format.FORMAT_YMD
                                    )
                            )
                    ).collect(Collectors.groupingBy(Tuple2::getT2)).entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, map -> map.getValue().size()))
            );
        }
        statisticsIntegrity(mongodbResult, begin.toLocalDate(), end.toLocalDate());
        List<IndexStatisticalFigureDTO> result = mongodbResult.entrySet().stream()
                .map(map -> Tuples.of(DateUtils.formatStringToDate(map.getKey(), DateUtils.Format.FORMAT_YMD), map.getValue().longValue()))
                .map(tuple -> new IndexStatisticalFigureDTO().setDay(tuple.getT1()).setNumber(tuple.getT2()))
                .sorted(Comparator.comparing(IndexStatisticalFigureDTO::getDay)).collect(Collectors.toList());
        return result;
    }

    /**
     * 控制报表数据的完整性，保证 begin->end 全都有数据
     *
     * @param map
     * @param begin
     * @param end
     * @return
     */
    private void statisticsIntegrity(Map<String, Integer> map, LocalDate begin, LocalDate end) {
        while (begin.isBefore(end)) {
            String day = DateUtils.formatLocalDateToString(begin, DateUtils.Format.FORMAT_YMD);
            if (Objects.isNull(map.get(day))) {
                map.put(day, 0);
            }
            begin = begin.plusDays(1);
        }
    }

    /**
     * 动态条件
     *
     * @param begin
     * @param end
     * @return
     */
    private Criteria dymQuery(LocalDateTime begin, LocalDateTime end) {

        List<Criteria> criteriaList = Lists.newArrayList();
        criteriaList.add(Criteria.where("invoke_time").gte(begin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        criteriaList.add(Criteria.where("complete_time").lt(end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));

        Criteria[] arr = new Criteria[criteriaList.size()];
        criteriaList.toArray(arr);
        Criteria criteria = new Criteria().andOperator(arr);

        return criteria;
    }
}
