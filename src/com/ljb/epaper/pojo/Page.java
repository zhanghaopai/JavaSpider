package com.ljb.epaper.pojo;

import java.io.Serializable;
import java.util.Date;

public class Page implements Serializable{
	
	public Page(String name, Date time, String page_num, String page_info, String pdf_url, String pdf_position,
			String png_url, String png_position, String html_position) {
		super();
		this.name = name;
		this.time = time;
		this.page_num = page_num;
		this.page_info = page_info;
		this.pdf_url = pdf_url;
		this.pdf_position = pdf_position;
		this.png_url = png_url;
		this.png_position = png_position;
		this.html_position = html_position;
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((time == null) ? 0 : time.hashCode());
        result = prime * result + ((page_num == null) ? 0 : page_num.hashCode());
        return result;
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Page other = (Page) obj;
        if(name.equals(other.getName()) && time.equals(other.getTime()) && page_num.equals(other.getPage_num())) {
        	return true;
        }
        return false;
	}
	public Page() {}
	private String name;
	private Date time;
	private String page_num;
	private String page_info;
	private String pdf_url;
	private String pdf_position;
	private String png_url;
	private String png_position;
	private String html_position;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getPage_num() {
		return page_num;
	}
	public void setPage_num(String page_num) {
		this.page_num = page_num;
	}
	public String getPage_info() {
		return page_info;
	}
	public void setPage_info(String page_info) {
		this.page_info = page_info;
	}
	public String getPdf_url() {
		return pdf_url;
	}
	public void setPdf_url(String pdf_url) {
		this.pdf_url = pdf_url;
	}
	public String getPdf_position() {
		return pdf_position;
	}
	public void setPdf_position(String pdf_position) {
		this.pdf_position = pdf_position;
	}
	public String getPng_url() {
		return png_url;
	}
	public void setPng_url(String png_url) {
		this.png_url = png_url;
	}
	public String getPng_position() {
		return png_position;
	}
	public void setPng_position(String png_position) {
		this.png_position = png_position;
	}
	public String getHtml_position() {
		return html_position;
	}
	public void setHtml_position(String html_position) {
		this.html_position = html_position;
	}
	
	
}
