package com.luoxi.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.luoxi.aop.ApiPermission;
import com.luoxi.aop.ApiPermission.AUTH;
import com.luoxi.base.BaseController;

@Controller
@RequestMapping("main")
public class MainController extends BaseController{
	
	@ApiPermission(AUTH.OPEN)
	@RequestMapping
	public String main(HttpServletRequest request) {
		request.setAttribute("admin", getAdmin());
		return "page/main/main";
	}
	
}
