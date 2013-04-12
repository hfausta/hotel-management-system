package com.enterprise.beans;

import java.sql.Date;

public class DiscountRateByPeriodBean {
	private int id;
	private int hotelid;
	private Date start;
	private Date end;
	public String roomType;
	private double discountrate;
	
	public DiscountRateByPeriodBean(){}
	public int getHotelid() {
		return hotelid;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setHotelid(int hotelid) {
		this.hotelid = hotelid;
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
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public double getDiscountrate() {
		return discountrate;
	}
	public void setDiscountrate(double discountrate) {
		this.discountrate = discountrate;
	}
	
	public String toString(){
		return hotelid + " " + start.toString() + " " + end.toString() + " " + discountrate;
	}

}
