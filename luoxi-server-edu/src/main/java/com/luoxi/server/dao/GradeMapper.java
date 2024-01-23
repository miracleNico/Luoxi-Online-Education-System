/**  
 * @Title: OrderMapper.java
 * @Description: TODO
 * @author wanbo
 * @date 2018年3月28日
 */
package com.luoxi.server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.luoxi.api.grade.protocol.ReqGradeList.RespGradeList;
import com.luoxi.model.Grade;

import tk.mybatis.mapper.common.Mapper;

public interface GradeMapper extends Mapper<Grade>{
	
	@Select("select * from t_grade where volume='ZERO' order by grade_id asc")
	List<RespGradeList> gradeList();
	
}
