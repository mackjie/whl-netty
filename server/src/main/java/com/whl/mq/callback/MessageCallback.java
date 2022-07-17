package com.whl.mq.callback;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.core.RocketMQLocalRequestCallback;
import org.springframework.stereotype.Component;

/**
 * @author hl.Wu
 * @date 2022/7/14
 **/
@Component
@Slf4j
public class MessageCallback implements RocketMQLocalRequestCallback {
    @Override
    public void onSuccess(Object message) {
       log.info("call back message info is {}", JSONObject.toJSONString(message));
    }

    @Override
    public void onException(Throwable e) {
        log.error("send message is error {}",e.getMessage(),e);
    }
}
