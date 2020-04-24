package com.zjg.monitor.util;

import lombok.Data;
import lombok.Getter;

/**
 * 做静态变量用
 * @Author zhangjingao3
 * @Date 2020/3/26 16:45
 */
public enum Contant {

    /**
     * kafka的server端接收的topic
     */
    TOPIC_MONITOR("monitor");

    @Getter
    public String topic;

    Contant(String topic) {
        this.topic = topic;
    }


}
