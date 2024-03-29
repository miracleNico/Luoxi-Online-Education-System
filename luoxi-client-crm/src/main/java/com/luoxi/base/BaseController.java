package com.luoxi.base;

import javax.servlet.http.HttpServletRequest;

import com.luoxi.api.admin.protocol.ReqAdminLogin.RespAdminLogin;
import com.luoxi.utils.HttpServletRequestUtil;

public class BaseController {
	
	public String getAdminId(){
		return getAdmin()!=null?getAdmin().getAdminId():null;
	}
	
	public String getUsername(){
		return getAdmin()!=null?getAdmin().getUsername():null;
	}
	
	public RespAdminLogin getAdmin(){
		HttpServletRequest request = HttpServletRequestUtil.getRequest();
		Object object = request.getSession().getAttribute("admin");
		if(object!=null) {
			RespAdminLogin admin = (RespAdminLogin) object;
			return admin;
		}
		return null;
	}
	
}
