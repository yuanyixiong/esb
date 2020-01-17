package com.sinoif.esb.query.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 进程相关信息
 */
public class ProcessInfoDTO implements Serializable {

    /**
     * 进程数
     */
    private String processCount;

    /**
     * 线程数
     */
    private String threadCount;

    /**
     * 进程列表
     */
    private List<ProcessesDTO> processesList = new ArrayList<>();

    public String getProcessCount() {
        return processCount;
    }

    public ProcessInfoDTO setProcessCount(String processCount) {
        this.processCount = processCount;
        return this;
    }

    public String getThreadCount() {
        return threadCount;
    }

    public ProcessInfoDTO setThreadCount(String threadCount) {
        this.threadCount = threadCount;
        return this;
    }

    public List<ProcessesDTO> getProcessesList() {
        return processesList;
    }

    public ProcessInfoDTO setProcessesList(List<ProcessesDTO> processesList) {
        this.processesList = processesList;
        return this;
    }

    public ProcessInfoDTO(String processCount, String threadCount, List<ProcessesDTO> processesList) {
        this.processCount = processCount;
        this.threadCount = threadCount;
        this.processesList = processesList;
    }

    public ProcessInfoDTO() {
    }
}

