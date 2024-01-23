package com.luoxi;

import java.io.File;
import java.io.FileFilter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.luoxi.constant.ConstEduResourceMediaType;
import com.luoxi.constant.ConstEduResourceType;
import com.luoxi.model.EduResource;
import com.luoxi.server.dao.EduResourceMapper;
import com.luoxi.utils.CosUtil;
import com.luoxi.vo.WanqiaoResourceVo;
import com.luoxi.vo.WanqiaoResourceVo.WanqiaoResourceJson;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

@SpringBootTest
class 万桥资源数据处理 {
	
	@Autowired CosUtil cosUtil;
	@Autowired EduResourceMapper eduResourceMapper;
	
	public static void main(String[] args) throws Exception{
		new 万桥资源数据处理().go();
	}
	
	@Test
	void go() {
		String rootPath = "F:\\万桥\\all-new\\";
		File excelFile = FileUtil.file(rootPath+"万桥.xlsx");
		ExcelReader excelReader = ExcelUtil.getReader(excelFile,"资源表3");
		List<WanqiaoResourceVo> excelResources = excelReader.read(0,1, excelReader.getRowCount(), WanqiaoResourceVo.class);
		excelReader.close();
		
		for (WanqiaoResourceVo wanqiaoResource : excelResources) {
			wanqiaoResource.setTitle(wanqiaoResource.getTitle().trim());
			
			System.out.println(wanqiaoResource);
			
			List<WanqiaoResourceVo> resourcesPages = new ArrayList<WanqiaoResourceVo>();
			String resourcePath = rootPath+(wanqiaoResource.getTitle().replace("?", "_").replace(":", "_"))+"/";
			
			/**
			FileUtil.mkdir(resourcePath);
			File zipFile = FileUtil.file(rootPath+wanqiaoResource.getId()+".zip");
			if(!FileUtil.exist(zipFile)) {
				zipFile = FileUtil.file(rootPath+wanqiaoResource.getTitle()+".zip");
				if(!FileUtil.exist(zipFile)) {
					//System.out.println("zip is null:"+wanqiaoResource);
					continue;
				}
			}
			System.out.println(wanqiaoResource);
			ZipUtil.unzip(zipFile, FileUtil.file(resourcePath),Charset.forName("GBK"));
			*/
			
			if(!FileUtil.exist(resourcePath)) {
				resourcePath = rootPath+("The "+wanqiaoResource.getTitle().replace("?", "_").replace(":", "_"))+"/";
				if(!FileUtil.exist(resourcePath)) {
					System.out.println("------EXCEL 资源对应的文件未找到---:"+wanqiaoResource.getTitle());
					continue;
				}
			}
			
			FileUtil.del(resourcePath+SecureUtil.md5(wanqiaoResource.getId())+".json");
			
			List<File> files = FileUtil.loopFiles(FileUtil.file(resourcePath), new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					return pathname.isFile() && (
							pathname.getName().endsWith(".json") || 
							pathname.getName().endsWith(".mp3") || 
							pathname.getName().endsWith(".jpg") ||
							pathname.getName().endsWith(".jpeg") ||
							pathname.getName().endsWith(".png")
							);
				}
			});
			/**
			for (File file : files) {
				if(file.getName().endsWith(".json")) {
					jsonObject = JSONUtil.readJSONObject(file, Charset.defaultCharset());
				}
			}*/
			
			JSONObject jsonObject = files.stream()
					.filter(file-> file.getName().endsWith(".json"))
					.findFirst()
					.map(file->JSONUtil.readJSONObject(file, Charset.defaultCharset()))
					.orElse(null);
			
			wanqiaoResource.setResourceId(SecureUtil.md5(wanqiaoResource.getId()));
			wanqiaoResource.setResourceType(ConstEduResourceType.RESOURCE.name());
			wanqiaoResource.setMediaType(ConstEduResourceMediaType.IMGBOOK.name());
			wanqiaoResource.setIntroduction(jsonObject.getJSONObject("metadatum").getStr("description"));
			if(StrUtil.isBlank(wanqiaoResource.getIntroduction())) {
				wanqiaoResource.setIntroduction(jsonObject.getStr("featured_text"));
			}
			wanqiaoResource.setMinAge(Integer.parseInt(String.valueOf(jsonObject.getJSONObject("metadatum").getJSONArray("age_groups").get(0)).split("-")[0]));
			wanqiaoResource.setMaxAge(Integer.parseInt(String.valueOf(jsonObject.getJSONObject("metadatum").getJSONArray("age_groups").get(0)).split("-")[1]));
			wanqiaoResource.setCoverUrl(findFile(files,jsonObject.getStr("cover_art_filename")).getPath());
			
			WanqiaoResourceJson jsonVo = new WanqiaoResourceJson();
			BeanUtil.copyProperties(wanqiaoResource, jsonVo);
			jsonVo.setResourceTitle(wanqiaoResource.getTitle());
			jsonVo.setCoverUrl("JPGs/"+FileUtil.file(jsonVo.getCoverUrl()).getName());
			
			List<Map<Object, Object>> jsonPageVos = new ArrayList<Map<Object, Object>>();
			//pages
			JSONArray jsonArray = jsonObject.getJSONArray("pages");
			for (int i = 0; i < jsonArray.size(); i++) {
				
				JSONObject jsonPage = JSONUtil.parseObj(jsonArray.get(i));
				
				//System.out.println(jsonPage);
				
				WanqiaoResourceVo wanqiaoResourcePage = new WanqiaoResourceVo();
				wanqiaoResourcePage.setId(jsonPage.getStr("_id"));
				wanqiaoResourcePage.setResourceId(SecureUtil.md5(wanqiaoResourcePage.getId()));
				wanqiaoResourcePage.setTitle(wanqiaoResource.getTitle());
				wanqiaoResourcePage.setContent(jsonPage.getStr("text"));
				
				wanqiaoResourcePage.setCoverUrl(findFile(files,jsonPage.getStr("background_image_filename")).getPath());
				if(findFile(files,jsonPage.getStr("voice_over_filename"))!=null) {
					wanqiaoResourcePage.setFileUrl(findFile(files,jsonPage.getStr("voice_over_filename")).getPath());
				}
				wanqiaoResourcePage.setParentId(wanqiaoResource.getResourceId());
				wanqiaoResourcePage.setResourceType(ConstEduResourceType.PAGE.name());
				resourcesPages.add(wanqiaoResourcePage);
				
				Map<Object, Object> jsonPageVo = MapUtil.builder()
				.put("resourceId", wanqiaoResourcePage.getResourceId())
				.put("coverUrl", "JPGs/"+FileUtil.file(wanqiaoResourcePage.getCoverUrl()).getName())
				.put("content", wanqiaoResourcePage.getContent())
				.build();
				
				if(FileUtil.exist(wanqiaoResourcePage.getFileUrl())) {
					jsonPageVo.put("fileUrl", "audio/"+FileUtil.file(wanqiaoResourcePage.getFileUrl()).getName());
				}
				jsonPageVos.add(jsonPageVo);
			}
			wanqiaoResource.setPages(resourcesPages);
			jsonVo.setPages(jsonPageVos);
			
			FileWriter fileWriter = new FileWriter(resourcePath+wanqiaoResource.getResourceId()+".json");
			File jsonFile = fileWriter.write(JSONUtil.toJsonPrettyStr(jsonVo));
			
			List<File> zipFiles = Arrays.asList(jsonFile,FileUtil.file(resourcePath+"JPGs"),FileUtil.file(resourcePath+"audio"));
			File bookZipFile = ZipUtil.zip(FileUtil.file(resourcePath+wanqiaoResource.getResourceId()+".zip"),true,ArrayUtil.toArray(zipFiles, File.class));
			wanqiaoResource.setFileUrl(bookZipFile.getPath());
			
		}
		
		
		System.out.println("------------------------ db ------------------------------");
		
		/** db */
		for (WanqiaoResourceVo wanqiaoResource : excelResources) {
			
			if(StrUtil.isBlank(wanqiaoResource.getResourceId())) {
				continue;
			}
			
			System.out.println(wanqiaoResource);
			
			EduResource eduResource = new EduResource();
			BeanUtil.copyProperties(wanqiaoResource, eduResource);
			eduResource.setResourceTitle(wanqiaoResource.getTitle());
			eduResource.setThirdId(wanqiaoResource.getId());
			eduResource.setBusinessId("4505b849a9fa4d2f8833dc45a0f11f9c");
			eduResource.setMinAge(Integer.parseInt(wanqiaoResource.get年龄().split("~")[0]));
			eduResource.setMaxAge(Integer.parseInt(wanqiaoResource.get年龄().split("~")[1]));
			if(StrUtil.isNotBlank(eduResource.getCoverUrl())) {
				eduResource.setCoverUrl(cosUtil.filterUrlDomain(cosUtil.upload("/wanq/"+eduResource.getResourceId()+"/JPGs/"+FileUtil.file(eduResource.getCoverUrl()).getName(), FileUtil.file(eduResource.getCoverUrl()))));
			}
			if(StrUtil.isNotBlank(eduResource.getFileUrl())) {
				eduResource.setFileUrl(cosUtil.filterUrlDomain(cosUtil.upload("/wanq/"+eduResource.getResourceId()+"/"+FileUtil.file(eduResource.getFileUrl()).getName(), FileUtil.file(eduResource.getFileUrl()))));				
			}
			eduResourceMapper.insertSelective(eduResource);
			
			for (WanqiaoResourceVo wanqiaoResourcePage : wanqiaoResource.getPages()) {
				EduResource eduResourcePage = new EduResource();
				BeanUtil.copyProperties(wanqiaoResourcePage, eduResourcePage);
				eduResourcePage.setResourceTitle(wanqiaoResourcePage.getTitle());
				eduResourcePage.setThirdId(wanqiaoResourcePage.getId());
				eduResourcePage.setBusinessId(eduResource.getBusinessId());
				if(StrUtil.isNotBlank(eduResourcePage.getCoverUrl())) {
					eduResourcePage.setCoverUrl(cosUtil.filterUrlDomain(cosUtil.upload("/wanq/"+eduResource.getResourceId()+"/JPGs/"+FileUtil.file(eduResourcePage.getCoverUrl()).getName(), FileUtil.file(eduResourcePage.getCoverUrl()))));					
				}
				if(StrUtil.isNotBlank(eduResourcePage.getFileUrl())) {
					eduResourcePage.setFileUrl(cosUtil.filterUrlDomain(cosUtil.upload("/wanq/"+eduResource.getResourceId()+"/audio/"+FileUtil.file(eduResourcePage.getFileUrl()).getName(), FileUtil.file(eduResourcePage.getFileUrl()))));					
				}
				System.out.println(JSONUtil.toJsonStr(eduResourcePage));
				eduResourceMapper.insertSelective(eduResourcePage);
			}
		}
	}
	
	private File findFile(List<File> files,String fileName) {
		for (File file : files) {
			if(file.getName().equals(fileName)){
				return file;
			}
		}
		return null;
	}

}
