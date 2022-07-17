package com.whl.mq;

import com.whl.mq.server.NioNettyServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Slf4j
public class NettyServerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(NettyServerApplication.class, args);
		// start to netty server
		context.getBean(NioNettyServer.class).start();
	}

	@Override
	public void run(String... args) {
		log.info("========================server start success========================");
	}

}
