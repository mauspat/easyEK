package de.nrw.hspv.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import de.nrw.hspv.backend.ShoppingList;



public class ListOverviewPanel extends JPanel{
	
	ArrayList<ShoppingList> allShoppingLists = ShoppingList.getAllShoppingLists();
	
	ListOverviewPanel(){
		
		
		this.setBackground(UI.getBgColor().brighter());
		
		for (ShoppingList sl: allShoppingLists) {
			EKButton button = new EKButton();
			button.setText(sl.getName());
			button.setPreferredSize(new Dimension(300,50));
			button.addActionListener(new ActionListener() {

				@Override
				//Der Button erstellt eine neue Ebene auf dem CardLayout der Klasse UI. Anschließend wird das erstellte Panel aufgerufen und damit eingeblendet.
				public void actionPerformed(ActionEvent e) {
					
					UI.getMainPanel().add(new ListPanel(sl), "actualList");
					UI.getCl().show(UI.getMainPanel(), "actualList");
					
				}

			});
			this.add(button);
		}
		
		
		
		
	}
	
}
