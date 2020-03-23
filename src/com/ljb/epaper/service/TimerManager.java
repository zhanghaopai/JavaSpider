package com.ljb.epaper.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import com.ljb.epaper.utils.Log;

public class TimerManager {//�����Ե�ִ��Spiders����
	private static final long PERIOD_DAY = 24*60*60*1000;
	
	public TimerManager() {
		Calendar calender = Calendar.getInstance();
		
		//�ƶ���һ����ִ�з���
		calender.set(Calendar.HOUR_OF_DAY,8);//ÿ��8��
		calender.set(Calendar.MINUTE,0);
		calender.set(Calendar.SECOND,0);
		
		Date date = calender.getTime();//��ȡ��һ�ζ�ʱ�����ʱ�䣬δִ��
		
		if(date.before(new Date())) {//��һ��ִ�������ʱ���Ѿ����ˣ�ִ����һ�γ����ʱ���Ѿ���������8��
			date = this.addDay(date,1);
		}
		
		Log.log("��һ��ִ�У�ʱ��Ϊ��"+date.toString());//�����ж�����ҵ��˵�һ��ִ�е�ʱ�䣬�������Ϊ�����ʼִ����γ���ʱ��û��8��00am����ô��һ�ε�ִ��ʱ��Ϊ����8��00���������ʼʱ���Ѿ�����8��00am����ô��һ��ִ��ʱ��Ϊ����8��00am
		
		Timer timer = new Timer();
		
		Spiders task = new Spiders();//��������������
		
		timer.schedule(task, date, PERIOD_DAY);//�����������date��ʼ����PERIOD_DAYΪ����ִ��
		
		
	}
	public Date addDay(Date date, int num) { 
        Calendar startDT = Calendar.getInstance(); 
        startDT.setTime(date); 
        startDT.add(Calendar.DAY_OF_MONTH, num); 
        return startDT.getTime(); 
       }
	
	public static void main(String[] args) {
		new TimerManager();//����
	}
}
