package com.zjg.monitor.response;

import com.zjg.monitor.util.Config;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * @Author zhangjingao3
 * @Date 2020/3/26 13:57
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CpuResult extends BaseMessage {

    /**
     * 几个cpu的使用率
     */
    private List<String> cpuRates;

    public CpuResult () {
        this.setSystem(Config.getAppName());
        this.setMsgType(MsgType.CPU);
        this.setCurrTime(new Date());
    }

}
