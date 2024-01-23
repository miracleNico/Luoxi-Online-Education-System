package com.luoxi.server.service;


import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.luoxi.api.family.IFamilyMemberService;
import com.luoxi.api.family.protocol.ReqFamilyMemberInfo;
import com.luoxi.api.family.protocol.ReqFamilyMemberInfo.RespFamilyMemberInfo;
import com.luoxi.api.family.protocol.ReqFamilyMemberList.RespFamilyMemberList;
import com.luoxi.api.family.protocol.ReqGrowthRecord;
import com.luoxi.api.family.protocol.ReqGrowthRecord.RespGrowthRecord;
import com.luoxi.api.family.protocol.ReqGrowthRecord.RespGrowthRecordVaccine;
import com.luoxi.api.family.protocol.ReqRemoveFamilyMember;
import com.luoxi.api.family.protocol.ReqSaveFamilyMember;
import com.luoxi.api.family.protocol.ReqSaveFamilyMember.RespSaveFamilyMember;
import com.luoxi.constant.ConstCacheKey;
import com.luoxi.exception.LxException;
import com.luoxi.model.FamilyMember;
import com.luoxi.server.dao.FamilyMemberMapper;
import com.luoxi.utils.CosUtil;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

@DubboService
public class FamilyMemberService implements IFamilyMemberService{
	
	@Autowired
	private FamilyMemberMapper familyMemberMapper;
	@Autowired
	private CosUtil cosUtil;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	/**
	 * @Description: 成长记录
	 * @Author wanbo
	 * @DateTime 2020/04/14
	 */
	@Override
	public RespGrowthRecord growthRecord(ReqGrowthRecord req) throws Exception {
		String cacheKey = ConstCacheKey.FM_GROWTH_RECORD.cacheKey(req.getUserId());
		Object value = stringRedisTemplate.opsForHash().get(cacheKey, req.getMonth());
		RespGrowthRecord resp = new RespGrowthRecord();
		if(stringRedisTemplate.hasKey(cacheKey) && value!=null) {
			resp = JSONUtil.toBean(new JSONObject(value), RespGrowthRecord.class);
		}else {
			Map<String, Object> monthAgeMap = familyMemberMapper.getMonthAge(req);
			List<Map<String, String>> standardList = familyMemberMapper.getStandardHeightAndWeight(req);
			List<RespGrowthRecordVaccine> vaccineList = familyMemberMapper.vaccineList(req);
			
			StringBuffer sb = new StringBuffer("亲爱的{nick_name}，今天你{age}岁");
			if(Convert.toInt(monthAgeMap.get("month"))>0) {
				sb.append(monthAgeMap.get("month")).append("个月");
			}
			sb.append("了，快量一下身高体重是不是在这个范围哦：");
			for (Map<String, String> map : standardList) {
				if(map.get("standard_type").equals("HEIGHT")) {
					sb.append(" 身高:").append(map.get("value")).append("CM");
				}
				if(map.get("standard_type").equals("WEIGHT")) {
					sb.append(" 体重:").append(map.get("value")).append("KG");
				}
			}
			resp.setInfo(
					StrUtil.format(sb.toString(), MapUtil.builder()
							.put("nick_name", monthAgeMap.get("nick_name")) 
							.put("age", monthAgeMap.get("age"))
							.build()
							)
					);
			resp.setVaccineList(vaccineList);
			stringRedisTemplate.opsForHash().put(cacheKey, req.getMonth(), JSONUtil.toJsonStr(resp));
			stringRedisTemplate.expire(cacheKey, ConstCacheKey.FM_GROWTH_RECORD.getExpire(),TimeUnit.SECONDS);
		}
		return resp;
	}
	
	@Override
	public List<RespFamilyMemberList> familyMemberList(String joinUserId) throws Exception {
		List<RespFamilyMemberList> list = familyMemberMapper.familyMemberList(joinUserId);
		for (RespFamilyMemberList resp : list) {
			resp.setHeadUrl(cosUtil.getAccessUrl(resp.getHeadUrl()));
		}
		return list;
	}
	
	
	@Override
	public RespFamilyMemberInfo familyMemberInfo(ReqFamilyMemberInfo req) throws Exception{
		RespFamilyMemberInfo resp = null;
		FamilyMember familyMember = familyMemberMapper.selectByPrimaryKey(req.getFamilyMemberId());
		if(!BeanUtil.isEmpty(familyMember)) {
			resp = new RespFamilyMemberInfo();
			BeanUtil.copyProperties(familyMember, resp);
			resp.setHeadUrl(cosUtil.getAccessUrl(resp.getHeadUrl()));
		}
		return resp;
	}
	
	
	@Override
	public RespSaveFamilyMember saveFamilyMember(ReqSaveFamilyMember req) throws Exception{
		if(req.getBirthday()!=null) {
			req.setAge(DateUtil.ageOfNow(req.getBirthday()));			
		}
		FamilyMember familyMember = new FamilyMember();
		BeanUtil.copyProperties(req, familyMember);
		
		familyMember.setHeadUrl(cosUtil.filterUrlDomain(familyMember.getHeadUrl()));
		
		if(StringUtils.isNotEmpty(req.getFamilyMemberId())) {
			//修改
			familyMemberMapper.updateByPrimaryKeySelective(familyMember);
		}else {
			//新增
			if(familyMemberMapper.existRelationName(req.getJoinUserId(), req.getRelationName()))
				throw LxException.of().setMsg("家庭角色已存在");
			
			familyMember.setFamilyMemberId(IdUtil.fastSimpleUUID());
			familyMemberMapper.insertSelective(familyMember);
		}
		
		RespSaveFamilyMember resp = null;
		
		if(familyMember!=null) {
			resp = new RespSaveFamilyMember();
			BeanUtil.copyProperties(familyMember, resp);
			resp.setHeadUrl(cosUtil.getAccessUrl(resp.getHeadUrl()));
			resp.setBirthday(DateUtil.formatDate(familyMember.getBirthday()));
			stringRedisTemplate.delete(ConstCacheKey.FM_GROWTH_RECORD.cacheKey(req.getJoinUserId()));
		}
		
		return resp;
	}
	
	
	/**
	 * @Description: 删除家庭成员
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public void removeFamilyMember(ReqRemoveFamilyMember req) throws Exception {
		familyMemberMapper.removeFamilyMember(req.getFamilyMemberId(), req.getUserId());
	}
	
	
}
