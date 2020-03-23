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


public class Downloader  {//�����ļ��İ�����

	public String get(String url) {
		//�ж��������
		String suffix = url.split("\\.")[url.split("\\.").length-1].toLowerCase();//��ȡurl�ĺ�׺��ע���Ե�ָ���Ҫת��
		String type;
		if("jpg".equals(suffix) || "jpeg".equals(suffix)|| "png".equals(suffix)|| "gif".equals(suffix) ) {//�����׺�����ϵ�����
			type = "IMAGE";//���ж�����������ͼƬ
		}else if("pdf".equals(suffix)) {//�����׺����������
			type = "PDF";
		}else {//��������ǣ����׳��쳣
			try {new Exception("������������");}catch(Exception e) {e.printStackTrace();}
			return null;
		}
		
		//�����ļ��У���Ҫ�ж��ļ����Ƿ��Ѿ�����
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
		File folder = new File("F://ServerData//"+type+"//"+sdf.format(date));
		if(!folder.exists() && !folder.isDirectory()) {//����ļ��в����ڣ��򴴽��ļ���
			folder.mkdirs();
			Log.log("�����ļ��У�"+folder.getPath());
		}else {
			Log.log("�ļ����Ѵ���");
		}
		
		//�����ļ��������ļ��У���Ҫ�ж��ļ��Ƿ��Ѿ�����
		String fileName = url.split("/")[url.split("/").length-1].toLowerCase();
		File file = new File(folder.getPath()+"//"+fileName);
		if(!file.exists()) {//������ļ������ڣ��򴴽��ļ�
			try {
				
				URL u = new URL(url);
				URLConnection conn = u.openConnection();
				InputStream in = conn.getInputStream();
				FileOutputStream fos = new FileOutputStream(file);
				byte [] buf = new byte[1];
				int length = 0;
				Log.log("��ʼ���أ�"+url);
				while((length = in.read(buf, 0, buf.length))!=-1) {
					fos.write(buf, 0, buf.length);
				}
				fos.flush();
				
				in.close();
				fos.close();
				Log.log("�������");
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.log("DownloadPNG����"+url+"ʧ��");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.log("�洢·������");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.log("������");
			}
		}else {
			Log.log("���ļ��Ѵ��ڣ�δ��ʼ���ء�");
		}
		
		return file.getPath();
	}
	public static void main(String[] args) {//���Գ���
		Downloader dl = new Downloader();
		try {
			//��������ͼƬ
			String imagePath = dl.get("http://epaper.yzwb.net/images/2020-03/16/A01/20200316A01_brief.jpg");
			System.out.println(imagePath);
			
			//��������pdf
			String pdfPath = dl.get("http://epaper.yzwb.net/images/2020-03/18/A01/20200318A01_pdf.pdf");
			System.out.println(pdfPath);
			
			//�������ش����ַ
			String errorPath = dl.get("http://epaper.yzwb.net/images/2020-03/16/A01/20200316A01_pdf.pf");
			System.out.println(errorPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.log("������������");
			e.printStackTrace();
		}
	}

}
