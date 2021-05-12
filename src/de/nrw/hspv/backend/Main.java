package de.nrw.hspv.backend;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import de.nrw.hspv.gui.UI;

import java.io.Serializable;

public class Main implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 
	 * Die contains-Methode erhält ein int-Array sowie einen int-Wert als Übergabeparameter. Sie überprüft dann,
	 * ob der int-Wert im Array enthalten ist. In diesem Fall liefert die Methode den Wert true zurück,
	 * ansonsten den Wert false.
	 */
	public static boolean contains(final int[] supermarket, final int categoryID) {
		// Die Lambda-Expression "c -> c == categoryID" nutzt das funktionale Interface
		// "IntPredicate" und überprüft, ob c dem Wert der übergebenen Variablen "categoryID"
		// entspricht. Ist dies der Fall, ist der Rückgabewert true, ansonsten false.
		return Arrays.stream(supermarket).anyMatch(c -> c == categoryID);
	}
	
	// Methode zum Sortieren der Einkaufsliste nach dem jeweiligen Supermarkt
	/**
	 * listSort sortiert eine übergebene Liste (ArrayList vom Typ Item) anhand eines ebenfalls übergebenenen
	 * int-Arrays. Jeder Eintrag aus der Liste wird mit dem aktuellen int-Wert des Arrays abgeglichen;
	 * stimmen die Werte überein, wird das Item auf die sortierte Einkaufsliste gesetzt.
	 * Die Werte des int-Arrays entsprechend dabei der Sortier-Reihenfolge des Supermarktes. Items,
	 * die in einem Supermarkt nicht gefunden werden können, werden unten an die Liste angehängt.
	 * @param list
	 * @param supermarket
	 * @return ArrayList<Item> sortedList
	 */
	public static ArrayList<Item> listSort(ArrayList<Item> list, int[] supermarket) {
		// Die Liste categorized erhält vorab alle Items, deren Kategorien im gewählten Supermarkt gefunden werden.
		ArrayList<Item> categorized = new ArrayList<Item>();
		
		// Auf die bucketList werden die Items gepackt, deren Kategorien im gewählten Supermarkt nicht vorkommen.
		// Die bucketList ist nur temporär, alle Items auf der bucketList werden zum Schluss an die
		// sortedList angehängt, damit diese Items ganz unten auf der Liste stehen.
		ArrayList<Item> bucketList = new ArrayList<Item>();
		
		// in sortedList werden die sortierten Einträge gespeichert
		ArrayList<Item> sortedList = new ArrayList<Item>();
		
		// Hier wird vorsortiert - Wenn die Kategorie-ID des Produktes als Kategorie im
		// Supermarkt vorkommt, wird das Item auf die categorized-Liste gesetzt,
		// ansonsten auf die bucketList.
		Iterator<Item> rawItr = list.iterator();
		while(rawItr.hasNext()) {
			Item i = rawItr.next();
			if(contains(supermarket, i.getCategory().getCategoryID())) {
				categorized.add(i);
			} else {
				bucketList.add(i);
			}
		}
		
		// Die categorized-Liste wird durchlaufen; jeder Eintrag wird mit dem Supermarkt-Array
		// abgeglichen; wenn die ID des aktuellen Items dem aktuellen Supermarkt-Array-Wert gleicht,
		// wird das Item auf die Liste sortedList gesetzt.
		for(int i = 0; i < supermarket.length; i++) {
			Iterator<Item> sortItr = categorized.iterator();
			while(sortItr.hasNext()) {
				Item sorter = sortItr.next();
				if(sorter.getCategory().getCategoryID() == supermarket[i]) {
					sortedList.add(sorter);
				}
			}
		}
		
		// Alle Items auf der bucketList werden jetzt an die sortedList angehängt
		Iterator<Item> bucket = bucketList.iterator();
		while(bucket.hasNext()) {
			Item temp = bucket.next();
			sortedList.add(temp);
		}
		
		// Die sortierte Liste wird als Rückgabewert der Funktion zurückgegeben.
		return sortedList;
	}

	
	/*-------------------------------------------*
	 *--------- BEGINN DER MAIN-METHODE ---------*
     *-------------------------------------------*/
	public static void main(String[] args) {
		// Kategorien und Items werden zu Programmstart geladen
		// Die Einträge werden direkt in die statischen TreeMaps
		// itemList und categoryList in den jeweiligen Klassen geschrieben
		loadCategories();
		loadItems();
		loadShoppingLists();
		
		//--------- Zu Testzwecken - Ausgabe der Kategorien und Items ---------
//		displayCategories();
		displayItems();
		
		
		System.out.println("Es wird eine neue Shopping Liste mit dem Namen \"TestListe\" angelegt ");
		ShoppingList myList = new ShoppingList("TestListe");
		new ShoppingList("TestListe2").saveList();
		new ShoppingList("Testliste3").saveList();
		
		
		System.out.println("Es werden Äpfel, Wasser, Brot, Mehl und Kekse Käse hinzugefügt ...");
		myList.addToList(Item.itemList.get("Äpfel"));
		myList.addToList(Item.itemList.get("Wasser"));
		myList.addToList(Item.itemList.get("Brot"));
		myList.addToList(Item.itemList.get("Mehl"));
		myList.addToList(Item.itemList.get("Kekse"));
		
		System.out.println("Die Liste wird ausgegeben ...\n");
		myList.showList();
		System.out.println("\nDie Liste wird gespeichert...");
		myList.saveList();
		
		System.out.println(ShoppingList.getAllShoppingLists().size());
		ShoppingList del = ShoppingList.getAllShoppingLists().get(1);
		System.out.println(del.getShoppingListName());

		System.out.println(ShoppingList.getAllShoppingLists().size());
		del = ShoppingList.getAllShoppingLists().get(1);
	
		System.out.println(ShoppingList.getAllShoppingLists().size());
		
		System.out.println(ShoppingList.getAllShoppingLists().get(0).getShoppingListName());
		
		
		int[] lidlGrid= {1,2,3,4,5,6,7,8};
		new Supermarket("Lidl",lidlGrid); 
		ShoppingList LidlAmSonntag = new ShoppingList("Lidl Sonntag");
		LidlAmSonntag.addToList(Item.itemList.get("Vodka"));
		LidlAmSonntag.addToList(Item.itemList.get("Rum"));
		LidlAmSonntag.addToList(Item.itemList.get("Chips"));
		LidlAmSonntag.addToList(Item.itemList.get("Kekse"));
		LidlAmSonntag.addToList(Item.itemList.get("Backkakao"));
		LidlAmSonntag.addToList(Item.itemList.get("Kaffee"));
		
		new Supermarket("Lidl",lidlGrid);
		new Supermarket("Aldi",lidlGrid);
		new Supermarket("Rewe",lidlGrid);
		new Supermarket("Combo",lidlGrid);
		new Supermarket("Schlecker wtf? - Den gibts gar nicht mehr!",lidlGrid);
		new Supermarket("MediaMark",lidlGrid);
		new Supermarket("EDEKA -> richbitch Laden",lidlGrid);
		new Supermarket("MediaMark",lidlGrid);
		new Supermarket("MediaMark",lidlGrid);
		new Supermarket("MediaMark",lidlGrid);
		new Supermarket("MediaMark",lidlGrid);
		new Supermarket("MediaMark",lidlGrid);
		new Supermarket("MediaMark",lidlGrid);
		new Supermarket("MediaMark",lidlGrid);
		new Supermarket("MediaMark",lidlGrid);
		
		
		new UI();
		
		System.out.println(Supermarket.getSupermarketList().size());
	}
	
	
	

//	public static void loadAllItems() {
//		try {
//			FileInputStream fileInput = new FileInputStream(new File("resource//safedItems.txt"));
//			ObjectInputStream objectInput = new ObjectInputStream(fileInput);
//			
//			//TODO Zu testzwecken: - noch in die Liste schreiben!
//			
//			Item i1 = (Item)objectInput.readObject();
//			Item i2 = (Item)objectInput.readObject();
//			Item i3 = (Item)objectInput.readObject();
//			
//			System.out.println(i1.getName()+"-"+i1.getCategory());
//			System.out.println(i2.getName()+"-"+i2.getCategory());
//			System.out.println(i3.getName()+"-"+i3.getCategory());
//			
//		} catch (IOException | ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	
	
	/**
	 * Die Methode loadItems() lädt alle hinterlegten Produkte aus der Datei easyEK_productList.txt ein
	 * und speichert sie in der statischen Variablen "itemList" (vom Typen TreeMap).
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
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Die Methode loadShoppingLists() liest das Verzeichnis "resource/shoppinglists/" ein.
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
				} catch (Exception e) {
					System.out.println("Einkaufsliste konnte nicht geladen werden.");
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
			System.out.println("Keine Listen vorhanden.");
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
