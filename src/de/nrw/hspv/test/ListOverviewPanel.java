package de.nrw.hspv.test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ListOverviewPanel extends JPanel{
	
	
	//TODO Actual list muss mit geklicktem Button verbunden werden, damit die richtige Liste an das ListPanel übergeben wird.
	private ArrayList<String> actualList01 = new ArrayList<String>();
	private ArrayList<String> actualList02= new ArrayList<String>();
	
	ListOverviewPanel(){
		
		//TODO Main Panel wieder auf private setzten
		JButton button = new JButton();
		
		actualList01.add("Stuff");
		button.setText("Liste 1");
		button.addActionListener(new ActionListener() {

			@Override
			//Der Button erstellt eine neue Ebene auf dem CardLayout der Klasse Ui. Anschließend wird das erstellte Panel aufgerufen und damit eingeblendet.
			public void actionPerformed(ActionEvent e) {
				Ui.getMainPanel().add(new ListPanel(actualList01), "actualList");
				Ui.getCl().show(Ui.getMainPanel(), "actualList");
			
			}
			
		});
		this.add(button);
		
		
		
		JButton button2 = new JButton();
		actualList02.add("Mango Mango");
		actualList02.add("Banananaaaaana nana");
		actualList02.add("MilleniumFalke");
		actualList02.add("StormTrooper");
		
		button2.setText("Liste 2");
		button2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			Ui.getMainPanel().add(new ListPanel(actualList02), "actualList");
			Ui.getCl().show(Ui.getMainPanel(), "actualList");
			}
			
		});
		this.add(button2);
	}
	
}
