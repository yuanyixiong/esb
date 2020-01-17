package com.sinoif.esb.core.service;

import com.alibaba.fastjson.JSONObject;
import com.sinoif.esb.port.bean.Interface;
import com.sinoif.esb.port.bean.InvokeResult;
import com.sinoif.esb.port.dto.InterfaceApproveDTO;
import com.sinoif.esb.port.param.InterfaceApproveParam;

import java.util.HashMap;
import java.util.List;

/**
 * esb-core模块对外接口
 */
public interface EsbCoreRemoteService {
    /**
     * 向kafka topic 发送数据。
     *
     * @param topic topic
     * @param key 数据键
     * @param jsonData 数据值（json）
     * @return 是否发送成功
     */
    boolean inputData(String topic, String key, String jsonData);

    /**
     * 接口注册，创建kafka topic 与mongodb Collection(类似mysql的table)
     *
     * @param esbInterface 向核心模块注册的接口
     * @return success 调用成功， 其它 注册失败的原因
     */
    String registerInterface(Interface esbInterface);

    /**
     * 删除接口注册
     *
     * @param esbInterface 要删除的接口
     * @return success：成功，其它：失败的原因
     */
    String deleteInterfae(Interface esbInterface);

    /**
     * 查询mongodb中的业务数据
     *
     * @param topic 接口对应的topic，也是mongodb中的表名（collection）
     * @return 返回数值
     */
    String mongoDbQuery(String topic, HashMap<String,String> params) throws Exception;

    /**
     * 瀑布流分页
     *
     * @param topic      Collection名称
     * @param params     条件参数
     * @param pagingSize 每页显示的数据条数
     * @param asc        瀑布流分页顺序
     * @param lastKey    瀑布流分页的列
     * @param lastValue  瀑布流分页的值
     * @return 返回查询json
     */
    String mongoDbQuery(String topic, HashMap<String, String> params, Integer pagingSize, boolean asc, String lastKey, String lastValue);

    /**
     * 向mongoDb中插入日志数据
     *
     * @param id 上级数据id
     * @param topic 目标Collection
     * @param jsonData 业务数据
     * @param keyProperty 主键名称
     * @param dataCount 接口传输数据的记录数
     * @return 是否成功
     */
    InvokeResult saveLog(String topic, String jsonData, String keyProperty,String time,int dataCount);

    InvokeResult saveLogData(long parentId,String interfaceId,String parentTime,String topic, String jsonString,String keyProperty);

    /**
     * 更新mongodb中的记录
     *
     * @param topic 目录collection
     * @param data 要更新的数据，key-列表， value-值
     * @param keyProperty id例名称
     */
    long mongoDbUpdate(String topic,HashMap<String,String> data, String keyProperty,Object keyValue);

    /**
     * 获取查询接口获取数具的“更新时间”字段。
     *
     * @param topic 接口对应topic
     * @param lastUpdateField 接口数据更新字段属性名称
     * @return 获取的所有的接口数据中的最大的更新时间
     */
    String getMaxDateOfFetchedData(String topic,String lastUpdateField);


    /**
     * 保存接口数据审核状态
     *
     * @param interfaceId 接口id
     * @param topic 接口topic
     * @param inputSystem 源系充
     * @param outputSystem 目标系统
     * @param interfaceName 接口名称
     * @param data 需要审核的数据
     */
    void saveApproveInfo(long id,long interfaceId,String topic,String inputSystem,String outputSystem,
                         String interfaceName,String time,String data);

    /**
     * 根据id查询数据
     *
     * @param idProperty id 属性
     * @param idValue id 值
     * @return 数据记录的 json 值
     */
    JSONObject findDataById(String topic, String idProperty, String idValue);

    String getListeners();

}
