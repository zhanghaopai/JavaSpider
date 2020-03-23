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

public class Spiders extends TimerTask {// ���������ʵ�֣�ÿ�춨ʱ��ȡָ�����ݣ����������ݿ⣬�����û�����
	private URLSBuilder builder = new URLSBuilder();
	private Downloader dl = new Downloader();
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//�����ݿ��л�ȡ���е��ӱ�ֽ����ַ
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session s = sf.openSession();
		s.beginTransaction();
		
		Query q = s.createQuery("select new Paper(name,address) from Paper");
		List<Paper> paperList = q.list();
		for(Paper paper:paperList) {//����ÿһ�����ӱ�����ҳ��ͨ���ж���ҳ��������Ӧ��ͼƬ��ַ�������������ݿ�
			Log.log("ȡ��"+paper.getName()+"����ҳ��ַ��"+paper.getAddress());
			
			List<String> pngUrls = builder.buildPNGAddress(paper.getAddress());//����ñ�ֽ���а��������ͼ��ַ
			List<String> pdfUrls = builder.buildPDFAddress(paper.getAddress());//����ñ�ֽ���а����pdf���ص�ַ
			
			try {
				for(int i = 0;i<pngUrls.size();i++) {
					Page page = new Page();
					
					page.setName(paper.getName());//���ð���������ֽ������
					page.setTime(new Date());//���ý���ʱ��
					page.setPage_num(i+1+"");//���ð����
					page.setPng_url(pngUrls.get(i));//��������ͼ����ַ
					String pngPath = dl.get(pngUrls.get(i));//���ظ�ͼƬ�������ļ�Ŀ¼�ĵ�ַ
					
					Thread.sleep(2000);//��Ϣһ��
					
					page.setPng_position(pngPath);//��������ͼ���ص����Ե��ļ�ϵͳ��ַ
					page.setPdf_url(pdfUrls.get(i));//����pdf����ַ
					String pdfPath = dl.get(pdfUrls.get(i));//���ص����ԣ����������ص��ļ�ϵͳ�ĵ�ַ
					
					Thread.sleep(2000);//��Ϣһ��
					
					page.setPdf_position(pdfPath);//����pdf���ļ�ϵͳ�ĵ�ַ
					String HTMLPath = "F:\\ServerData\\HTML\\"+new SimpleDateFormat("YYYY-MM-dd").format(new Date())+"\\";//����ת����ϵ�html��Ŀ¼
					if(Convertor.PDFtoHTML(pdfPath, HTMLPath)) {//���ת���ɹ�
						HTMLPath += pdfPath.split("\\\\")[pdfPath.split("\\\\").length - 1].split("\\.")[0];//����ת����ϵ�Ŀ¼
						page.setHtml_position(HTMLPath);
					}
					s.save(page);//��ʱ��û����ȫ�����ݿ��У���û��commit
					Thread.sleep(2000);//��Ϣһ��
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			s.getTransaction().commit();//�ύ
			s.close();
			sf.close();
		}

	}
	public static void main(String[] args) {
		new Thread(new Spiders()).run();
	}

}
