package com.sinoif.esbimpl.port.service;

import com.sinoif.esb.constants.PortalConstants;
import com.sinoif.esb.enums.TypeActiveEnum;
import com.sinoif.esb.enums.TypeTransferEnum;
import com.sinoif.esb.port.bean.Interface;
import com.sinoif.esb.port.bean.InvokeResult;
import com.sinoif.esb.utils.ConcurrentRunnable;
import com.sinoif.esb.utils.InvokeParamHolder;
import com.sinoif.esbimpl.port.interfaces.PassiveInputInterfaceExecutor;
import com.sinoif.esbimpl.port.service.bean.CallRequest;
import com.sinoif.esbimpl.port.service.router.ServiceRoute;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

/**
 * <p>被动输入方式</p>
 *
 * @author chenxj
 * @date 2019/10/22
 */
@ServiceRoute("reactive_input")
@Component
public class ReactiveInputServiceImpl extends AbstractCallService implements PortalConstants {
    private Logger logger = Logger.getLogger(ReactiveInputServiceImpl.class);

    @Autowired
    private PassiveInputInterfaceExecutor passiveInputInterfaceExecutor;

    @Override
    public InvokeResult process(CallRequest request) throws Exception {
        Interface itf = request.getItf();
        if (itf.getTypeTransfer() != TypeTransferEnum.INPUT
                && itf.getTypeActive() != TypeActiveEnum.REACTIVE) {
            return InvokeResult.fail(itf, ITF_TYPE_ERROR);
        }
        ConcurrentRunnable runnable = new ConcurrentRunnable(()->passiveInputInterfaceExecutor.invokeInterface(itf, false),request.getData());
        List<Future<InvokeResult>> future = executor.invokeAll(Collections.singletonList(runnable));
        return future.get(0).get();
    }

    /**
     * 负责执行被动接口的线程池
     */
    ThreadPoolExecutor executor = new ThreadPoolExecutor(30, 30, 100, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>()) {
        @Override
        protected void beforeExecute(Thread t, Runnable r) {
            FutureTask task = ((FutureTask)r);
            try {
                Field field = FutureTask.class.getDeclaredField("callable");
                field.setAccessible(true);
                ConcurrentRunnable concurrentCallable = (ConcurrentRunnable) field.get(task);
                InvokeParamHolder.set(concurrentCallable.getParam());
            } catch (Exception e) {
                e.printStackTrace();
            }
            super.beforeExecute(t, r);
        }
    };

}
