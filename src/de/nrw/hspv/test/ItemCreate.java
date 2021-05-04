package de.nrw.hspv.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;


public class ItemCreate {
	public static void serializeObects() {
		try {
			List<String> listItem = Files.readAllLines(Paths.get("resource/groceries.txt"));
			Iterator<String> itr = listItem.iterator();
			while(itr.hasNext()) {
				
			}
			
		} catch (IOException e) {
			
		}
	}
}
