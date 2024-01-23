package com.luoxi;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.boot.test.context.SpringBootTest;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;

@SpringBootTest
class 赶考封面下载 {	
	
	public static void main(String[] args) throws Exception{
		List<String> lines = FileUtil.readLines("F:\\赶考封面\\封面图.txt",Charset.forName("UTF-8"));
		for (String url : lines) {
			HttpUtil.downloadFile(url,"F:\\赶考封面\\"+URLUtil.getPath(url).replace("/", "_"));			
		}
	}
	

}
