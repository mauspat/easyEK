package de.nrw.hspv.backend;

import java.awt.Color;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import de.nrw.hspv.gui.UI;

import java.io.Serializable;

public class Main implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*-------------------------------------------*
	 *--------- BEGINN DER MAIN-METHODE ---------*
     *-------------------------------------------*/
	public static void main(String[] args) {
		
		
		//Ändert Farbe vom Toggle Button
		UIManager.put("ToggleButton.select", new Color(216,86,0));
		
		/*
		Kategorien und Items werden zu Programmstart geladen
		Die Einträge werden direkt in die statischen TreeMaps
		itemList und categoryList in den jeweiligen Klassen geschrieben
		*/
		
		loadCategories();
		loadShoppingLists();
		loadSupermarkets();
		loadAllItems();
		
		
		new UI();
		MyLogger.getInstance().getLogger().log(Level.INFO, "Programm wurde gestartet!");
	}
	
	
	// ---------- METHODEN --------- //
	
	
	/**
	 * Die {@code loadSupermarkets()}-Methode lädt zum Programmstart alle gespeicherten Supermärkte aus
	 * der Datei supermarkets/supermarkets.txt ein, damit diese in der Sortier-Liste zu Programmstart
	 * verfügbar sind.
	 */
	private static void loadSupermarkets() {
		try {
			FileInputStream fileInput = new FileInputStream(new File("resource/supermarkets/supermarkets.txt"));
			ObjectInputStream objectInput = new ObjectInputStream(fileInput);
			
			try {
				Supermarket.supermarketList = (ArrayList<Supermarket>)objectInput.readObject();
			} catch (EOFException e) {
					System.out.println("End of file.");
					MyLogger.getInstance().getLogger().severe("Ende der Datei");
			} catch (ClassCastException e) {
				System.out.println("Fehler beim casten der Klasse.");
				MyLogger.getInstance().getLogger().severe("Klasse konnte nicht gecastet werden");
				e.printStackTrace();
			}
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Fehler beim Laden der Supermärkte.");
			MyLogger.getInstance().getLogger().severe("Supermärkte konnten nicht geladen werden");
			e.printStackTrace();
		}
	}

	/**
	 * Die {@code loadAllItems()}-Methode lädt alle Produkte aus der savedItems.txt-Datei ein und speichert sie
	 * in der statischen Klassenvariablen itemList.  
	 */
	public static void loadAllItems() {
		
		
		try {
			FileInputStream fileInput = new FileInputStream(new File("resource/productlist/savedItems.txt"));
			ObjectInputStream objectInput = new ObjectInputStream(fileInput);
			
			
			
			while(true) {
			Item tempItem = (Item)objectInput.readObject();
			Item.getItemList().put(tempItem.getName(), tempItem);
			}
		} catch (IOException | ClassNotFoundException e ) {
			
			System.err.println("EOF Exception");
			MyLogger.getInstance().getLogger().severe("Liste ist Zuende (EOF Exception)");
		} 
		
	}

	
	
	/**
	 * Die Methode {@code loadItems()} lädt alle hinterlegten Produkte aus der Datei easyEK_productList.txt ein
	 * und speichert sie in der statischen Variablen "itemList" (vom Typen TreeMap). Sie ist nur beim allerersten
	 * Programmstart relevant um vorher in der txt geschriebene Inhalte in das Programm zu überführen und dann
	 * mittels {@code saveItems() } serialisiert abzuspeichern.
	 */
	public static void loadItems() {
		List<String> itemListTemp;
		try {
			itemListTemp = Files.readAllLines(Paths.get("resource/productlist/easyEK_productList.txt"));
			
			Iterator<String> itr = itemListTemp.iterator();
			while(itr.hasNext()) {
				String line = itr.next();
				String[] segments = line.split(Pattern.quote("/"));
				String itemName = segments[2];
				int itemCategoryID = Integer.parseInt(segments[0]);
				Category itemCategory = Category.categoryList.get(itemCategoryID);
				Item.getItemList().put(itemName, new Item(itemName, itemCategory));
			}
		} catch (IOException e) {
			System.out.println("Fehler beim Einlesen der Produktliste.");
			e.printStackTrace();
			MyLogger.getInstance().getLogger().severe("Produktliste konnte nicht eingelesen werden");
		}		
	}
	
	/**
	 * Die Methode {@code loadCategories()} lädt alle vorhandenen Kategorien aus der Datei categories.txt
	 * ein und speichert diese in der statischen Variablen {@code categoryList} (vom Typen TreeMap) ab.
	 */
	public static void loadCategories() {
		List<String> categoryList;
		try {
			categoryList = Files.readAllLines(Paths.get("resource/categorylist/categories.txt"));
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
			MyLogger.getInstance().getLogger().severe("Kategorien konnten nicht eingelesen werden");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Die Methode {@code loadShoppingLists()} liest das Verzeichnis "resource/shoppinglists/" ein.
	 * Jede txt-Datei im Verzeichnis wird als eigene Liste behandelt und eingelesen.
	 */
	public static void loadShoppingLists() {
		File dir = new File("resource/shoppinglists");
		File[] directoryListing = dir.listFiles();
		if(directoryListing != null) {
			for(File child : directoryListing) {
				System.out.println(child.getName());
				try {
					FileInputStream fileIn = new FileInputStream(child);
					ObjectInputStream in = new ObjectInputStream(fileIn);
					ShoppingList temp = (ShoppingList) in.readObject();
					ShoppingList.getAllShoppingLists().add(temp);
					fileIn.close();
					in.close();
				} catch (Exception e) {
					System.out.println("Einkaufsliste konnte nicht geladen werden.");
					MyLogger.getInstance().getLogger().severe("Einkaufsliste konnt nicht geladen werden");
					e.printStackTrace();
				}
				
				System.out.println("Folgende Listen sind gespeichert:");
				Iterator<ShoppingList> itr = ShoppingList.getAllShoppingLists().iterator();
				while(itr.hasNext()) {
					ShoppingList l = itr.next();
					System.out.println(l.getShoppingListName());
				}
				
			}
		} else {
			JOptionPane.showMessageDialog(UI.getMainPanel(), "Keine Listen hinterlegt", "Warnung", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	//--------- für Testzwecke ---------
	/**
	 * Die Methode {@code displayItems()} liest die statische Variable {@code itemList} (vom Typen TreeMap) aus der Klasse Item aus
	 * und zeigt alle dort hinterlegten Items in der Console an.
	 */
	public static void displayItems() {
		for(Map.Entry<String, Item> entry: Item.getItemList().entrySet()) {
			String key = entry.getKey();
			Item currentItem = entry.getValue();
			System.out.println("Key lautet: " + key);
			System.out.println("Item: " + currentItem.getName());
			System.out.println("Item Category: " + currentItem.getCategory().getCategoryName());
		}
	}
	
	/**
	 * Die Methode {@code displayItems()} liest die statische Variable {@code itemList} (vom Typen TreeMap) aus der Klasse Item aus
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
