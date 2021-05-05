package de.nrw.hspv;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;
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
	
	ShoppingList(String name) {
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
			int fileNumber = id;
			String filename = name + "_" + fileNumber + ".txt";

			// Verzeichnis wird nach Datei durch
			File dir = new File("resource/shoppinglists");
			File[] directoryListing = dir.listFiles();
			if(directoryListing != null) {
				for(File child : directoryListing) {
					String savedFile = child.getName();
					if(filename.equals(savedFile)) {
						String[] fileSegs = savedFile.split(Pattern.quote("_"));
						String[] fileEnding = fileSegs[1].split(Pattern.quote("."));
						int fileNo = Integer.parseInt(fileEnding[0]);
						// Wenn bereits eine Liste mit demselben Namen und _1 existiert, wird
						// die Datei umbenannt in Dateiname_2.txt
						if(fileNumber == fileNo) {
							fileNumber += 1;
							filename = name + "_" + fileNumber + ".txt";
							
			// TODO: OVERWRITE
							
						}
					}
				}
			}
			
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
		int counter = 0;
		while(itr.hasNext()) {
			ShoppingList s = itr.next();
			if(s.equals(shoppingList)) {
				savedLists.remove(counter);
			}
			counter++;
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
}
