//package com.whl.mq.listener;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
//import org.apache.rocketmq.spring.core.RocketMQReplyListener;
//import org.springframework.stereotype.Component;
//
///**
// * @author hl.Wu
// * @date 2022/7/14
// **/
//@RocketMQMessageListener(topic = "VERIFICATION_EMS", consumerGroup= "verification_ems")
//@Slf4j
//@Component
//public class MessageListener implements RocketMQReplyListener<String,String> {
//
//    @Override
//    public String onMessage(String message) {
//        log.info("receive message info is :{}", message);
//        return "接收成功";
//    }
//}
