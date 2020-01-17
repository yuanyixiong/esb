package com.sinoif.esb.utils;

import org.dozer.DozerBeanMapperBuilder;
import org.dozer.Mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author 袁毅雄
 * @description Bean的转换:系统入参Bean、系统业务Bean、系统返回Bean
 * @date 2019/11/1
 */
public class BeanMapper {

    private static Mapper MAPPER = DozerBeanMapperBuilder.buildDefault();

    public static <T> T map(Object source, Class<T> destinationClass) {
        if (source == null) {
            return null;
        }
        return MAPPER.map(source, destinationClass);
    }

    public static void map(Object source, Object destination) {
        MAPPER.map(source, destination);
    }

    public static <T> List<T> mapList(Collection sourceList, Class<T> destinationClass) {
        List<T> destinationList = new ArrayList<>();
        for (Object sourceObject : sourceList) {
            destinationList.add(MAPPER.map(sourceObject, destinationClass));
        }
        return destinationList;
    }

}
