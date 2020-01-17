package com.sinoif.esb.query.page;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;

/**
 * 分页包装类
 *
 * @param <T> 要包装的分页对象
 */
public class PageData<T> implements Serializable {
    int page;
    long totalCount;
    List<T> datas;

    private void install(Object[] queryResult, Class<T> clzz) {
        setTotalCount( Long.parseLong(queryResult[2].toString()));
        setPage(Integer.parseInt(queryResult[1].toString()));
        setDatas(JSON.parseArray(queryResult[0].toString(), clzz));
    }

    /**
     * 获取分页数据
     *
     * @param queryResult 查询结果 object[] 数据 0：json结果， 1：页面记录数 2：页数
     * @param tClass 返回结果的类型的class
     * @param <T> 返回结果的对象
     * @return 分页结果
     */
    public static <T> PageData getPageData(Object[] queryResult, Class<T> tClass) {
        PageData<T> pageData = new PageData<>();
        pageData.install(queryResult, tClass);
        return pageData;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }
}
