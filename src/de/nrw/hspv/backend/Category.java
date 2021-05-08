package de.nrw.hspv.backend;

import java.util.TreeMap;
import java.io.Serializable;

public class Category implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int idCounter = 1;;
	private int id;
	private String name;
	// Statische Liste mit allen Kategorien
	public static TreeMap<Integer, Category> categoryList = new TreeMap<Integer, Category>();
	
	public Category(String name, int id) {
		this.name = name;
		this.id = idCounter;
		idCounter++;
	}
	
	
	//--------- GETTERS ---------
	public String getCategoryName() {
		return name;
	}
	
	public int getCategoryID() {
		return id;
	}
	
	
	//--------- SETTERS ---------
	public void setCategoryName(String name) {
		this.name = name;
	}
	
	public void setCategoryID(int id) {
		this.id = id;
	}
}
