package com.ljb.epaper.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class URLSBuilder {
	public List<String> buildPNGAddress(String url) {
		List result = new ArrayList<String>();
		switch (url) {
		case "http://epaper.yzwb.net": {
			//http://epaper.yzwb.net/images/2020-03/17/A03/20200317A03_brief.jpg
			SimpleDateFormat df1 = new SimpleDateFormat("YYYY-MM/dd");
			SimpleDateFormat df2 = new SimpleDateFormat("YYYYMMdd");
			for(int i = 1;i<=8;i++) {
				String r = String.format("%s//images/%s/A%02d/%sA%02d_brief.jpg", url, df1.format(new Date()), i, df2.format(new Date()), i);
				result.add(r);
			}
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + url);
		}
		return result;
	}
	public List<String> buildPDFAddress(String url) {
		List result = new ArrayList<String>();
		switch (url) {
		case "http://epaper.yzwb.net": {
			//http://epaper.yzwb.net/images/2020-03/17/A03/20200317A03_pdf.pdf
			SimpleDateFormat df1 = new SimpleDateFormat("YYYY-MM/dd");
			SimpleDateFormat df2 = new SimpleDateFormat("YYYYMMdd");
			for(int i = 1;i<=8;i++) {
				String r = String.format("%s//images/%s/A%02d/%sA%02d_pdf.pdf", url, df1.format(new Date()), i, df2.format(new Date()), i);
				result.add(r);
			}
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + url);
		}
		return result;
	}
	public static void main(String[] args) {
		URLSBuilder builder = new URLSBuilder();
		String url = "http://epaper.yzwb.net";
		List<String> list1 = builder.buildPNGAddress(url);
		List<String> list2 = builder.buildPDFAddress(url);
		for(String l:list1) {
			System.out.println("缩略图地址生成器："+l);
		}
		for(String l:list2) {
			System.out.println("pdf下载地址生成器："+l);
		}
		
	}

}
