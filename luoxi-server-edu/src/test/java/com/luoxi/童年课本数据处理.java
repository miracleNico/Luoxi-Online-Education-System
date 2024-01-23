package com.luoxi;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.luoxi.vo.BookVo;
import com.luoxi.vo.BookVo.BookPageContentVo;
import com.luoxi.vo.BookVo.BookPageVo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.extra.pinyin.PinyinUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

@SpringBootTest
class 童年课本数据处理 {
	
	public static void main(String[] args) throws Exception{
		//new 童年课本数据处理().go();
	}
	
	@Test
	void go() {
		String rootPath = "F:/童年小样/zipAll";
		List<File> files = FileUtil.loopFiles(FileUtil.file(rootPath),1,null);
		for (File bookFile : files) {
			
			String bookSourcePath = bookFile.getPath();
			
			System.out.println(bookSourcePath);
			String bookName = bookFile.getName();
			String bookId = PinyinUtil.getFirstLetter(bookName.replace(" ", ""), "").toUpperCase();
			String bookPath = bookSourcePath+"/"+bookId;
			FileUtil.del(bookPath);
			
			BookVo bookVo = new BookVo();
			bookVo.setBookId(bookId);
			bookVo.setBookName(bookName);
			bookVo.setSubject(bookName.contains("英语")?"英语":null);
			
			//特征文件目录
			FileUtil.mkdir(bookPath+"/feature");
			
			//图片目录
			FileUtil.copy(bookSourcePath+"/图片", bookPath, true);
			File picFile = FileUtil.rename(FileUtil.file(bookPath+"/图片"), "pic", false, true);
			String picPath = picFile.getPath();
			
			//封面图
			File coverFile = FileUtil.file(bookPath+"/cover.jpg");
			FileUtil.move(FileUtil.file(picPath+"/封面.jpg"), coverFile, true);
			FileUtil.copy(coverFile.getPath(), "F:/童年小样/model/"+bookId+".jpg", true);
			Integer width = ImgUtil.read(coverFile).getWidth();
			Integer height = ImgUtil.read(coverFile).getHeight();
			
			//特征图片
			FileWriter writer = FileWriter.create(FileUtil.file(picPath+"/page.txt"));
			StringBuffer sb = new StringBuffer();
			for (File file : FileUtil.loopFiles(picPath)) {
				sb.append(file.getName()).append("\n");
			};
			writer.append(StrUtil.removeSuffix(sb.toString(), "\n"));
			
			//音频目录
			FileUtil.copy(bookSourcePath+"/声音", bookPath, true);
			File audioFile = FileUtil.rename(FileUtil.file(bookPath+"/声音"), "audio", false, true);
			String audioPath = audioFile.getPath();
			
			//读取excel
			File excelFile = FileUtil.file(bookSourcePath+"/文本.xls");
			ExcelReader readerBook = ExcelUtil.getReader(excelFile,"文本");
			List<List<Object>> list = readerBook.read(0);
			readerBook.close();
			Map<Integer, BookPageVo> mapPage = new HashMap<Integer, BookPageVo>();
			
			for (List<Object> rows : list) {
				String pageStr = rows.get(0).toString();
				Integer page = Convert.toInt(StrUtil.removePrefix(StrUtil.removePrefix(pageStr, "C"),"A"));
				Integer pageNum = Convert.toInt(StrUtil.removePrefix(rows.get(1).toString(),"BOX-"));
				String xyStr = rows.get(2).toString().replace(" ", "");
				Integer xmin = Convert.toInt(xyStr.split(",")[0]);
				Integer ymin = Convert.toInt(xyStr.split(",")[1]);
				Integer xmax = Convert.toInt(xyStr.split(",")[2]);
				Integer ymax = Convert.toInt(xyStr.split(",")[3]);
				String content = rows.get(3).toString();
				String audioFileUrl = rows.get(4).toString();
				String translation = null;
				String audioCNFileUrl = null;
				if(rows.size()>=6) {
					translation = ObjectUtil.isNotNull(rows.get(5))?rows.get(5).toString():null;					
				}
				if(rows.size()>=7) {
					audioCNFileUrl = ObjectUtil.isNotNull(rows.get(6))?rows.get(6).toString():null;					
				}
				
				//内页
				if(BeanUtil.isEmpty(mapPage.get(page))) {
					mapPage.put(page, new BookPageVo().setPage(page).setTitle(content).setWidth(width).setHeight(height));
				}
				BookPageVo bookPageVo = mapPage.get(page);
				List<BookPageContentVo> pageContentVos = bookPageVo.getContents();
				if(CollUtil.isEmpty(pageContentVos)) {
					pageContentVos = new ArrayList<BookPageContentVo>();
				}
				pageContentVos.add(new BookPageContentVo()
						.setContentNo(pageNum)
						.setContent(content)
						.setTranslation(translation)
						.setXmin(xmin)
						.setYmin(ymin)
						.setXmax(xmax)
						.setYmax(ymax)
						.setAudioFile(audioFileUrl)
						.setAudioCNFile(audioCNFileUrl)
						);
				bookPageVo.setContents(pageContentVos);
				mapPage.put(page, bookPageVo);
			}
			
			
			Iterator<Integer> it = mapPage.keySet().iterator();
			while(it.hasNext()){
				BookPageVo bookPageVo = mapPage.get(it.next());
				
				//每页音频打包-上传
				Set<File> audioFiles = new HashSet<File>();
				for (BookPageContentVo bookPageContentVo : bookPageVo.getContents()) {
					File auioFile = FileUtil.file((audioPath+"/"+bookPageContentVo.getAudioFile()));
					if(!FileUtil.exist(auioFile))continue;
					audioFiles.add(auioFile);
				}
				
				File audioPageZipFile = ZipUtil.zip(FileUtil.file(bookPath + "/audios/"+bookId+"-"+bookPageVo.getPage()+".zip"), false, ArrayUtil.toArray(audioFiles, File.class));
				bookPageVo.setAudioZipUrl("/books/"+bookId+"/audios"+"/"+FileUtil.getName(audioPageZipFile));
				bookVo.getPages().add(bookPageVo);
			}
			
			//生成课本json文件
			writer = new FileWriter(FileUtil.newFile(bookPath+"/"+bookId+".json"));
			writer.write(JSONUtil.toJsonPrettyStr(bookVo));
		}
	}

}
