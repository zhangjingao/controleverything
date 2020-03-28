package com.zjg.monitor.response;

import com.zjg.monitor.util.Config;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Author zhangjingao3
 * @Date 2020/3/26 13:57
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CpuResult extends BaseResult {

    /**
     * 几个cpu的使用率
     */
    private List<Double> cpuRates;

    public CpuResult () {
        this.setSystem(Config.getAppName());
        this.setMsgType(MsgType.CPU);
    }

}
