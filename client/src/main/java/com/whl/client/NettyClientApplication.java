package com.whl.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Slf4j
public class NettyClientApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(NettyClientApplication.class, args);
	}

	@Override
	public void run(String... args) {
		log.info("========================client start success========================");
	}
}