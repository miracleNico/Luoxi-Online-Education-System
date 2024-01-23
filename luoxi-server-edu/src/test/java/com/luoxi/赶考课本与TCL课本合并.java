package com.luoxi;

import java.io.File;
import java.util.List;

import org.springframework.boot.test.context.SpringBootTest;

import cn.hutool.core.io.FileUtil;

@SpringBootTest
public class 赶考课本与TCL课本合并 {
	
	public static void main(String[] args) {
		String gankaoPath = "F:\\赶考训练封面图";
		String mergerPath = "F:\\赶考课本与TCL课本合并";
		List<File> gankaoList = FileUtil.loopFiles(gankaoPath);
		List<File> mergeList = FileUtil.loopFiles(mergerPath);
		for (File gankaoFile : gankaoList) {
			boolean b = false;
			for (File mergeFile : mergeList) {
				if(gankaoFile.getName().equals(mergeFile.getName())) {
					b = true;
					break;
				}
			}
			if(!b) {
				FileUtil.copy(gankaoFile.getPath(), mergerPath, false);
			}
		}
	}
	
}
