package de.nrw.hspv.backend;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.TreeMap;



public class Item implements Serializable {
	
	
	private static final long serialVersionUID = 6423603262569584025L;
	
	private static TreeMap<String, Item> itemList = new TreeMap<String, Item>();
	
	private String name;
	private Category category;
	
	public Item(String name, Category category){
		this.name = name;
		this.category = category;
		itemList.put(name, this);
	}
	
	

	// Wird diese Funktion gebraucht, wenn wir die Items
	// zu Programmstart aus der Liste auslesen?
	
	public static void saveItems() {
		FileOutputStream dataOutStream=null;
		ObjectOutputStream objectOutputStream=null;
		
		try {
			dataOutStream = new FileOutputStream(new File("resource/productlist/savedItems.txt"));
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
	
	 
	
	
	@Override
	 public String toString() {
		return this.name;
	}
	
	// GETTERS
	public String getName() {
		return name;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public static TreeMap<String, Item> getItemList() {
		return itemList;
	}
	
	
	// SETTERS
	public void setName(String name) {
		this.name = name;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}
	
}
