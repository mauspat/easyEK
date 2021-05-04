package de.nrw.hspv;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class Main {

	
	public static void main(String[] args) {
//		// Produkt-Liste wird zum Programmstart eingelesen
//		List<String> itemList;
//		try {
//			itemList = Files.readAllLines(Paths.get("resource/groceries.txt"));	
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		
		
		//new UIMainFrame();
		
		Item.itemList.put("Banane", new Item("Banane","Obst", 2));
		Item.itemList.put("Apfel", new Item("Apfel","Obst", 2));
		Item.itemList.put("Brot", new Item("Brot","Gebäck", 5));
		Item.saveItems();
		loadAllItems();
		
		
		
	}
	
	public static void loadAllItems() {
		try {
			FileInputStream fileInput = new FileInputStream(new File("resource//safedItems.txt"));
			ObjectInputStream objectInput = new ObjectInputStream(fileInput);
			
			//Zu testzwecken:
			
			Item i1 = (Item)objectInput.readObject();
			Item i2 = (Item)objectInput.readObject();
			Item i3 = (Item)objectInput.readObject();
			
			System.out.println(i1.getName()+"-"+i1.getCategory());
			System.out.println(i2.getName()+"-"+i2.getCategory());
			System.out.println(i3.getName()+"-"+i3.getCategory());
			
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
