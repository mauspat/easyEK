package de.nrw.hspv.backend;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Supermarket implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static ArrayList<Supermarket> supermarketList = new ArrayList<Supermarket>();

	private int postalCode;
	private String name;
	private String street;

	private String city;
	private int[] grid;

	public Supermarket() {

	}

	// --------- KONSTRUKTOREN ---------
	public Supermarket(String name, int[] grid) {
		this.name = name;
		this.grid = grid;

		// --------- Standardwerte ---------
		street = "N/A"; // N/A = Not Available
		city = "N/A";

		postalCode = 99999;
		// ---------------------------------

		supermarketList.add(this);
	}

	public Supermarket(String name, String city, int[] grid) {
		this.name = name;
		this.city = city;
		this.grid = grid;

		// --------- Standardwerte ---------
		street = "N/A";

		postalCode = 9999;
		// ---------------------------------

		supermarketList.add(this);
	}

	public Supermarket(String name, String city, String street,  int postalCode, int[] grid) {
		this.name = name;
		this.city = city;
		this.street = street;

		this.postalCode = postalCode;
		this.grid = grid;

		supermarketList.add(this);
	}

	/**
	 * Die methode vergleicht alle in der übegebenen Supermarkts Liste enthaltenen Elemente mit denen, welche in der supermarketList stehen.
	 * Wird ein identisches Element gefunden, wird dieses aus der supermarketList gelöscht.
	 * 
	 * @param supermarkets
	 */
	public static void deleteSupermarket(List<Supermarket> supermarkets) {

		Iterator<Supermarket> selectedMarkets = supermarkets.iterator();
		while (selectedMarkets.hasNext()) {
			Supermarket deleteThisMarket = selectedMarkets.next();

			Iterator<Supermarket> allMarkets = supermarketList.iterator();
			while(allMarkets.hasNext()) {
				if(deleteThisMarket.equals(allMarkets.next())) {
					allMarkets.remove();
					
				}
			}
		}
		
		saveSupermarket();
	}
	

	/**
	 * Die Methode saveSupermarket() speichert die erfassten Supermärkte in der Datei
	 * supermarkets/supermarkets.txt. Dazu werden das Serializable-Interface und die Methode
	 * writeObject genutzt.
	 */
	public static void saveSupermarket() {
		try {
			// Verzeichnis wird nach Datei durch
//			File dir = new File("resource/shoppinglists");
//			File[] directoryListing = dir.listFiles();
			
			FileOutputStream fileOut = new FileOutputStream("resource/supermarkets/supermarkets.txt");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			// Die aktuelle Liste wird in eine .txt-Datei geschrieben
			out.writeObject(supermarketList);
			out.close();
			System.out.println("Der Supermarkt wurde gespeichert.");
		} catch (IOException e) {
			System.out.println("Fehler beim Speichern des Supermarktes.");
			e.printStackTrace();
			MyLogger.getInstance().getLogger().severe("Supermarkt konnte nicht gespeichert werden");
		}
	}

	@Override
	public String toString() {
		return this.name;
	}

	// --------- SETTERS ---------
	public void setName(String name) {
		this.name = name;
	}

	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public static void setSupermarketList(ArrayList<Supermarket> supermarketList) {
		Supermarket.supermarketList = supermarketList;
	}

	// --------- GETTERS ---------
	public Supermarket getSupermarket() {
		return this;
	}

	public String getSupermarketName() {
		return name;
	}

	public int[] getSupermarketGrid() {
		return grid;
	}

	public static ArrayList<Supermarket> getSupermarketList() {
		return supermarketList;
	}
}