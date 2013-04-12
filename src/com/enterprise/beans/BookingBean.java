package com.enterprise.beans;

import java.sql.Date;

public class BookingBean {
	private int id;
	private int hotelid;
	private int customerid;
	private Date start;
	private Date end;
	private String pin;
	
	public BookingBean(){
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getHotelid() {
		return hotelid;
	}
	public void setHotelid(int hotelid) {
		this.hotelid = hotelid;
	}
	public int getCustomerid() {
		return customerid;
	}
	public void setCustomerid(int customerid) {
		this.customerid = customerid;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	
	public String getPin() {
		return pin;
	}
	
	public void setPin(String pin) {
		this.pin = pin;
	}
	
	public String toString(){
	 return id + " "  + hotelid + " " + customerid + " " + start.toString() + " " + end.toString();
	}
}
