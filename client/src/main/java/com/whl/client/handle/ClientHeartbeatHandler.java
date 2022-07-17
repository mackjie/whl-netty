package com.whl.client.handle;

import com.alibaba.fastjson.JSON;
import com.whl.common.bo.MessageBO;
import com.whl.client.client.NioNettyClient;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 心跳监测
 *
 * @author hl.Wu
 * @date 2022/7/15
 **/
@Component
@Slf4j
@ChannelHandler.Sharable
public class ClientHeartbeatHandler extends ChannelInboundHandlerAdapter {

    private final NioNettyClient nioNettyClient;

    public ClientHeartbeatHandler(NioNettyClient nioNettyClient) {
        this.nioNettyClient = nioNettyClient;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.WRITER_IDLE) {
                log.info("已经10s没有发送消息给服务端");
                //发送心跳消息，并在发送失败时关闭该连接
                MessageBO message = new MessageBO();
                message.setHeartbeat(Boolean.TRUE);
                ctx.writeAndFlush(JSON.toJSONString(message)).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //如果运行过程中服务端挂了,执行重连机制
        log.info("start to reconnect netty server");
        ctx.channel().eventLoop().schedule(() -> nioNettyClient.start(), 3L, TimeUnit.SECONDS);
        super.channelInactive(ctx);
    }

}
