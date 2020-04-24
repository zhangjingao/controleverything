package com.zjg.monitor.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author zjg
 * <p> 2020/4/2 20:47 </p>
 */
@Data
public class CpuParent implements Serializable {

    private int id;
    /**
     * 返回的系统名
     */
    private String system;
    /**
     * cpu编号，如：1，2，3，4
     */
    private String cpuIds;

    public CpuParent(String system, String cpuIds) {
        this.system = system;
        this.cpuIds = cpuIds;
    }
}
