package de.nrw.hspv.backend;

import java.util.ArrayList;
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

	public static ArrayList<ShoppingList> savedLists = new ArrayList<ShoppingList>();
	private static final long serialVersionUID = 1L;
	private int counter = 1;
	private String name;
	private int id;
	private ArrayList<Item> shoppingList = new ArrayList<Item>();
	
	public ShoppingList(String name) {
		this.name = name;
		this.id = counter;
		savedLists.add(this);
		counter += 1;
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

			// Verzeichnis wird nach Datei durch
//			File dir = new File("resource/shoppinglists");
//			File[] directoryListing = dir.listFiles();
			
			FileOutputStream fileOut = new FileOutputStream("resource/shoppinglists/" + filename);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			// Die aktuelle Liste wird in eine .txt-Datei geschrieben
			out.writeObject(this);
			out.close();
			System.out.println("Die Liste wurde gespeichert.");
		} catch (IOException e) {
			System.out.println("Fehler beim Speichern der Liste.");
			e.printStackTrace();
		}
	}
	
	public static void deleteList(ShoppingList shoppingList) {
		Iterator<ShoppingList> itr = savedLists.iterator();
		while(itr.hasNext()) {
			ShoppingList s = itr.next();
			if(s == shoppingList) {
				String filename = s.getShoppingListName();
				itr.remove();
				File file = new File("resource/shoppinglists/" + filename + ".txt");
				file.delete();
			}
		}
	}
	
	public void showList() {
		Iterator<Item> itr = shoppingList.iterator();
		while(itr.hasNext()) {
			Item i = itr.next();
			System.out.println(i.getName() + " (" + i.getCategory().getCategoryName() + ")");
		}
	}
	
	//--------- SETTERS ---------
	public void setName(String name) {
		this.name = name;
	}
	
	//--------- GETTERS ---------
	public int getListCounter() {
		return counter;
	}
	
	public String getShoppingListName() {
		return name;
	}
	
	public int getSize() {
		return shoppingList.size();
	}
	
	public void printList() {
		shoppingList.stream().forEach(e -> System.out.println(e));
	}
}
