package com.zjg.monitor.mq;

import com.zjg.monitor.response.BaseMessage;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zjg
 * <p> 2020/4/1 11:54 </p>
 */
public class MqUtil {

    private static KafkaTemplate<String, String> kafkaTemplate;

    public static void init () {
        Map<String, Object> properties = new HashMap<>();
        //kafka的链接及端口
        properties.put("bootstrap.servers", "39.106.214.166:9092");
        //配置value序列化类
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //配置key的序列化类
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //0表示不确认主服务器是否收到消息,马上返回,低延迟但最弱的持久性,数据可能会丢失
        //1表示确认主服务器收到消息后才返回,持久性稍强,可是如果主服务器死掉,从服务器数据尚未同步,数据可能会丢失
        //all表示确认所有服务器都收到数据,完美!
        properties.put("request.required.acks", "all");
        //失败后重试次数
        properties.put("retries","3");

        DefaultKafkaProducerFactory<String, String> kafkaProducerFactory = new DefaultKafkaProducerFactory<>(properties);
        kafkaTemplate = new KafkaTemplate<>(kafkaProducerFactory);
    }

    public static KafkaTemplate<String, String> getKafkaTemplate () {
        if (kafkaTemplate == null) {
            synchronized (KafkaTemplate.class) {
                if (kafkaTemplate == null) {
                    init();
                }
            }
        }
        return kafkaTemplate;
    }

}
