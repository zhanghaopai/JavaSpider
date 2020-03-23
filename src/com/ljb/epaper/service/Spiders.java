package com.ljb.epaper.service;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;

import com.ljb.epaper.pojo.Page;
import com.ljb.epaper.pojo.Paper;
import com.ljb.epaper.utils.URLSBuilder;
import com.ljb.epaper.utils.Convertor;
import com.ljb.epaper.utils.Downloader;
import com.ljb.epaper.utils.Log;

public class Spiders extends TimerTask {// 网络爬虫的实现，每天定时爬取指定内容，并存入数据库，不与用户交互
	private URLSBuilder builder = new URLSBuilder();
	private Downloader dl = new Downloader();
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//从数据库中获取所有电子报纸的网址
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session s = sf.openSession();
		s.beginTransaction();
		
		Query q = s.createQuery("select new Paper(name,address) from Paper");
		List<Paper> paperList = q.list();
		for(Paper paper:paperList) {//遍历每一个电子报刊主页，通过判断主页，生成相应的图片地址，并保存至数据库
			Log.log("取出"+paper.getName()+"的主页地址："+paper.getAddress());
			
			List<String> pngUrls = builder.buildPNGAddress(paper.getAddress());//构造该报纸所有版面的缩略图地址
			List<String> pdfUrls = builder.buildPDFAddress(paper.getAddress());//构造该报纸所有版面的pdf下载地址
			
			try {
				for(int i = 0;i<pngUrls.size();i++) {
					Page page = new Page();
					
					page.setName(paper.getName());//设置版面所属报纸的名字
					page.setTime(new Date());//设置今日时间
					page.setPage_num(i+1+"");//设置版面号
					page.setPng_url(pngUrls.get(i));//设置缩略图的网址
					String pngPath = dl.get(pngUrls.get(i));//下载该图片并返回文件目录的地址
					
					Thread.sleep(2000);//休息一下
					
					page.setPng_position(pngPath);//设置缩略图下载到电脑的文件系统地址
					page.setPdf_url(pdfUrls.get(i));//设置pdf的网址
					String pdfPath = dl.get(pdfUrls.get(i));//下载到电脑，并返回下载到文件系统的地址
					
					Thread.sleep(2000);//休息一下
					
					page.setPdf_position(pdfPath);//设置pdf在文件系统的地址
					String HTMLPath = "F:\\ServerData\\HTML\\"+new SimpleDateFormat("YYYY-MM-dd").format(new Date())+"\\";//设置转换完毕的html的目录
					if(Convertor.PDFtoHTML(pdfPath, HTMLPath)) {//如果转换成功
						HTMLPath += pdfPath.split("\\\\")[pdfPath.split("\\\\").length - 1].split("\\.")[0];//设置转换完毕的目录
						page.setHtml_position(HTMLPath);
					}
					s.save(page);//此时还没有完全到数据库中，还没有commit
					Thread.sleep(2000);//休息一下
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			s.getTransaction().commit();//提交
			s.close();
			sf.close();
		}

	}
	public static void main(String[] args) {
		new Thread(new Spiders()).run();
	}

}
