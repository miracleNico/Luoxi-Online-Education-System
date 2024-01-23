package com.luoxi.exception;

import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.alibaba.dubbo.rpc.RpcException;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.CryptoException;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONUtil;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;


/**
 * @Description: 全局异常处理
 * @Author wanbo
 * @DateTime 2019/11/29
 */
@Slf4j
@RestControllerAdvice
public class LxExceptionHandler {
    
    @ExceptionHandler(LxException.class)
    private ModelAndView exceptionHandler(HttpServletRequest request, HttpServletResponse response, LxException e) {
    	return checkModelAndView(request, response, e, e.getResult());
    }
    
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    private ModelAndView exceptionHandler(HttpServletRequest request, HttpServletResponse response, SQLIntegrityConstraintViolationException e) {
    	return checkModelAndView(request, response, e, ResultMessage.FAILURE_HANDLER_SERIOUS.result().setMsg("SQL执行异常"));
    }

    @ExceptionHandler(RpcException.class)
    private ModelAndView exceptionHandler(HttpServletRequest request, HttpServletResponse response, RpcException e) {
    	return checkModelAndView(request, response, e, ResultMessage.FAILURE_HANDLER_SERIOUS.result().setMsg("服务维护中，请稍后再试！"));
    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    private ModelAndView exceptionHandler(HttpServletRequest request, HttpServletResponse response, HttpMessageNotReadableException e) {
    	return checkModelAndView(request, response, e, ResultMessage.FAILURE_HANDLER.result().setMsg("请求数据格式错误"));
    }
    
    @ExceptionHandler(CryptoException.class)
    private ModelAndView exceptionHandler(HttpServletRequest request, HttpServletResponse response, CryptoException e) {
    	return checkModelAndView(request, response, e, ResultMessage.FAILURE_HANDLER.result().setMsg("加密异常"));
    }
    
    @ExceptionHandler(SignatureException.class)
    private ModelAndView exceptionHandler(HttpServletRequest request, HttpServletResponse response, SignatureException e) {
    	return checkModelAndView(request, response, e, ResultMessage.FAILURE_HANDLER.result().setMsg("token解析错误"));
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    private ModelAndView exceptionHandler(HttpServletRequest request, HttpServletResponse response, IllegalArgumentException e) {
    	return checkModelAndView(request, response, e, ResultMessage.FAILURE_HANDLER.result().setMsg(e.getMessage()));
    }
    
    @ExceptionHandler(Exception.class)
    private ModelAndView exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
    	return checkModelAndView(request, response, e, ResultMessage.FAILURE.result());
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ModelAndView exception(HttpServletRequest request, HttpServletResponse response,MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuffer sb = new StringBuffer();
		for (ObjectError error : bindingResult.getAllErrors()) {
			sb.append(error.getDefaultMessage()).append(";");
		}
		if(sb.length()>0)sb.delete(sb.length()-1, sb.length());
		return checkModelAndView(request, response, e, ResultMessage.FAILURE_REQUEST_PARAM.result().setData(sb.toString()));
    }
    
    private ModelAndView checkModelAndView(HttpServletRequest request, HttpServletResponse response, Exception e,Result<?> result) {
    	if(ResultMessage.FAILURE.getCode()==result.getCode()) {
    		log.error(ExceptionUtils.getStackTrace(e));
    	}
    	log.error(JSONUtil.toJsonStr(result));    		
    	
    	//返回页面
    	String accept = ServletUtil.getHeader(request, "Accept", "utf-8");
    	if(StringUtils.isNotBlank(accept) && accept.contains("text/html")) {
    		return new ModelAndView("redirect:/error");
    	}
    	
    	//返回JSON
    	return new ModelAndView(new MappingJackson2JsonView()).addAllObjects(BeanUtil.beanToMap(result));
    }
    
    
}