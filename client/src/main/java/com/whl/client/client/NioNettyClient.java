package com.whl.client.client;

import com.whl.client.handle.NettyClientChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

/**
 * @author hl.Wu
 * @date 2021/11/9
 *
 * @description: netty client info
 **/
@Slf4j
@Component
public class NioNettyClient {

    @Value("${netty.server.host:localhost}")
    private String host;

    @Value("${netty.server.port:8099}")
    private int port;

    private SocketChannel socketChannel;

    /**
     * work 线程组用于数据处理
     */
    private EventLoopGroup work = new NioEventLoopGroup();

    @Autowired
    private NettyClientChannelInitializer nettyClientChannelInitializer;

    /**
     * 发送消息
     *
     * @param msg
     */
    public void sendMsg(String msg) {
        if(!socketChannel.isActive()){
            // 如果失去连接，重新创建新的连接
            log.info("****************服务失去连接，开始创建新的连接****************");
            start();
        }
        // 发送消息
        socketChannel.writeAndFlush(msg);
    }

    @PostConstruct
    public void start() {
        work = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(work)
                // 设置通道
                .channel(NioSocketChannel.class)
                // 日志处理格式
                .handler(new LoggingHandler(LogLevel.INFO))
                // 禁用nagle算法
                .option(ChannelOption.TCP_NODELAY, true)
                // 保持长连接
                .option(ChannelOption.SO_KEEPALIVE, Boolean.TRUE)
                // 流程处理
                .handler(nettyClientChannelInitializer);
            // start to channel
            ChannelFuture future = bootstrap.connect(host, port).sync();
            future.addListener((ChannelFutureListener) future1 -> {
                if (future1.isSuccess()) {
                    log.info("**********************服务连接成功**********************");
                } else {
                    log.warn("**********************服务连接失败，20s后开始重新连接服务器**********************");
                    // 20s后重新连接
                    future1.channel().eventLoop().schedule(() -> this.start(), 20, TimeUnit.SECONDS);
                }
            });
            socketChannel = (SocketChannel) future.channel();
        } catch (Exception e) {
            log.error("connection error", e.getMessage(), e);
        }
    }

    @PreDestroy
    private void close() {
        if(socketChannel != null){
            socketChannel.close();
        }
        work.shutdownGracefully();
    }
}
