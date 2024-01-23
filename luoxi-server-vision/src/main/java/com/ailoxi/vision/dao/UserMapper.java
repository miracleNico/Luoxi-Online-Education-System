/**  
 * @Title: OrderMapper.java
 * @Description: TODO
 * @author wanbo
 * @date 2018年3月28日
 */
package com.ailoxi.vision.dao;

import java.util.List;
import java.util.Map;

import com.luoxi.api.user.protocol.ReqUserList.RespUserList;
import com.luoxi.model.User;
import com.luoxi.server.vo.UserVo;

import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User>{
	UserVo getUser(Map<String, Object> map);
	
	List<RespUserList> getUserList(Map<String, Object> map);
}
