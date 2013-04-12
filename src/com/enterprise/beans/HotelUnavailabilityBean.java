package com.enterprise.beans;

import java.sql.Date;

public class HotelUnavailabilityBean {
	private int id;
	private int hotelid;
	private Date start;
	private Date end;
	String status;
	
	public HotelUnavailabilityBean(){}
	
	

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String toString(){
		return hotelid + " " + start.toString() + " " + end.toString() + " " + status;
	}

}
