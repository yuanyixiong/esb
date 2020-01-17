package com.sinoif.esb.query.model.dto;

import org.apache.commons.collections.MapUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnvironmentInformationAggregationDTO implements Serializable {

    /**
     * 节点总数
     */
    private Integer node;

    /**
     * 统计时间
     */
    private Map<String, String> agggregationTime;

    /**
     * 每个节点的 CPU
     */
    private Map<String, CpuDTO> cpuNode = new HashMap<>();

    /**
     * 每个节点的 内存
     */
    private Map<String, MemoryDTO> memoryNode = new HashMap<>();

    /**
     * 每个节点的 网络
     */
    private Map<String, List<NetworkDTO>> networkNode = new HashMap<>();

    /**
     * 每个节点的 磁盘
     */
    private Map<String, List<DiskDTO>> diskNode = new HashMap<>();

    /**
     * 统计CPU信息
     *
     * @return
     */
    public Map<CpuEnum, Number> aggregationCpuLoad() {
        if (MapUtils.isEmpty(this.getCpuNode())) {
            return new HashMap<>();
        }
        Double systemLoadAverage = this.getCpuNode().values().stream().map(CpuDTO::getSystemLoadAverage).flatMap(List::stream).mapToDouble(Double::valueOf).summaryStatistics().getAverage();
        Long logicalProcessorCountSum = this.getCpuNode().values().stream().mapToInt(CpuDTO::getLogicalProcessorCount).summaryStatistics().getSum();
        Long physicalProcessorCountSum = this.getCpuNode().values().stream().mapToInt(CpuDTO::getPhysicalProcessorCount).summaryStatistics().getSum();

        return new HashMap<CpuEnum, Number>() {
            {
                put(CpuEnum.SYSTEM_LOAD_AVERAGE, systemLoadAverage);
                put(CpuEnum.LOGICAL_PROCESSOR_COUNT_SUM, logicalProcessorCountSum);
                put(CpuEnum.PHYSICAL_PROCESSOR_COUNT_SUM, physicalProcessorCountSum);
            }
        };
    }

    /**
     * 统计内存信息
     *
     * @return
     */
    public Map<MemoryEnum, Long> aggregationMemory() {
        if (MapUtils.isEmpty(this.getMemoryNode())) {
            return new HashMap<>();
        }
        Long totalSum = this.getMemoryNode().values().stream().mapToLong(MemoryDTO::getTotal).summaryStatistics().getSum();
        Long availableSum = this.getMemoryNode().values().stream().mapToLong(MemoryDTO::getAvailable).summaryStatistics().getSum();
        Long swapTotalSum = this.getMemoryNode().values().stream().mapToLong(MemoryDTO::getSwapTotal).summaryStatistics().getSum();
        Long swapUsedSum = this.getMemoryNode().values().stream().mapToLong(MemoryDTO::getSwapUsed).summaryStatistics().getSum();
        return new HashMap<MemoryEnum, Long>() {
            {
                put(MemoryEnum.TOTAL, totalSum);
                put(MemoryEnum.AVAILABLE, availableSum);
                put(MemoryEnum.SWAP_TOTAL, swapTotalSum);
                put(MemoryEnum.SWAP_USED, swapUsedSum);
            }
        };
    }

    /**
     * 统计网络信息
     *
     * @return
     */
    public Map<NetworkEnum, Number> aggregationNetwork() {
        if (MapUtils.isEmpty(this.getNetworkNode())) {
            return new HashMap<>();
        }
        Double receiveBytesAverage = this.getNetworkNode().values().stream().flatMap(List::stream).mapToLong(NetworkDTO::getReceiveBytes).summaryStatistics().getAverage();
        Long receivePacketsSum = this.getNetworkNode().values().stream().flatMap(List::stream).mapToLong(NetworkDTO::getReceivePackets).summaryStatistics().getSum();
        Long receiveErrorSum = this.getNetworkNode().values().stream().flatMap(List::stream).mapToLong(NetworkDTO::getReceiveError).summaryStatistics().getSum();

        Double sentBytesAverage = this.getNetworkNode().values().stream().flatMap(List::stream).mapToLong(NetworkDTO::getSentBytes).summaryStatistics().getAverage();
        Long sentPacketsSum = this.getNetworkNode().values().stream().flatMap(List::stream).mapToLong(NetworkDTO::getSentPackets).summaryStatistics().getSum();
        Long sentErrorSum = this.getNetworkNode().values().stream().flatMap(List::stream).mapToLong(NetworkDTO::getSentError).summaryStatistics().getSum();
        return new HashMap<NetworkEnum, Number>() {
            {
                put(NetworkEnum.RECEIVE_BYTES_AVERAGE, receiveBytesAverage);
                put(NetworkEnum.RECEIVE_PACKETS_SUM, receivePacketsSum);
                put(NetworkEnum.RECEIVE_ERROR_SUM, receiveErrorSum);
                put(NetworkEnum.SENT_BYTES_AVERAGE, sentBytesAverage);
                put(NetworkEnum.SENT_PACKETS_SUM, sentPacketsSum);
                put(NetworkEnum.SENT_ERROR_SUM, sentErrorSum);
            }
        };
    }

    /**
     * 统计磁盘信息
     *
     * @return
     */
    public Map<DiskEnum, Number> aggregationDisk() {
        if (MapUtils.isEmpty(this.getDiskNode())) {
            return new HashMap<>();
        }

        Long readsSum = this.getDiskNode().values().stream().flatMap(List::stream).mapToLong(DiskDTO::getReads).summaryStatistics().getSum();
        Double readsAverage = this.getDiskNode().values().stream().flatMap(List::stream).mapToLong(DiskDTO::getReads).summaryStatistics().getAverage();

        Long readBytesSum = this.getDiskNode().values().stream().flatMap(List::stream).mapToLong(DiskDTO::getReadBytes).summaryStatistics().getSum();
        Double readBytesAverage = this.getDiskNode().values().stream().flatMap(List::stream).mapToLong(DiskDTO::getReadBytes).summaryStatistics().getAverage();

        Long writesSum = this.getDiskNode().values().stream().flatMap(List::stream).mapToLong(DiskDTO::getWrites).summaryStatistics().getSum();
        Double writesAverage = this.getDiskNode().values().stream().flatMap(List::stream).mapToLong(DiskDTO::getWrites).summaryStatistics().getAverage();

        Long writeBytesSum = this.getDiskNode().values().stream().flatMap(List::stream).mapToLong(DiskDTO::getWriteBytes).summaryStatistics().getSum();
        Double writeBytesAverage = this.getDiskNode().values().stream().flatMap(List::stream).mapToLong(DiskDTO::getWriteBytes).summaryStatistics().getAverage();

        Double transferTimeMsAverage = this.getDiskNode().values().stream().flatMap(List::stream).mapToLong(DiskDTO::getTransferTimeMs).summaryStatistics().getAverage();
        return new HashMap<DiskEnum, Number>() {
            {
                put(DiskEnum.READS_SUM, readsSum);
                put(DiskEnum.READS_AVERAGE, readsAverage);
                put(DiskEnum.READ_BYTES_SUM, readBytesSum);
                put(DiskEnum.READ_BYTES_AVERAGE, readBytesAverage);
                put(DiskEnum.WRITES_SUM, writesSum);
                put(DiskEnum.WRITES_AVERAGE, writesAverage);
                put(DiskEnum.WRITE_BYTES_SUM, writeBytesSum);
                put(DiskEnum.WRITE_BYTES_AVERAGE, writeBytesAverage);
                put(DiskEnum.TRANSFER_TIME_MS_AVERAGE, transferTimeMsAverage);
            }
        };
    }


    public Integer getNode() {
        return node;
    }

    public EnvironmentInformationAggregationDTO setNode(Integer node) {
        this.node = node;
        return this;
    }

    public Map<String, String> getAgggregationTime() {
        return agggregationTime;
    }

    public EnvironmentInformationAggregationDTO setAgggregationTime(Map<String, String> agggregationTime) {
        this.agggregationTime = agggregationTime;
        return this;
    }

    public Map<String, CpuDTO> getCpuNode() {
        return cpuNode;
    }

    public EnvironmentInformationAggregationDTO setCpuNode(Map<String, CpuDTO> cpuNode) {
        this.cpuNode = cpuNode;
        return this;
    }

    public Map<String, MemoryDTO> getMemoryNode() {
        return memoryNode;
    }

    public EnvironmentInformationAggregationDTO setMemoryNode(Map<String, MemoryDTO> memoryNode) {
        this.memoryNode = memoryNode;
        return this;
    }

    public Map<String, List<NetworkDTO>> getNetworkNode() {
        return networkNode;
    }

    public EnvironmentInformationAggregationDTO setNetworkNode(Map<String, List<NetworkDTO>> networkNode) {
        this.networkNode = networkNode;
        return this;
    }

    public Map<String, List<DiskDTO>> getDiskNode() {
        return diskNode;
    }

    public EnvironmentInformationAggregationDTO setDiskNode(Map<String, List<DiskDTO>> diskNode) {
        this.diskNode = diskNode;
        return this;
    }

    public enum CpuEnum {
        SYSTEM_LOAD_AVERAGE, LOGICAL_PROCESSOR_COUNT_SUM, PHYSICAL_PROCESSOR_COUNT_SUM
    }

    public enum MemoryEnum {
        TOTAL, AVAILABLE, SWAP_TOTAL, SWAP_USED
    }

    public enum NetworkEnum {
        RECEIVE_BYTES_AVERAGE, RECEIVE_PACKETS_SUM, RECEIVE_ERROR_SUM, SENT_BYTES_AVERAGE, SENT_PACKETS_SUM, SENT_ERROR_SUM
    }

    public enum DiskEnum {
        READS_SUM, READS_AVERAGE, READ_BYTES_SUM, READ_BYTES_AVERAGE, WRITES_SUM, WRITES_AVERAGE, WRITE_BYTES_SUM, WRITE_BYTES_AVERAGE, TRANSFER_TIME_MS_AVERAGE
    }
}
