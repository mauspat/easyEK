package de.nrw.hspv;

import java.util.ArrayList;
import java.util.Iterator;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ShoppingList implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int counter = 1;
	private String name;
	private int id;
	private ArrayList<Item> shoppingList = new ArrayList<Item>();
	
	ShoppingList(String name) {
		this.name = name;
		this.id = counter;
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
	 * zu speichern. Der Dateiname ergibt sich dabei aus dem Listennamen sowie der ID.
	 */
	public void saveList() {
		try {
			// Dateiname wird dynamisch ermittelt aus dem Namen und der ID der Liste.
			// also z. B. Wochenendeinkauf_02.txt
			String filename = name + "_" + id + ".txt";
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
}
