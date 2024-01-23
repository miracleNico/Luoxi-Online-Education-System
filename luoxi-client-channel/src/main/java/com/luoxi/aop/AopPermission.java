package com.luoxi.aop;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.luoxi.aop.ApiPermission.AUTH;
import com.luoxi.api.channel.protocol.ReqChannelLogin.RespChannelLogin;
import com.luoxi.base.BaseController;
import com.luoxi.base.ResultMessage;
import com.luoxi.exception.LxException;
import com.luoxi.utils.HttpServletRequestUtil;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;

@Aspect
@Order(1)
@Component
public class AopPermission extends BaseController{
	
	@Value("${spring.application.name}")
    private String appName;
	
	@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    private void pointCut() {}
	
	@Before("pointCut()")
	public void doBefore() {
		
	}
	
	@Around("pointCut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
		Method method = signature.getMethod();
		//RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
		ApiPermission apiPermission = method.getAnnotation(ApiPermission.class);
		HttpServletRequest request = HttpServletRequestUtil.getRequest();
		
		//System.out.println(request.getRequestURI());
		
		if(BeanUtil.isNotEmpty(apiPermission) && apiPermission.value()==AUTH.OPEN) {
			return proceedingJoinPoint.proceed();
		}
		
		RespChannelLogin channel = (RespChannelLogin) request.getSession().getAttribute("channel");
		
		if(channel!=null && ArrayUtil.contains(StrUtil.splitToArray(channel.getAdminPermissionApis(), ','), request.getRequestURI())) {
			return proceedingJoinPoint.proceed();
		}
		
		ResultMessage resultMessage = ResultMessage.FAILURE_AUTH_PERMISSION;
		String accept = ServletUtil.getHeader(request, "Accept", "utf-8");
    	if(StringUtils.isNotBlank(accept) && accept.contains("application/json")) {
    		throw LxException.of().setResult(resultMessage.result());
    	}else {
    		return String.valueOf(resultMessage.getCode());
    	}
		
    }
	
	@AfterReturning(pointcut = "pointCut()", returning = "resp")
    public void doAfterReturning(JoinPoint joinPoint, Object resp) {
		
	}
	
	@AfterThrowing(pointcut = "pointCut()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
		
	}
	
}
