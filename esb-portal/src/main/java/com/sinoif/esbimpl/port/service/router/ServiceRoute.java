package com.sinoif.esbimpl.port.service.router;

import java.lang.annotation.*;


/**
 * @author chenxj
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ServiceRoute {

    String value();
}
