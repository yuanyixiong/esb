package com.sinoif.esbimpl.port.annotation;

import java.lang.annotation.*;

/***
 * @author yuanyixiong
 * @date 2019/10/29
 * @describe 接口调用, 记录接口信息注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InvokeLog {
}
