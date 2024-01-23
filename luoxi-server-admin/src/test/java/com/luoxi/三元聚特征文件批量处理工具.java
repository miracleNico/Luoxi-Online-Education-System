package com.luoxi;

import org.springframework.boot.test.context.SpringBootTest;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.RuntimeUtil;

@SpringBootTest
class 三元聚特征文件批量处理工具 {
	
	
	public static void main(String[] args) {
		FileUtil.loopFiles(FileUtil.file("F:/三元聚/tool/input"), 1, null).forEach(file->{
			String name = file.getName();
			Process pr = RuntimeUtil.exec(null,FileUtil.file("F:/三元聚/tool/plugins"),"F:/三元聚/tool/plugins/extractor.exe ../output/"+name+" ../input/"+name);
			RuntimeUtil.getResultLines(pr).forEach(line->{
				Console.log(line);
			});
		});
		
	}

}
