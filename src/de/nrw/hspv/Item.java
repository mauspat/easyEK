package de.nrw.hspv;

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
	private Category category;
	
	Item(String name, Category category){
		this.name = name;
		this.category = category;
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
	
	
	// SETTERS
	public void setName(String name) {
		this.name = name;
	}
	
}
