package com.ljb.epaper.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Downloader  {//下载文件的帮助类

	public String get(String url) {
		//判断下载类别
		String suffix = url.split("\\.")[url.split("\\.").length-1].toLowerCase();//截取url的后缀，注意以点分割需要转义
		String type;
		if("jpg".equals(suffix) || "jpeg".equals(suffix)|| "png".equals(suffix)|| "gif".equals(suffix) ) {//如果后缀是以上的类型
			type = "IMAGE";//则判断下载类型是图片
		}else if("pdf".equals(suffix)) {//如果后缀是以上类型
			type = "PDF";
		}else {//如果都不是，则抛出异常
			try {new Exception("下载类型有误");}catch(Exception e) {e.printStackTrace();}
			return null;
		}
		
		//创建文件夹，需要判断文件夹是否已经存在
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
		File folder = new File("F://ServerData//"+type+"//"+sdf.format(date));
		if(!folder.exists() && !folder.isDirectory()) {//如果文件夹不存在，则创建文件夹
			folder.mkdirs();
			Log.log("创建文件夹："+folder.getPath());
		}else {
			Log.log("文件夹已存在");
		}
		
		//下载文件到上述文件夹，需要判断文件是否已经存在
		String fileName = url.split("/")[url.split("/").length-1].toLowerCase();
		File file = new File(folder.getPath()+"//"+fileName);
		if(!file.exists()) {//如果该文件不存在，则创建文件
			try {
				
				URL u = new URL(url);
				URLConnection conn = u.openConnection();
				InputStream in = conn.getInputStream();
				FileOutputStream fos = new FileOutputStream(file);
				byte [] buf = new byte[1];
				int length = 0;
				Log.log("开始下载："+url);
				while((length = in.read(buf, 0, buf.length))!=-1) {
					fos.write(buf, 0, buf.length);
				}
				fos.flush();
				
				in.close();
				fos.close();
				Log.log("下载完成");
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.log("DownloadPNG：打开"+url+"失败");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.log("存储路径错误");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.log("流错误");
			}
		}else {
			Log.log("该文件已存在，未开始下载。");
		}
		
		return file.getPath();
	}
	public static void main(String[] args) {//测试程序
		Downloader dl = new Downloader();
		try {
			//测试下载图片
			String imagePath = dl.get("http://epaper.yzwb.net/images/2020-03/16/A01/20200316A01_brief.jpg");
			System.out.println(imagePath);
			
			//测试下载pdf
			String pdfPath = dl.get("http://epaper.yzwb.net/images/2020-03/18/A01/20200318A01_pdf.pdf");
			System.out.println(pdfPath);
			
			//测试下载错误地址
			String errorPath = dl.get("http://epaper.yzwb.net/images/2020-03/16/A01/20200316A01_pdf.pf");
			System.out.println(errorPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.log("下载类型有误");
			e.printStackTrace();
		}
	}

}
