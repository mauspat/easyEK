package de.nrw.hspv.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class ListPanel extends JPanel {

	// TODO NUR zu Testzwecken!!
	private ArrayList<ArrayList<String>> Einkaufslisten = new ArrayList<ArrayList<String>>();
	private ArrayList<String> wocheneinkauf = new ArrayList<String>();

	JPanel mainPanel = new JPanel();

	ListPanel() {
		
		//TODO Scrolldownleiste einfügen
		//TODO Neu Sortieren Button überdenken und aufjedenfall ins Design einpflegen
		//TODO Klassenname Fraglich... Impliziert das Panel für die Auswahl von Supermarktlisten.

		/*
		 * Nur zu
		 * Testzwecken:-----------------------------------------------------------------
		 * -----------------------------
		 */
		wocheneinkauf.add("Item");
		wocheneinkauf.add("Apfel");
		wocheneinkauf.add("Birne");
		wocheneinkauf.add("Stockbrotteig");
		wocheneinkauf.add("Litschi");
		wocheneinkauf.add("Quengelware");
		
		Einkaufslisten.add(wocheneinkauf);

		// Testzweck
		// ENDE----------------------------------------------------------------------------------------------------

		this.setLayout(new BorderLayout());// BorderLayout, um im Zentrum das Flowlayout zu verwenden, und links und
											// rechts gleichzeitig einen Rand zu haben.

		// mainPanel mit FlowLayout wird im Centrum des BorderLAyouts hinzugefügt
		mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 7));
		mainPanel.setBackground(Ui.getBgColor().brighter());
		mainPanel.setSize(Ui.getWindowWidh(), Ui.getWindowHight());
		this.add(mainPanel, BorderLayout.CENTER);

		// Abstandhalter zum Rand werden initialisiert und rechts bzw links (spacer2)
		// hinzugefügt
		JPanel spacer1 = new JPanel();
		spacer1.setBackground(Ui.getBgColor().brighter());
		spacer1.setPreferredSize(new Dimension(Ui.getWindowWidh() / 20, Ui.getWindowHight() / 20));
		this.add(spacer1, BorderLayout.EAST);

		JPanel spacer2 = new JPanel();
		spacer2.setBackground(Ui.getBgColor().brighter());
		spacer2.setPreferredSize(new Dimension(25, Ui.getWindowHight()));
		this.add(spacer2, BorderLayout.WEST);

		addItemButtons();
		addNewSortButton();

	}

	private void addNewSortButton() {
		JToolBar newSortTB = new JToolBar();
		newSortTB.setBackground(Ui.getBgColor());
		newSortTB.setPreferredSize(new Dimension(Ui.getWindowWidh(),Ui.getWindowHight()/23) ); 	//Setzt die größe aus 1/22 der Fensterhöhe fest
		newSortTB.setBorderPainted(false); 	// hässliche Grenze die gefärbt ist weg
		newSortTB.setFloatable(false); 		//Toolbar kann nicht verschoben werden
		newSortTB.setBorder(new EmptyBorder(0,30,0,30));		// setzt einen Rand von jeweils 30 Pixeln links und rechts
		
		this.add(newSortTB, BorderLayout.SOUTH);
		
		EKButton newSortButton = new EKButton();
		newSortButton.setText("Neu Sortieren");
		newSortButton.setHorizontalAlignment(JButton.CENTER);
		newSortButton.setVerticalAlignment(JButton.CENTER);
		
		newSortTB.setAlignmentY(CENTER_ALIGNMENT);
		newSortTB.setAlignmentX(CENTER_ALIGNMENT);
		newSortTB.add(newSortButton);
		
	
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
		for (int i = 0; i < wocheneinkauf.size(); i++) {
			EKListItemButton button = new EKListItemButton();

			String tempName = wocheneinkauf.get(i); // String, der die Beschriftung enthält.

			button.setPosition(i); //Speichert gesetzte Position, um später anhand dieser Position eine neusortierung durchführen zu können
			button.setHorizontalAlignment(JButton.LEFT); //Textausrichtung im Button ist Linksbündig
			
			//wird der Button gedrückt, färbt er sich um und bekommt ein Häkchen. Weiterhin Sortiert er sich ans Ende der Liste.
				
			button.setText(String.format("%d%-40s%s", 1, "x", tempName));
			button.setAlignmentX(JButton.CENTER);

			mainPanel.add(button);
			
		}
	}

	/**
	 * Der EKListItemButton erweitert den JToggleButton. Neben einigen Design-
	 * formatierungen bekommt der Button ein Attribut "Position". So behält er die
	 * Info, an welcher Position der Button des Ensprechenden Items auf dem UI der Einkaufsliste 
	 * hinzugefügt wurde.
	 */
	private class EKListItemButton extends JToggleButton implements ActionListener{
		// Position soll beim erstellen des Buttons zugewiesen werden.
		private int position;
		private Color buttonColor = new Color(0, 209, 155);

		EKListItemButton() {
			// Standartmäßige Höhe und Breite des Buttons.
			int hight = 50, width = Ui.getWindowWidh() - 50;

			this.setPreferredSize(new Dimension(width, hight));
			this.setBackground(new Color(0, 209, 155));
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
		public void actionPerformed(ActionEvent e) { 
			ImageIcon greenHook= new ImageIcon("resource\\greenCheck.png");
			greenHook.setImage(greenHook.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
			if (this.isSelected()) { 		//Wenn der Button ausgewählt wird (Der Button setzt sich mit Klick auf den Button sofort auf "isSelected==true" dann...
				
				mainPanel.setComponentZOrder(this, mainPanel.getComponentCount()-1); 			//...wird das Element (der Button) ans Ende der Liste geschoben
				this.setIcon(greenHook);														//...wird ein grüner Haken als Icon hinzugefügt
				
			} else {						//wird er erneut angetippt wird der Haken wierder entfernt und der Button oben in der Liste eingefügt
					
				mainPanel.setComponentZOrder(this, 0);										//
				this.setIcon(null);
				
			}

		}

	}
		
		
}


