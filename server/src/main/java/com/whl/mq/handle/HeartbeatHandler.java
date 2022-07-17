package com.whl.mq.handle;

import cn.hutool.core.util.StrUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hl.Wu
 * @date 2022/7/15
 **/
@Slf4j
public class HeartbeatHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        if(StrUtil.isNotBlank(msg)){

        }
    }


}
