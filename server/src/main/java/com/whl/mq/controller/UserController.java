package com.whl.mq.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.whl.mq.handle.ClientUserHandler;
import io.netty.channel.Channel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author hl.Wu
 * @date 2022/7/14
 **/
@RestController
@RequestMapping("/user")
@Api(tags = "用户管理")
public class UserController {
    @Autowired
    private ClientUserHandler clientUserHandler;

    @GetMapping("/token")
    @ApiOperation("获取token信息")
    public String getToken(){
        String token = IdUtil.fastSimpleUUID();
        ClientUserHandler.userMap.put(token,token);
        return token;
    }

    @PostMapping("/tips")
    @ApiOperation("发送提醒")
    public void sendToClient(@RequestParam("tips") String tips, @RequestParam("userId") String userId){
        Map<String, Channel> channelMap = clientUserHandler.channelMap;
        Channel channel = channelMap.get(userId);
        if(ObjectUtil.isNotNull(channel)){
            channel.writeAndFlush(tips);
        }
    }
}
