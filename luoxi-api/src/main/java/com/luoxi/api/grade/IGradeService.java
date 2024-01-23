package com.luoxi.api.grade;

import java.util.List;

import com.luoxi.api.grade.protocol.ReqGradeList.RespGradeList;

public interface IGradeService {
	
	List<RespGradeList> gradeList() throws Exception;
	
}
