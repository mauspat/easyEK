package de.nrw.hspv.backend;

public class Supermarket {
	private static int counter = 0;
	private int id;
	private int postalCode;
	private String name;
	private String street;
	private int addrNumber;
	private String city;
	private int[] grid;
	
	// --------- KONSTRUKTOREN ---------
	Supermarket(String name, int[] grid) {
		this.name = name;
		this.grid = grid;
		this.id = counter + 1;
		counter += 1;
		
		//--------- Standardwerte ---------
		street = "N/A"; // N/A = Not Available
		city = "N/A";
		addrNumber = 9999;
		postalCode = 9999;
		//---------------------------------
	}
	
	Supermarket(String name, String city, int[] grid) {
		this.name = name;
		this.city = city;
		this.grid = grid;
		this.id = counter + 1;
		counter += 1;
		
		//--------- Standardwerte ---------
		street = "N/A";
		addrNumber = 9999;
		postalCode = 9999;
		//---------------------------------
	}
	
	Supermarket(String name, String city, String street, int addrNumber, int postalCode, int[] grid) {
		this.name = name;
		this.city = city;
		this.street = street;
		this.addrNumber = addrNumber;
		this.postalCode = postalCode;
		this.grid = grid;
		this.id = counter + 1;
		counter += 1;
	}
	
	//--------- GETTERS ---------
	public Supermarket getSupermarket() {
		return this;
	}
	
	public String getSupermarketName() {
		return name;
	}
	
	public int[] getSupermarketGrid() {
		return grid;
	}
	
}