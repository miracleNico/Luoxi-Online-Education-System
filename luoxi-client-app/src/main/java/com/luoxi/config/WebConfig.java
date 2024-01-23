package com.luoxi.config;

import java.util.Arrays;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.luoxi.interceptor.DeviceAuthInterceptor;
import com.luoxi.interceptor.HeaderInterceptor;
import com.luoxi.interceptor.LogInterceptor;
import com.luoxi.interceptor.YmsjSignAuthInterceptor;
import com.luoxi.interceptor.SignInterceptor;
import com.luoxi.interceptor.UserAuthInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Resource
	private LogInterceptor logInterceptor;
	@Resource
	private HeaderInterceptor headerInterceptor;
	@Resource
	private SignInterceptor signAuthInterceptor;
	@Resource
	private UserAuthInterceptor userAuthInterceptor;
	@Resource
	private DeviceAuthInterceptor deviceAuthInterceptor;
	@Autowired
	private YmsjSignAuthInterceptor ymsjSignAuthInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//自定义拦截器，添加拦截路径和排除拦截路径
		
		registry.addInterceptor(logInterceptor).addPathPatterns("/api/**","/openapi/**");
		
		registry.addInterceptor(headerInterceptor).addPathPatterns("/api/**");
		
		registry.addInterceptor(signAuthInterceptor).addPathPatterns("/api/**").excludePathPatterns(Arrays.asList(
			"/api/app/upgrade"
			,"/api/businessApp/upgradeThirdApp"
		));
		
		registry.addInterceptor(ymsjSignAuthInterceptor).addPathPatterns("/ymsj/**");
		
		/**
		registry.addInterceptor(deviceAuthInterceptor).addPathPatterns("/api/**").excludePathPatterns(Arrays.asList(
				"/api/device/deviceAuth"
				,"/api/device/activate"
				,"/api/app/upgrade"
				,"/api/businessApp/upgradeThirdApp"
				,"/api/eduResource/recommendResource"
				));
				*/
		
		registry.addInterceptor(userAuthInterceptor).addPathPatterns("/api/**").excludePathPatterns(Arrays.asList(
				"/api/user/sendCode"
				,"/api/user/register"
				,"/api/user/loginSuper"
				,"/api/feedback/addFeedback"
				,"/api/feedback/getFeedbackOption"
				,"/api/eduResource/recommendResource"
				,"/api/eduResource/searchResource"
				,"/api/eduResource/eduResourceInfo"
				,"/api/eduResource/getEduResourceDetail"
				,"/api/eduResource/eduCondition"
				,"/api/eduResource/eduConditionOther"
				,"/api/eduResource/eduConditionTheme"
				,"/api/device/deviceAuth"
				,"/api/device/activate"
				,"/api/app/upgrade"
				,"/api/businessApp/upgradeThirdApp"
				,"/api/storeApp/searchStoreApp"
				,"/api/k12/**"
				));
	}
	
	//参考https://www.cnblogs.com/anxminise/p/9808279.html
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
		        .allowedOrigins("*")
		        .allowCredentials(true)
		        .allowedMethods("*")
				.allowedHeaders("*")
		        .maxAge(3600);
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//配置模板资源路径
		//registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");
		//registry.addResourceHandler("/templates/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/templates/");
		
		//解决 swagger-ui.html 404报错
        //registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        
        // 解决 doc.html 404 报错
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
	}
	
}