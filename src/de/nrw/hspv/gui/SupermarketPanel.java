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
import de.nrw.hspv.backend.Supermarket;

public class SupermarketPanel extends JPanel {

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
		 * Textefelderwerden mit Beschriftung hinzugefügt.
		 */
		String[] tfDescribtion = { "Anzeigename", "Stadt", "Postleitzahl", "Straße" };
		for (int i = 0; i < 4; i++) {
			JLabel description = new JLabel(tfDescribtion[i]);
			description.setForeground(Color.WHITE);
			createSupermarketPanel.add(description);

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
		lowerPanel.add(createSupermarketPanel);

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
				String name = textFields[0].getText();
				String city = textFields[1].getText();
				int plz = 99999;
				try {
					plz = Integer.parseInt(textFields[2].getText());
				} catch (NumberFormatException exc) {
					// exc.printStackTrace();
					JOptionPane.showMessageDialog(UI.getMainPanel(), "Postleitzahl kann nur aus Ziffern bestehen!",
							"Fehler", JOptionPane.ERROR_MESSAGE);
					return;
				}
				String street = textFields[3].getText();


				int a = JOptionPane.showConfirmDialog(UI.getMainPanel(), sortCatButtons, "Sortierung des Supermarktes",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);
				ArrayList<CatButton> grid = new ArrayList<CatButton>();
				if (a == 0) {

					for (Component c : sortCatButtons.getComponents()) {
						CatButton actB = ((CatButton) c);
						if (actB.order != 0) {
							grid.add(actB);
						}
					}
					grid.sort(null);

					int[] gridArray = new int[grid.size()];
					for (int i = 0; i < gridArray.length; i++) {
						gridArray[i] = grid.get(i).CategoryID;
					}

					new Supermarket(name, city, street, plz, gridArray);
					System.out.println(Supermarket.getSupermarketList());

					DefaultListModel<Supermarket> lm = new DefaultListModel<Supermarket>();
					lm.addAll(Supermarket.getSupermarketList());
					overviewList.setModel(lm);

					JOptionPane.showMessageDialog(UI.getMainPanel(), "Supermarkt wurde erstellt", "Supermarkt erstellt",
							JOptionPane.INFORMATION_MESSAGE);
					
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
		ArrayList<Category> cl = new ArrayList<Category>();
		cl.addAll(Category.getCategoryList().values());

		for (Category c : cl) {
			buttonHolder.add(new CatButton(c));
		}

		return buttonHolder;
	}

	private static class CatButton extends JToggleButton implements Comparable<CatButton> {
		private static int clickCount = 0;
		private int order = 0;
		private Category category;
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

					overviewList.setModel(dlm);
				}
			}

		});

		upperPanel.add(delButton);

	}
}
