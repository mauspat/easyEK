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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
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

	static DefaultListModel<String> itemListModel = new DefaultListModel<String>();
	DefaultListModel<String> choosenListModel = new DefaultListModel<String>();
	JList<String> list = new JList<String>(itemListModel);

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
		initUpPanel();
		initJLists();
		initButtons();
	}
	
	
	/**
	 * f??gt dem JTextField einen KeyListener und FocusListener hinzu
	 */
	private void initSearchPanel() {
		
		// wenn man Textfeld nicht bearbeitet, steht im Textfeld "Suche..."
		JTextField searchPanel = new JTextField();
		searchPanel.addFocusListener(new FocusListener() { 

			@Override
			public void focusGained(FocusEvent e) {
				searchPanel.setText("");
			}

			@Override
			public void focusLost(FocusEvent e) {
				searchPanel.setText("Suche....");
			}
		});

		
		searchPanel.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				searchFilter(searchPanel.getText());

			}

		});

		centerPanel.add(searchPanel);
		centerPanel.add(Box.createVerticalStrut(10));

	}

	/**
	 * Neue Liste wird erstellt, die alle Produkte enth??lt, die der Eingabe aus JTextField teilweise entsprechen
	 * @param searchText
	 */
	private void searchFilter(String searchText) {
		DefaultListModel<String> searchModel = new DefaultListModel<String>();

		for (String e : Item.getItemList().keySet()) {
			if (e.toLowerCase().contains(searchText.toLowerCase())) {
				searchModel.addElement(e);
			}
		}

		itemListModel = searchModel;
		list.setModel(itemListModel);

	}

	/**
	 * Initialisiert Panels bez??glich Farbe und Gr????e und f??ngt sie anhand des
	 * BorderLayouts dem Frame hinzu
	 */
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
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
	}

	/**
	 * F??gt dem oberen Panel ein Label hinzu
	 */
	public void initUpPanel() {

		// JLabel
		JLabel label = new JLabel();
		label.setText("W??hlen Sie Ihre Produkte");
		label.setFont(new Font("SansSerif", Font.BOLD, 14));
		label.setOpaque(true);
		label.setBackground(BG_COLOR);
		label.setForeground(Color.WHITE);
		label.setHorizontalTextPosition(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);

		upPanel.add(label);

	}

	/**
	 * Es wird eine JList erstellt, die alle gespeicherten Produkte aufruft, wenn
	 * man auf ein Produkt dieser JList klickt, wird dieses automatisch der zweiten
	 * JList hinzugef??gt
	 */
	private void initJLists() {

		// JLabel mit Titel
		JLabel listTitle = new JLabel("Produkte ausw??hlen");
		listTitle.setForeground(Color.white);
		listTitle.setPreferredSize(new Dimension(centerPanel.getWidth(), 15));
		listTitle.setHorizontalTextPosition(JLabel.LEFT);
		centerPanel.add(listTitle);

		itemListModel.addAll(Item.getItemList().keySet());

		// JList list mit allen vorhandenen Produkten
		list.setFixedCellWidth(300);
		list.setSelectionBackground(new Color(0, 209, 155));
		list.setBackground(BG_COLOR.brighter().brighter());
		list.setVisibleRowCount(10);
		list.setForeground(Color.white);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setFixedCellHeight(40);

		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {// wenn ein Produkt ausgew??hlt ist, wird es der anderen
															// JList mit den
															// ausgew??hlten Produkten hinzugef??gt
				if (e.getValueIsAdjusting()) {

					String choosenOne = list.getSelectedValue();
					choosenListModel.addElement(choosenOne);

				}
			}
		});

		centerPanel.add(new JScrollPane(list));

		// Platzhalter:
		centerPanel.add(Box.createRigidArea(new Dimension(100, 10)));

		// Jlabel wird initialisiert
		JLabel listTitle2 = new JLabel("ausgew??hlte Produkte");
		listTitle2.setForeground(Color.white);
		listTitle2.setPreferredSize(new Dimension(centerPanel.getWidth(), 15));
		listTitle2.setHorizontalTextPosition(JLabel.LEFT);
		centerPanel.add(listTitle2);

		// JList mit Items, die aus der JList 1 ausgew??hlt wurden
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

					// wenn Listenelement gel??scht wird, wird auch wieder etwas ge??ndert und damit
					// der ListSelectionListener erneut aufgerufen
					// dann ist in der Liste kein Element mehr selektiert und index wird -1

					if (index != -1) {
						choosenListModel.removeElementAt(index);
					}
				}
			}
		});
		centerPanel.add(new JScrollPane(list2));

		// Platzhalter:
		centerPanel.add(Box.createRigidArea(new Dimension(100, 10)));
	}

	/**
	 * F??gt dem DownPanel EKButtons hinzu mit einem GridLayout
	 */
	public void initButtons() {

		downPanel.setLayout(new GridLayout(1, 2, 4, 0));

		// zur??ck Button, Frame wird verborgen
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

		
		// Einkaufsliste erstellen Button
		EKButton addToList = new EKButton();
		addToList.setText("<html>Einkaufsliste<br>erstellen</html>");
		addToList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String listName = JOptionPane.showInputDialog(getContentPane(),
						"Geben sie einen Namen f??r Ihre Einkaufsliste ein:", "Listenname",
						JOptionPane.QUESTION_MESSAGE);

				if (listName == null) {
					// Wenn Cancel gedr??ckt wird, ist der Listenname "null" und der User soll weiter
					// beim Liste erstellen machen.
				} else if (listName.equals("")) {
					JOptionPane.showMessageDialog(getContentPane(), "Sie m??ssen einen Namen f??r die Liste angeben!",
							"Fehler", JOptionPane.ERROR_MESSAGE);
				} else { // wenn ein Name eingegeben wurde und dieser Name nicht Leer ist

					// Neues AbfrageFenster -> Nach welchem Supermarkt soll die Liste sortiert sein?
					JComboBox<Supermarket> supermarkets = new JComboBox<Supermarket>();
					// ComboBox wird bef??llt. Dazu werden alle Element der supermarketList
					// hinzugef??gt.
					for (Supermarket s : Supermarket.getSupermarketList()) {
						supermarkets.addItem(s);
					}

					// Abfragefenster wird angezeigt
					int clickedButton = JOptionPane.showConfirmDialog(centerPanel, supermarkets, "Supermarkt ausw??hlen",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

					// Nur wenn Okay gedr??ckt wird, ansonsten passiert nichts.
					if (clickedButton == 0) {
						ShoppingList sl = new ShoppingList(listName);
						for (int i = 0; i < choosenListModel.size(); i++) {
							sl.addToList(Item.getItemList().get(choosenListModel.elementAt(i)));
						}
						// Die Liste wird sortiert.
						sl.sortList((Supermarket) supermarkets.getSelectedItem());
						sl.saveList();

						JOptionPane.showMessageDialog(getContentPane(), "Neue Einkaufsliste wurde erstellt!",
								"Liste erstellt", JOptionPane.INFORMATION_MESSAGE);

						UI.getMainPanel().add(new ListOverviewPanel(), "ListOverview");
						UI.getCl().show(UI.getMainPanel(), "ListOverview");
						dispose();
						MyLogger.getInstance().getLogger().log(Level.INFO,"Neue Liste konnte erfolgreich erstellt werden!");
					}
				}
			}
		});
		downPanel.add(addToList);
	}

	/**
	 * Notwendig, damit in der ProductPanel Klasse auf die DefaultListModel
	 * zugegriffen werden kann, muss nicht neu erzeugt werden
	 * 
	 * @return itemListModel
	 */
	public static DefaultListModel<String> getItemListModel() {
		return itemListModel;
	}
}
