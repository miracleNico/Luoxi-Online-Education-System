package com.luoxi.aop.refresh;

import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import com.luoxi.util.mybatis.MybatisMapperUtil;

//@Aspect
//@Component
public class AopRefreshMapper {
	
	@Autowired
	private MybatisMapperUtil mybatisMapperUtil;
	
	@Pointcut("execution(* com.luoxi.server..dao.*.*(..))")
    private void pointCut() {}
	
	@Async
	@Before("pointCut()")
	public void doBefore() throws InterruptedException {
		mybatisMapperUtil.refreshMapper();
	}
	
}
