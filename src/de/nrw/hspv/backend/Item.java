package de.nrw.hspv.backend;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.TreeMap;



public class Item implements Serializable {
	
	
	private static final long serialVersionUID = 6423603262569584025L;
	
	// Statische TreeMap, in der alle Items gespeichert sind, die im Programm verfügbar sind.
	// Die Liste wird zu Programmstart über die loadItems()-Methode befüllt.
	private static TreeMap<String, Item> itemList = new TreeMap<String, Item>();
	
	private String name;
	private Category category;
	
	public Item(String name, Category category){
		this.name = name;
		this.category = category;
		itemList.put(name, this);
	}
	
	/**
	 * {@code saveItems()} speichert mittels {@code Serializable}-Interface alle Items, die in der {@code itemList} sind, in die Datei
	 * resource\savedItems.txt.
	 */
	public static void saveItems() {
		FileOutputStream dataOutStream=null;
		ObjectOutputStream objectOutputStream=null;
		
		try {
			// OutputStreams werden erzeugt
			dataOutStream = new FileOutputStream(new File("resource/productlist/savedItems.txt"));
			objectOutputStream = new ObjectOutputStream(dataOutStream);
			
			// Jedes Item wird der Reihe nach in die Datei geschrieben.
			// Dazu wird die Methode writeObject des Objects ObjectOutputStream genutzt.
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
