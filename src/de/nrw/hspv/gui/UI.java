package de.nrw.hspv.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;



public class UI extends JFrame{
	
	

	//TODO  - JSpinner liefert "Up and Down" Zählmenu für Mengenangabe, JInternalFrame bietet "inneren" Frame (Tipp: deaktiviere windows Baar und co...)
	
	//Scale ist willkürlich gewählt
	private static final int scale= 50;
	private static final int WINDOW_WIDH = scale*9;
	private static final int WINDOW_HIGHT = scale*16;
	private static final Color BG_COLOR = Color.DARK_GRAY; //Eigentliche Backgroundfarbe soll BG_COLOR.brighter() sein!!! Von der Farbe ist es einfacher auszugehen und den Componenten ihre Farben zu zu weisen

	
	//Das CardLyout muss als Instanzvariable gespeichert werden, da das Layout selbst bei der "durchblättern" Methode übergeben werden muss!
	private static CardLayout cl = new CardLayout();
	private static JPanel mainPanel = new JPanel(cl);
	private static JToolBar toolbar = new JToolBar();
	
	UI(){			
		this.setSize(WINDOW_WIDH, WINDOW_HIGHT);
		this.getContentPane().setBackground(BG_COLOR);
		
		class MyWindowAdapter extends WindowAdapter{
			public void windowClosing(WindowEvent e) {
				//TODO Dialoge, Speichern hinzufügen!, ACHTUNG: Fenster nicht über "defaulCloseOperation" schließen... speichern, Userabfragen dann usw nicht möglich
				System.exit(0);
			}
		}
		this.addWindowListener(new MyWindowAdapter());		
		addButtonLine();
		addToolbar();
		addMainPanel();
		addRightClick();
		this.add(mainPanel, BorderLayout.CENTER);
		this.setVisible(true);
	}
	
	
	
	
	
	/**
	 * Erzeugt ein PopupMenu. Es dient dem Logging und wird erstellt mit einem Klick auf die rechte Maustaste.
	 */
	public void addRightClick() {
		
		JPopupMenu logging = new JPopupMenu();
		
		JMenuItem log = new JMenuItem("Log!");
		logging.add(log);
		
		log.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("Öffne Logging Klasse oder so");
			}
			
		});

		
		this.addMouseListener(new MouseAdapter(){
			public void mouseClicked (MouseEvent e) {
				
				if(SwingUtilities.isRightMouseButton(e)) {
					
				logging.show(UI.this,e.getX(), e.getY());
			}
			}
		});
		this.add(logging);
		
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
		
		mainPanel.add(new ListOverviewPanel(),"ListOverview"); //hinzufügen eines Panel mit Schlüsselwort
		mainPanel.add(new ProductPanel(), "Produkts");
	}
	
	/**
	 * Fügt dem Objekt eine Toolbar mit diversen Buttons hinzu.
	 * @author Lukas Wisniewski
	 * @category GUI
	 */	
	private void addToolbar() {
		
		//Toolbar wird erstellt und mit darauffolgenden Methoden designt
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
		addList.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				NewListFrame Liste1 = new NewListFrame();
				
			}
			
		});
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
		
		toolbar.add(addList);
		toolbar.add(Box.createHorizontalGlue()); //sorgt dafür, dass die darauffolgenden Buttons rechtsbündig sind
		toolbar.add(test2);
		toolbar.addSeparator(); // fügt Abstand zwischen beiden Buttons ein
		toolbar.add(test1);
		
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
		listButton.addActionListener(e->cl.show(mainPanel, "ListOverview")); //Interface ActionListener hat nur eine zu überschreibene Methode, daher kann auch der Lamda-Ausdruck verwendet werden
		buttonPanel.add(listButton);
		
		EKButton menu = new EKButton();// Erstellt Button um ins Setting Menu zu kommen.
		menu.setText("Produkte");		
		menu.addActionListener(e->cl.show(mainPanel, "Produkts"));
		buttonPanel.add(menu);
		
		EKButton supermarketButton = new EKButton(); // Erstellt Button, um ins Supermarktmanagementmenu zu kommen.
		supermarketButton.setText("Supermärkte");		
		buttonPanel.add(supermarketButton);
		
		
		this.add(buttonPanel, BorderLayout.SOUTH);
	}
	public static CardLayout getCl() {
		return cl;
	}
	public static void setCl(CardLayout cl) {
		UI.cl = cl;
	}
	public static JPanel getMainPanel() {
		return mainPanel;
	}
	public static void setMainPanel(JPanel mainPanel) {
		UI.mainPanel = mainPanel;
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
	public static JToolBar getToolbar() {
		return toolbar;
	}
	public static void setToolbar(JToolBar toolbar) {
		UI.toolbar = toolbar;
	}

	
	/**
	 * Beim Aufruf der Methode werden alle Buttons, welche rechts in der Toolbar liegen gelöscht. Anschließend werden alle übergebenen Buttons der Toolbar hinzugefügt.
	 * Es können beliebig viele Buttons übergeben werden. Wenn keine Buttons übergeben werden, werden nur die vorhandenen Buttons gelöscht.
	 * @param EKbuttons
	 */
	public static void ChangeToolbarButton(EKButton...EKbuttons) {
		
		toolbar.getComponentCount();
		
		for(int i=toolbar.getComponentCount()-1; i>1; i--) {
			toolbar.remove(i);
		}
		
		if(EKbuttons!=null) {
			for(int i=0; i<EKbuttons.length; i++) 
				toolbar.add(EKbuttons[i]);
				System.out.println(EKbuttons.length);
		}
		toolbar.repaint();
	}
	
	
	
}