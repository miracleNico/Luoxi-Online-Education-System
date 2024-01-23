package com.luoxi.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.luoxi.interceptor.HeaderInterceptor;
import com.luoxi.interceptor.SignAuthInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Resource
	private HeaderInterceptor headerInterceptor;
	@Resource
	private SignAuthInterceptor signAuthInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		//自定义拦截器，添加拦截路径和排除拦截路径
		registry.addInterceptor(headerInterceptor).addPathPatterns("/api/**");
		
		/**
		registry.addInterceptor(signAuthInterceptor).addPathPatterns("/api/**").excludePathPatterns(Arrays.asList(
			"/api/app/upgrade"
			,"/api/businessApp/upgradeThirdApp"
		));*/
		
	}
	
	//参考https://www.cnblogs.com/anxminise/p/9808279.html
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("*")
		        .allowedOrigins("*")
		        .allowCredentials(true)
		        .allowedMethods("*")
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
        //registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
	}
	
}