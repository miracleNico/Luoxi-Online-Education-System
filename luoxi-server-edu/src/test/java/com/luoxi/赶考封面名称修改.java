package com.luoxi;

import java.io.File;
import java.util.List;

import org.springframework.boot.test.context.SpringBootTest;

import cn.hutool.core.io.FileUtil;

@SpringBootTest
class 赶考封面名称修改 {	
	
	public static void main(String[] args) throws Exception{
		List<File> list = FileUtil.loopFiles("F:\\童年小样\\人民教育出版社封面");
		for (File file : list) {
			FileUtil.rename(file, file.getName().replace("-封面", ""), false, true);
		}
	}
	

}
