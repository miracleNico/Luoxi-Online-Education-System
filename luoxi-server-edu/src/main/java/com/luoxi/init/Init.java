package com.luoxi.init;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;

//@Component
@Order(1) //执行顺序
public class Init implements ApplicationRunner {
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
	}
}