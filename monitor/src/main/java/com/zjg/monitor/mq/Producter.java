package com.zjg.monitor.mq;

import com.alibaba.fastjson.JSON;
import com.zjg.monitor.response.BaseMessage;
import com.zjg.monitor.util.Contant;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

/**
 * @Author zhangjingao3
 * @Date 2020/3/25 15:44
 */
@Slf4j
@Data
public class Producter {

    public static void send (BaseMessage baseResult) {
        log.error("msg: {}", baseResult.toString());
        ListenableFuture<SendResult<String, String>> result = MqUtil.getKafkaTemplate().send(Contant.TOPIC_MONITOR.getTopic(), JSON.toJSONString(baseResult));
        try {
            SendResult<String, String> stringStringSendResult = result.get();
            log.error("msg发送结果：{}", stringStringSendResult.toString());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}
