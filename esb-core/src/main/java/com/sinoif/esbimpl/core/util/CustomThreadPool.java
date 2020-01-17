package com.sinoif.esbimpl.core.util;

import com.sinoif.esb.utils.ConcurrentRunnable;
import com.sinoif.esb.utils.InvokeParamHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.concurrent.*;

/**
 * 自定义executor,确保每一个接口调用之前在线程上绑定正确的参数
 */
@Component
public class CustomThreadPool extends ThreadPoolExecutor {

    // 自定义的任务对象
    private Field field;

    /**
     * 构造方法，executor的配置
     */
    public CustomThreadPool() throws NoSuchFieldException {
        super(30, 30, 100, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
        field = FutureTask.class.getDeclaredField("callable");
        field.setAccessible(true);
    }

    /**
     * 每一个任务开始执行前，将新的接口参数绑定到线程上，确保数据不乱
     *
     * @param t 线程
     * @param r 任务
     */
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        FutureTask task = ((FutureTask) r);
        try {
            ConcurrentRunnable concurrentCallable = (ConcurrentRunnable) field.get(task);
            InvokeParamHolder.set(concurrentCallable.getParam());
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.beforeExecute(t, r);
    }

}
