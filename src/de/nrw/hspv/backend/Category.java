package de.nrw.hspv.backend;

import java.util.TreeMap;
import java.io.Serializable;

public class Category implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int idCounter = 1;
	private int id;
	private String name;
	// Statische Liste mit allen Kategorien
	public static TreeMap<Integer, Category> categoryList = new TreeMap<Integer, Category>();
	
	public Category(){}
	public Category(String name, int id) {
		this.name = name;
		this.id = idCounter;
		idCounter++;

	}
	
	
	//--------- GETTERS ---------
	/**
	 * {@code getCategoryName()} liefert den Kategorie-Namen als String zurück.
	 * @return
	 */
	public String getCategoryName() {
		return name;
	}
	
	/**
	 * {@code getCategoryID()} liefert die aktuelle Kategorie-ID als int-Wert zurück.
	 * @return
	 */
	public int getCategoryID() {
		return id;
	}
	
	
	//--------- SETTERS ---------
	/**
	 * {@code setCategoryName(String name)} erhält einen String als Parameter und setzt den Kategorie-Namen auf diesen Wert.
	 * @param name
	 */
	public void setCategoryName(String name) {
		this.name = name;
	}
	
	/**
	 * {@code getName()} liefert den Kategorie-Namen als String zurück.
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * {@code setCategoryID(int id)} erhält einen int-Wert als Parameter und setzt die Kategorie-ID auf diesen Wert.
	 * @param id
	 */
	public void setCategoryID(int id) {
		this.id = id;
	}


	/**
	 * {@code setName(String name)} erhält einen String als Parameter und setzt den Kategorie-Namen auf diesen Wert.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * {@code getCategoryList()} liefert die TreeMap categoryList zurück. 
	 * @return
	 */
	public static TreeMap<Integer, Category> getCategoryList() {
		return categoryList;
	}


	/**
	 * {@code setCategoryList()} erhält eine TreeMap<Integer, Category> als Parameter und setzt die categoryList auf diesen Wert.
	 * @param categoryList
	 */
	public static void setCategoryList(TreeMap<Integer, Category> categoryList) {
		Category.categoryList = categoryList;
	}

	/**
	 * {@code getSerialversionuid()} liefert die serialVersionUID als long-Wert zurück.
	 * @return
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
