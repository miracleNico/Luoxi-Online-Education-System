/**  
 * @Title: OrderMapper.java
 * @Description: TODO
 * @author wanbo
 * @date 2018年3月28日
 */
package com.luoxi.server.dao;

import java.util.List;
import java.util.Map;

import com.luoxi.api.user.protocol.ReqSyncUserFromThird;
import com.luoxi.api.user.protocol.ReqUserInfo;
import com.luoxi.model.UserFromThird;
import org.apache.ibatis.annotations.Param;

import com.luoxi.api.user.protocol.ReqRemoveUser;
import com.luoxi.api.user.protocol.ReqUserList.RespUserList;
import com.luoxi.model.User;
import com.luoxi.server.vo.UserVo;

import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User>{
	
	Integer userTotal(@Param("channelId")String channelId);
	
	UserVo getUser(Map<String, Object> map);
	
	List<RespUserList> getUserList(Map<String, Object> map);
	
	/**
	 * @Description: 删除用户
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	int removeUser(@Param("list")List<ReqRemoveUser> list);

	ReqUserInfo.RespUserInfo userInfoFromThird(UserFromThird uft);

	void updateUserById(Map map);
	
}
