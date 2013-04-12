package com.enterprise.beans;

import java.sql.Date;

public class BookingRecordBean {
	private int bookingid;
	private int hotelid;
	private boolean extrabed;
	private int roomid;
	private String roomtype;
	private double price;
	private Date start;
	private Date end;
	private int roomNum;
	private int id;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getRoomtype() {
		return roomtype;
	}
	public int getHotelid() {
		return hotelid;
	}
	public void setHotelid(int hotelid) {
		this.hotelid = hotelid;
	}

	public void setRoomId(int roomnum) {
		this.roomid = roomnum;
	}
	public BookingRecordBean(){
	}
	public int getBookingid() {
		return bookingid;
	}
	public void setBookingid(int bookingid) {
		this.bookingid = bookingid;
	}
	public boolean isExtrabed() {
		return extrabed;
	}
	public void setExtrabed(boolean extrabed) {
		this.extrabed = extrabed;
	}
	
	public void setRoomtype(String roomtype){
	 this.roomtype = roomtype;
	}
	
	public String getRoomType(){
		return roomtype;
	}
	
	public int getRoomId(){
		return roomid;
	}
	
	
	public Date getStart() {
		return start;
	}
	
	public int getRoomNum(){
		return roomNum;
	}
	public void setRoomNum(int roomNum){
		this.roomNum = roomNum;
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
	public String toString(){
	 return bookingid  + " " + hotelid + " " + extrabed + " " + roomid + " " + roomtype + " " + start + " " + end + " " + price ;
	}

}
