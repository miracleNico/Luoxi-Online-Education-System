package com.luoxi;

import java.io.File;
import java.io.FileFilter;
import java.util.List;
import java.util.Map;

import org.springframework.boot.test.context.SpringBootTest;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

@SpringBootTest
class 赶考课本特征文件提取 {
	
	
	public static void main(String[] args) throws Exception{
		String accessToken = "030fb89abe498fd9649241412d88a167.7828625.77d9c676bd346effb2b0a0e08f41a7fb";
		int pageIndex = 1;
		while (true) {
			System.out.println("----------------"+pageIndex+"-------------------");
			Map<String, Object> paramMap = MapUtil.newHashMap();
			paramMap.put("pageIndex", pageIndex);
			paramMap.put("pageSize", 30);
			paramMap.put("accessToken", accessToken);
			String resp = HttpUtil.post("https://diandu.api.gankao.com/rest/opencontent/bookList", paramMap);
			
			JSONObject respJsonObject = new JSONObject(resp);
			if(respJsonObject.getInt("code")==0) {
				JSONArray jsonArray = respJsonObject.get("result",JSONObject.class).get("bookList",JSONArray.class);
				if(jsonArray.size()==0)break;
				for (Object object : jsonArray) {
					JSONObject bookJsonObject = (JSONObject)object;
					String bookId = bookJsonObject.getStr("bookId");
					System.out.println(bookId);
					String bookPath = "F:\\赶考课本特征文件\\"+bookId;
					FileUtil.del(bookPath);
					File bookFile = FileUtil.mkdir(bookPath);
					
					paramMap = MapUtil.newHashMap();
					paramMap.put("bookId", bookId);
					paramMap.put("accessToken", accessToken);
					resp = HttpUtil.post("https://diandu.api.gankao.com/rest/opencontent/bookInfo", paramMap);
					respJsonObject = new JSONObject(resp);
					
					if(respJsonObject.getInt("code")==0) {
						bookJsonObject = respJsonObject.get("result",JSONObject.class).get("bookInfo",JSONObject.class);
						String insidePageFeature = bookJsonObject.getStr("insidePageFeature");
						if(StrUtil.isNotBlank(insidePageFeature)) {
							String bookZipPath = bookPath+"\\"+FileUtil.getName(insidePageFeature);
							
							HttpUtil.downloadFile(insidePageFeature, bookPath);
							
							ZipUtil.unzip(bookZipPath, bookPath);
							
							List<File> featureFiles = FileUtil.loopFiles(bookFile, new FileFilter() {
								@Override
								public boolean accept(File pathname) {
									return pathname.isFile() && (
											pathname.getName().endsWith(".dat")
											);
								}
							});
							for (File featureFile : featureFiles) {
								FileUtil.move(featureFile, bookFile, true);
							}
							FileUtil.del(bookZipPath);
							FileUtil.del(bookPath+"\\"+StrUtil.removeSuffix(FileUtil.getName(insidePageFeature), ".zip"));
						}
					}
				}
			}
			pageIndex ++;
			Thread.sleep(1000);
		}
	}
	
	
}
