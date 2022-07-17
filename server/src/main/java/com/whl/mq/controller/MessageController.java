//package com.whl.mq.controller;
//
//import cn.hutool.core.io.file.FileReader;
//import cn.hutool.core.util.RandomUtil;
//import com.alibaba.fastjson.JSON;
//import com.whl.mq.bo.MessageVO;
//import com.whl.mq.callback.MessageCallback;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.text.StringSubstitutor;
//import org.apache.rocketmq.spring.core.RocketMQTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author hl.Wu
// * @date 2022/7/14
// **/
//@Api(tags = "消息中心")
//@RequestMapping("/message")
//@RestController
//@Slf4j
//public class MessageController {
//    @Autowired
//    private RocketMQTemplate rocketMQTemplate;
//
//    @Value("${user.ems:VERIFICATION_EMS}")
//    private String verificationEmsTopic;
//    @Autowired
//    private MessageCallback messageCallback;
//
//    @PostMapping("/send")
//    @ApiOperation(value = "消息发送")
//    public void send() {
//        // 读取文件手机号码
//        List<String> mobiles = new FileReader("mobiles.txt").readLines();
//        String template = "尊敬的${userName}用户您好，您的验证码是：${code}，若非本人操作，请忽略！";
//        Map<String,Object> valueMap = new HashMap<>();
//        mobiles.stream().forEach(mobile -> {
//            long code = RandomUtil.randomLong(100000,999999);
//            valueMap.put("userName", mobile);
//            valueMap.put("code", code);
//            StringSubstitutor ss = new StringSubstitutor(valueMap);
//            String msg = ss.replace(template);
//            MessageVO message = MessageVO
//                  .builder()
//                  .mobile(mobile)
//                  .message(msg)
//                  .build();
//            log.info("send message info is : {}", JSON.toJSONString(message, true));
//            rocketMQTemplate.syncSend(verificationEmsTopic,message);
//        });
//    }
//
//    @PostMapping("/delaySend")
//    @ApiOperation(value = "延时消息发送")
//    public void delaySend() {
//        // 读取文件手机号码
//        List<String> mobiles = new FileReader("mobiles.txt").readLines();
//        String template = "你好 ${mobile}！这是一条延时消息 =.=";
//        Map<String, Object> valueMap = new HashMap<>();
//        mobiles.stream().forEach(mobile -> {
//            valueMap.put("mobile", mobile);
//            StringSubstitutor ss = new StringSubstitutor(valueMap);
//            String msg = ss.replace(template);
//            MessageVO message = MessageVO.builder().mobile(mobile).message(msg).build();
//            log.info("send message info is : {}", JSON.toJSONString(message, true));
//            rocketMQTemplate.sendAndReceive(verificationEmsTopic, message, messageCallback, 30000, 3);
//        });
//    }
//}
