package com.luoxi.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ConstPrizeStatus {
	
	UP("已上架"),
	DOWN("已下架")
	;
	
	private String text;
	
}
