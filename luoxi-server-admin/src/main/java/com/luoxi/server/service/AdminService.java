package com.luoxi.server.service;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luoxi.api.admin.IAdminService;
import com.luoxi.api.admin.protocol.ReqAdminInfo;
import com.luoxi.api.admin.protocol.ReqAdminInfo.RespAdminInfo;
import com.luoxi.api.admin.protocol.ReqAdminList;
import com.luoxi.api.admin.protocol.ReqAdminList.RespAdminList;
import com.luoxi.api.admin.protocol.ReqAdminLogin;
import com.luoxi.api.admin.protocol.ReqAdminLogin.RespAdminLogin;
import com.luoxi.api.admin.protocol.ReqRemoveAdmin;
import com.luoxi.api.admin.protocol.ReqSaveAdmin;
import com.luoxi.api.admin.protocol.ReqUpdAdminPassword;
import com.luoxi.api.permission.vo.MenuVo;
import com.luoxi.base.RespPaging;
import com.luoxi.base.ResultMessage;
import com.luoxi.constant.ConstCacheKey;
import com.luoxi.constant.Constant;
import com.luoxi.exception.LxException;
import com.luoxi.model.Admin;
import com.luoxi.server.dao.AdminMapper;
import com.luoxi.server.dao.PermissionMapper;
import com.luoxi.server.dao.RoleMapper;
import com.luoxi.server.vo.AdminVo;
import com.luoxi.util.MenuUtil;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;

@DubboService
public class AdminService implements IAdminService{

	@Autowired
	private AdminMapper adminMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	PermissionMapper permissionMapper;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private Constant constant;
	
	@Override
	public String cacheSession(String adminId) throws Exception {
		return stringRedisTemplate.opsForValue().get(ConstCacheKey.SESSION.cacheKey(adminId));
	}
	
	@Override
	public void cacheSession(String adminId, String sessionId) throws Exception {
		stringRedisTemplate.opsForValue().set(
				ConstCacheKey.SESSION.cacheKey(adminId), 
				sessionId,
				Duration.ofSeconds(ConstCacheKey.SESSION.getExpire())
				);
	}
	
	@Override
	public RespAdminLogin login(ReqAdminLogin req) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", req.getUsername());
		map.put("password", req.getPassword());
		AdminVo admin = adminMapper.getAdmin(map);
		
		if(admin==null)
			throw LxException.of().setMsg("用户名或密码错误");
		if(!admin.getEnableStatus())
			throw LxException.of().setMsg("您已被锁定");
		
		//菜单权限
		List<MenuVo> menuList = permissionMapper.adminMenuList(admin.getAdminId());
		String jsonMenus = MenuUtil.jsonMenus(menuList, constant.getCrm_menu_path());
		
		//功能权限
		String adminPermissionApis = permissionMapper.adminPermissionApis(admin.getAdminId());
		String adminPermissionTags = permissionMapper.adminPermissionTags(admin.getAdminId());
		
		RespAdminLogin resp = new RespAdminLogin();
		BeanUtil.copyProperties(admin, resp);
		resp.setJsonMenus(jsonMenus);
		resp.setAdminPermissionApis(adminPermissionApis);
		resp.setAdminPermissionTags(adminPermissionTags);
		return resp;
	}
	
	@Override
	public void updAdminPassword(String adminId, ReqUpdAdminPassword req) throws Exception {
		Admin admin = adminMapper.selectByPrimaryKey(adminId);
		if(!admin.getPassword().equals(SecureUtil.md5(req.getOldPassword())))
			throw LxException.of().setMsg("原密码错误");
		admin.setPassword(SecureUtil.md5(req.getNewPassword()));
		adminMapper.updateByPrimaryKeySelective(admin);
	}
	

	/**
	 * @Description: 管理员列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public RespPaging<RespAdminList> adminList(ReqAdminList req) throws Exception {
		RespPaging<RespAdminList> respPaging = new RespPaging<RespAdminList>();
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		List<RespAdminList> list = adminMapper.adminList(BeanUtil.beanToMap(req));
		BeanUtil.copyProperties(new PageInfo<RespAdminList>(list), respPaging);
		return respPaging;
	}

	/**
	 * @Description: 角色信息
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public RespAdminInfo adminInfo(ReqAdminInfo req) throws Exception {
		RespAdminInfo resp = adminMapper.adminInfo(BeanUtil.beanToMap(req));
		if(resp!=null) {
			resp.setRoleIdList(roleMapper.getRoleIdListByAdminId(req.getAdminId()));
		}
		return resp;
	}

	
	/**
	 * @Description: 保存角色
	 * @Author wanbo
	 * @DateTime 2020/06/16
	 */
	@Override
	@Transactional
	public void saveAdmin(ReqSaveAdmin req) throws Exception {
		Admin admin = new Admin();
		BeanUtil.copyProperties(req, admin);
		Admin dbAdmin = adminMapper.adminOnly(MapUtil.builder("username",req.getUsername()).build());
		//修改操作
		if(StringUtils.isNotBlank(req.getAdminId())) {
			if(!BeanUtil.isEmpty(dbAdmin) && !dbAdmin.getAdminId().equals(req.getAdminId())) throw LxException.of().setMsg("管理员已存在");
			if(BeanUtil.isEmpty(dbAdmin) || dbAdmin.getAdminId().equals(req.getAdminId())) {
				adminMapper.updateByPrimaryKeySelective(admin);
			}else {
				throw LxException.of().setResult(ResultMessage.FAILURE.result());				
			}
		}else {//新增操作
			if(!BeanUtil.isEmpty(dbAdmin)) throw LxException.of().setMsg("管理员已存在");
			admin.setAdminId(IdUtil.fastSimpleUUID());
			admin.setPassword(constant.getDefault_password());
			adminMapper.insertSelective(admin);
		}
		
		//管理员角色关联
		roleMapper.removeAdminRole(admin.getAdminId());
		if(CollUtil.isNotEmpty(req.getRoleIdList())) {
			roleMapper.addAdminRole(admin.getAdminId(), req.getRoleIdList());
		}
	}
	
	/**
	 * @Description: 删除管理员
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public void removeAdmin(List<ReqRemoveAdmin> req) throws Exception {
		adminMapper.removeAdmin(req);
	}
	
	/**
	 * @Description: 重置密码
	 * @Author wanbo
	 * @DateTime 2020/07/03
	 */
	@Override
	public void resetAdminPassword(String adminId) throws Exception {
		adminMapper.updateByPrimaryKeySelective(new Admin().setAdminId(adminId).setPassword(constant.getDefault_password()));
	}
	
}
