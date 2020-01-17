package com.sinoif.esbimpl.port.interfaces;

/**
 * 参数处理，负责将接口参数值表达式解释具体的参数值；
 */
class ParameterEvaluator {

    /**
     * 解释参数表达式
     * @param valueExpression 值表达式
     * @param data 数据源
     * @return 参数的实际数值
     */
    public static String evaluateParameterValue(String valueExpression, String data) {
        if("${data}".equals(valueExpression)){
            return data;
        }else{
            return valueExpression;
        }
    }
}
