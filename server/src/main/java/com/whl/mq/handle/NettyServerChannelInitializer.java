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
        channel.pipeline().addLast("decoder",new StringDecoder(CharsetUtil.UTF_8));
        channel.pipeline().addLast("encoder",new StringEncoder(CharsetUtil.UTF_8));
        channel.pipeline().addLast("ClientTokenHandler", clientUserHandler);
        channel.pipeline().addLast("ClientMessageHandler",clientMessageHandler);
    }
}
