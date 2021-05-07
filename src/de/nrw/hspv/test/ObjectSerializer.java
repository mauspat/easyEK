package de.nrw.hspv.test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class ObjectSerializer implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static List<String> itemList;
	
	public static void main(String[] args) {
		try {
			itemList = Files.readAllLines(Paths.get("resource/easyEK_productList.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(String line : itemList) {
			String[] currentItem = line.split(Pattern.quote("/"));
			
			String itemName = currentItem[2];
			String itemCategory = currentItem[1];
			int itemCategoryID = Integer.parseInt(currentItem[0]);
			
			try {
				FileOutputStream fileOut = new FileOutputStream("resource/productList.ser", true);
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(new Item_old(itemName, itemCategory, itemCategoryID));
				out.close();
				fileOut.close();
				System.out.println("Line was written ...");
			} catch (IOException e) {
				System.out.println("Fehler!");
				e.printStackTrace();
			}
		}
	}
}
