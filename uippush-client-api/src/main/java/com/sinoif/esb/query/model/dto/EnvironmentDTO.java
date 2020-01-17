package com.sinoif.esb.query.model.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 环境信息
 */
public class EnvironmentDTO implements Serializable {

    /**
     * JVM信息
     */
    private JvmDTO jvmDTO;

    /**
     * 计算机系统信息
     */
    private ComputerSystemInfoDTO computerSystemInfo;

    /**
     * 中央处理器
     */
    private CentralProcessorDTO centralProcessor;

    /**
     * 内存
     */
    private MemoryDTO memory;

    /**
     * CPU
     */
    private CpuDTO cpu;

    /**
     * 进程相关信息
     */
    private ProcessInfoDTO processInfo;

    /**
     * 传感器
     */
    private SensorsDTO sensors;

    /**
     * 磁盘
     */
    private List<DiskDTO> disks;

    /**
     * 文件系统
     */
    private FileSystemDTO fileSystem;

    /**
     * 网络接口
     */
    private List<NetworkDTO> networks;

    /**
     * 网络参数
     */
    private NetworkParametersDTO networkParameters;

    public JvmDTO getJvmDTO() {
        return jvmDTO;
    }

    public EnvironmentDTO setJvmDTO(JvmDTO jvmDTO) {
        this.jvmDTO = jvmDTO;
        return this;
    }

    public ComputerSystemInfoDTO getComputerSystemInfo() {
        return computerSystemInfo;
    }

    public EnvironmentDTO setComputerSystemInfo(ComputerSystemInfoDTO computerSystemInfo) {
        this.computerSystemInfo = computerSystemInfo;
        return this;
    }

    public CentralProcessorDTO getCentralProcessor() {
        return centralProcessor;
    }

    public EnvironmentDTO setCentralProcessor(CentralProcessorDTO centralProcessor) {
        this.centralProcessor = centralProcessor;
        return this;
    }

    public MemoryDTO getMemory() {
        return memory;
    }

    public EnvironmentDTO setMemory(MemoryDTO memory) {
        this.memory = memory;
        return this;
    }

    public CpuDTO getCpu() {
        return cpu;
    }

    public EnvironmentDTO setCpu(CpuDTO cpu) {
        this.cpu = cpu;
        return this;
    }

    public ProcessInfoDTO getProcessInfo() {
        return processInfo;
    }

    public EnvironmentDTO setProcessInfo(ProcessInfoDTO processInfo) {
        this.processInfo = processInfo;
        return this;
    }

    public SensorsDTO getSensors() {
        return sensors;
    }

    public EnvironmentDTO setSensors(SensorsDTO sensors) {
        this.sensors = sensors;
        return this;
    }

    public List<DiskDTO> getDisks() {
        return disks;
    }

    public EnvironmentDTO setDisks(List<DiskDTO> disks) {
        this.disks = disks;
        return this;
    }

    public FileSystemDTO getFileSystem() {
        return fileSystem;
    }

    public EnvironmentDTO setFileSystem(FileSystemDTO fileSystem) {
        this.fileSystem = fileSystem;
        return this;
    }

    public List<NetworkDTO> getNetworks() {
        return networks;
    }

    public EnvironmentDTO setNetworks(List<NetworkDTO> networks) {
        this.networks = networks;
        return this;
    }

    public NetworkParametersDTO getNetworkParameters() {
        return networkParameters;
    }

    public EnvironmentDTO setNetworkParameters(NetworkParametersDTO networkParameters) {
        this.networkParameters = networkParameters;
        return this;
    }
}
