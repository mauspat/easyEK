package de.nrw.hspv;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.io.Serializable;

public class Main implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		// Kategorien und Items werden zu Programmstart geladen
		// Die Einträge werden direkt in die statischen TreeMaps
		// itemList und categoryList in den jeweiligen Klassen geschrieben
		loadCategories();
		loadItems();
		
		//--------- Zu Testzwecken - Ausgabe der Kategorien und Items ---------
//		displayCategories();
//		displayItems();
		
		//--------- Listen werden geladen ---------
		File dir = new File("resource/shoppinglists");
		File[] directoryListing = dir.listFiles();
		if(directoryListing != null) {
			for(File child : directoryListing) {
				System.out.println(child.getName());
			}
		}
		//-----------------------------------------
		
		System.out.println("Es wird eine neue Shopping Liste mit dem Namen \"TestListe\" angelegt ");
		ShoppingList myList = new ShoppingList("TestListe");
		
		System.out.println("Es werden Äpfel, Wasser, Brot, Mehl und Kekse hinzugefügt ...");
		myList.addToList(Item.itemList.get("Äpfel"));
		myList.addToList(Item.itemList.get("Wasser"));
		myList.addToList(Item.itemList.get("Brot"));
		myList.addToList(Item.itemList.get("Mehl"));
		myList.addToList(Item.itemList.get("Kekse"));
		
		System.out.println("Die Liste wird ausgegeben ...\n");
		myList.showList();
		System.out.println("\nDie Liste wird gespeichert...");
		myList.saveList();
		
		//new UIMainFrame();
	}
	
	
	
	/*
	public static void loadAllItems() {
		try {
			FileInputStream fileInput = new FileInputStream(new File("resource//safedItems.txt"));
			ObjectInputStream objectInput = new ObjectInputStream(fileInput);
			
			//TODO Zu testzwecken: - noch in die Liste schreiben!
			
			Item i1 = (Item)objectInput.readObject();
			Item i2 = (Item)objectInput.readObject();
			Item i3 = (Item)objectInput.readObject();
			
//			System.out.println(i1.getName()+"-"+i1.getCategory());
//			System.out.println(i2.getName()+"-"+i2.getCategory());
//			System.out.println(i3.getName()+"-"+i3.getCategory());
			
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/
	
	
	/**
	 * Die Methode loadItems() lädt alle hinterlegten Produkte aus der Datei easyEK_productList.txt ein
	 * und speichert sie in der statischen Variablen "itemList" (vom Typen TreeMap).
	 */
	public static void loadItems() {
		List<String> itemListTemp;
		try {
			itemListTemp = Files.readAllLines(Paths.get("resource/easyEK_productList.txt"));
			
			Iterator<String> itr = itemListTemp.iterator();
			while(itr.hasNext()) {
				String line = itr.next();
				String[] segments = line.split(Pattern.quote("/"));
				String itemName = segments[2];
				int itemCategoryID = Integer.parseInt(segments[0]);
				Category itemCategory = Category.categoryList.get(itemCategoryID);
				Item.itemList.put(itemName, new Item(itemName, itemCategory));
			}
		} catch (IOException e) {
			System.out.println("Fehler beim Einlesen der Produktliste.");
			e.printStackTrace();
		}		
	}
	
	/**
	 * Die Methode loadCategories() lädt alle vorhandenen Kategorien aus der Datei categories.txt
	 * ein und speichert diese in der statischen Variablen "categoryList" (vom Typen TreeMap) ab.
	 */
	public static void loadCategories() {
		List<String> categoryList;
		try {
			categoryList = Files.readAllLines(Paths.get("resource/categories.txt"));
			Iterator<String> itr = categoryList.iterator();
			while(itr.hasNext()) {
				String line = itr.next();
				String[] segments = line.split(Pattern.quote("/"));
				String categoryName = segments[1];
				int categoryID = Integer.parseInt(segments[0]);
				Category.categoryList.put(categoryID, new Category(categoryName, categoryID));
			}
		} catch (IOException e) {
			System.out.println("Fehler beim Lesen der Kategorie-Liste.");
			e.printStackTrace();
		}
		
	}
	
	
	//--------- für Testzwecke ---------
	/**
	 * Die Methode displayItems() liest die statische Variable "itemList" (vom Typen TreeMap) aus der Klasse Item aus
	 * und zeigt alle dort hinterlegten Items in der Console an.
	 */
	public static void displayItems() {
		for(Map.Entry<String, Item> entry: Item.itemList.entrySet()) {
			String key = entry.getKey();
			Item currentItem = entry.getValue();
			System.out.println("Key lautet: " + key);
			System.out.println("Item: " + currentItem.getName());
			System.out.println("Item Category: " + currentItem.getCategory().getCategoryName());
		}
	}
	
	/**
	 * Die Methode displayItems() liest die statische Variable "itemList" (vom Typen TreeMap) aus der Klasse Item aus
	 * und zeigt alle dort hinterlegten Items in der Console an.
	 */
	public static void displayCategories() {
		for(Map.Entry<Integer, Category> entry: Category.categoryList.entrySet()) {
			int key = entry.getKey();
			Category currentCategory = entry.getValue();
			System.out.println("Key lautet: " + key);
			System.out.println("Item: " + currentCategory.getCategoryName());
			System.out.println("Item to String: " + currentCategory.toString());
		}
	}
}
