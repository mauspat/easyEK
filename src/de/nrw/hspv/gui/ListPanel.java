package de.nrw.hspv.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.AbstractButton;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.nrw.hspv.backend.Item;
import de.nrw.hspv.backend.ShoppingList;
import de.nrw.hspv.backend.Supermarket;

public class ListPanel extends JPanel {

	private ShoppingList actualEkList;

	JPanel centerPanel = new JPanel();

	/**
	 * Konstruktor zum erstellen eines ListPanels. Das ListPanel wird auf Basis der
	 * Übergebenen Einkaufsliste erstellt. Die Elemente der Einkaufsliste werden im
	 * Panel als Button angezeigt
	 * 
	 * @param actualEkList
	 */
	ListPanel(ShoppingList actualEkList) {

		this.actualEkList = actualEkList;


		this.setLayout(new BorderLayout());// BorderLayout, um im Zentrum das Flowlayout zu verwenden, und links und
											// rechts gleichzeitig einen Rand zu haben.
		this.setVisible(true);
		this.add(centerPanel);

		// mainPanel mit FlowLayout wird im Centrum des BorderLayouts hinzugefügt
		centerPanel.setLayout(new WrapLayout(FlowLayout.CENTER, 8, 8));
		centerPanel.setBackground(UI.getBgColor().brighter());

		addScrollPane();
		addItemButtons();
		changeToolbarButtons();

	}
	ListPanel(){
		this.setBackground(Color.black);
	};

	/**
	 * Toolbar wird ein "Liste löschen" Button hinzugefügt
	 */
	private void changeToolbarButtons() {
		ImageIcon garbage = new ImageIcon("resource\\GarbageSymbol.PNG");
		garbage.setImage(garbage.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		EKButton button = new EKButton();
		
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int checkProgress = JOptionPane.showConfirmDialog(centerPanel, "Soll diese Liste wirklich gelöscht werden?", "Liste löschen", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if(checkProgress==0) {
					//Iterieren über die Liste, bis diese gefunden ist und dann wird diese gelöscht
					Iterator<ShoppingList> it = ShoppingList.getAllShoppingLists().iterator();
					while(it.hasNext()) {
						if(it.next().equals(actualEkList)) {
							it.remove();
							break;
						}
					}
					
					UI.getMainPanel().add(new ListOverviewPanel(), "ListOverview");
					UI.getCl().show(UI.getMainPanel(), "ListOverview");
					UI.changeToolbarButton();
					System.out.println(UI.getMainPanel().getComponentCount());
				}
				
			}
			
		});
		
		button.setIcon(garbage);

		EKButton sortButton = initSortButton();

		UI.changeToolbarButton(sortButton, button);

	}
	
	/**
	 * Initialiesiert den "Sortieren" Button, mit der der Supermarkt der Liste ausgewählt werden kann.
	 * @return EKButton
	 */

	private EKButton initSortButton() {
		EKButton button = new EKButton("Sortieren");
		
		//Der Combobox werden die Supermärkte aus der Liste aller zur Verfügung stehenden Supermärkte übergeben.
		JComboBox<Supermarket> supermarkets = new JComboBox<Supermarket>();
		for (Supermarket s : Supermarket.getSupermarketList()) {
			supermarkets.addItem(s);
		}

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//Mit Klick auf den Button wird ein ConfirmDialog (ok/cancel) angezeigt. Darin kann dann mit der Combobox ausgewählt werden,
				//welcher Supermarkt als Basis für die Sortierung dienen soll.
				//Wenn dies mit Klick auf Ok bestätigt wird, dann wird die ShoppingList sortiert und die Buttons neu angelegt.
				int clickedButton = JOptionPane.showConfirmDialog(centerPanel, supermarkets, "Supermarkt auswählen",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (clickedButton == 0) {
					actualEkList.sortList((Supermarket) supermarkets.getSelectedItem());
					sortButtons();
				}
			}

		});
		return button;
	}

	/**
	 * Die Methode sortiert die Buttons auf Basis der im Backend sortierten Liste. Bereits ausgewählte Buttons bleiben ausgewählt und
	 * werden dabei weiterhin unten in der Liste gehalten. So kann die auch Liste beim betreten eines neuen Supermarktes sortiert werden und bereits getätigte Einkäufe bleiben
	 * erhalten.
	 */
	private void sortButtons() {
		
		/*
		 * Zwischenspeichern der Namen der Button, die vor dem umsortieren bereits ausgewählt worden sind.
		 */
		ArrayList<String> selectedButtonsNames = new ArrayList<String>(); 
		
		//Ist ein Button ausgewählt, wird sein Name der Liste hinzugefügt
		for(int i =0; i<centerPanel.getComponentCount(); i++) {
			EKListItemButton button =((EKListItemButton) centerPanel.getComponent(i));
			if(button.isSelected()) {
				selectedButtonsNames.add(button.getText());
			}
		}
		
		
		//Alle Buttons auf dem Panel werden entfernt.
		centerPanel.removeAll();
		
		//Alle Buttons werden mit der addItemButton Methode hinzugefügt. Die Reihenfolge des hinzufügens basiert auf der der Liste im Backend. 
		//Diese wurde bereits mit Aufruf des ActionListeners des "Sortieren" Buttons sortiert in der Klasse ShoppingList sortiert.
		addItemButtons();
		
		
		/*
		 * Im letzten Schritt werden die Buttons, welche in der selectedButtonNames Liste stehen wieder "angeklickt". Dadurch sortieren diese sich wieder hinten in der Liste ein.
		 */
		Component[] newButtons = centerPanel.getComponents();
		for(int i = 0; i<newButtons.length;i++) {
			for(String s: selectedButtonsNames) {
				EKListItemButton button = (EKListItemButton)newButtons[i];
				if(button.getText().equals(s)) {
					button.setSelected(true);
				}
			}
		}
		
		centerPanel.revalidate();
	}

	/**
	 * Fügt dem CENTER Panel ein Panel mit einer Scrollbar hinzu. Die Methode
	 * entfernt Rahmen des Panels, deaktiviert das horizontale Scrollen und bestimmt
	 * die "Scrollweite" bei einem Pfeilklick, bzw. bei einer Drehung des Mausrades.
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
	 * @TODO weiter ausformulieren
	 * ItemButtons sind JToggleButtons mit bestimmten Eigenschaften... ... ... ...
	 */

	private void addItemButtons() {

		// Greift in den UI Manager ein, um die Farbe zu ändern, wenn Button "getoggelt"
		// ist
		UIManager.put("ToggleButton.select", new Color(186, 132, 76));

		// Iteriert über die länge der gesamten Liste und fügt für jedes Element einen
		// eigenen ToggleButton in das Panel ein.
		for (int i = 0; i < actualEkList.getShoppingList().size(); i++) {
			EKListItemButton button = new EKListItemButton();

			String tempName = actualEkList.getShoppingList().get(i).getName(); // String, der die Beschriftung enthält.

			button.setPosition(i); // Speichert gesetzte Position, um später anhand dieser Position eine
									// neusortierung durchführen zu können
			button.setHorizontalAlignment(JButton.LEFT); // Textausrichtung im Button ist Linksbündig

			button.setText(String.format("%d%-30s%s", 1, "x", tempName));
			button.setAlignmentX(JButton.CENTER);
			button.setPosition(i);
			centerPanel.add(button);

		}
	}

	/**
	 * Der EKListItemButton erweitert den JToggleButton. Neben einigen Design-
	 * formatierungen bekommt der Button ein Attribut "Position". So behält er die
	 * Info, an welcher Position der Button des Ensprechenden Items auf dem UI der
	 * Einkaufsliste hinzugefügt wurde. Mit der Überschriebenen Action Performed
	 * Methode bekommt der Button beim toggeln einen grünen Haken und wird unten in
	 * der Liste angereiht. Wird er wieder "enttoggelt" bekommt er eine die oberste
	 * Position, da man davon ausgehen kann, dass das Produkt dann als nächstes
	 * geholt werden muss, da man schon vorbeigelaufen ist.
	 */
	private class EKListItemButton extends JToggleButton implements ChangeListener {
		// Position soll beim erstellen des Buttons zugewiesen werden.
		private int position;
		private Color buttonColor = new Color(0, 209, 155);
		private String name;

		EKListItemButton() {
			// Standartmäßige Höhe und Breite des Buttons.
			int hight = 40, width = UI.getWindowWidh() - 100;

			this.setPreferredSize(new Dimension(width, hight));
			this.setBackground(new Color(0, 209, 155));

			// entfernt Rahmen, wenn Button angeklickt oder Fokussiert ist.
			this.setFocusPainted(false);
			this.setHorizontalTextPosition(JButton.LEFT);
			this.setIconTextGap(50);
			this.setFont(new Font("SansSerif", Font.BOLD, 15));

			this.addChangeListener(this);
		}

		public int getPosition() {
			return position;
		}

		public void setPosition(int position) {
			this.position = position;
		}



		@Override
		public void stateChanged(ChangeEvent e) {
			ImageIcon greenHook = new ImageIcon("resource\\greenCheck.png");
			greenHook.setImage(greenHook.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));

			if (this.isSelected()) { // Wenn der Button ausgewählt wird (Der Button setzt sich mit Klick auf den
										// Button sofort auf "isSelected==true" dann...
				centerPanel.setComponentZOrder(this, centerPanel.getComponentCount() - 1); // ...wird das Element (der
																							// Button) ans Ende der
																							// Liste geschoben
				this.setIcon(greenHook); // ...wird ein grüner Haken als Icon hinzugefügt
		

			} else { // wird er erneut angetippt...
				centerPanel.setComponentZOrder(this, 0); // ... wird der Button oben in der Liste eingefügt
				this.setIcon(null); // ... der Haken wird wieder entfernt
				

			}

		}

	}

}
