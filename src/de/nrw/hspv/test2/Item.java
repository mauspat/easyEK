package de.nrw.hspv.test2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.TreeMap;



public class Item implements Serializable {
	
	
	private static final long serialVersionUID = 6423603262569584025L;
	
	//TODO auf priovate setzten und Getter setter hinzufügen
	public static TreeMap<String, Item> itemList = new TreeMap<String, Item>();
	
	private String name;
	private int amount;
	private int categoryID;
	private String categoryName;
	
	Item(String name) {
		this.name = name;
		amount = 1;
	}
	
	Item(String name, String categoryName, int categoryID) {
		this.name = name;
		this.categoryName = categoryName;
		this.categoryID = categoryID;
		amount = 1;
	}
	
	Item(String name, int amount, String categoryName, int categoryID) {
		this.name = name;
		this.amount = amount;
		this.categoryName = categoryName;
		this.categoryID = categoryID;
	}
	
	public static void saveItems() {
		FileOutputStream dataOutStream=null;
		ObjectOutputStream objectOutputStream=null;
		
		try {
			dataOutStream = new FileOutputStream(new File("resource//safedItems.txt"));
			objectOutputStream = new ObjectOutputStream(dataOutStream);
			
			for(Item i: itemList.values()) {
				objectOutputStream.writeObject(i);
			}
			
			dataOutStream.close();
			objectOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}	
	
	// GETTERS
	public String getName() {
		return name;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public int getCategory() {
		return categoryID;
	}
	
	public String getCategoryName() {
		return categoryName;
	}
	
	
	// SETTERS
	public void setName(String name) {
		this.name = name;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public void setCategory(int id) {
		categoryID = id;
	}
}