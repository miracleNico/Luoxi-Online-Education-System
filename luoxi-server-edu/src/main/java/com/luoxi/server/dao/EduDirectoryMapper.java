package com.luoxi.server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.luoxi.api.eduDirectory.protocol.ReqEduCondition.RespEduConditionKV;
import com.luoxi.model.EduDirectory;

import tk.mybatis.mapper.common.Mapper;

public interface EduDirectoryMapper extends Mapper<EduDirectory>{

	@Select("select directory_name `key`,directory_id val from t_edu_directory where status=true and directory_type='THEME' and parent_id='' order by sort")
	List<RespEduConditionKV> eduThemeList();
	
	List<RespEduConditionKV> eduThemeListByMediaType(@Param("mediaType")String mediaType);
	
}
