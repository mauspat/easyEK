package de.nrw.hspv.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map.Entry;
import java.util.logging.Level;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import de.nrw.hspv.backend.*;

public class ProductPanel extends JPanel {


	JPanel upPanel = new JPanel();
	JPanel downPanel = new JPanel();
	JPanel leftPanel = new JPanel();
	JPanel rightPanel = new JPanel();
	JPanel mainPanel = new JPanel();
	JPanel centerPanel = new JPanel();

	JLabel label = new JLabel();
	JList<String> allProducts = new JList<String>();

	ProductPanel() {

		this.setBackground(Color.DARK_GRAY.brighter());
		this.setLayout(new BorderLayout());

		initPanels();
		addProductOverview();
		addNewProduct();
		this.setVisible(true);
	}

	/**
	 * Initialisiert Panels bezüglich Farbe und Größe und füngt sie anhand des BorderLayouts dem Frame hinzu
	 */
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

	/**
	 * Fügt dme Panel 
	 */
	private void addProductOverview() {
		mainPanel.setLayout(new BorderLayout());
//		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		// JLabel
		label.setText("Übersicht aller Produkte");
		label.setFont(new Font("SansSerif", Font.BOLD, 14));
		label.setOpaque(true);
		label.setBackground(UI.getBgColor());
		label.setForeground(Color.WHITE);
		label.setHorizontalTextPosition(JLabel.CENTER);
		upPanel.add(label);

		// JList
		JPanel jListPanel = new JPanel();
		jListPanel.setBackground(UI.getBgColor());
		
		allProducts.setVisibleRowCount(9);
		allProducts.setFixedCellHeight(25);
		allProducts.setFixedCellWidth(300);
		allProducts.setBackground(UI.getBgColor().brighter().brighter());
		allProducts.setForeground(Color.WHITE);
		allProducts.setModel(NewListFrame.getItemListModel());
		jListPanel.add(new JScrollPane(allProducts));
		mainPanel.add(jListPanel, BorderLayout.NORTH);

		// Platzhalter
		mainPanel.add(Box.createRigidArea(new Dimension(50, 150)));
	}

	
	/**
	 * fügt alle notwendigen Container für das Erstellen eines neuen Items hinzu
	 */
	public void addNewProduct() {
		
		// Extra Panel 
		JPanel itemInputPanel = new JPanel();
		itemInputPanel.setBackground(UI.getBgColor());
		itemInputPanel.setLayout(new BoxLayout(itemInputPanel, BoxLayout.Y_AXIS));

		
		itemInputPanel.add(Box.createRigidArea(new Dimension(100, 80)));

		// JLabel
		JPanel newProductPanel = new JPanel();
		newProductPanel.setBackground(UI.getBgColor());
		JLabel newProduct = new JLabel("Neues Produkt hinzufügen");
		newProduct.setFont(new Font("SansSerif", Font.BOLD, 16));
		newProduct.setHorizontalTextPosition(JLabel.CENTER);
		newProduct.setForeground(Color.white);
		newProduct.setAlignmentX(Component.CENTER_ALIGNMENT);
		itemInputPanel.add(newProduct);
		
		itemInputPanel.add(Box.createRigidArea(new Dimension(100, 20)));

		
		// JComboBox = Kategorieauswahl
		JComboBox<String> chooseCategory = new JComboBox<String>();
		for (Entry<Integer, Category> entry : Category.categoryList.entrySet()) {

			chooseCategory.addItem(entry.getValue().getCategoryName());
		}
		chooseCategory.setSize(new Dimension(1, 30));
		chooseCategory.setSelectedIndex(-1);
		chooseCategory.setRenderer(new ComboBoxRenderer("Kategorie auswählen"));
		chooseCategory.getRenderer().toString();
		System.out.println(chooseCategory.getRenderer().toString());
		itemInputPanel.add(chooseCategory);

		// Platzhalter
		itemInputPanel.add(Box.createRigidArea(new Dimension(100, 30)));

		// JLabel
		JLabel productNameLabel = new JLabel("Bezeichnung:");
		productNameLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
		productNameLabel.setForeground(Color.white);
		productNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		itemInputPanel.add(productNameLabel);

		// TextField für Eingabe des Namen des Produkts
		JTextField productName = new JTextField();
		productName.setSize(new Dimension(mainPanel.getWidth(), 35));
		itemInputPanel.add(productName);

		// Platzhalter
		itemInputPanel.add(Box.createRigidArea(new Dimension(100, 80)));

		mainPanel.add(itemInputPanel, BorderLayout.CENTER);
		
		// Button zum Hinzufügen
		JPanel buttonPanel = new JPanel();
		EKButton addProduct = new EKButton();
		buttonPanel.setBackground(UI.getBgColor().darker().darker());
		addProduct.setBackground(UI.getBgColor().darker().darker());
		addProduct.setForeground(Color.white);
		addProduct.setSize(new Dimension(buttonPanel.getWidth(), buttonPanel.getHeight()));
		addProduct.setText("Produkt hinzufügen");
		addProduct.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Wenn kein Name eingegeben wurde, wird nochmal darauf hingewiesen
				if (productName.getText().equals(null)|| productName.getText().equals("")) {

					JOptionPane.showMessageDialog(UI.getMainPanel(), "Sie müssen einen Namen eingeben");

				}
				// keine Kategorie ausgewählt
				else if (chooseCategory.getSelectedItem() == null) {
					
					JOptionPane.showMessageDialog(UI.getMainPanel(), "Sie müssen eine Kategorie wählen");
					
				}
				else {
				String name = productName.getText();
				
				
				// Kontrolle, ob es Produkt schon gibt
				if (NewListFrame.itemListModel.contains(name)) {
					
					JOptionPane.showMessageDialog(UI.getMainPanel(), "Produkt ist schon vorhanden!");
				}
				else {
				
				
				Category cat = new Category();
				for (Entry<Integer, Category> entry : Category.categoryList.entrySet()) {
					if (entry.getValue().getCategoryName().equals(chooseCategory.getSelectedItem())) {
						cat = entry.getValue();
					}
				}
				// neues Produkt erzeugen und Speichern
				Item product = new Item(name, cat);
				Item.saveItems();
				MyLogger.getInstance().getLogger().log(Level.INFO, name + "(Produkt) wurde hinzugefügt!");


				System.out.println(product.getName());
				System.out.println(product.getCategory().getCategoryName());
				}
				productName.setText("");
				
				UI.getMainPanel().add(new ProductPanel(), "ProductPanel");			
				}
			}
		});

		
		buttonPanel.add(addProduct);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

	}
	
}

// Klasse, damit in der ComboBox Befehl steht und nicht direkt eine Kategorie
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
 public String getTitle() {
	 return title2;
 }
}