package com.zjg.monitor.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zjg
 * <p> 2020/4/2 21:44 </p>
 */
@Data
@TableName("gc")
@NoArgsConstructor
@AllArgsConstructor
public class GC implements Serializable {

    private int id;
    /**
     * 返回的系统名
     */
    private String system;
    /**
     * 当前时间
     */
    private Date currTime;
    /**
     * gc总次数
     */
    private long gcCount;

    public GC(String system, Date currTime, long gcCount) {
        this.system = system;
        this.currTime = currTime;
        this.gcCount = gcCount;
    }
}
