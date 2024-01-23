package com.luoxi.api.subject;

import com.luoxi.api.subject.protocol.ReqSubjectList.RespSubjectList;

import java.util.List;

public interface ISubjectService {

	List<RespSubjectList> subjectList() throws Exception;
	
}
