package com.luoxi.api.role;

import java.util.List;

import com.luoxi.api.role.protocol.ReqRemoveRole;
import com.luoxi.api.role.protocol.ReqRoleInfo;
import com.luoxi.api.role.protocol.ReqRoleInfo.RespRoleInfo;
import com.luoxi.api.role.protocol.ReqRoleList;
import com.luoxi.api.role.protocol.ReqRoleList.RespRoleList;
import com.luoxi.api.role.protocol.ReqSaveRole;
import com.luoxi.base.RespPaging;

public interface IRoleService {

	/**
	 * @Description: 角色列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespPaging<RespRoleList> roleList(ReqRoleList req) throws Exception;
	
	/**
	 * @Description: 角色详情
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespRoleInfo roleInfo(ReqRoleInfo req) throws Exception;
	
	/**
	 * @Description: 保存角色
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	void saveRole(ReqSaveRole req) throws Exception;
	
	/**
	 * @Description: 删除角色
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	void removeRole(List<ReqRemoveRole> req) throws Exception;
	
	
	/**
	 * @Description:配置管理员角色 
	 * @Author wanbo
	 * @DateTime 2020/09/10
	 */
	int addAdminRole(String adminId,List<String> roleIdList);
	
	/**
	 * @Description: 获取管理员角色
	 * @Author wanbo
	 * @DateTime 2020/07/03
	 */
	List<String> getRoleIdListByAdminId(String adminId);
	
	/**
	 * @Description: 删除管理员角色关联
	 * @Author wanbo
	 * @DateTime 2020/06/18
	 */
	int removeAdminRole(String adminId);
	
}
