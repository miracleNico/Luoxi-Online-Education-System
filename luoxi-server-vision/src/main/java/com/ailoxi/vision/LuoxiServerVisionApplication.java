package com.ailoxi.vision;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

import com.spring4all.mongodb.EnableMongoPlus;

import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(scanBasePackages = {"com.ailoxi","com.luoxi"})
@EnableAsync
@EnableMongoPlus
@EnableDiscoveryClient
@MapperScan("com.ailoxi.vision.dao")
@EnableAutoDataSourceProxy
public class LuoxiServerVisionApplication {

	public static void main(String[] args) {
		SpringApplication.run(LuoxiServerVisionApplication.class, args);
	}

}
