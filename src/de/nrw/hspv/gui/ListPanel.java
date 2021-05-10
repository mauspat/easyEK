package de.nrw.hspv.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.UIManager;

import de.nrw.hspv.backend.Item;
import de.nrw.hspv.backend.ShoppingList;
import de.nrw.hspv.backend.Supermarket;


public class ListPanel extends JPanel {

	// TODO NUR zu Testzwecken!!
	
	private ArrayList<Item> actualEkList;

	JPanel centerPanel = new JPanel();
	
	
	/**
	 * Konstruktor zum erstellen eines ListPanels. Das ListPanel wird auf Basis der Übergebenen Einkaufsliste erstellt. Die Elemente der Einkaufsliste werden im Panel als Button angezeigt
	 * @param actualEkList
	 */
	ListPanel(ShoppingList actualEkList) {
		
		
		//TODO Konstruktor muss Liste enthalten, welche im Panel dargestellt werden soll

		/*
		 * Nur zu
		 * Testzwecken:-----------------------------------------------------------------
		 * -----------------------------
		 */
		
		this.actualEkList = actualEkList.getShoppingList();
		
		
		
		

		// Testzweck
		// ENDE----------------------------------------------------------------------------------------------------
		
		
		this.setLayout(new BorderLayout());// BorderLayout, um im Zentrum das Flowlayout zu verwenden, und links und rechts gleichzeitig einen Rand zu haben.
		this.setVisible(true);
		this.add(centerPanel);
		
		// mainPanel mit FlowLayout wird im Centrum des BorderLayouts hinzugefügt
		centerPanel.setLayout(new WrapLayout(FlowLayout.CENTER, 8, 8));
		centerPanel.setBackground(UI.getBgColor().brighter());
		
		addScrollPane();
		addItemButtons();
		changeButtons();

	}
	
	
	/**
	 * Toolbar wird ein "Liste löschen" Button hinzugefügt
	 */
	private void changeButtons() {
		ImageIcon garbage = new ImageIcon("resource\\GarbageSymbol.PNG");
		garbage.setImage(garbage.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		EKButton button = new EKButton();
		button.setIcon(garbage);
		
		EKButton sortButton = initSortButton();
		
		
		UI.ChangeToolbarButton(sortButton, button);
		
	}

	private EKButton initSortButton() {
		EKButton button = new EKButton("Sortieren");
		
		JComboBox<Supermarket> supermarkets = new JComboBox<Supermarket>();
		for (Supermarket s: Supermarket.getSupermarketList()) {
			supermarkets.addItem(s);
		}
		
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(centerPanel, supermarkets, "Supermarkt auswählen", JOptionPane.QUESTION_MESSAGE);
				
			}
			
		});
		return button;
	}


	ListPanel(){
		
		this.add(new EKButton("Hello"));
		
	}
	
	
	/**
	 * Fügt dem CENTER Panel ein Panel mit einer Scrollbar hinzu. Die Methode entfernt Rahmen des Panels, deaktiviert das horizontale Scrollen und bestimmt die "Scrollweite"
	 * bei einem Pfeilklick, bzw. bei einer Drehung des Mausrades.
	 * 
	 */
	private void addScrollPane() {
		JScrollPane sp = new JScrollPane(centerPanel);
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setBorder(BorderFactory.createEmptyBorder());
		sp.getVerticalScrollBar().setUnitIncrement(10);
		this.add(sp);
	}

	/**
	 * ItemButtons sind JToggleButtons mit bestimmten Eigenschaften... ... ... ...
	 */

	// TODO Auswahl der Items muss an "echte Item Liste angepasst werden". Bisher
	// wird hier nur mit den Listen aus dieser Klasse gearbeitet.
	private void addItemButtons() {

		// Greift in den UI Manager ein, um die Farbe zu ändern, wenn Button "getoggelt" ist
		UIManager.put("ToggleButton.select", new Color(186, 132, 76)); 
	
		// Iteriert über die länge der gesamten Liste und fügt für jedes Element einen
		// eigenen ToggleButton in das Panel ein.		
		for (int i = 0; i < actualEkList.size(); i++) {
			EKListItemButton button = new EKListItemButton();

			String tempName = actualEkList.get(i).getName(); // String, der die Beschriftung enthält.

			button.setPosition(i); //Speichert gesetzte Position, um später anhand dieser Position eine neusortierung durchführen zu können
			button.setHorizontalAlignment(JButton.LEFT); //Textausrichtung im Button ist Linksbündig
				
			button.setText(String.format("%d%-30s%s", 1, "x", tempName));
			button.setAlignmentX(JButton.CENTER);

			centerPanel.add(button);
			
		}
	}
	
	

	/**
	 * Der EKListItemButton erweitert den JToggleButton. Neben einigen Design-
	 * formatierungen bekommt der Button ein Attribut "Position". So behält er die
	 * Info, an welcher Position der Button des Ensprechenden Items auf dem UI der Einkaufsliste 
	 * hinzugefügt wurde.
	 * Mit der Überschriebenen Action Performed Methode bekommt der Button beim toggeln einen grünen Haken und wird unten in der Liste angereiht.
	 * Wird er wieder "enttoggelt" bekommt er eine die oberste Position, da man davon ausgehen kann, dass das Produkt dann als nächstes geholt werden muss, da man schon vorbeigelaufen ist.
	 */
	private class EKListItemButton extends JToggleButton implements ActionListener{
		// Position soll beim erstellen des Buttons zugewiesen werden.
		private int position;
		private Color buttonColor = new Color(0, 209, 155);

		EKListItemButton() {
			// Standartmäßige Höhe und Breite des Buttons.
			int hight = 40, width = UI.getWindowWidh() - 100;

			this.setPreferredSize(new Dimension(width, hight));
			this.setBackground(new Color(0, 209, 155));
			
			//entfernt Rahmen, wenn Button angeklickt oder Fokussiert ist.
			this.setFocusPainted(false);
			this.setHorizontalTextPosition(JButton.LEFT);
			this.setIconTextGap(50);
			this.setFont(new Font("SansSerif", Font.BOLD, 15));
			this.addActionListener(this);
		}

		public int getPosition() {
			return position;
		}

		public void setPosition(int position) {
			this.position = position;
		}

		@Override
		//wird der Button gedrückt, färbt er sich um und bekommt ein Häkchen. Weiterhin Sortiert er sich ans Ende der Liste.
		public void actionPerformed(ActionEvent e) { 
			ImageIcon greenHook= new ImageIcon("resource\\greenCheck.png");
			greenHook.setImage(greenHook.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
			
			if (this.isSelected()) { 		//Wenn der Button ausgewählt wird (Der Button setzt sich mit Klick auf den Button sofort auf "isSelected==true" dann...
				centerPanel.setComponentZOrder(this, centerPanel.getComponentCount()-1); 			//...wird das Element (der Button) ans Ende der Liste geschoben
				this.setIcon(greenHook);														//...wird ein grüner Haken als Icon hinzugefügt
				
			} else {						//wird er erneut angetippt...
				centerPanel.setComponentZOrder(this, 0);				//... wird der Button oben in der Liste eingefügt			
				this.setIcon(null);									//... der Haken wird wieder entfernt
				
			}

		}

	}
		
		
}


