package com.whl.mq.handle;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.whl.common.bo.MessageBO;
import com.whl.common.bo.ResponseBO;
import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * @author hl.Wu
 * @date 2022/7/14
 **/
@Component
@Slf4j
@ChannelHandler.Sharable
public class ClientMessageHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) {
        log.info("接收到的信息数据={}", msg);
        // 返回请求结果
        String requestUid = (String)JSONUtil.getByPath(JSONUtil.parse(msg), MessageBO.Fields.requestUid);
        ResponseBO resp = ResponseBO
            .builder()
            .requestUid(requestUid)
            .code(HttpStatus.OK.toString())
            .success(Boolean.TRUE)
            .message("请求成功")
            .build();
        Channel channel = ctx.channel();
        channel.writeAndFlush(JSONUtil.toJsonStr(resp));
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        Channel channel = ctx.channel();
        ResponseBO resp = ResponseBO.builder()
            .code(HttpStatus.INTERNAL_SERVER_ERROR.toString())
            .success(Boolean.FALSE)
            .build();
        // 返回错误code
        if(cause instanceof IllegalArgumentException){
            resp.setMessage(cause.getMessage());
            channel.writeAndFlush(JSON.toJSONString(resp));
            log.warn("业务异常请排查， ；{}", cause.getMessage(), cause);
        }
        log.error("error message {}",cause.getMessage(),cause);
    }

}
