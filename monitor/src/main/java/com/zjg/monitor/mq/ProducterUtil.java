package com.zjg.monitor.mq;

import com.zjg.monitor.response.BaseResult;
import com.zjg.monitor.util.Contant;
import lombok.Data;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhangjingao3
 * @Date 2020/3/25 15:44
 */
@Data
public class ProducterUtil {

    public static KafkaTemplate<String, BaseResult> kafkaTemplate;

    public ProducterUtil() {
        Map<String, Object> properties = new HashMap<>();
        //kafka的链接及端口
        properties.put("metadata.broker.list", "39.106.214.166:9092");
        //配置value序列化类
        properties.put("serializer.class", "kafka.serializer.StringEncoder");
        //配置key的序列化类
        properties.put("key.serializer.class", "kafka.serializer.StringEncoder");
        //0表示不确认主服务器是否收到消息,马上返回,低延迟但最弱的持久性,数据可能会丢失
        //1表示确认主服务器收到消息后才返回,持久性稍强,可是如果主服务器死掉,从服务器数据尚未同步,数据可能会丢失
        //-1表示确认所有服务器都收到数据,完美!
        properties.put("request.required.acks", "-1");
        //异步生产,批量存入缓存后再发到服务器去
        properties.put("producer.type", "async");

        DefaultKafkaProducerFactory<String, BaseResult> kafkaProducerFactory = new DefaultKafkaProducerFactory<>(properties);
        kafkaTemplate = new KafkaTemplate<>(kafkaProducerFactory);
    }

    public static void send (BaseResult baseResult) {
        kafkaTemplate.send(Contant.TOPIC_MONITOR.toString(), baseResult);
    }

}
