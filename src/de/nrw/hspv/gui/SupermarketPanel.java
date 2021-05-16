package de.nrw.hspv.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import de.nrw.hspv.backend.Category;
import de.nrw.hspv.backend.MyLogger;
import de.nrw.hspv.backend.Supermarket;

public class SupermarketPanel extends JPanel {
	
	/**
	 * Ein Array der vier angezeigten TextFields, um beim auslesen des Inhalts beim klicken auf den Button  einfach auf die Textfelder zugreifen zu könne
	 */
	private JTextField[] textFields = new JTextField[4];
	private JPanel sortCatButtons = initCatSortButtons();
	private JPanel lowerPanel = new JPanel();
	JList<Supermarket> overviewList = new JList<Supermarket>();

	public SupermarketPanel() {

		this.setLayout(new GridLayout(2, 1, 15, 15));
		this.setBackground(UI.getBgColor().brighter());

		this.addOverviewList();
		this.addCreateSupermarketPanel();
	}
	
	/**
	 * Fügt dem Panel den unteren Teil hinzu, in dem ein neuer Supermarkt angelegt werden kann.
	 */
	private void addCreateSupermarketPanel() {

		lowerPanel.setLayout(new BorderLayout(20, 20));
		lowerPanel.setBackground(UI.getBgColor().brighter());

		JPanel createSupermarketPanel = new JPanel();
		createSupermarketPanel.setBackground(UI.getBgColor().brighter());
		createSupermarketPanel.setLayout(new GridLayout(4, 2));
		TitledBorder border = BorderFactory.createTitledBorder("Neuen Supermarkt anlegen:");
		border.setTitleColor(Color.WHITE);

		createSupermarketPanel.setBorder(border);

		/*
		 * Textefelder werden mit Beschriftung hinzugefügt.
		 */
		String[] tfDescribtion = { "Anzeigename", "Stadt", "Postleitzahl", "Straße" };
		for (int i = 0; i < 4; i++) {
			//Beschriftung des Textfeldes
			JLabel description = new JLabel(tfDescribtion[i]);
			description.setForeground(Color.WHITE);
			createSupermarketPanel.add(description);
			
			//Die Textfelder werden einem JPanel hinzugefügg, damit sie innerhalb des GridLayouts nicht den volln Platz einnehmen
			JPanel tfHolder = new JPanel();
			tfHolder.setLayout(new BoxLayout(tfHolder, BoxLayout.LINE_AXIS));
			tfHolder.setBackground(UI.getBgColor().brighter());

			JTextField tf = new JTextField();
			tf.setAlignmentY(CENTER_ALIGNMENT);
			tf.setMaximumSize(new Dimension(200, 25));
			textFields[i] = tf;

			tfHolder.add(textFields[i]);
			createSupermarketPanel.add(tfHolder);
		}

		lowerPanel.add(createSupermarketPanel, BorderLayout.CENTER);


		lowerPanel.add(Box.createRigidArea(new Dimension(25, 10)), BorderLayout.WEST);
		lowerPanel.add(Box.createRigidArea(new Dimension(25, 10)), BorderLayout.EAST);
		this.add(lowerPanel);

		/*
		 * Erstellen Button wird initialisiert und hinzugefügt
		 */
		EKButton createButton = new EKButton("Supermarkt erstellen");
		createButton.setToolTipText(
				"Beim anlegen wird ein Fenster aufgerufen, in dem du die Reihenfolge der Kategorien bestimmen kannst.");
		createButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//Im ersten Schritt werden die Inhalte der Textfelder ausgelesen
				String name = textFields[0].getText();
				String city = textFields[1].getText();
				int plz = 99999;
				try {
					plz = Integer.parseInt(textFields[2].getText());
				} catch (NumberFormatException exc) {
		
					JOptionPane.showMessageDialog(UI.getMainPanel(), "Postleitzahl kann nur aus Ziffern bestehen!",
							"Fehler", JOptionPane.ERROR_MESSAGE);
					return;
				}
				String street = textFields[3].getText();
				
				//Scrollpane wird ertellt, damit die Kategorie Buttons im Confirm Dialog nicht zu viel Platz einnehmen
				JScrollPane sp = new JScrollPane(sortCatButtons,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				sp.setPreferredSize(new Dimension(350,600));
				
				int a = JOptionPane.showConfirmDialog(UI.getMainPanel(), sp, "Sortierung des Supermarktes",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);
				
				//Eine Sammlung der ausgewählten Buttons -  wird benötigt, um später die Kategorien der einzelnen Buttons auszulesen und der Reihenfolge nach zu sortieren
				ArrayList<CatButton> grid = new ArrayList<CatButton>();
				
				//Wenn der Dialog mit ok bestätigt wird:
				if (a == 0) {
					
					//Über alle Komponenten des Panels "SortCatButtons" wird Iteriert.
					for (Component c : (sortCatButtons.getComponents())) {
						//Dem Panel werden nur CatButtons hinzugefügt. Daher können doe Componenten zum CatButton gecastet werden
						CatButton actB = ((CatButton) c);
						// Wenn der Button im Dialog ausgwählt wurde, bekommt er eine Reihenfolgennumer. Wurde er nicht ausgewählt ist die Reihenfolge
						//nummer  0 . Dann hat der Supermarkt diese Kategorie nicht und sie soll dem Sortierungsraster nicht hinzugefügt werden
						if (actB.order != 0) {
							
							grid.add(actB);
						}
					}
					//Die ArrayListe wird anhand des Attributs "order" sortiert. Dazu implementiert der "CatButton" das Interface Comparable
					grid.sort(null);
					//Die ArrayListe mit Buttons wird in ein int Array überführt, da ein Supermarkt ein int Array mit den Kategorie IDs erwartet.
					//Dazu wurde im CatButton auch die zugehörige Kategorie ID hinterlegt, auf die hier in der Schleife zugegriffen wird.
					int[] gridArray = new int[grid.size()];
					for (int i = 0; i < gridArray.length; i++) {
						gridArray[i] = grid.get(i).CategoryID;
					}
					//Der Supermart wird mittels Konstruktoraufruf erstellt.
					new Supermarket(name, city, street, plz, gridArray);
					//Nach anlegen wird der Supermarkt abgespeichert
					Supermarket.saveSupermarket();
					
					//Die Übersicht von Supermärkten wird aktualisiert, sodass der neu erstelle Supermarkt hier auftaucht
					DefaultListModel<Supermarket> lm = new DefaultListModel<Supermarket>();
					lm.addAll(Supermarket.getSupermarketList());
					overviewList.setModel(lm);
					
					//Info an den User, dass der Supermarkt erfolgreich erstellt wurde
					JOptionPane.showMessageDialog(UI.getMainPanel(), "Supermarkt wurde erstellt", "Supermarkt erstellt",
							JOptionPane.INFORMATION_MESSAGE);
					MyLogger.getInstance().getLogger().log(Level.INFO, "Neuer Supermarkt, namens " + name + " wurde erfolgreich erstellt");
					
					//Wenn okay gedrückt wird, dann werden die  Inhalte der Textfelder gelöscht
					for (JTextField t : textFields) {
						t.setText("");
					}
				}
			}

		});
		lowerPanel.add(createButton, BorderLayout.SOUTH);
	}

	private JPanel initCatSortButtons() {
		
		JPanel buttonHolder = new JPanel();
		

		buttonHolder.setLayout(new GridLayout(0, 1, 5, 5));
		//Liste mit Categorien wird erstellt, um einfacher durch die Values der TreeMap zu iterieren.
		ArrayList<Category> cl = new ArrayList<Category>();
		cl.addAll(Category.getCategoryList().values());
		
		//Für jede KAtegorie wird ein neuer Button dem Panel hinzugefügt.
		for (Category c : cl) {
			buttonHolder.add(new CatButton(c));
		}
		
		return buttonHolder;
	}
	
	/**
	 * Der CatButton ist eine Art Zählbutton. Werden Button ausgewählt, wird gespeichert, in welcher Reihenfolge die einzelenen Buttons 
	 * ausgewählt worden sind. Weiterhin ist hier die Kategorie ID hinterlegt.
	 * Der nicht ausgewählte Anzeigetext des Buttons wird ebenfalls auf Basis der Kategorie bestimmt
	 * Er implementiert das Interface Comparable, damit doie Buttons nach Ihrer angeklickten Reihenfolge in einer Liste sortiert werden können-.
	 * @author Lukas
	 *
	 */
	private static class CatButton extends JToggleButton implements Comparable<CatButton> {
		private static int clickCount = 0;
		private int order = 0;
		private int CategoryID;
		private String normalText;
		private String toggleText;

		CatButton(Category category) {
			normalText = category.getCategoryName();
			CategoryID = category.getCategoryID();
			this.setPreferredSize(new Dimension(300, 30));
			this.setText(normalText);
			this.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (isSelected()) {
						clickCount++;
						toggleText = category.getName() + " - Position im Markt: " + clickCount;
						setText(toggleText);
						order = clickCount;
					} else {
						clickCount--;
						setText(normalText);
						order = 0;
					}

				}

			});
		}

		@Override
		public int compareTo(CatButton cat) {
			if (this.order > cat.order)
				return 1;
			else if (this.order < cat.order)
				return -1;
			else
				return 0;
		}

	}

	private void addOverviewList() {
		JPanel upperPanel = new JPanel();
		upperPanel.setBackground(UI.getBgColor().brighter());

		overviewList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		overviewList.setVisibleRowCount(9);
		overviewList.setFixedCellHeight(30);
		overviewList.setFixedCellWidth(350);
		overviewList.setBackground(UI.getBgColor());
		overviewList.setForeground(Color.WHITE);
		overviewList.setSelectionBackground(new Color(186, 33, 33));
		overviewList.setSelectionForeground(Color.BLACK);

		DefaultListModel<Supermarket> dlm = new DefaultListModel<Supermarket>();
		dlm.addAll(Supermarket.getSupermarketList());
		overviewList.setModel(dlm);

		upperPanel.add(new JScrollPane(overviewList));
		this.add(upperPanel);

		/*
		 * Button zum löschen eines Supermarktes hinzufügen
		 */
		EKButton delButton = new EKButton("Ausgewählte Elemente löschen");
		delButton.setPreferredSize(new Dimension(374, 35));
		delButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int a = JOptionPane.showConfirmDialog(upperPanel,
						"Sollen die ausgewählten Märkte wirklich gelöscht werden?", "Löschen",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (a == 0) {
					Supermarket.deleteSupermarket(overviewList.getSelectedValuesList());
					dlm.clear();
					dlm.addAll(Supermarket.getSupermarketList());
					JOptionPane.showMessageDialog(upperPanel, "Die Supermärkte wurden gelöscht!", "Elemente gelöscht",
							JOptionPane.INFORMATION_MESSAGE);
					MyLogger.getInstance().getLogger().log(Level.INFO, "Supermarkt wurde erfolgreich gelöscht");
					overviewList.setModel(dlm);
				}
			}

		});

		upperPanel.add(delButton);

	}
}
