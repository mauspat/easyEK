package de.nrw.hspv.test;

public class Item implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private int amount;
	private int categoryID;
	private String categoryName;
	
	Item(String name) {
		this.name = name;
		amount = 1;
	}
	
	Item(String name, String categoryName, int categoryID) {
		this.name = name;
		this.categoryName = categoryName;
		this.categoryID = categoryID;
		amount = 1;
	}
	
	Item(String name, int amount, String categoryName, int categoryID) {
		this.name = name;
		this.amount = amount;
		this.categoryName = categoryName;
		this.categoryID = categoryID;
	}
	
	
	@Override
	public String toString() {
		return this.name;
	}
	
	// GETTERS
	public String getName() {
		return name;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public int getCategory() {
		return categoryID;
	}
	
	public String getCategoryName() {
		return categoryName;
	}
	
	
	// SETTERS
	public void setName(String name) {
		this.name = name;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public void setCategory(int id) {
		categoryID = id;
	}
}