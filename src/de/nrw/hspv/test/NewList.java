package de.nrw.hspv.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class NewList extends JFrame {

	private static final int scale = 50;
	private static final int WINDOW_WIDH = scale * 9;
	private static final int WINDOW_HIGHT = scale * 16;
	private static final Color BG_COLOR = Color.DARK_GRAY;

	JPanel upPanel = new JPanel();
	JPanel downPanel = new JPanel();
	JPanel leftPanel = new JPanel();
	JPanel rightPanel = new JPanel();
	JPanel centerPanel = new JPanel();

	NewList() {

		this.setSize(WINDOW_WIDH, WINDOW_HIGHT);
		this.getContentPane().setBackground(BG_COLOR);

		class MyWindowAdapter extends WindowAdapter { // nur die Methode implementieren, die wir brauchen, weil die
														// Klasse WindowAdapter das Interface implementiert
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		}
		this.addWindowListener(new MyWindowAdapter());
		this.getContentPane().setLayout(new BorderLayout());

		initPanels();
		initCenter();
		initButtons();
		this.setVisible(true);

	}

	public void initPanels() {

		upPanel.setBackground(BG_COLOR);
		downPanel.setBackground(BG_COLOR);
		leftPanel.setBackground(BG_COLOR);
		rightPanel.setBackground(BG_COLOR);
		centerPanel.setBackground(BG_COLOR);

		upPanel.setPreferredSize(new Dimension(WINDOW_WIDH, 50));
		downPanel.setPreferredSize(new Dimension(WINDOW_WIDH, WINDOW_HIGHT/15));
		leftPanel.setPreferredSize(new Dimension(50, WINDOW_HIGHT));
		rightPanel.setPreferredSize(new Dimension(50, WINDOW_HIGHT));
		centerPanel.setPreferredSize(new Dimension());

		this.add(upPanel, BorderLayout.NORTH);
		this.add(downPanel, BorderLayout.SOUTH);
		this.add(leftPanel, BorderLayout.WEST);
		this.add(rightPanel, BorderLayout.EAST);
		this.add(centerPanel, BorderLayout.CENTER);
	}
	/**
	 * 
	 */

	public void initCenter() {

		JPanel chooseCategoryText = new JPanel();
		chooseCategoryText.setBackground(BG_COLOR);

		JPanel chooseCategoryPanel = new JPanel();
		chooseCategoryPanel.setBackground(BG_COLOR);

		//JLabel
		JLabel label = new JLabel();
		label.setText("Wählen Sie Ihre Produkte");
		label.setFont(new Font("SansSerif", Font.BOLD, 14));
		label.setOpaque(true);
		label.setBackground(BG_COLOR);
		label.setForeground(Color.WHITE);
		label.setHorizontalTextPosition(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
		

		//JList mit allen Produkten aus TextFile groceries.txt
		
		Scanner scan = null; 											//Scanner erstellen und initialisieren
		try {

			scan = new Scanner(new File("resource/groceries.txt"));		// File einlesen
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();										// Fehlerausgabe
		}

		JList<String> myList = new JList<String>();						// JList erstellen
		DefaultListModel<String> model = new DefaultListModel<>();		// Model muss der Liste übergeben werden, damit sie einen Inhalt hat

		while (scan.hasNext()) {										// Geht Liste durch, bis Ende

			String[] line = scan.nextLine().split("/");					// teilt Zeilen in einzelne Segmente {"Key","Kategorie","Name"}
			model.addElement(line[2]);									// drittes Segment wird dem DefaulListModel hinzugefügt

		}
		
		myList.setFixedCellWidth(300);

		
		JScrollPane scrollPane = new JScrollPane();
		centerPanel.add(new JScrollPane(myList));
		
		
//		myList.getSelectionModel().addListSelectionListener(new ListSelectionListener(){	//der Liste in der JList wird ein ListSelectionListener hinzugefügt
//		gucken weche Methode implementiert werden muss});
		
		

	
		myList.setModel(model);											// der JList wird die Liste mit den Produkten übergeben
		upPanel.add(label);
		centerPanel.add(chooseCategoryText, BorderLayout.NORTH);
		centerPanel.add(chooseCategoryPanel, BorderLayout.CENTER);

	}
	
	public void initButtons() {
		
		downPanel.setLayout(new GridLayout(1,3,4,0));
		
		
		EKButton goBack = new EKButton();
		goBack.setText("zurück");
		downPanel.add(goBack);
		
		EKButton addToList = new EKButton();
		addToList.setText("Einkaufsliste erstellen");
		downPanel.add(addToList);
		
		EKButton addNewItem = new EKButton();
		addNewItem.setText("Neues Produkt hinzufügen");
		downPanel.add(addNewItem);
	}
}
