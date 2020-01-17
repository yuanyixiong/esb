package com.sinoif.esb.query.model.dto;

import java.io.Serializable;

/**
 * JVM信息
 */
public class JvmDTO implements Serializable {

    /**
     * 用户的账户名称
     */
    private String userName;

    /**
     * 计算机名
     */
    private String computerName;

    /**
     * 计算机域名
     */
    private String userDomain;

    /**
     * 本地ip地址
     */
    private String ip;

    /**
     * 本地主机名
     */
    private String hostName;

    /**
     * JVM可以使用的总内存
     */
    private Long totalMemory;

    /**
     * JVM可以使用的剩余内存
     */
    private Long freeMemory;

    /**
     * JVM可以使用的处理器个数
     */
    private Integer availableProcessors;

    /**
     * Java的运行环境版本
     */
    private String javaVersion;

    /**
     * Java的运行环境供应商
     */
    private String javaVendor;

    /**
     * Java供应商的URL
     */
    private String javaVendorUrl;

    /**
     * Java的安装路径
     */
    private String javaHome;

    /**
     * Java的虚拟机规范版本
     */
    private String javaVmSpecificationVersion;

    /**
     * Java的虚拟机规范供应商
     */
    private String javaVmSpecificationVendor;

    /**
     * Java的虚拟机规范名称
     */
    private String javaVmSpecificationName;

    /**
     * Java的虚拟机实现版本
     */
    private String javaVmVersion;

    /**
     * Java的虚拟机实现供应商
     */
    private String javaVmVendor;

    /**
     * Java的虚拟机实现名称
     */
    private String javaVmName;

    /**
     * Java运行时环境规范版本
     */
    private String javaSpecificationVersion;

    /**
     * Java运行时环境规范供应商
     */
    private String javaSpecificationVender;

    /**
     * Java运行时环境规范名称
     */
    private String javaSpecificationName;

    /**
     * Java的类格式版本号
     */
    private String javaClassVersion;

    /**
     * Java的类路径
     */
    private String javaClassPath;

    /**
     * 加载库时搜索的路径列表
     */
    private String javaLibraryPath;

    /**
     * 默认的临时文件路径
     */
    private String javaIoTmpdir;

    /**
     * 一个或多个扩展目录的路径
     */
    private String javaExtDirs;

    /**
     * 操作系统的名称
     */
    private String osName;

    /**
     * 操作系统的构架
     */
    private String osArch;

    /**
     * 操作系统的版本
     */
    private String osVersion;

    /**
     * 文件分隔符
     */
    private String fileeparator;

    /**
     * 路径分隔符
     */
    private String pathSeparator;

    /**
     * 行分隔符
     */
    private String lineSeparator;

    /**
     * 用户的主目录
     */
    private String userHome;

    /**
     * 用户的当前工作目录
     */
    private String userDir;


    public String getUserName() {
        return userName;
    }

    public JvmDTO setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getComputerName() {
        return computerName;
    }

    public JvmDTO setComputerName(String computerName) {
        this.computerName = computerName;
        return this;
    }

    public String getUserDomain() {
        return userDomain;
    }

    public JvmDTO setUserDomain(String userDomain) {
        this.userDomain = userDomain;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public JvmDTO setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getHostName() {
        return hostName;
    }

    public JvmDTO setHostName(String hostName) {
        this.hostName = hostName;
        return this;
    }

    public Long getTotalMemory() {
        return totalMemory;
    }

    public JvmDTO setTotalMemory(Long totalMemory) {
        this.totalMemory = totalMemory;
        return this;
    }

    public Long getFreeMemory() {
        return freeMemory;
    }

    public JvmDTO setFreeMemory(Long freeMemory) {
        this.freeMemory = freeMemory;
        return this;
    }

    public Integer getAvailableProcessors() {
        return availableProcessors;
    }

    public JvmDTO setAvailableProcessors(Integer availableProcessors) {
        this.availableProcessors = availableProcessors;
        return this;
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    public JvmDTO setJavaVersion(String javaVersion) {
        this.javaVersion = javaVersion;
        return this;
    }

    public String getJavaVendor() {
        return javaVendor;
    }

    public JvmDTO setJavaVendor(String javaVendor) {
        this.javaVendor = javaVendor;
        return this;
    }

    public String getJavaVendorUrl() {
        return javaVendorUrl;
    }

    public JvmDTO setJavaVendorUrl(String javaVendorUrl) {
        this.javaVendorUrl = javaVendorUrl;
        return this;
    }

    public String getJavaHome() {
        return javaHome;
    }

    public JvmDTO setJavaHome(String javaHome) {
        this.javaHome = javaHome;
        return this;
    }

    public String getJavaVmSpecificationVersion() {
        return javaVmSpecificationVersion;
    }

    public JvmDTO setJavaVmSpecificationVersion(String javaVmSpecificationVersion) {
        this.javaVmSpecificationVersion = javaVmSpecificationVersion;
        return this;
    }

    public String getJavaVmSpecificationVendor() {
        return javaVmSpecificationVendor;
    }

    public JvmDTO setJavaVmSpecificationVendor(String javaVmSpecificationVendor) {
        this.javaVmSpecificationVendor = javaVmSpecificationVendor;
        return this;
    }

    public String getJavaVmSpecificationName() {
        return javaVmSpecificationName;
    }

    public JvmDTO setJavaVmSpecificationName(String javaVmSpecificationName) {
        this.javaVmSpecificationName = javaVmSpecificationName;
        return this;
    }

    public String getJavaVmVersion() {
        return javaVmVersion;
    }

    public JvmDTO setJavaVmVersion(String javaVmVersion) {
        this.javaVmVersion = javaVmVersion;
        return this;
    }

    public String getJavaVmVendor() {
        return javaVmVendor;
    }

    public JvmDTO setJavaVmVendor(String javaVmVendor) {
        this.javaVmVendor = javaVmVendor;
        return this;
    }

    public String getJavaVmName() {
        return javaVmName;
    }

    public JvmDTO setJavaVmName(String javaVmName) {
        this.javaVmName = javaVmName;
        return this;
    }

    public String getJavaSpecificationVersion() {
        return javaSpecificationVersion;
    }

    public JvmDTO setJavaSpecificationVersion(String javaSpecificationVersion) {
        this.javaSpecificationVersion = javaSpecificationVersion;
        return this;
    }

    public String getJavaSpecificationVender() {
        return javaSpecificationVender;
    }

    public JvmDTO setJavaSpecificationVender(String javaSpecificationVender) {
        this.javaSpecificationVender = javaSpecificationVender;
        return this;
    }

    public String getJavaSpecificationName() {
        return javaSpecificationName;
    }

    public JvmDTO setJavaSpecificationName(String javaSpecificationName) {
        this.javaSpecificationName = javaSpecificationName;
        return this;
    }

    public String getJavaClassVersion() {
        return javaClassVersion;
    }

    public JvmDTO setJavaClassVersion(String javaClassVersion) {
        this.javaClassVersion = javaClassVersion;
        return this;
    }

    public String getJavaClassPath() {
        return javaClassPath;
    }

    public JvmDTO setJavaClassPath(String javaClassPath) {
        this.javaClassPath = javaClassPath;
        return this;
    }

    public String getJavaLibraryPath() {
        return javaLibraryPath;
    }

    public JvmDTO setJavaLibraryPath(String javaLibraryPath) {
        this.javaLibraryPath = javaLibraryPath;
        return this;
    }

    public String getJavaIoTmpdir() {
        return javaIoTmpdir;
    }

    public JvmDTO setJavaIoTmpdir(String javaIoTmpdir) {
        this.javaIoTmpdir = javaIoTmpdir;
        return this;
    }

    public String getJavaExtDirs() {
        return javaExtDirs;
    }

    public JvmDTO setJavaExtDirs(String javaExtDirs) {
        this.javaExtDirs = javaExtDirs;
        return this;
    }

    public String getOsName() {
        return osName;
    }

    public JvmDTO setOsName(String osName) {
        this.osName = osName;
        return this;
    }

    public String getOsArch() {
        return osArch;
    }

    public JvmDTO setOsArch(String osArch) {
        this.osArch = osArch;
        return this;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public JvmDTO setOsVersion(String osVersion) {
        this.osVersion = osVersion;
        return this;
    }

    public String getFileeparator() {
        return fileeparator;
    }

    public JvmDTO setFileeparator(String fileeparator) {
        this.fileeparator = fileeparator;
        return this;
    }

    public String getPathSeparator() {
        return pathSeparator;
    }

    public JvmDTO setPathSeparator(String pathSeparator) {
        this.pathSeparator = pathSeparator;
        return this;
    }

    public String getLineSeparator() {
        return lineSeparator;
    }

    public JvmDTO setLineSeparator(String lineSeparator) {
        this.lineSeparator = lineSeparator;
        return this;
    }

    public String getUserHome() {
        return userHome;
    }

    public JvmDTO setUserHome(String userHome) {
        this.userHome = userHome;
        return this;
    }

    public String getUserDir() {
        return userDir;
    }

    public JvmDTO setUserDir(String userDir) {
        this.userDir = userDir;
        return this;
    }
}

