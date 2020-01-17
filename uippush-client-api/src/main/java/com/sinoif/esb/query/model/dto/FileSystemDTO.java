package com.sinoif.esb.query.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件系统
 */
public class FileSystemDTO implements Serializable {

    private List<VirtualDiskDTO> fileStores = new ArrayList<>();

    /**
     * 开启的文件数
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long openFile;

    /**
     * 所有文件数
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long allFile;

    public List<VirtualDiskDTO> getFileStores() {
        return fileStores;
    }

    public FileSystemDTO setFileStores(List<VirtualDiskDTO> fileStores) {
        this.fileStores = fileStores;
        return this;
    }

    public Long getOpenFile() {
        return openFile;
    }

    public FileSystemDTO setOpenFile(Long openFile) {
        this.openFile = openFile;
        return this;
    }

    public Long getAllFile() {
        return allFile;
    }

    public FileSystemDTO setAllFile(Long allFile) {
        this.allFile = allFile;
        return this;
    }
}
