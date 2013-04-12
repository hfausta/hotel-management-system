package com.enterprise.beans;

public class HotelRoomBean {
	private int id;
	private int hotelid;
	private int roomnum;
	private String type;
	private String status;
	
	public HotelRoomBean(){
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
	public int getRoomnum() {
		return roomnum;
	}
	public void setRoomnum(int roomnum) {
		this.roomnum = roomnum;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String toString(){
		return id+" " + hotelid + " " + roomnum + " " + type + " " + status;
	}
	
	

}
