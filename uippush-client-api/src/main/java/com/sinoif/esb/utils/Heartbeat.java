package com.sinoif.esb.utils;

import com.sinoif.esb.query.model.dto.ServiceInformationDTO;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.ArrayList;
import java.util.List;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public final class Heartbeat {

    /**
     * 检测服务是否正常的心跳
     *
     * @param heartbeat
     * @param serviceSurviveState
     * @return
     */
    public static Tuple2<Boolean, List<ServiceInformationDTO>> heartbeat(BooleanSupplier heartbeat, Supplier<List<ServiceInformationDTO>> serviceSurviveState) {
        try {
            if (heartbeat.getAsBoolean()) {
                List<ServiceInformationDTO> list = serviceSurviveState.get();
                return Tuples.of(true, list);
            }
            return Tuples.of(false, new ArrayList());
        } catch (Exception e) {
            return Tuples.of(false, new ArrayList());
        }
    }
}
