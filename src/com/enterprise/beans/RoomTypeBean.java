package com.enterprise.beans;

public class RoomTypeBean {
	private String id;
	private int people;
	private boolean extraBed;
	private double price;
	
	public RoomTypeBean(){}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getPeople() {
		return people;
	}

	public void setPeople(int people) {
		this.people = people;
	}

	public boolean isExtraBed() {
		return extraBed;
	}

	public void setExtraBed(boolean extraBed) {
		this.extraBed = extraBed;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public String toString(){
		return id + " " + people + " " + extraBed + " " + price;
	}
}
