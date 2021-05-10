package de.nrw.hspv.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.TreeMap;
import java.util.logging.Level;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import de.nrw.hspv.backend.*;

public class NewListFrame extends JFrame {

	private static final int scale = 50;
	private static final int WINDOW_WIDH = scale * 9;
	private static final int WINDOW_HIGHT = scale * 16;
	private static final Color BG_COLOR = Color.DARK_GRAY;

	JPanel upPanel = new JPanel();
	JPanel downPanel = new JPanel();
	JPanel leftPanel = new JPanel();
	JPanel rightPanel = new JPanel();
	JPanel centerPanel = new JPanel();

	DefaultListModel<String> itemListModel = new DefaultListModel<String>();
	DefaultListModel<String> choosenListModel = new DefaultListModel<String>();
	JList<String> list = new JList<String>(itemListModel);

	JTextField searchPanel = new JTextField();

	NewListFrame() {

		this.setSize(WINDOW_WIDH, WINDOW_HIGHT);
		this.getContentPane().setBackground(BG_COLOR);

		class MyWindowAdapter extends WindowAdapter { // nur die Methode implementieren, die wir brauchen, weil die
														// Klasse WindowAdapter das Interface implementiert
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		}
		this.addWindowListener(new MyWindowAdapter());
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().setBackground(BG_COLOR);

		initSearchPanel();

		initPanels();
		initCenter();
		initButtons();
		initJList();
		this.setVisible(true);

	}

	private void initSearchPanel() {
		// TODO Textfeld größe anpassen wenn langeweile vorhanden!
		// TODO Text "Suche..." bei anklicken entfernen

		searchPanel.setText("Suche...");
//		searchPanel.addFocusListener(new FocusListener() {
//
//			@Override
//			public void focusGained(FocusEvent e) {
//				if(searchPanel.getText().equals("Suche...")) {
//					searchPanel.setText("");
//				}
//				
//			}
//
//			@Override
//			public void focusLost(FocusEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//		});

		searchPanel.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				searchFilter(searchPanel.getText());

			}

		});

		centerPanel.add(searchPanel);
		centerPanel.add(Box.createVerticalStrut(10));

	}

	private void searchFilter(String searchText) {
		DefaultListModel<String> searchModel = new DefaultListModel<String>();

		for (String e : Item.itemList.keySet()) {
			if (e.toLowerCase().contains(searchText.toLowerCase())) {
				searchModel.addElement(e);
			}
		}

		itemListModel = searchModel;
		list.setModel(itemListModel);

	}

	public void initPanels() {

		upPanel.setBackground(BG_COLOR);
		downPanel.setBackground(BG_COLOR);
		leftPanel.setBackground(BG_COLOR);
		rightPanel.setBackground(BG_COLOR);
		centerPanel.setBackground(BG_COLOR);

		upPanel.setPreferredSize(new Dimension(WINDOW_WIDH, 50));
		downPanel.setPreferredSize(new Dimension(WINDOW_WIDH, WINDOW_HIGHT / 15));
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

//		centerPanel.setLayout(new GridLayout(0,1,5,10));
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		// JLabel
		JLabel label = new JLabel();
		label.setText("Wählen Sie Ihre Produkte");
		label.setFont(new Font("SansSerif", Font.BOLD, 14));
		label.setOpaque(true);
		label.setBackground(BG_COLOR);
		label.setForeground(Color.WHITE);
		label.setHorizontalTextPosition(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);

		upPanel.add(label);

	}

	private void initJList() {

		// -------------Testzweck entfernen--------------
		Item.itemList.put("Apfel", new Item("Apfel", new Category("Obst", 1)));
		Item.itemList.put("Banane", new Item("Banane", new Category("Obst", 1)));
		// ----------------------------------------------

		// JLabel mit Titel
		JLabel listTitle = new JLabel("Produkte auswählen");
		listTitle.setForeground(Color.white);
		listTitle.setPreferredSize(new Dimension(centerPanel.getWidth(), 15));
		listTitle.setHorizontalTextPosition(JLabel.LEFT);
		centerPanel.add(listTitle);

		itemListModel.addAll(Item.itemList.keySet());

		// JList list mit allen Produkten, die es gibt

		list.setFixedCellWidth(300);
		list.setSelectionBackground(new Color(0, 209, 155));
		list.setBackground(BG_COLOR.brighter().brighter());
		list.setVisibleRowCount(10);
		list.setForeground(Color.white);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setFixedCellHeight(40);

		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {

				if (e.getValueIsAdjusting()) {

					String choosenOne = list.getSelectedValue();
					System.out.println(choosenOne);

					choosenListModel.addElement(choosenOne);

				}
			}
		});

		centerPanel.add(new JScrollPane(list));

		// Abstand zwischen Liste und Label
		centerPanel.add(Box.createRigidArea(new Dimension(100, 10)));

		JLabel listTitle2 = new JLabel("ausgewählte Produkte");
		listTitle2.setForeground(Color.white);
		listTitle2.setPreferredSize(new Dimension(centerPanel.getWidth(), 15));
		listTitle2.setHorizontalTextPosition(JLabel.LEFT);
		centerPanel.add(listTitle2);

		// JList mit Items, die aus der JList 1 ausgewählt wurden
		JList<String> list2 = new JList<String>(choosenListModel);
		list2.setSelectionBackground(new Color(0, 209, 155));
		list2.setBackground(BG_COLOR.brighter().brighter());
		list2.setVisibleRowCount(5);
		list2.setForeground(Color.white);
		list2.setFixedCellHeight(40);
		list2.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {

				if (e.getValueIsAdjusting()) {

					int index = list2.getSelectedIndex();

					// wenn Listenelement gelöscht wird, wird auch wieder etwas geändert und damit
					// der ListSelectionListener erneut aufgerufen
					// dann ist in der Liste kein Element mehr selectiert und index wird -1 (Siehe
					// .getSelectedIndex). ->IndexOutofBoundsException
					if (index != -1) {
						choosenListModel.removeElementAt(index);
					}
				}
			}
		});

		centerPanel.add(new JScrollPane(list2));
//		myList.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
//
//			@Override
//			public void valueChanged(ListSelectionEvent e) {
//				// TODO Auto-generated method stub
//				
//			}	
////		gucken weche Methode implementiert werden muss
//			});
//		
		centerPanel.add(Box.createRigidArea(new Dimension(100, 10)));

		// der JList wird die Liste mit den Produkten übergeben

	}

	public void initButtons() {

		downPanel.setLayout(new GridLayout(1, 2, 4, 0));

		EKButton goBack = new EKButton();
		
		
		ImageIcon backArrow = new ImageIcon("resource/icons/goback.png");
		backArrow.setImage(backArrow.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		
		goBack.setIcon(backArrow);
		
		
		

		goBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				dispose();
			}
		});
		downPanel.add(goBack);

		EKButton addToList = new EKButton();
		
		addToList.setText("<html>Einkaufsliste<br>erstellen</html>");
		addToList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String listName = JOptionPane.showInputDialog(getContentPane(),
						"Geben sie einen Namen für Ihre Einkaufsliste ein:", "Listenname",
						JOptionPane.QUESTION_MESSAGE);

				if (listName == null) {
					//Wenn Cancel gedrückt wird, ist der Listenname "null" und der User soll weiter beim Liste erstellen machen.
				} else if (listName.equals("")) {
					JOptionPane.showMessageDialog(getContentPane(), "Sie müssen einen Namen für die Liste angeben!",
							"Fehler", JOptionPane.ERROR_MESSAGE);
				}

				else {
					ShoppingList sl = new ShoppingList(listName);
					for (int i = 0; i < choosenListModel.size(); i++) {
						sl.addToList(Item.itemList.get(choosenListModel.elementAt(i)));
					}

					dispose();
					MyLogger.getInstance().getLogger().log(Level.FINE,
							"Neue Liste konnte erfolgreich erstellt werden!");

				}

			}
		});
		downPanel.add(addToList);

		
	}
}
