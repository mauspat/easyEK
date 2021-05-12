package de.nrw.hspv.backend;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Supermarket {

	private static ArrayList<Supermarket> supermarketList = new ArrayList<Supermarket>();

	private static int counter = 0;
	private int id;
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
		this.id = counter + 1;
		counter += 1;

		// --------- Standardwerte ---------
		street = "N/A"; // N/A = Not Available
		city = "N/A";

		postalCode = 9999;
		// ---------------------------------

		supermarketList.add(this);
	}

	public Supermarket(String name, String city, int[] grid) {
		this.name = name;
		this.city = city;
		this.grid = grid;
		this.id = counter + 1;
		counter += 1;

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
		this.id = counter + 1;
		counter += 1;

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