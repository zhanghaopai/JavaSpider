package com.ljb.epaper.pojo;


public class Paper {
	String name;
	String address;
	public Paper(String name, String address) {
		super();
		this.name = name;
		this.address = address;
	}
	public Paper() {}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
