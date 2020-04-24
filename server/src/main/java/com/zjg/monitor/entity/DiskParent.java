package com.zjg.monitor.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * @author zjg
 * <p> 2020/4/2 20:49 </p>
 */
@Data
@NoArgsConstructor
public class DiskParent implements Serializable {

    private int id;
    /**
     * 返回的系统名
     */
    private String system;
    /**
     * 记录该系统的磁盘情况,多个盘符使用，分开
     */
    private String devNames;

    public DiskParent(String system, String devNames) {
        this.system = system;
        this.devNames = devNames;
    }
}
