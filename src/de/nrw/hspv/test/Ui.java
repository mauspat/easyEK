package de.nrw.hspv.test;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.JToolTip;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class Ui extends JFrame{
	
	
	//TODO  - Buttons in eigener Klassse erstellen -> spart Code
	//TODO  - JSpinner liefert "Up and Down" Zählmenu für Mengenangabe, JInternalFrame bietet "inneren" Frame (Tipp: deaktiviere windows Baar und co...)
	
	//Scale ist willkürlich gewählt
	private static final int scale= 50;
	private static final int WINDOW_WIDH = scale*9;
	private static final int WINDOW_HIGHT = scale*16;
	private static final Color BG_COLOR = Color.DARK_GRAY; //Eigentliche Backgroundfarbe soll BG_COLOR.brighter() sein!!! Von der Farbe ist es einfacher auszugehen und den Componenten ihre Farben zu zu weisen
	
	//Das CardLyout muss als Instanzvariable gespeichert werden, da das Layout selbst bei der "durchblättern" Methode übergeben werden muss!
	private CardLayout cl = new CardLayout();
	private JPanel mainPanel = new JPanel(cl);
	
	Ui(){			
		this.setSize(WINDOW_WIDH, WINDOW_HIGHT);
		this.getContentPane().setBackground(BG_COLOR);
		this.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {}

			@Override
			public void windowClosing(WindowEvent e) {
				//TODO Dialoge, Speichern hinzufügen!, ACHTUNG: Fenster nicht über "defaulCloseOperation" schließen... speichern, Userabfragen dann usw nicht möglich
				//TODO Adapterklasse basteln
				System.exit(0);
			}

			@Override
			public void windowClosed(WindowEvent e) {}

			@Override
			public void windowIconified(WindowEvent e) {}

			@Override
			public void windowDeiconified(WindowEvent e) {}

			@Override
			public void windowActivated(WindowEvent e) {}

			@Override
			public void windowDeactivated(WindowEvent e) {}
			
		});
		addButtonLine();
		addToolbar();
		addMainPanel();
		this.add(mainPanel, BorderLayout.CENTER);

		this.setVisible(true);
		
		
		
		
	}
	/**
	 * Weist dem Zentrum des Frames das "MainPanel" hinzu.  Dies ist durch ein CardLayout in der Lage, mehrere Panels übereinander zu lagern, wobei nur eins sichtbar ist.
	 * Die auswahl der einzelenen Panels erfolgt über die Buttons. Siehe: {@code addButtonLine()}
	 * Die Auswahl der Panels ist über die Methode {@code mainPanel.show("PanelName")} möglich.
	 * @return void
	 * @category GUI
	 */
	private void addMainPanel() {
		this.add(mainPanel, BorderLayout.CENTER);
		mainPanel.setBackground(BG_COLOR);
		
		mainPanel.add(new ListPanel(),"LIST"); //hinzufügen eines Panel mit Schlüsselwort
		mainPanel.add(new SettingPanel(), "SETTINGS");	
	}
	
	/**
	 * Fügt dem Objekt eine Toolbar mit diversen Buttons hinzu.
	 * @author Lukas Wisniewski
	 * @category GUI
	 */	
	private void addToolbar() {
		
		//Toolbar wird erstellt und mit darauffolgenden Methoden designt
		JToolBar toolbar = new JToolBar();
		toolbar.setBackground(BG_COLOR.darker());
		toolbar.setPreferredSize(new Dimension(WINDOW_WIDH,WINDOW_HIGHT/18) ); 	//Setzt die größe aus 1/22 der Fensterhöhe fest
		toolbar.setBorderPainted(false); 										// hässliche Grenze die gefärbt ist weg
		toolbar.setFloatable(false); 											//Toolbar kann nicht verschoben werden
		toolbar.setBorder(new EmptyBorder(0,30,0,30));							// setzt einen Rand von jeweils 30 Pixeln links und rechts
		
		
		/*
		 * Buttons aufbauen
		 */
		int buttonSizeW = 40;
		int buttonSizeH = 40;
		
		//TODO Buttons ein Icon hinzufügen und Text löschen
		
		//Erstellt die Buttons, die oben in der Toolbar angezeigt werden sollen
		EKButton addList = new EKButton();
		addList.setText("Neue Liste");
	//	addList.setPreferredSize( new Dimension(buttonSizeW, buttonSizeH));//setzt neue Buttongrößen
		
		
		EKButton test1 = new EKButton();
		test1.setText("Do more");
	//	test1.setPreferredSize( new Dimension(buttonSizeW, buttonSizeH));
		
		
		EKButton test2 = new EKButton();
		test2.setText("Do ");
	//	test2.setPreferredSize( new Dimension(buttonSizeW, buttonSizeH));

	/*
	 * Buttons der Toolbar hinzufügen	
	 */
		toolbar.add(test2);
		toolbar.addSeparator(); // fügt Abstand zwischen beiden Buttons ein
		toolbar.add(test1);
		
		toolbar.add(Box.createHorizontalGlue()); //sorgt dafür, dass die darauffolgenden Buttons rechtsbündig sind
		
		toolbar.add(addList);
		
		this.add(toolbar, BorderLayout.NORTH);
		
		
	}

	/**
	 * Fügt dem Objekt unten größere Buttons hinzu. Das Layout ist ein GridLayout mit lediglich einer Zeile und drei Spalten (bei drei Buttons) 
	 */
	private void addButtonLine() {
		JPanel buttonPanel = new JPanel(); //Erstellt Panel, um die MenuButtons aufzunehmen
		buttonPanel.setPreferredSize(new Dimension(WINDOW_WIDH, WINDOW_HIGHT/15)); //So konfiguriert, hatten diese untere ButtonLeiste einen Anteil von 15 % an der zu beginn erstellten Größe. Die Leiste bleibt von der Pixelhöhe her immer gleich!
		buttonPanel.setBackground(BG_COLOR);
		buttonPanel.setLayout(new GridLayout(1,3,4,0));
		
		// Es werden Buttons der Unteren Leiste erstellt und hinzugefügt
		EKButton listButton = new EKButton();
		listButton.setText("Einkaufslisten"); //
		listButton.addActionListener(e->cl.show(mainPanel, "LIST")); //Interface ActionListener hat nur eine zu überschreibene Methode, daher kann auch der Lamda-Ausdruck verwendet werden
		buttonPanel.add(listButton);
		
		EKButton menu = new EKButton();// Erstellt Button um ins Setting Menu zu kommen.
		menu.setText("Menu");		
		menu.addActionListener(e->cl.show(mainPanel, "SETTINGS"));
		buttonPanel.add(menu);
		
		EKButton supermarketButton = new EKButton(); // Erstellt Button, um ins Supermarktmanagementmenu zu kommen.
		supermarketButton.setText("Supermärkte");		
		buttonPanel.add(supermarketButton);
		
		
		this.add(buttonPanel, BorderLayout.SOUTH);
	}
	public CardLayout getCl() {
		return cl;
	}
	public void setCl(CardLayout cl) {
		this.cl = cl;
	}
	public JPanel getMainPanel() {
		return mainPanel;
	}
	public void setMainPanel(JPanel mainPanel) {
		this.mainPanel = mainPanel;
	}
	public static int getScale() {
		return scale;
	}
	public static int getWindowWidh() {
		return WINDOW_WIDH;
	}
	public static int getWindowHight() {
		return WINDOW_HIGHT;
	}
	public static Color getBgColor() {
		return BG_COLOR;
	}
	
	
}
