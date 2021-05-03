package de.nrw.hspv.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class NewList extends JFrame{

	private static final int scale = 50;
	private static final int WINDOW_WIDH = scale * 9;
	private static final int WINDOW_HIGHT = scale * 16;
	private static final Color BG_COLOR = Color.DARK_GRAY;
	
	
	JPanel uppanel = new JPanel();
	JPanel downpanel = new JPanel();
	JPanel leftpanel = new JPanel();
	JPanel rightpanel = new JPanel();
	JPanel centerpanel = new JPanel();
	

	NewList() {

		this.setSize(WINDOW_WIDH, WINDOW_HIGHT);
		this.getContentPane().setBackground(BG_COLOR);

		class MyWindowAdapter extends WindowAdapter { // nur die Methode implementieren, die wir brauchen, weil die
														// Klasse WindowAdapter das Interface implementiert
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		}
		this.addWindowListener(new MyWindowAdapter());
		this.getContentPane().setLayout(new BorderLayout());
		
		
		initPanels();
		initCenter();
		this.setVisible(true);
		
		}
	public void initPanels() {
		
		uppanel.setBackground(BG_COLOR);
		downpanel.setBackground(BG_COLOR);
		leftpanel.setBackground(BG_COLOR);
		rightpanel.setBackground(BG_COLOR);
		centerpanel.setBackground(Color.RED);
		
		uppanel.setPreferredSize(new Dimension(WINDOW_WIDH,50));
		downpanel.setPreferredSize(new Dimension(WINDOW_WIDH,50));
		leftpanel.setPreferredSize(new Dimension(50,WINDOW_HIGHT));
		rightpanel.setPreferredSize(new Dimension(50,WINDOW_HIGHT));
		centerpanel.setPreferredSize(new Dimension());
		
		this.add(uppanel, BorderLayout.NORTH);
		this.add(downpanel, BorderLayout.SOUTH);
		this.add(leftpanel,BorderLayout.WEST);
		this.add(rightpanel, BorderLayout.EAST); // rechts
		this.add(centerpanel, BorderLayout.CENTER);

//		centerpanel.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));

	}
	
	public void initCenter() {
		
		centerpanel.setLayout(new BorderLayout());
		
		
	
		JPanel chooseCategoryText = new JPanel();
		chooseCategoryText.setBackground(BG_COLOR);
	
		JPanel chooseCategoryPanel = new JPanel();
		chooseCategoryPanel.setBackground(BG_COLOR);
		
		JLabel label = new JLabel();
		label.setText("Wählen Sie eine Kategorie");
		label.setFont(new Font("SansSerif", Font.BOLD, 14));
		label.setOpaque(true);
		label.setBackground(BG_COLOR);
		label.setForeground(Color.WHITE);
		label.setHorizontalTextPosition(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
		
		
		
		JComboBox<String> choosecat = new JComboBox<String>(); 
		choosecat.addItem(new Category(1, "Backwaren").getName());
		choosecat.addItem(new Category(2, "Obst und Gemüse").getName());
		choosecat.addItem(new Category(3, "Fleisch und Fisch").getName());
		choosecat.addItem(new Category(4, "Milchprodukte").getName());
		
//		choosecat.addActionListener(new ActionListener(){
//
//			@Override
//			public void actionPerformed(ActionEvent event) {
//				// TODO Auto-generated method stub
//			if (choosecat)
//			}
//			
//		}
		
		
		chooseCategoryText.add(label);
		chooseCategoryPanel.add(choosecat);
		centerpanel.add(chooseCategoryText, BorderLayout.NORTH);
		centerpanel.add(chooseCategoryPanel, BorderLayout.CENTER);
		
	}
}
