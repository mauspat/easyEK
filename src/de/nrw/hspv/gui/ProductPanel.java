package de.nrw.hspv.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Arrays;
import java.util.Map.Entry;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

import de.nrw.hspv.backend.*;
import de.nrw.hspv.gui.*;

public class ProductPanel extends JPanel {

	JPanel upPanel = new JPanel();
	JPanel downPanel = new JPanel();
	JPanel leftPanel = new JPanel();
	JPanel rightPanel = new JPanel();
	JPanel mainPanel = new JPanel();
	JPanel centerPanel = new JPanel();
	
	JLabel label = new JLabel();

	ProductPanel() {

		this.setBackground(Color.DARK_GRAY.brighter());
		this.setLayout(new BorderLayout());

		initPanels();
		addProductOverview();
		addNewProduct();
		this.setVisible(true);
	}

	private void initPanels() {

		upPanel.setBackground(UI.getBgColor());
		downPanel.setBackground(UI.getBgColor());
		leftPanel.setBackground(UI.getBgColor());
		rightPanel.setBackground(UI.getBgColor());
		mainPanel.setBackground(UI.getBgColor());

		upPanel.setPreferredSize(new Dimension(UI.getWindowWidh(), 35));
		downPanel.setPreferredSize(new Dimension(UI.getWindowWidh(), UI.getWindowHight() / 15));
		leftPanel.setPreferredSize(new Dimension(50, UI.getWindowHight()));
		rightPanel.setPreferredSize(new Dimension(50, UI.getWindowHight()));
		mainPanel.setPreferredSize(new Dimension());

		this.add(upPanel, BorderLayout.NORTH);
		this.add(downPanel, BorderLayout.SOUTH);
		this.add(leftPanel, BorderLayout.WEST);
		this.add(rightPanel, BorderLayout.EAST);
		this.add(mainPanel, BorderLayout.CENTER);

	}

	private void addProductOverview() {
//		mainPanel.setLayout(new BorderLayout());
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		// JLabel
		label.setText("Übersicht aller Produkte");
		label.setFont(new Font("SansSerif", Font.BOLD, 14));
		label.setOpaque(true);
		label.setBackground(UI.getBgColor());
		label.setForeground(Color.WHITE);
		label.setHorizontalTextPosition(JLabel.CENTER);
//		label.setVerticalAlignment(JLabel.CENTER);
		upPanel.add(label);

		// JList
		JList<String> allProducts = new JList<String>();
		allProducts.setVisibleRowCount(9);
		allProducts.setFixedCellHeight(25);
		allProducts.setBackground(UI.getBgColor().brighter().brighter());
		allProducts.setForeground(Color.WHITE);
		allProducts.setModel(NewListFrame.getItemList());
		mainPanel.add(new JScrollPane(allProducts));

		// Platzhalter
		mainPanel.add(Box.createRigidArea(new Dimension(50, 150)));
	}

	public void addNewProduct() {
		// JLabel 
		JLabel newProduct = new JLabel("Neues Produkt hinzufügen");
		newProduct.setFont(new Font("SansSerif", Font.BOLD, 14));
		newProduct.setHorizontalTextPosition(JLabel.CENTER);
		newProduct.setForeground(Color.white);
		newProduct.setAlignmentX(Component.LEFT_ALIGNMENT);
		mainPanel.add(newProduct);
		
		mainPanel.add(Box.createRigidArea(new Dimension(100, 40)));
		
		// JComboBox = Kategorieauswahl
		JComboBox<String> chooseCategory = new JComboBox<String>();
		for (Entry<Integer, Category> entry : Category.categoryList.entrySet()) {

			chooseCategory.addItem(entry.getValue().getCategoryName());
		}
		chooseCategory.setSize(new Dimension(1, 30));
		chooseCategory.setSelectedIndex(-1);
		chooseCategory.setRenderer(new ComboBoxRenderer("Kategorie auswählen"));
		mainPanel.add(chooseCategory);
		
		//Platzhalter
		mainPanel.add(Box.createRigidArea(new Dimension(100, 30)));
		
		//JLabel
		JLabel productNameLabel = new JLabel("Bezeichnung:");
		productNameLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
		productNameLabel.setForeground(Color.white);
		productNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		mainPanel.add(productNameLabel);
		
		// TextField für Eingabe des Namen des Produkts
		JTextField productName = new JTextField();
		productName.setSize(new Dimension(mainPanel.getWidth(), 35));
		productName.setAlignmentY(30);;
		mainPanel.add(productName);
		
		// Platzhalter
		mainPanel.add(Box.createRigidArea(new Dimension(100, 80)));
		
		// Button zum Hinzufügen
		EKButton addProduct = new EKButton("Produkt hinzufügen");
		addProduct.setBackground(UI.getBgColor().darker().darker());
		addProduct.setForeground(Color.white);
		addProduct.setPreferredSize(new Dimension(374, 35));
		addProduct.setHorizontalTextPosition(JLabel.CENTER);
		mainPanel.add(addProduct);
		
		

	}

	// Klasse, damit in der ComboBox Befehl steht von StackOverflow
	class ComboBoxRenderer extends JLabel implements ListCellRenderer {
		private String title2;

		public ComboBoxRenderer(String title) {
			title2 = title;
		}

		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			if (index == -1 && value == null) {
				setText(title2);
			} else
				setText(value.toString());
			return this;
		}

	}
}