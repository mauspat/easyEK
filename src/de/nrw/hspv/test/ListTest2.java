package de.nrw.hspv.test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ListTest2 implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Die Methode findCategory sucht in der Liste groceries.txt
	// nach dem eingegebenen Item. Wird das Item gefunden, werden
	// category-ID und Name in ein Array übertragen und zurückgeliefert.
	// Ist das Item nicht vorhanden, sind ID = 0 und der Name der Kategorie "VOID".
	public static String[] findCategory(String item, List<String> listItem) throws IOException {
		// String-Array für Rückgabe
		String[] category = new String[2];
		// Die Datei "groceries.txt" wird eingelesen
//		List<String> listItem = Files.readAllLines(Paths.get("resource/groceries.txt"));
		// die Liste (listItem) wird durchlaufen ...
		for(String line : listItem) {
			// wenn in "line" der Wert vom aktuellen "item" enthalten ist ...
			if(line.contains(item.toLowerCase())) {
				String[] segs = line.split(Pattern.quote("/"));		// String wird zerlegt, "/" ist der Delimiter
				// dem category-Array werden die Werte übergeben (ID und Name)
				category[0] = segs[0];
				category[1] = segs[1];
				break;	// Suchprozess wird nach Treffer abgebrochen
			} else {
				// Wenn Item nicht gefunden wird ...
				category[0] = "VOID";
				category[1] = "VOID";
			}
//			System.out.println(category[0]);
		}
		return category;
	}
	
	/* 
	 * Ich verstehe die Syntax hier selbst nicht, das stammt von StackOverflow
	 * Hier müssen wir evtl. was ändern oder uns mit der Syntax vertraut machen,
	 * damit wir ertwas dazu sagen können, wenn Bök fragt :-D
	 */
	// Prüft, ob ein bestimmter int-Wert in einem int-Array enthalten ist
	public static boolean contains(final int[] supermarket, final int key) {
		return Arrays.stream(supermarket).anyMatch(i -> i == key);
	}
	
	// Methode zum Sortieren der Einkaufsliste nach dem jeweiligen Supermarkt
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
	public static ArrayList<Item> listSort(ArrayList<Item> list, int[] supermarket) {
		ArrayList<Item> categorized = new ArrayList<Item>();
		
		// Auf die BucketList werden die Items gepackt, deren Kategorien im gewählten Supermarkt nicht vorkommen
		// Die BucketList ist nur temporär, alle Werte auf der BucketList werden zum Schluss an die
		// SortedList angehängt, damit diese ganz unten auf der Liste stehen.
		ArrayList<Item> bucketList = new ArrayList<Item>();
		
		// in sortedList werden die sortierten Einträge gespeichert
		ArrayList<Item> sortedList = new ArrayList<Item>();
		
		// Hier wird vorsortiert - Wenn die Kategorie des Produktes als Kategorie im
		// Supermarkt vorkommt, wird das Item auf die categorized-Liste gesetzt.
		// Ansonsten auf die BucketList.
		Iterator<Item> rawItr = list.iterator();
		while(rawItr.hasNext()) {
			Item xy = rawItr.next();
			if(contains(supermarket, xy.getCategory())) {
				categorized.add(xy);
			} else {
				bucketList.add(xy);
			}
		}
		
		// Die categorized-Liste wird durchlaufen; jeder Eintrag wird mit dem Supermarkt-Array
		// abgeglichen; wenn die ID des aktuellen Items dem aktuellen Supermarkt-Array-Wert gleicht,
		// wird das Item auf die Liste sortedList gesetzt.
		for(int i = 0; i < supermarket.length; i++) {
			Iterator<Item> sortItr = categorized.iterator();
			while(sortItr.hasNext()) {
				Item sorter = sortItr.next();
				if(sorter.getCategory() == supermarket[i]) {
					sortedList.add(sorter);
				}
			}
		}
		
		// Alle Items auf der BucketList werden jetzt an die SortedList angehängt
		Iterator<Item> bucket = bucketList.iterator();
		while(bucket.hasNext()) {
			Item temp = bucket.next();
			sortedList.add(temp);
		}
				
		return sortedList;
	}
	
	public static void main(String[] args) throws IOException {
		List<String> listItem = Files.readAllLines(Paths.get("resource/groceries.txt"));		
		
		// Shopping-Liste und Array für sortierte Liste werden angelegt
		ArrayList<Item> shoppingList = new ArrayList<Item>();
		ArrayList<Item> sortedList = new ArrayList<Item>();
		
		// int-Arrays für Supermärkte
		int[] lidl = {2, 7, 8, 1, 3, 10, 9, 4};
		int[] aldi = {5, 4, 7, 1, 3, 2, 9, 8};
		int[] edeka = {8, 1, 2, 7, 4, 10, 3, 5, 9};
		
		String[] itemCategory = new String[2];
		String categoryName = "";
		int categoryID = 0;
		boolean repeater = true;
		boolean sortRepeater = true;
		// Scanner für Eingabe
		Scanner scan = new Scanner(System.in);
		System.out.println("Auf die Einkaufsliste setze ich ...");
		System.out.println("(wenn mehr als eine Einheit gekauft werden soll, bitte /ANZAHL eingeben,");
		System.out.println("also z. B. \"Äpfel/5\" für 5 Äpfel oder \"Champignons/250\" für 250 g Champignons.)");
		
		while(repeater == true) {
			// Anzahl wird zum Anfang der Schleife auf Standard-Wert (1) gesetzt
			int amount = 1;
			// Gegenstand für Einkaufsliste wird eingelesen
			String itemName = scan.nextLine();
		
			// Wenn ein "/" enthalten ist, möchte der Nutzer mehrere Produkte
			if(itemName.contains("/")) {
				// String wird gesplittet
				String[] segs = itemName.split(Pattern.quote("/"));
				// Der Itemname wird neu zugewiesen, damit die Anzahl nicht mehr im Namen enthalten ist
				itemName = segs[0];
				// Die Anzahl wird in amount (int) gespeichert
				amount = Integer.parseInt(segs[1]);
			}
			
			// Wenn END oder x oder nichts eingegeben wird, bricht das Programm ab
			if(itemName.equals("END") || itemName.equals("") || itemName.equals("x")) {
				repeater = false;
				break;
			} else {
				// Gleiche Eingabe mit hinterlegter Liste (resource/groceries.txt) ab
				// ID und Name der Kategorie werden in itemCategory gespeichert
				// itemCategory[0] = ID
				// itemCategory[1] = Name
				itemCategory = findCategory(itemName, listItem);
				
				// Wenn Item nicht gefunden wurde (Name ist "VOID")
				if(itemCategory[0].equals("VOID")) {
					categoryName = "VOID";
					categoryID = 0;
					// Wenn der Nutzer keine Anzahl eingegeben hat ...
					if(amount == 1) {
						Item temp = new Item(itemName, categoryName, categoryID);
						shoppingList.add(temp);
					} else {
						// ... ansonsten wird die Anzahl an das Objekt übergeben
						Item temp = new Item(itemName, amount, categoryName, categoryID);
						shoppingList.add(temp);
					}
				} else {
					// Wenn Item gefunden wurde
					categoryName = itemCategory[1];
					categoryID = Integer.parseInt(itemCategory[0]);
					if(amount == 1) {
						Item temp = new Item(itemName, categoryName, categoryID);
						shoppingList.add(temp);
					} else {
						Item temp = new Item(itemName, amount, categoryName, categoryID);
						shoppingList.add(temp);
					}
				}
			}
			
		}
		
		System.out.println("Anzahl der erfassten Elemente: " + shoppingList.size());
		
		System.out.println("Die Eingabe wurde beendet.\n\nFolgende Elemente stehen auf deiner Liste:");
		System.out.println("\nMENGE // PRODUKT // KATEGORIE");
		
		// Die eingegebene Einkaufsliste wird wieder ausgegeben
		Iterator<Item> list = shoppingList.iterator();
		while(list.hasNext()) {
			Item p = list.next();
			if(p.getCategoryName().equals("VOID")) {
				System.out.println(p.getAmount() + "\t" + p.getName() + " (bisher ohne Kategorie)");
			} else {
				System.out.println(p.getAmount() + "\t" + p.getName() + " (" + p.getCategoryName() + ")");
			}
		}
		
		Scanner sort = new Scanner(System.in);
		
		// Nach Ausgabe der Roh-Liste folgt hier die Abfrage, für welchen Supermarkt sortiert werden soll
		System.out.println();
		while(sortRepeater == true) {
			System.out.println("Für welchen Supermarkt möchten Sie die Liste sortieren?");
			System.out.println("1. Lidl\n2. Aldi\n3. Edeka");
			System.out.print("Ihre Wahl (1, 2 oder 3): ");
			int usrSort = sort.nextInt();
			switch(usrSort) {
				case 1:
					// ZUM TESTEN
					System.out.println("LIDL");
					
					// die ArrayList sortedList erhält den Rückgabewert
					// der Methode listSort
					sortedList = listSort(shoppingList, lidl);
					break;
				case 2:
					// ZUM TESTEN:
					System.out.println("ALDI");
					
					// siehe oben
					sortedList = listSort(shoppingList, aldi);
					break;
				case 3:
					// ZUM TESTEN:
					System.out.println("EDEKA");
					
					// siehe oben
					sortedList = listSort(shoppingList, edeka);
					break;					
				default:
					System.out.println("Fehler bei der Eingabe. Supermarkt wurde nicht gefunden.");
					break;
			}
			
			// Die sortierte Liste wird ausgegeben
			System.out.println();
			Iterator<Item> sorted = sortedList.iterator();
			while(sorted.hasNext()) {
				Item n = sorted.next();
				if(n.getCategoryName().equals("VOID")) {
					System.out.println(n.getAmount() + "\t" + n.getName() + " (bisher ohne Kategorie)");
				} else {
					System.out.println(n.getAmount() + "\t" + n.getName() + " (" + n.getCategoryName() + ")");
				}
			}
			
			// Der Nutzer wird gefragt, ob er die Liste nochmal umsortierten möchte.
			System.out.println();
			System.out.print("Möchten Sie eine weitere Sortierung durchführen? (Y/N): ");
			String repeatSort = scan.nextLine();
			if(repeatSort.toUpperCase().equals("Y")) {
				sortRepeater = true;
			} else {
				sortRepeater = false;
				break;
			}
		}

		System.out.println("\nDas Programm wird nun beendet.");
		
		scan.close();
		sort.close();
		System.exit(0);
	}
}
