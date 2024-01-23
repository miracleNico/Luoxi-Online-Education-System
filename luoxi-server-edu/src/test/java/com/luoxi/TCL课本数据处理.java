package com.luoxi;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.luoxi.vo.BookVo;
import com.luoxi.vo.BookVo.BookPageContentVo;
import com.luoxi.vo.BookVo.BookPageVo;
import com.luoxi.vo.ExcelPageVo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

@SpringBootTest
class TCL课本数据处理 {
	
	public static void main(String[] args) throws Exception{
		new TCL课本数据处理().go();
	}
	
	@Test
	void 音频文件名处理() {
		File[] files = FileUtil.ls("F:\\tcl课本\\zipAll\\RJB_3B\\audio");
		for (File file : files) {
			String[] strs = StrUtil.removeSuffix(file.getName(), ".mp3").split("_");
			String fileName = "RJB_3B-"+(Integer.valueOf(strs[1])-1)+"-"+strs[2]+".mp3";
			String newFile = "F:\\tcl课本\\zipAll\\RJB_3B\\audio\\"+fileName;
			if(FileUtil.exist(newFile)) {
				System.out.println("已存在:"+newFile);
			}
			FileUtil.rename(file, fileName, false, true);
		}
	}
	
	@Test
	void 图片文件名处理() {
		List<File> files = FileUtil.loopFiles("F:\\tcl课本\\zipAll\\RJB_3B\\pic", new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.isFile() && (
						pathname.getName().endsWith(".jpg")
						);
			}
		});
		for (File file : files) {
			String pageNum = StrUtil.removeSuffix(file.getName(), ".jpg");
			String newName = (Integer.valueOf(pageNum)-1)+".jpg";
			String newFile = "F:\\tcl课本\\zipAll\\RJB_3B\\pic\\"+newName;
			if(FileUtil.exist(newFile)) {
				System.out.println("已存在:"+newFile);
			}
			FileUtil.rename(file, newName, false, true);
		}
	}
	
	@Test
	void go() {
		String rootPath = "F:/tcl课本/zipAll";
		List<File> files = FileUtil.loopFiles(FileUtil.file(rootPath),1,null);
		for (File bookFile : files) {
			String bookId = bookFile.getName();
			System.out.println(bookId);
			String bookPath = bookFile.getPath();
			String audioPath = bookPath+"/audio";
			String featurePath = bookPath+"/feature";
			String coordinatesPath = bookPath+"/coordinates";
			File coverFile = FileUtil.file(bookPath+"/"+"cover.jpg");
			File excelFile = FileUtil.file(bookPath+"/"+bookId+".xlsx");
			
			//读取课本信息
			ExcelReader readerBook = ExcelUtil.getReader(excelFile,"book");
			List<BookVo> bookVos = readerBook.read(1,2, 2, BookVo.class);
			readerBook.close();
			BookVo bookVo = CollectionUtil.isNotEmpty(bookVos)?bookVos.get(0):null;
			bookVo.setBookName(bookVo.getPressAbbr()+bookVo.getBookName()+bookVo.getGrade()+bookVo.getVolume());
			
			//读取内页信息
			ExcelReader readerPage = ExcelUtil.getReader(excelFile,"page");
			List<ExcelPageVo> excepageVos = readerPage.read(1,2, readerPage.getRowCount(), ExcelPageVo.class);
			readerPage.close();
			
			Map<Integer, BookPageVo> mapPage = new HashMap<Integer, BookPageVo>();
			Map<Integer, Document> mapPageDocument = new HashMap<Integer, Document>();
			
			//遍历excel内页数据
			for (ExcelPageVo excelPageVo : excepageVos) {
				
				//读取单页某项数据
				Integer pageNum = excelPageVo.getPage();
				Integer contentNo = excelPageVo.getContentNo();
				String section = excelPageVo.getSection();
				String content = excelPageVo.getContent();
				String translation = excelPageVo.getTranslation();
				Integer audioType = excelPageVo.getAudioType();
				
				if(contentNo==0)continue;
				
				//获取单页xml数据
				Document document = mapPageDocument.get((pageNum-1));
				if(BeanUtil.isEmpty(document)) {
					document = XmlUtil.readXML(coordinatesPath+"/"+pageNum+".xml");
					mapPageDocument.put((pageNum-1), document);
				}
				
				//读取单页某坐标数据
				Element elementSize = XmlUtil.getElementByXPath("//size", document);
				Element elementBndbox = XmlUtil.getElementByXPath("//object/name[text()='"+contentNo+"']/following-sibling::bndbox", document);
				String width = elementSize.getElementsByTagName("width").item(0).getTextContent();
				String height = elementSize.getElementsByTagName("height").item(0).getTextContent();
				String xmin = elementBndbox.getElementsByTagName("xmin").item(0).getTextContent();
				String ymin = elementBndbox.getElementsByTagName("ymin").item(0).getTextContent();
				String xmax = elementBndbox.getElementsByTagName("xmax").item(0).getTextContent();
				String ymax = elementBndbox.getElementsByTagName("ymax").item(0).getTextContent();
				
				
				//设置单页数据
				BookPageVo bookPageVo = mapPage.get((pageNum-1));
				if(BeanUtil.isEmpty(bookPageVo)) {
					bookPageVo = new BookPageVo()
							.setPage((pageNum-1))
							.setTitle(excelPageVo.getTitle())
							.setWidth(Integer.parseInt(width))
							.setHeight(Integer.parseInt(height))
							;
				}
				
				//设置单页某坐标数据
				BookPageContentVo bookPageContentVo = new BookPageContentVo()
						.setContentNo(contentNo)
						.setXmin(Integer.parseInt(xmin))
						.setYmin(Integer.parseInt(ymin))
						.setXmax(Integer.parseInt(xmax))
						.setYmax(Integer.parseInt(ymax))
						.setSection(section)
						.setContent(content)
						.setTranslation(translation)
						.setAudioFile(bookId+"-"+(pageNum-1)+"-"+contentNo+".mp3")
						.setAudioType(audioType)
						;
				
				bookPageVo.getContents().add(bookPageContentVo);
				
				mapPage.put((pageNum-1), bookPageVo);
			}
			
			
			Iterator<Integer> it = mapPage.keySet().iterator();
			while(it.hasNext()){
				BookPageVo bookPageVo = mapPage.get(it.next());
				
				//每页音频打包-上传
				List<File> audioFiles = new ArrayList<File>();
				for (BookPageContentVo bookPageContentVo : bookPageVo.getContents()) {
					File auioFile = FileUtil.file((bookPath+"/audio/"+bookPageContentVo.getAudioFile()));
					if(!FileUtil.exist(auioFile))continue;
					audioFiles.add(auioFile);
				}
				File audioPageZipFile = ZipUtil.zip(FileUtil.file(bookPath+"/audios/"+bookId+"-"+bookPageVo.getPage()+".zip"), false, ArrayUtil.toArray(audioFiles, File.class));
				bookPageVo.setAudioZipUrl("/books/"+bookId+"/audios"+"/"+FileUtil.getName(audioPageZipFile));
				bookVo.getPages().add(bookPageVo);
			}
			
			//生成课本json文件
			FileWriter writer = new FileWriter(FileUtil.newFile(bookPath+"/"+bookId+".json"));
			bookVo.setDetail(null).setGrade(null).setPress(null).setPressAbbr(null).setVolume(null);
			File jsonFile = writer.write(JSONUtil.toJsonPrettyStr(bookVo));
			
			//课本打包
			//List<File> zipFiles = Arrays.asList(FileUtil.file(featurePath),jsonFile,coverFile);
			//ZipUtil.zip(FileUtil.file(bookPath+"/"+bookId+".zip"),true,ArrayUtil.toArray(zipFiles, File.class));
		}
		
	}

}
