package com.moteefe.challenge.dto;

import java.io.Serializable;

public class ItemDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4659936744169399519L;
	String title;
	Long count;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	
}
