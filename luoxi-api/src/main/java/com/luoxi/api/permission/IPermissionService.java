package com.luoxi.api.permission;

import java.util.List;
import java.util.Map;

import com.luoxi.api.permission.protocol.ReqPermissionInfo;
import com.luoxi.api.permission.protocol.ReqPermissionInfo.RespPermissionInfo;
import com.luoxi.api.permission.protocol.ReqPermissionList;
import com.luoxi.api.permission.protocol.ReqPermissionList.RespPermissionList;
import com.luoxi.api.permission.protocol.ReqRemovePermission;
import com.luoxi.api.permission.protocol.ReqSavePermission;
import com.luoxi.api.permission.protocol.ReqUpdPermissionParent;
import com.luoxi.api.permission.vo.MenuVo;

public interface IPermissionService {

	/**
	 * @Description: 权限列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	List<RespPermissionList> permissionList(ReqPermissionList req) throws Exception;
	
	/**
	 * @Description: 权限详情
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespPermissionInfo permissionInfo(ReqPermissionInfo req) throws Exception;
	
	/**
	 * @Description: 保存权限
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	void savePermission(ReqSavePermission req) throws Exception;
	
	/**
	 * @Description: 删除权限
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	void removePermission(List<ReqRemovePermission> req) throws Exception;
	
	/**
	 * @Description: 修改父级节点
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	void updPermissionParent(List<ReqUpdPermissionParent> req) throws Exception;
	
	List<Map<String, Object>> permissionTree(String roleId) throws Exception;
	
	String adminPermissionApis(String adminId)throws Exception;
	
	String adminPermissionTags(String adminId)throws Exception;
	
	List<MenuVo> adminMenuList(String adminId)throws Exception;
	
}
