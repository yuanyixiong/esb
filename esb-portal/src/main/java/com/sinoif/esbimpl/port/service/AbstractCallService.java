package com.sinoif.esbimpl.port.service;

import com.sinoif.esbimpl.port.interfaces.InterfaceContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p></p>
 *
 * @author chenxj
 * @date 2019/10/22
 */
public abstract class AbstractCallService implements ICallService {
    @Autowired
    InterfaceContext interfaceContext;
}
