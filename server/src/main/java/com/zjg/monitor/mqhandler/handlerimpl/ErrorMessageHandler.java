package com.zjg.monitor.mqhandler.handlerimpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zjg.monitor.mqhandler.AbstractMessageHandler;
import com.zjg.monitor.response.BaseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author zjg
 * <p> 2020/4/4 19:16 </p>
 */
@Slf4j
@Service
public class ErrorMessageHandler extends AbstractMessageHandler {

    @Override
    public void receiveMessage(JSONObject message) {
        if (message == null
                || !message.get("code").equals(BaseMessage.CodeEnum.OK.toString())
                || StringUtils.isEmpty(message.get("system"))) {
            handlerMessage(message);
        } else if (super.nextHandler == null) {
            log.error("消息正常，但是没有handler");
        } else {
            super.nextHandler.receiveMessage(message);
        }
    }

    @Override
    protected void handlerMessage(JSONObject message) {
        log.error("消息错误, message:{}", JSON.toJSONString(message));
    }
}
