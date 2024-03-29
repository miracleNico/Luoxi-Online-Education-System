package com.luoxi.server.service;


import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luoxi.api.channel.IChannelService;
import com.luoxi.api.channel.protocol.ReqChannelInfo;
import com.luoxi.api.channel.protocol.ReqChannelInfo.RespChannelInfo;
import com.luoxi.api.channel.protocol.ReqChannelList;
import com.luoxi.api.channel.protocol.ReqChannelList.RespChannelList;
import com.luoxi.api.channel.protocol.ReqChannelLogin;
import com.luoxi.api.channel.protocol.ReqChannelLogin.RespChannelLogin;
import com.luoxi.api.channel.protocol.ReqRemoveChannel;
import com.luoxi.api.channel.protocol.ReqSaveChannel;
import com.luoxi.api.channel.protocol.ReqUpdChannelPassword;
import com.luoxi.api.permission.IPermissionService;
import com.luoxi.api.permission.vo.MenuVo;
import com.luoxi.api.role.IRoleService;
import com.luoxi.base.RespPaging;
import com.luoxi.base.ResultMessage;
import com.luoxi.constant.ConstCacheKey;
import com.luoxi.constant.Constant;
import com.luoxi.exception.LxException;
import com.luoxi.model.Channel;
import com.luoxi.server.dao.ChannelMapper;
import com.luoxi.server.dao.SequenceMapper;
import com.luoxi.util.MenuUtil;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.extra.pinyin.PinyinUtil;

@DubboService
@Component
public class ChannelService implements IChannelService{

	@Autowired
	private ChannelMapper channelMapper;
	@Autowired
	private SequenceMapper sequenceMapper;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private Constant constant;
	@DubboReference
	private IRoleService roleService;
	@DubboReference
	private IPermissionService permissionService;
	
	/**
	 * @Description: 重置渠道密码
	 * @Author wanbo
	 * @DateTime 2020/05/12
	 */
	@Override
	public void resetChannelPassword(String channelId) throws Exception {
		Channel channel = new Channel();
		channel.setChannelId(channelId);
		channel.setPassword(constant.getDefault_password());
		channelMapper.updateByPrimaryKeySelective(channel);
	}
	
	@Override
	public void cacheSession(String channelId, String sessionId) throws Exception {
		stringRedisTemplate.opsForValue().set(
				ConstCacheKey.SESSION.cacheKey(channelId), 
				sessionId,
				Duration.ofSeconds(ConstCacheKey.SESSION.getExpire())
				);
	}
	
	@Override
	public String cacheSession(String channelId) throws Exception {
		return stringRedisTemplate.opsForValue().get(ConstCacheKey.SESSION.cacheKey(channelId));
	}
	
	@Override
	public RespChannelLogin login(ReqChannelLogin req) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", req.getUsername());
		map.put("password", req.getPassword());
		Channel channel = channelMapper.getChannel(map);
		if(channel==null)
			throw LxException.of().setMsg("用户名或密码错误");
		
		//菜单权限
		List<MenuVo> menuList = permissionService.adminMenuList(channel.getChannelId());
		String jsonMenus = MenuUtil.jsonMenus(menuList, constant.getChannel_menu_path());
		
		//功能权限
		String adminPermissionApis = permissionService.adminPermissionApis(channel.getChannelId());
		String adminPermissionTags = permissionService.adminPermissionTags(channel.getChannelId());
		
		RespChannelLogin resp = new RespChannelLogin();
		BeanUtil.copyProperties(channel, resp);
		resp.setJsonMenus(jsonMenus);
		resp.setAdminPermissionApis(adminPermissionApis);
		resp.setAdminPermissionTags(adminPermissionTags);
		return resp;
	}
	
	@Override
	public void updChannelPassword(String channelId, ReqUpdChannelPassword req) throws Exception {
		Channel channel = channelMapper.selectByPrimaryKey(channelId);
		if(!channel.getPassword().equals(SecureUtil.md5(req.getOldPassword())))
			throw LxException.of().setMsg("原密码错误");
		channel.setPassword(SecureUtil.md5(req.getNewPassword()));
		channelMapper.updateByPrimaryKeySelective(channel);
	}
	
	/**
	 * @Description: 渠道列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public RespPaging<RespChannelList> channelList(ReqChannelList req) throws Exception {
		RespPaging<RespChannelList> respPaging = new RespPaging<RespChannelList>();
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		List<RespChannelList> list = channelMapper.channelList(BeanUtil.beanToMap(req));
		BeanUtil.copyProperties(new PageInfo<RespChannelList>(list), respPaging);
		return respPaging;
	}

	/**
	 * @Description: 渠道信息
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public RespChannelInfo channelInfo(ReqChannelInfo req) throws Exception {
		RespChannelInfo resp = channelMapper.channelInfo(BeanUtil.beanToMap(req));
		if(resp!=null) {
			resp.setRoleIdList(roleService.getRoleIdListByAdminId(req.getChannelId()));
		}
		return resp;
	}

	/**
	 * @Description: 保存渠道
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public void saveChannel(ReqSaveChannel req) throws Exception {
		Channel channel = new Channel();
		BeanUtil.copyProperties(req, channel);
		if(StrUtil.isBlank(channel.getUsername())) {
			channel.setUsername(PinyinUtil.getPinyin(channel.getChannelName(), ""));			
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("channelName", req.getChannelName());
		Channel dbChannel = channelMapper.getChannel(map);
		
		if(StringUtils.isNotBlank(channel.getChannelId())) {
			//修改操作
			if(!BeanUtil.isEmpty(dbChannel) && !dbChannel.getChannelId().equals(channel.getChannelId())) 
				throw LxException.of().setMsg("名称已存在");
			if(BeanUtil.isEmpty(dbChannel) || dbChannel.getChannelId().equals(channel.getChannelId())) {
				channel.setPassword(null);
				channelMapper.updateByPrimaryKeySelective(channel);
			}else {
				throw LxException.of().setResult(ResultMessage.FAILURE.result());			
			}
		}else {
			//新增操作
			if(!BeanUtil.isEmpty(dbChannel))
				throw LxException.of().setMsg("名称已存在");
			channel.setChannelId(sequenceMapper.generateChannelId());
			channel.setPassword(constant.getDefault_password());
			channelMapper.insertSelective(channel);
		}
		
		//管理员角色关联
		roleService.removeAdminRole(channel.getChannelId());
		if(CollUtil.isNotEmpty(req.getRoleIdList())) {
			roleService.addAdminRole(channel.getChannelId(), req.getRoleIdList());
		}
	}

	/**
	 * @Description: 删除渠道
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public void removeChannel(List<ReqRemoveChannel> req) throws Exception {
		channelMapper.removeChannel(req);
	}
	
	
	
	
	
	
	
	
}
