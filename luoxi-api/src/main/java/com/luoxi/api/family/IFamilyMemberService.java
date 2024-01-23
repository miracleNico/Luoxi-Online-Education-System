package com.luoxi.api.family;

import java.util.List;

import com.luoxi.api.family.protocol.ReqFamilyMemberInfo;
import com.luoxi.api.family.protocol.ReqGrowthRecord;
import com.luoxi.api.family.protocol.ReqFamilyMemberInfo.RespFamilyMemberInfo;
import com.luoxi.api.family.protocol.ReqFamilyMemberList.RespFamilyMemberList;
import com.luoxi.api.family.protocol.ReqGrowthRecord.RespGrowthRecord;
import com.luoxi.api.family.protocol.ReqRemoveFamilyMember;
import com.luoxi.api.family.protocol.ReqSaveFamilyMember;
import com.luoxi.api.family.protocol.ReqSaveFamilyMember.RespSaveFamilyMember;

public interface IFamilyMemberService {
	
	/**
	 * @Description: 成长记录
	 * @Author wanbo
	 * @DateTime 2020/04/14
	 */
	RespGrowthRecord growthRecord(ReqGrowthRecord req)throws Exception;

	/**
	 * @Description: 家庭成员列表
	 * @Author wanbo
	 * @DateTime 2019/11/18
	 */
	List<RespFamilyMemberList> familyMemberList(String joinUserId) throws Exception;
	
	/**
	 * @Description: 获取家庭成员信息
	 * @Author wanbo
	 * @DateTime 2019/11/18
	 */
	RespFamilyMemberInfo familyMemberInfo(ReqFamilyMemberInfo req) throws Exception;
	
	/**
	 * @Description: 保存家庭成员信息
	 * @Author wanbo
	 * @DateTime 2019/11/18
	 */
	RespSaveFamilyMember saveFamilyMember(ReqSaveFamilyMember req) throws Exception;
	
	/**
	 * @Description: 删除家庭成员
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	void removeFamilyMember(ReqRemoveFamilyMember req) throws Exception;
	
}
