package com.sinoif.esb.query.service;

import com.sinoif.esb.query.model.dto.IndexStatisticalFigureDTO;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 首页统计数据
 */
public interface IndexStatisticsService {

    Long intervalStatisticsLog(LocalDateTime begin, LocalDateTime end);

    Long intervalStatisticsInterface(LocalDateTime begin, LocalDateTime end);

    List<IndexStatisticalFigureDTO> intervalStatisticalFigure(LocalDateTime begin, LocalDateTime end);
}
