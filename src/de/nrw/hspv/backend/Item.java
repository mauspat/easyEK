package de.nrw.hspv.backend;

import java.io.Serializable;
import java.util.TreeMap;



public class Item implements Serializable {
	
	
	private static final long serialVersionUID = 6423603262569584025L;
	
	//TODO auf priovate setzten und Getter setter hinzufügen
	public static TreeMap<String, Item> itemList = new TreeMap<String, Item>();
	
	private String name;
	private Category category;
	
	Item(String name, Category category){
		this.name = name;
		this.category = category;
//		itemList.put(name, this);
	}
	
	

	
	// Wird diese Funktion gebraucht, wenn wir die Items
	// zu Programmstart aus der Liste auslesen?
	/*
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
	
	 
	*/ 	
	
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
	
	
	// SETTERS
	public void setName(String name) {
		this.name = name;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}
	
}
