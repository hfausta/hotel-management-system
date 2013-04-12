package com.enterprise.beans;

public class HotelBean {
	private int id;
	private int managerid;
	private int ownerid;
	private String address;
	private int phone;
	private String name;
	
	public HotelBean(){
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getManagerid() {
		return managerid;
	}
	public void setManagerid(int managerid) {
		this.managerid = managerid;
	}
	public int getOwnerid() {
		return ownerid;
	}
	public void setOwnerid(int ownerid) {
		this.ownerid = ownerid;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getPhone() {
		return phone;
	}
	public void setPhone(int i) {
		this.phone = i;
	}
	public String toString(){
		return id + " " + name + " " +  managerid + " " + ownerid + " " + address + " " + phone;
	}

}
