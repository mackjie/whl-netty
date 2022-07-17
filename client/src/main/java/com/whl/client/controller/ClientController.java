package com.whl.client.controller;

import com.alibaba.fastjson.JSON;
import com.whl.common.bo.MessageBO;
import com.whl.client.client.NioNettyClient;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hl.Wu
 * @date 2022/7/14
 **/
@RestController
@RequestMapping("/client")
@Api(tags = "客户端管理")
@Slf4j
public class ClientController {
    @Autowired
    private NioNettyClient nioNettyClient;

    @PostMapping("/send")
    public void send(@RequestBody MessageBO message) {
        log.info(JSON.toJSONString(message));
        nioNettyClient.sendMsg(JSON.toJSONString(message));
    }
}
