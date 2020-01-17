package com.sinoif.esb.query.model.dto;

import java.io.Serializable;

/**
 * 进程
 */
public class ProcessesDTO implements Serializable {

    /**
     * 进程id
     */
    private String pid;

    /**
     * cpu占用
     */
    private String cpu;

    /**
     * 主频占用
     */
    private String mem;

    /**
     * 表示进程分配的虚拟内存
     */
    private String vsz;

    /**
     * 所有分配的栈内存和堆内存
     */
    private String rss;

    /**
     * 应用名称
     */
    private String name;

    public String getPid() {
        return pid;
    }

    public ProcessesDTO setPid(String pid) {
        this.pid = pid;
        return this;
    }

    public String getCpu() {
        return cpu;
    }

    public ProcessesDTO setCpu(String cpu) {
        this.cpu = cpu;
        return this;
    }

    public String getMem() {
        return mem;
    }

    public ProcessesDTO setMem(String mem) {
        this.mem = mem;
        return this;
    }

    public String getVsz() {
        return vsz;
    }

    public ProcessesDTO setVsz(String vsz) {
        this.vsz = vsz;
        return this;
    }

    public String getRss() {
        return rss;
    }

    public ProcessesDTO setRss(String rss) {
        this.rss = rss;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProcessesDTO setName(String name) {
        this.name = name;
        return this;
    }

    public ProcessesDTO(String pid, String cpu, String mem, String vsz, String rss, String name) {
        this.pid = pid;
        this.cpu = cpu;
        this.mem = mem;
        this.vsz = vsz;
        this.rss = rss;
        this.name = name;
    }

    public ProcessesDTO() {
    }
}
