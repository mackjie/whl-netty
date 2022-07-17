package com.whl.mq.server;

import com.whl.mq.handle.NettyServerChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author hl.Wu
 *
 * @date 2021/11/4
 * @description: netty server info
 **/
@Slf4j
@Component
public class NioNettyServer {

    @Value("${nio.netty.server.port: 8099}")
    private int port;
    @Autowired
    private NettyServerChannelInitializer nettyServerChannelInitializer;

    @Async
    public void start() {
        log.info("start to netty server port is {}", port);
        // 接收连接
        EventLoopGroup boss = new NioEventLoopGroup();
        // 处理信息
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            // 定义server
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 添加分组
            serverBootstrap.group(boss, worker)
                // 添加通道设置非阻塞
                .channel(NioServerSocketChannel.class)
                // 服务端可连接队列数量
                .option(ChannelOption.SO_BACKLOG, 128)
                // 开启长连接
                .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE)
                // 流程处理
                .childHandler(nettyServerChannelInitializer);
            // 绑定端口
            ChannelFuture cf = serverBootstrap.bind(port).sync();
            // 优雅关闭连接
            cf.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("connection error",e.getMessage(), e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

}
