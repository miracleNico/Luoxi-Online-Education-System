package com.luoxi.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ConstSyncModel {
	
	LEARN_DIR("com.luoxi.model.mongo.LearnDir"),
	TAKE_READ("com.luoxi.model.mongo.TakeRead"),
	TAKE_READ_DETAIL("com.luoxi.model.mongo.TakeReadDetail")
	;
	
	private String className;
	
}
