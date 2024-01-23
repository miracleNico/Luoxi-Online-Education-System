/**  
 * @Title: OrderMapper.java
 * @Description: TODO
 * @author wanbo
 * @date 2018年3月28日
 */
package com.luoxi.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.luoxi.api.family.protocol.ReqFamilyMemberList.RespFamilyMemberList;
import com.luoxi.api.family.protocol.ReqGrowthRecord;
import com.luoxi.api.family.protocol.ReqGrowthRecord.RespGrowthRecordVaccine;
import com.luoxi.model.FamilyMember;

import tk.mybatis.mapper.common.Mapper;

public interface FamilyMemberMapper extends Mapper<FamilyMember>{
	
	/**
	 * @Description: 每月疫苗信息
	 * @Author wanbo
	 * @DateTime 2020/04/14
	 */
	List<RespGrowthRecordVaccine> vaccineList(ReqGrowthRecord req);
	
	/**
	 * @Description: 每月身标准高体重信息
	 * @Author wanbo
	 * @DateTime 2020/04/14
	 */
	List<Map<String, String>> getStandardHeightAndWeight(ReqGrowthRecord req);
	
	/**
	 * @Description: 获取月龄
	 * @Author wanbo
	 * @DateTime 2020/04/14
	 */
	Map<String, Object> getMonthAge(ReqGrowthRecord req);
	
	/**
	 * @Description: 家庭成员列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	List<RespFamilyMemberList> familyMemberList(String joinUserId);
	
	/**
	 * @Description: 家庭关系名称是否已存在
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	boolean existRelationName(@Param("joinUserId")String joinUserId, @Param("relationName")String relationName);
	
	
	/**
	 * @Description: 删除家庭成员
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Update("update t_family_member set status=false,update_user=#{userId} where join_user_id=#{userId} and family_member_id=#{familyMemberId}")
	int removeFamilyMember(@Param("familyMemberId")String familyMemberId, @Param("userId")String userId);
	
}
