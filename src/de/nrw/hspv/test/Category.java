package de.nrw.hspv.test;

public class Category {
	
	private int key;
	private String name;
	
	
	public Category (int key, String name) {
		
		this.key = key;
		this.name = name;
		
	}
	
	
	
	
	
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

}
