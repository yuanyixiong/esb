package com.sinoif.esb.utils;

import com.sinoif.esb.port.bean.InvokeResult;

import java.util.LinkedHashMap;
import java.util.concurrent.Callable;

public class ConcurrentRunnable implements Callable<InvokeResult> {
    Callable<InvokeResult> callable;
    LinkedHashMap<String, String> param;

    public ConcurrentRunnable(Callable<InvokeResult> callable, LinkedHashMap<String, String> param) {
        this.callable = callable;
        this.param = param;
    }

    public Callable<InvokeResult> getCallable() {
        return callable;
    }

    public LinkedHashMap<String, String> getParam() {
        return param;
    }

    @Override
    public InvokeResult call() throws Exception {
        return callable.call();
    }
}
