package de.nrw.hspv;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class UIMainFrame extends JFrame implements ActionListener {
	
	/**
	 * Die Funktion findCategory erhält als Übergabeparameter den Namen eines Items
	 * sowie eine Item-Liste, die zum Programmstart aus einer Datei eingelesen wird.
	 * Wenn der Produktname in der Liste auftaucht, wird die in der Liste hinterlegte
	 * Kategorie zugewiesen (sowohl eine ID im int-Format als auch ein Name vom Typen String).
	 * 
	 * Als Rückgabewert wird ein Array zurückgeliefert; Das erste Element (category[0]) liefert
	 * die Kategorie-ID, das zweite Element (category[1]) den Kategorie-Namen.
	 *  
	 * @param item
	 * @param itemList
	 * @return category
	 * @throws IOException
	 */
	public static String[] findCategory(String item, List<String> itemList) throws IOException {
		// String-Array für Rückgabe
		String[] category = new String[2];
		// Die Datei "groceries.txt" wird eingelesen
//		List<String> listItem = Files.readAllLines(Paths.get("resource/groceries.txt"));
		// die Liste (listItem) wird durchlaufen ...
		for(String line : itemList) {
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
	
	JButton apfel, brot, mehl, anzeigen;
	List<String> itemList;
	ArrayList<Item> shoppingList = new ArrayList<Item>();
	
	UIMainFrame() {		
		// Produkt-Liste wird zum Programmstart eingelesen
		try {
			itemList = Files.readAllLines(Paths.get("resource/groceries.txt"));	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.setSize(500, 800);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		
		apfel = new JButton("Apfel");
		apfel.setActionCommand("Apfel");
		apfel.addActionListener(this);
		
		brot = new JButton("Brot");
		brot.setActionCommand("Brot");
		brot.addActionListener(this);
		
		mehl = new JButton("Mehl");
		mehl.setActionCommand("Mehl");
		mehl.addActionListener(this);
		
		anzeigen = new JButton("Liste anzeigen");
		anzeigen.setActionCommand("Anzeigen");
		anzeigen.addActionListener(this);
		
		panel.add(apfel);
		panel.add(brot);
		panel.add(mehl);
		panel.add(anzeigen);
		
		this.add(panel);
		
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String buttonName = e.getActionCommand().toString();
		if(!buttonName.equals("Anzeigen")) {
			String category[] = new String[2];
			try {
				category = findCategory(buttonName, itemList);
				int categoryID = Integer.parseInt(category[0]);
				Item temp = new Item(buttonName, category[1], categoryID);
				shoppingList.add(temp);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else {
			Iterator<Item> itr = shoppingList.iterator();
			while(itr.hasNext()) {
				Item p = itr.next();
				System.out.println(p.getName() + " / " + p.getCategoryName());
			}
		}
		
		
	}
}
