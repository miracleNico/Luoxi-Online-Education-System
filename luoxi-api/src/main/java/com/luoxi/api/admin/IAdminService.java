package com.luoxi.api.admin;

import java.util.List;

import com.luoxi.api.admin.protocol.ReqAdminInfo;
import com.luoxi.api.admin.protocol.ReqAdminInfo.RespAdminInfo;
import com.luoxi.api.admin.protocol.ReqAdminList;
import com.luoxi.api.admin.protocol.ReqAdminList.RespAdminList;
import com.luoxi.api.admin.protocol.ReqAdminLogin;
import com.luoxi.api.admin.protocol.ReqAdminLogin.RespAdminLogin;
import com.luoxi.api.admin.protocol.ReqRemoveAdmin;
import com.luoxi.api.admin.protocol.ReqSaveAdmin;
import com.luoxi.api.admin.protocol.ReqUpdAdminPassword;
import com.luoxi.base.RespPaging;

public interface IAdminService {
	
	void cacheSession(String adminId,String sessionId) throws Exception;
	
	String cacheSession(String adminId) throws Exception;

	RespAdminLogin login(ReqAdminLogin req)throws Exception; 
	
	void updAdminPassword(String adminId,ReqUpdAdminPassword req) throws Exception;
	
	/**
	 * @Description: 管理员列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespPaging<RespAdminList> adminList(ReqAdminList req) throws Exception;
	
	/**
	 * @Description: 管理员详情
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespAdminInfo adminInfo(ReqAdminInfo req) throws Exception;
	
	/**
	 * @Description: 保存管理员
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	void saveAdmin(ReqSaveAdmin req) throws Exception;
	
	/**
	 * @Description: 删除管理员
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	void removeAdmin(List<ReqRemoveAdmin> req) throws Exception;
	
	/**
	 * @Description: 重置密码
	 * @Author wanbo
	 * @DateTime 2020/07/03
	 */
	void resetAdminPassword(String adminId) throws Exception;
	
}
