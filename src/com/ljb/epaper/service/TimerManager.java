package com.ljb.epaper.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import com.ljb.epaper.utils.Log;

public class TimerManager {//周期性的执行Spiders服务
	private static final long PERIOD_DAY = 24*60*60*1000;
	
	public TimerManager() {
		Calendar calender = Calendar.getInstance();
		
		//制定下一分钟执行方法
		calender.set(Calendar.HOUR_OF_DAY,8);//每天8点
		calender.set(Calendar.MINUTE,0);
		calender.set(Calendar.SECOND,0);
		
		Date date = calender.getTime();//获取第一次定时任务的时间，未执行
		
		if(date.before(new Date())) {//第一次执行任务的时间已经过了，执行这一段程序的时候已经过了早上8点
			date = this.addDay(date,1);
		}
		
		Log.log("第一次执行，时间为："+date.toString());//经过判断这才找到了第一次执行的时间，具体表现为如果开始执行这段程序时，没过8：00am，那么第一次的执行时间为当天8：00，如果程序开始时间已经过了8：00am，那么第一次执行时间为明日8：00am
		
		Timer timer = new Timer();
		
		Spiders task = new Spiders();//创建周期性任务
		
		timer.schedule(task, date, PERIOD_DAY);//周期性任务从date开始，以PERIOD_DAY为周期执行
		
		
	}
	public Date addDay(Date date, int num) { 
        Calendar startDT = Calendar.getInstance(); 
        startDT.setTime(date); 
        startDT.add(Calendar.DAY_OF_MONTH, num); 
        return startDT.getTime(); 
       }
	
	public static void main(String[] args) {
		new TimerManager();//测试
	}
}
