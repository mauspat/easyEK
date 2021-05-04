package de.nrw.hspv.test2;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.PrintJob;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;

public class UI extends JFrame implements ActionListener {
	/**
	 * Die Methode findCategory erhält als Übergabeparameter den Namen eines Items
	 * sowie eine Item-Liste vom Typen List, deren Inhalt zum Programmstart aus einer Datei eingelesen wird.
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
				category[0] = "0";
				category[1] = "VOID";
			}
//			System.out.println(category[0]);
		}
		return category;
	}
	
	private List<String> itemList;
	JTextField entry;
	JTextArea listOut;
	ArrayList<Item> shoppingList = new ArrayList<Item>();	
	JList<Item> shoppingListJ = new JList<Item>();
	JPanel panelCenter;
	
	UI() {
		// Liste wird eingelesen
		try {
			itemList = Files.readAllLines(Paths.get("resource/groceries.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int scaler = 50;
		this.setSize(scaler*9, scaler*16);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getContentPane().setBackground(new Color(0x2E3047));
		
		// Panels werden erstellt
		JPanel panelBottom = new JPanel();
		JPanel panelTop = new JPanel();
		panelCenter = new JPanel();
		
		panelBottom.setBackground(new Color(0x2E3047));
		panelTop.setBackground(new Color(0x2E3047));
		panelCenter.setBackground(new Color(0x2E3047));
		
		entry = new JTextField(20);
		entry.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					String currentValue = entry.getText();
					entry.setText("");
					try {
						String[] item = findCategory(currentValue, itemList);
						int categoryID = Integer.parseInt(item[0]);
						String category = item[1];
						Item temp = new Item(currentValue, category, categoryID);				
						shoppingList.add(temp);
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		entry.setBorder(BorderFactory.createEmptyBorder());
		
		listOut = new JTextArea(35,30);
		listOut.setBackground(new Color(0x2E3047));
		listOut.setForeground(Color.WHITE);
		
		
		JButton ok = new JButton("OK");
		ok.setFocusable(false);
		ok.setBorder(BorderFactory.createEmptyBorder());
		ok.setBackground(new Color(0x3BBA9C));
		ok.setForeground(new Color(0x3C3F58));
		ok.setPreferredSize(new Dimension(70,20));
		ok.setActionCommand("OK");
		ok.addActionListener(this);
		
		JButton show = new JButton("Anzeigen");
		show.setFocusable(false);
		show.setBorder(BorderFactory.createEmptyBorder());
		show.setBackground(new Color(0x3BBA9C));
		show.setForeground(new Color(0x3C3F58));
		show.setPreferredSize(new Dimension(100,20));
		show.setActionCommand("Anzeigen");
		show.addActionListener(this);
		
		panelCenter.add(listOut);
		

		
		this.setLayout(new BorderLayout());
		panelBottom.setBounds(0, 0, scaler*9, 50);
		panelBottom.add(entry);
		panelBottom.add(ok);
		panelBottom.add(show);
		
		this.add(panelTop, BorderLayout.NORTH);
		this.add(panelCenter, BorderLayout.CENTER);
		this.add(panelBottom, BorderLayout.SOUTH);
		
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("OK")) {
			String currentValue = entry.getText();
			entry.setText("");
			try {
				String[] item = findCategory(currentValue, itemList);
				int categoryID = Integer.parseInt(item[0]);
				String category = item[1];
				Item temp = new Item(currentValue, category, categoryID);				
				shoppingList.add(temp);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} else if (e.getActionCommand().equals("Anzeigen")) {
			listOut.setText("");
			
			Iterator<Item> itr = shoppingList.iterator();

			
			//			ListModel<Item> jList = shoppingListJ.getModel();
//			for(int a = 0; a < jList.getSize(); a++) {
//				Object obj = jList.getElementAt(a);
//			}
			
			while(itr.hasNext()) {
				Item i = itr.next();
				System.out.println(i.getName() + " (" + i.getCategoryName() + ")");
				listOut.append(i.getName() + "\t\t (" + i.getCategoryName() + ")\n");
			}
			System.out.println("\n");
		}
		
	}

}
