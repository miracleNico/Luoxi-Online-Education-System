package com.luoxi;

import java.io.File;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;

public class 万桥音频加密处理 {

	public static void main(String[] args) {
		String encryptBasePath = "F:/万桥/all-new-encrypt"; 
		FileUtil.loopFiles(FileUtil.file("F:/万桥/all-new"),1 , file->{return file.isDirectory();}).forEach(resourceFile->{
			File zipFile = FileUtil.loopFiles(resourceFile, 1, file->{return file.getName().contains("zip");}).get(0);
			String resourceId = StrUtil.removeSuffix(zipFile.getName(), ".zip");
			Console.log(resourceFile.getPath());
			FileUtil.loopFiles(resourceFile.getPath()+"/audio").forEach(mp3File->{
				FileUtil.writeBytes(SecureUtil.aes("HgPam8KENC9iy1Wu".getBytes()).encrypt(FileUtil.readBytes(mp3File)), encryptBasePath+"/"+resourceId+"/audio_encrypt/"+mp3File.getName());
			});
		});
	}
	
}
