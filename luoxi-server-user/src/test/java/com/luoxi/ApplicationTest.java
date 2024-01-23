package com.luoxi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.luoxi.api.family.IFamilyMemberService;
import com.luoxi.api.family.protocol.ReqSaveFamilyMember;
import com.luoxi.api.family.protocol.ReqSaveFamilyMember.RespSaveFamilyMember;
import com.luoxi.api.user.IUserService;
import com.luoxi.constant.ConstSmsRequestType;
import com.luoxi.constant.Constant;
import com.luoxi.model.User;
import com.luoxi.server.dao.UserMapper;
import com.luoxi.third.YumushijiaApi;
import com.luoxi.util.SmsUtil;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;

@SpringBootTest
class ApplicationTest {

	@Autowired SmsUtil SmsUtil;
	@Autowired IUserService userService;
	@Autowired UserMapper userMapper;
	@Autowired IFamilyMemberService familyMemberService;
	@Autowired YumushijiaApi yumushijiaApi;
	@Autowired
	private Constant constant;
	
	@Test
	void testConst() {
		System.out.println(constant.getLearningMinutes_convert_score());
		System.out.println(constant.getDay_max_convert_score());
	}
	
	@Test
	void syncUser() {
		yumushijiaApi.syncUser("xxxxxxxxxxxxxxx", "17603010901");
	}
	
	@Test
	void saveFamilyMember() throws Exception{
		ReqSaveFamilyMember req = new ReqSaveFamilyMember();
		req.setRelationName("儿子");
		req.setJoinUserId("14762874de024c6eb990fed253a2db4e");
		RespSaveFamilyMember resp = familyMemberService.saveFamilyMember(req);
		System.out.println(JSONUtil.toJsonPrettyStr(resp));
	}
	
	@Test
	void sendSms() {
		SmsUtil.sendSms(null,ConstSmsRequestType.REGISTER, "17603010900", "1234");
	}
	
	@Test
	void userInfo() throws Exception {
		System.out.println(userService.userInfo("14762874de024c6eb990fed253a2db4e"));
	}
	
	@Test
	void saveUser() {
		User user = new User();
		user.setUserId(IdUtil.fastSimpleUUID());
		user.setUsername("aaa");
		user.setPassword(SecureUtil.md5("123456"));
		user.setPhone("17300001111");
		user.setNickName("sky");
		userMapper.insertSelective(user);
	}

}
