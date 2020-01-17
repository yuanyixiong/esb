package com.sinoif.esb.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sinoif.esb.query.model.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.*;

import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.hardware.CentralProcessor.TickType;
import oshi.software.os.FileSystem;
import oshi.software.os.NetworkParams;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;
import oshi.software.os.OperatingSystem.ProcessSort;
import oshi.util.FormatUtil;
import oshi.util.Util;

/**
 * @author 袁毅雄
 * @description 环境信息的实现
 * @date 2019/11/1
 */
public class OS {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * JVM
     *
     * @return
     * @throws UnknownHostException
     */
    private JvmDTO jvm() throws UnknownHostException {
        JvmDTO jvmDTO = new JvmDTO();
        Runtime r = Runtime.getRuntime();
        Properties props = System.getProperties();
        InetAddress addr = InetAddress.getLocalHost();
        String userName = System.getenv().get("USERNAME");// 获取用户名
        String computerName = System.getenv().get("COMPUTERNAME");// 获取计算机名
        String userDomain = System.getenv().get("USERDOMAIN");// 获取计算机域名
        jvmDTO.setUserName(userName)
                .setComputerName(computerName)
                .setUserDomain(userDomain)
                .setIp(addr.getHostAddress())
                .setHostName(addr.getHostName())
                .setTotalMemory(r.totalMemory())
                .setFreeMemory(r.freeMemory())
                .setAvailableProcessors(r.availableProcessors())
                .setJavaVersion(props.getProperty("java.version"))
                .setJavaVendor(props.getProperty("java.vendor"))
                .setJavaVendorUrl(props.getProperty("java.vendor.url"))
                .setJavaHome(props.getProperty("java.home"))
                .setJavaVmSpecificationVersion(props.getProperty("java.vm.specification.version"))
                .setJavaVmSpecificationVendor(props.getProperty("java.vm.specification.vendor"))
                .setJavaVmSpecificationName(props.getProperty("java.vm.specification.name"))
                .setJavaVmVersion(props.getProperty("java.vm.version"))
                .setJavaVmVendor(props.getProperty("java.vm.vendor"))
                .setJavaVmName(props.getProperty("java.vm.name"))
                .setJavaSpecificationVersion(props.getProperty("java.specification.version"))
                .setJavaSpecificationVender(props.getProperty("java.specification.vender"))
                .setJavaSpecificationName(props.getProperty("java.specification.name"))
                .setJavaClassVersion(props.getProperty("java.class.version"))
                .setJavaClassPath(props.getProperty("java.class.path"))
                .setJavaLibraryPath(props.getProperty("java.library.path"))
                .setJavaIoTmpdir(props.getProperty("java.io.tmpdir"))
                .setJavaExtDirs(props.getProperty("java.ext.dirs"))
                .setOsName(props.getProperty("os.name"))
                .setOsArch(props.getProperty("os.arch"))
                .setOsVersion(props.getProperty("os.version"))
                .setFileeparator(props.getProperty("file.separator"))
                .setPathSeparator(props.getProperty("path.separator"))
                .setLineSeparator(props.getProperty("line.separator"))
                .setUserName(props.getProperty("user.name"))
                .setUserHome(props.getProperty("user.home"))
                .setUserDir(props.getProperty("user.dir"));
        return jvmDTO;
    }

    /**
     * 计算机系统
     *
     * @param computerSystem
     * @return
     */
    private ComputerSystemInfoDTO computerSystem(final ComputerSystem computerSystem) {
        final Firmware firmware = computerSystem.getFirmware();
        String releaseDate = "release date: " + (
                firmware.getReleaseDate() == null ? "unknown" : firmware.getReleaseDate() == null ? "unknown" : FormatUtil.formatDate(firmware.getReleaseDate())
        );
        return JSON.parseObject(JSON.toJSONString(computerSystem), ComputerSystemInfoDTO.class);
    }

    /**
     * 处理器
     *
     * @param processor
     * @return
     */
    private CentralProcessorDTO processor(CentralProcessor processor) {
        return JSON.parseObject(JSON.toJSONString(processor), CentralProcessorDTO.class);
    }

    /**
     * 内存
     *
     * @param memory
     * @return
     */
    private MemoryDTO memory(GlobalMemory memory) {
        return new MemoryDTO()
                .setTotal(memory.getTotal())
                .setAvailable(memory.getAvailable())
                .setSwapTotal(memory.getSwapTotal())
                .setSwapUsed(memory.getSwapUsed());
    }

    /**
     * CPU
     *
     * @param processor
     * @return
     */
    private CpuDTO cpu(CentralProcessor processor) {

        DecimalFormat df = new DecimalFormat("0.00");

        //CPU正常运行时间
        String runTime = FormatUtil.formatElapsedSecs(processor.getSystemUptime());

        //监控CPU一秒的负载
        long[] beforeLoad = processor.getSystemCpuLoadTicks();
        Util.sleep(1000);
        long[] afterLoad = processor.getSystemCpuLoadTicks();

        //CPU各项负载明细
        long user = afterLoad[TickType.USER.getIndex()] - beforeLoad[TickType.USER.getIndex()];
        long nice = afterLoad[TickType.NICE.getIndex()] - beforeLoad[TickType.NICE.getIndex()];
        long sys = afterLoad[TickType.SYSTEM.getIndex()] - beforeLoad[TickType.SYSTEM.getIndex()];
        long idle = afterLoad[TickType.IDLE.getIndex()] - beforeLoad[TickType.IDLE.getIndex()];
        long iowait = afterLoad[TickType.IOWAIT.getIndex()] - beforeLoad[TickType.IOWAIT.getIndex()];
        long irq = afterLoad[TickType.IRQ.getIndex()] - beforeLoad[TickType.IRQ.getIndex()];
        long softirq = afterLoad[TickType.SOFTIRQ.getIndex()] - beforeLoad[TickType.SOFTIRQ.getIndex()];
        long steal = afterLoad[TickType.STEAL.getIndex()] - beforeLoad[TickType.STEAL.getIndex()];
        long totalCpu = user + nice + sys + idle + iowait + irq + softirq + steal;

        //CPU当前负载
        String systemCpuLoad = df.format(processor.getSystemCpuLoad() * 100);
        //CPU一段时间内的负载
        String systemCpuLoadBetweenTicks = df.format(processor.getSystemCpuLoadBetweenTicks() * 100);

        //CPU负载平均值
        double[] systemLoadAverage = processor.getSystemLoadAverage(processor.getLogicalProcessorCount() - 1);

        //每个处理器的CPU负载
        double[] processorCpuLoadBetweenTicks = Arrays.stream(processor.getProcessorCpuLoadBetweenTicks()).map(e -> e * 100).toArray();

        CpuDTO cpuDTO = new CpuDTO()
                .setRunTime(runTime)
                .setLogicalProcessorCount(processor.getLogicalProcessorCount())
                .setPhysicalProcessorCount(processor.getPhysicalProcessorCount())
                .setSystemCpuLoad(systemCpuLoad)
                .setSystemCpuLoadBetweenTicks(systemCpuLoadBetweenTicks);

        Arrays.stream(beforeLoad).forEach(e -> cpuDTO.getBeforeLoad().add(Long.valueOf(e).toString()));
        Arrays.stream(afterLoad).forEach(e -> cpuDTO.getAfterLoad().add(Long.valueOf(e).toString()));
        Arrays.stream(systemLoadAverage).forEach(e -> cpuDTO.getSystemLoadAverage().add(df.format(e)));
        Arrays.stream(processorCpuLoadBetweenTicks).forEach(e -> cpuDTO.getProcessorCpuLoadBetweenTicks().add(df.format(e)));
        cpuDTO.getLoadInfo().put(TickType.USER.name(), df.format(100d * user / totalCpu));
        cpuDTO.getLoadInfo().put(TickType.NICE.name(), df.format(100d * nice / totalCpu));
        cpuDTO.getLoadInfo().put(TickType.SYSTEM.name(), df.format(100d * sys / totalCpu));
        cpuDTO.getLoadInfo().put(TickType.IDLE.name(), df.format(100d * idle / totalCpu));
        cpuDTO.getLoadInfo().put(TickType.IOWAIT.name(), df.format(100d * iowait / totalCpu));
        cpuDTO.getLoadInfo().put(TickType.IRQ.name(), df.format(100d * irq / totalCpu));
        cpuDTO.getLoadInfo().put(TickType.SOFTIRQ.name(), df.format(100d * softirq / totalCpu));
        cpuDTO.getLoadInfo().put(TickType.STEAL.name(), df.format(100d * steal / totalCpu));

        return cpuDTO;
    }

    /**
     * 进程
     *
     * @param os
     * @param memory
     * @return
     */
    private ProcessInfoDTO processes(OperatingSystem os, GlobalMemory memory) {
        ProcessInfoDTO processInfoDTO = new ProcessInfoDTO();
        processInfoDTO.setProcessCount(String.valueOf(os.getProcessCount()));
        processInfoDTO.setThreadCount(String.valueOf(os.getThreadCount()));
        List<OSProcess> procs = Arrays.asList(os.getProcesses(os.getProcessCount(), ProcessSort.CPU));
        for (int i = 0; i < procs.size(); i++) {
            OSProcess p = procs.get(i);

            processInfoDTO.getProcessesList().add(new ProcessesDTO(
                    String.valueOf(p.getProcessID()),
                    String.valueOf(100d * (p.getKernelTime() + p.getUserTime()) / p.getUpTime()),
                    String.valueOf(100d * p.getResidentSetSize() / memory.getTotal()),
                    FormatUtil.formatBytes(p.getVirtualSize()),
                    FormatUtil.formatBytes(p.getResidentSetSize()), p.getName()
            ));
        }
        return processInfoDTO;
    }

    /**
     * 传感器
     *
     * @param sensors
     * @return
     */
    private SensorsDTO sensors(Sensors sensors) {
        return JSON.parseObject(JSON.toJSONString(sensors), SensorsDTO.class);
    }

    /**
     * 磁盘
     *
     * @param diskStores
     * @return
     */
    private List<DiskDTO> disks(HWDiskStore[] diskStores) {
        List<DiskDTO> disks = new ArrayList<>();
        for (HWDiskStore disk : diskStores) {
            DiskDTO diskDTO = new DiskDTO()
                    .setName(disk.getName())
                    .setMode(disk.getModel())
                    .setSerial(disk.getSerial())
                    .setSizeByte(disk.getSize())
                    .setSizeGB(FormatUtil.formatBytes(disk.getSize()))
                    .setReads(disk.getReads())
                    .setReadBytes(disk.getReadBytes())
                    .setReadGB(FormatUtil.formatBytes(disk.getReadBytes()))
                    .setWrites(disk.getWrites())
                    .setWriteBytes(disk.getWriteBytes())
                    .setReadGB(FormatUtil.formatBytes(disk.getWriteBytes()))
                    .setTransferTimeMs(disk.getTransferTime())
                    .setTransferTimeSeconds(disk.getTransferTime() / 1000);
            disks.add(diskDTO);
            HWPartition[] partitions = disk.getPartitions();
            if (partitions == null) {
                continue;
            }
            Arrays.stream(partitions).forEach(partition -> {
                diskDTO.getPartitions().add(
                        new PartitionDTO()
                                .setIdentification(partition.getIdentification())
                                .setName(partition.getName())
                                .setType(partition.getType())
                                .setMajor(partition.getMajor())
                                .setMinor(partition.getMinor())
                                .setSizeByte(partition.getSize())
                                .setSizeGB(FormatUtil.formatBytesDecimal(partition.getSize()))
                                .setMountPoint(partition.getMountPoint())
                );
            });
        }
        return disks;
    }

    /**
     * 文件系统
     *
     * @param fileSystem
     * @return
     */
    private FileSystemDTO fileSystem(FileSystem fileSystem) {
        FileSystemDTO fileSystemDTO = new FileSystemDTO()
                .setOpenFile(fileSystem.getOpenFileDescriptors())
                .setAllFile(fileSystem.getMaxFileDescriptors());
        Arrays.stream(fileSystem.getFileStores()).forEach(fs -> {
            fileSystemDTO.getFileStores().add(
                    new VirtualDiskDTO()
                            .setName(fs.getName())
                            .setDescription(fs.getDescription())
                            .setType(fs.getType())
                            .setUsableSpaceByte(fs.getUsableSpace())
                            .setUsableSpaceGib(FormatUtil.formatBytes(fs.getUsableSpace()))
                            .setTotalSpaceByte(fs.getTotalSpace())
                            .setTotalSpaceGib(FormatUtil.formatBytes(fs.getTotalSpace()))
                            .setAvailable(100d * fs.getUsableSpace() / fs.getTotalSpace())
                            .setVolume(fs.getVolume())
                            .setLogicalVolume(fs.getLogicalVolume())
                            .setMount(fs.getMount())
            );
        });
        return fileSystemDTO;
    }

    /**
     * 网络接口
     *
     * @param networkIFs
     * @return
     */
    private List<NetworkDTO> networkInterfaces(NetworkIF[] networkIFs) {
        List<NetworkDTO> networks = new ArrayList<>();
        Arrays.stream(networkIFs).forEach(net -> {
            networks.add(
                    new NetworkDTO()
                            .setName(net.getName())
                            .setDisplayName(net.getDisplayName())
                            .setMacaddr(net.getMacaddr())
                            .setMtu(net.getMTU())
                            .setSpeed(net.getSpeed())
                            .setSpeedBps(FormatUtil.formatValue(net.getSpeed(), "bps"))
                            .setIpv4(Arrays.asList(net.getIPv4addr()))
                            .setIpv6(Arrays.asList(net.getIPv6addr()))
                            .setReceivePackets(net.getPacketsRecv())
                            .setReceiveBytes(net.getBytesRecv())
                            .setReceiveMib(FormatUtil.formatBytes(net.getBytesRecv()))
                            .setReceiveError(net.getInErrors())
                            .setSentPackets(net.getPacketsSent())
                            .setSentBytes(net.getBytesSent())
                            .setSentMib(FormatUtil.formatBytes(net.getBytesSent()))
                            .setSentError(net.getOutErrors())
            );
        });
        return networks;
    }

    /**
     * 网络参数
     *
     * @param networkParams
     * @return
     */
    private NetworkParametersDTO networkParameters(NetworkParams networkParams) {
        return JSON.parseObject(JSON.toJSONString(networkParams), NetworkParametersDTO.class);
    }

    /**
     * 环境信息
     *
     * @return
     */
    public EnvironmentDTO environmentInformation() {

        EnvironmentDTO environment = new EnvironmentDTO();

        logger.info("-----------------------------------初始化系统,开始获取设备信息");
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        OperatingSystem os = si.getOperatingSystem();
        try {
            environment.setJvmDTO(jvm());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return environment
                //.setComputerSystemInfo(computerSystem(hal.getComputerSystem()))
                //.setCentralProcessor(processor(hal.getProcessor()))
                .setMemory(memory(hal.getMemory()))
                .setCpu(cpu(hal.getProcessor()))
                //.setProcessInfo(processes(os, hal.getMemory()))
                //.setSensors(sensors(hal.getSensors()))
                .setDisks(disks(hal.getDiskStores()))
                .setFileSystem(fileSystem(os.getFileSystem()))
                .setNetworks(networkInterfaces(hal.getNetworkIFs()))
                //.setNetworkParameters(networkParameters(os.getNetworkParams()))
                ;
    }
}
