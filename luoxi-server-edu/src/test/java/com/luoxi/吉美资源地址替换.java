package com.luoxi;

import java.io.File;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

public class 吉美资源地址替换 {
	
	public static void main(String[] args) {
		ExcelWriter excelWriter = ExcelUtil.getWriter("F:/吉美/吉美资源地址替换/吉美资源地址替换.xlsx","吉美资源地址替换");
		ExcelReader excelReader = ExcelUtil.getReader("F:/吉美/吉美资源地址替换/吉美资源地址替换.xlsx");
		for (int i = 1; i < excelReader.getRowCount(); i++) {
			String coverUrl = (String) excelReader.readCellValue(10, i);
			String fileUrl = (String) excelReader.readCellValue(12, i);
			if(StrUtil.isNotBlank(coverUrl)) {
				String path = "/jm" + StrUtil.removePrefix(coverUrl, URLUtil.getHost(URLUtil.url(coverUrl)).toString());
				try {
					File localFile = FileUtil.file("F:/吉美/吉美资源地址替换"+path);
					if(!FileUtil.exist(localFile)) {
						HttpUtil.downloadFile(coverUrl, localFile);
					}
				} catch (Exception e) {
					Console.error(coverUrl);
				}
				excelWriter.writeCellValue(11, i, path);
			}
			if(StrUtil.isNotBlank(fileUrl)) {
				String path = "/jm" + StrUtil.removePrefix(fileUrl, URLUtil.getHost(URLUtil.url(fileUrl)).toString());
				try {
					File localFile = FileUtil.file("F:/吉美/吉美资源地址替换"+path);
					if(!FileUtil.exist(localFile)) {
						HttpUtil.downloadFile(fileUrl, localFile);
					}
				} catch (Exception e) {
					Console.error(coverUrl);
				}
				excelWriter.writeCellValue(13, i, path);						
			}
			Console.log("{} {}",i,excelReader.readCellValue(0, i));
		}
		excelWriter.flush();
		excelWriter.close();
		Console.log("done!");
	}
	
}
