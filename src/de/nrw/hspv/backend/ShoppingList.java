package de.nrw.hspv.backend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ShoppingList implements Serializable {
	/**
	 * 
	 */

	private static ArrayList<ShoppingList> savedLists = new ArrayList<ShoppingList>();
	private static final long serialVersionUID = 1L;
	private int counter = 1;
	private String name;
	private int id;
	private Supermarket supermarket;
	private ArrayList<Item> shoppingList = new ArrayList<Item>();
	
	// --------- KONSTRUKTOREN --------- //
	public ShoppingList(String name, Supermarket supermarket) {
		this.name = name;
		this.id = counter;
		this.supermarket=supermarket;
		savedLists.add(this);
		counter += 1;
	}
	
	public ShoppingList(String name) {
		this.name = name;
		this.id = counter;
		savedLists.add(this);
		counter += 1;
	}
	
	/** 
	 * Die contains-Methode erhält ein int-Array sowie einen int-Wert als Übergabeparameter. Sie überprüft dann,
	 * ob der int-Wert im Array enthalten ist. In diesem Fall liefert die Methode den Wert true zurück,
	 * ansonsten den Wert false.
	 */
	private  boolean containsCategory(final int[] supermarket, final int categoryID) {
		// Die Lambda-Expression "c -> c == categoryID" nutzt die anyMatch-Methode der
		// InStream-Klasse und überprüft, ob c dem Wert der übergebenen Variablen "categoryID"
		// entspricht. Ist dies der Fall, ist der Rückgabewert true, ansonsten false.
		return Arrays.stream(supermarket).anyMatch(c -> c == categoryID);
	}
	
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
	public  ArrayList<Item> sortList(Supermarket supermarket) {
		
		this.supermarket=supermarket;
		int[] supermarketGrid = supermarket.getSupermarketGrid();
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
		Iterator<Item> rawItr = this.shoppingList.iterator();
		while(rawItr.hasNext()) {
			Item i = rawItr.next();
			if(containsCategory(supermarketGrid, i.getCategory().getCategoryID())) {
				categorized.add(i);
			} else {
				bucketList.add(i);
			}
		}
		
		// Die categorized-Liste wird durchlaufen; jeder Eintrag wird mit dem Supermarkt-Array
		// abgeglichen; wenn die ID des aktuellen Items dem aktuellen Supermarkt-Array-Wert gleicht,
		// wird das Item auf die Liste sortedList gesetzt.
		for(int i = 0; i < supermarketGrid.length; i++) {
			Iterator<Item> sortItr = categorized.iterator();
			while(sortItr.hasNext()) {
				Item sorter = sortItr.next();
				if(sorter.getCategory().getCategoryID() == supermarketGrid[i]) {
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
		
		// Die sortierte Liste wird als Rückgabewert der Funktion zurückgegeben und die Liste wird auf die umsortierte Liste gesetzt.
		this.shoppingList = sortedList;
		return sortedList;
	}
	
	/**
	 * Die Methode addToList fügt der ArrayList shoppingList ein Item hinzu.
	 * Als Übergabeparameter wird ein Objekt des Typs Item erwartet.
	 * @param item
	 */
	public void addToList(Item item) {
		shoppingList.add(item);
	}
	
	/**
	 * Die Methode saveList() nutzt das Serializable-Interface, um eine Einkaufsliste
	 * zu speichern. Der Dateiname ergibt sich dabei aus dem Listennamen sowie einer ID.
	 * 
	 * Außerdem prüft die Methode, ob ein Listenname bereits im Verzeichnis vorhanden ist.
	 * Wenn das zutrifft, wird die ID um 1 erhöht. Wenn also eine Liste Dateiname_1.txt
	 * existiert, wird der Dateiname zu Dateiname_2.txt geändert.
	 */
	public void saveList() {
		try {
			// Dateiname wird dynamisch ermittelt aus dem Namen und der ID der Liste.
			// also z. B. Wochenendeinkauf_02.txt

			String filename = name + ".txt";
			
			FileOutputStream fileOut = new FileOutputStream("resource/shoppinglists/" + filename);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			// Die aktuelle Liste wird in eine .txt-Datei geschrieben
			out.writeObject(this);
			out.close();
			System.out.println("Die Liste wurde gespeichert.");
		} catch (IOException e) {
			System.out.println("Fehler beim Speichern der Liste.");
			e.printStackTrace();
			MyLogger.getInstance().getLogger().severe("Einkaufsliste konnte nicht gespeichert werden");
		}
	}
	
	/**
	 * Die Methode {@code deleteList(ShoppingList shoppingList)} löscht eine übergebe Einkaufsliste.
	 * Dabei wird die ArrayList {@code savedLists} durchlaufen, bis die übergebene Liste gefunden wird.
	 * Anschließend wird die Datei mit demselben Namen aus dem Verzeichnis shoppingslists gelöscht.
	 * @param shoppingList
	 */
	public static void deleteList(ShoppingList shoppingList) {
		Iterator<ShoppingList> itr = savedLists.iterator();
		while(itr.hasNext()) {
			ShoppingList s = itr.next();
			// Abfrage: gleicht die aktuelle Einkaufsliste der übergebenen Einkaufsliste?
			if(s == shoppingList) {
				String filename = s.getShoppingListName();
				itr.remove();
				// Speichere die Datei in der Variablen file
				File file = new File("resource/shoppinglists/" + filename + ".txt");
				// Datei mit dem Namen der übergebenen Einkaufsliste wird gelöscht
				file.delete();
			}
		}
	}
	
	/**
	 * {@code showList()} gibt eine Einkaufsliste inkl. der Items auf dieser Liste in der Console ist.
	 * Die Methode dient nur zu Testzwecken.
	 */
	public void showList() {
		Iterator<Item> itr = shoppingList.iterator();
		while(itr.hasNext()) {
			Item i = itr.next();
			System.out.println(i.getName() + " (" + i.getCategory().getCategoryName() + ")");
		}
	}
	
	//--------- SETTERS ---------
	/**
	 * {@code setName(String name)} aktualisiert den Namen einer ShoppingList.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	//--------- GETTERS ---------
	/**
	 * [@code getListCounter()} liefert den aktuellen counter-Wert zurück.
	 * @return
	 */
	public int getListCounter() {
		return counter;
	}
	
	/**
	 * 	{@code getShoppingListName()} liefert den Namen der ShoppingList zurück.
	 */
	public String getShoppingListName() {
		return name;
	}
	
	/**
	 * {@code getSize()} liefert die Anzahl der Items auf einer ShoppingList zurück.
	 * @return
	 */
	public int getSize() {
		return shoppingList.size();
	}
	
	/**
	 * {@code printList()} gibt die Items auf einer ShoppingList in der Console aus.
	 */
	public void printList() {
		shoppingList.stream().forEach(e -> System.out.println(e));
	}
	
	/**
	 * {@code getAllShoppingLists()} liefert die ArrayList {@code savedLists} zurück, in der alle angelegten
	 * ShoppingLists enthalten sind.
	 * @return
	 */
	public static ArrayList<ShoppingList> getAllShoppingLists() {
		return savedLists;
	}

	/**
	 * {@code getName()} liefert den Namen der aktuellen ShoppingList zurück.
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * {@code getShoppingList()} liefert die aktuelle ShoppingList zurück.
	 * @return
	 */
	public ArrayList<Item> getShoppingList(){
		return shoppingList;
	}
	
	
}
