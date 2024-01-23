package com.luoxi.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class WanqiaoResourceVo implements Serializable{
	
	private static final long serialVersionUID = 8455857008135789361L;
	private String resourceId;
	private String id;
	private String title;
	private String coverUrl;
	private String fileUrl;
	private String tags;
	private String 年龄;
	private String introduction;
	private String content;
	private String parentId;
	private String businessCode;
	private String resourceType;
	private String mediaType;
	private Integer minAge;
    private Integer maxAge;
    private List<WanqiaoResourceVo> pages = new ArrayList<WanqiaoResourceVo>();
    
    @Data
    public static class WanqiaoResourceJson implements Serializable{
		private static final long serialVersionUID = -9114770674119926668L;
		private String resourceId;
    	private String resourceTitle;
    	private String introduction;
    	private String resourceType;
    	private String mediaType;
    	private String businessCode;
    	private String coverUrl;
    	private String tags;
    	private List<Map<Object, Object>> pages = new ArrayList<Map<Object, Object>>();
    }
	
}
