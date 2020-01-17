package com.sinoif.esb.query.remote;

import com.sinoif.esb.query.model.dto.IndexStatisticalFigureDTO;
import com.sinoif.esb.query.model.dto.IndexStatisticsDTO;

import java.util.*;
import java.util.Date;

/**
 * @author 袁毅雄
 * @description 远程接口-首页统计
 * @date 2019/11/7
 */
public interface IndexStatisticsRemoteService {

    /**
     * 统计
     *
     * @param currentTime
     * @return
     */
    IndexStatisticsDTO statistics(Date currentTime);

    /**
     * 报表
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    List<IndexStatisticalFigureDTO> statisticalFigure(Date beginTime, Date endTime);
}
