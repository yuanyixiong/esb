package com.sinoif.esb.utils;

import java.util.LinkedHashMap;

/**
 * <p>实际执行的参数</p>
 *
 * @date 2019/11/14
 */
public class InvokeParamHolder {
    private static ThreadLocal<LinkedHashMap<String, String>> threadVar = new ThreadLocal<>();

    public static LinkedHashMap<String, String> get() {
        return threadVar.get();
    }

    public static void set(LinkedHashMap<String, String> param) {
        threadVar.set(param);
    }

    public static void clear() {
        threadVar.remove();
    }
}
