package com.moteefe.challenge.domain;

import java.io.Serializable;

public class DeliveryTime implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2509527654864115123L;
	
	String location;
	Long days;
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Long getDays() {
		return days;
	}
	public void setDays(Long days) {
		this.days = days;
	}
}
