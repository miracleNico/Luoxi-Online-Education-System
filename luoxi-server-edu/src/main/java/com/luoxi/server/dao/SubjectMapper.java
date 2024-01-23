/**  
 * @Title: OrderMapper.java
 * @Description: TODO
 * @author wanbo
 * @date 2018年3月28日
 */
package com.luoxi.server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.luoxi.api.subject.protocol.ReqSubjectList.RespSubjectList;
import com.luoxi.model.Subject;

import tk.mybatis.mapper.common.Mapper;

public interface SubjectMapper extends Mapper<Subject>{
	
	@Select("select * from t_subject order by subject_id asc")
	List<RespSubjectList> subjectList();
	
}
