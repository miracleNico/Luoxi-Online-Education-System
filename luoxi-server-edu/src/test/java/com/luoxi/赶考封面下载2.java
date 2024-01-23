package com.luoxi;

import java.nio.charset.Charset;

import org.springframework.boot.test.context.SpringBootTest;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;

@SpringBootTest
class 赶考封面下载2 {	
	
	public static void main(String[] args) throws Exception{
		JSONArray jsonArray = JSONUtil.readJSONArray(FileUtil.file("F:\\赶考\\赶考完整封面.txt"), Charset.defaultCharset());
		for (Object object : jsonArray) {
			JSONArray ja = (JSONArray)object;
			HttpUtil.downloadFile(ja.get(1).toString(),"F:\\赶考\\封面完整图\\"+ja.get(0)+".jpg");
		}
		
	}
	

}
