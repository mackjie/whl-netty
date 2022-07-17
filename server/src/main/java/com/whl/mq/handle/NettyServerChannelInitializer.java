package com.whl.mq.handle;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author hl.Wu
 * @date 2022/7/14
 **/
@Component
@Slf4j
public class NettyServerChannelInitializer extends ChannelInitializer {
    @Autowired
    private ClientMessageHandler clientMessageHandler;
    @Autowired
    private ClientUserHandler clientUserHandler;
    @Override
    protected void initChannel(Channel channel) {
        // 设置编码类型
        channel.pipeline().addLast("decoder",new StringDecoder(CharsetUtil.UTF_8));
        // 设置解码类型
        channel.pipeline().addLast("encoder",new StringEncoder(CharsetUtil.UTF_8));
        // 用户校验处理逻辑
        channel.pipeline().addLast("ClientTokenHandler", clientUserHandler);
        // 通过校验最终消息业务处理
        channel.pipeline().addLast("ClientMessageHandler",clientMessageHandler);
    }
}
