package com.zjg.monitor.mqhandler.handlerimpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zjg.monitor.entity.Systems;
import com.zjg.monitor.entity.Threads;
import com.zjg.monitor.mqhandler.AbstractMessageHandler;
import com.zjg.monitor.response.BaseMessage;
import com.zjg.monitor.response.ThreadResult;
import com.zjg.monitor.service.ThreadsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zjg
 * <p> 2020/4/13 14:20 </p>
 */
@Slf4j
@Service
public class ThreadsMessageHandler extends AbstractMessageHandler {

    /**
     * 线程总数报警
     */
    private static final String THREAD_NUM = "threadNum";
    /**
     * 死锁线程
     */
    private static final String THREAD_DEAD = "threadDead";

    @Resource
    private ThreadsService threadsService;

    @Override
    public void receiveMessage(JSONObject message) {
        if (message.get("msgType").equals(BaseMessage.MsgType.THREAD.toString())) {
            handlerMessage(message);
        } else if (super.nextHandler == null) {
            log.error("这个消息处理不了，可能发生了异常，请关注");
        } else {
            super.nextHandler.receiveMessage(message);
        }
    }

    @Override
    protected void handlerMessage(JSONObject message) {
        ThreadResult threadResult = JSON.toJavaObject(message, ThreadResult.class);
        Systems systems = getSystems(threadResult.getSystem());
        thresholdAudio(systems, threadResult.getMsgType().toString(), THREAD_NUM, threadResult.getThreadCount(), threadResult.getCurrTime(), true);
        thresholdAudio(systems, threadResult.getMsgType().toString(), THREAD_DEAD, threadResult.getDeadLockThreadCount(), threadResult.getCurrTime(), true);
        Threads threads = Threads.builder()
                .system(threadResult.getSystem())
                .currTime(threadResult.getCurrTime())
                .threadCount(threadResult.getThreadCount())
                .daemonThreadCount(threadResult.getDaemonThreadCount())
                .threadInfos(threadResult.getThreadInfos())
                .deadLockThreadCount(threadResult.getDeadLockThreadCount())
                .deadLockThreadInfo(threadResult.getDeadLockThreadInfo()).build();
        threadsService.insert(threads);
    }
}
