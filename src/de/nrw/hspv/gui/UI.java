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
import java.util.logging.Level;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import de.nrw.hspv.backend.MyLogger;

public class UI extends JFrame {

	/**
	 * scale gibt den Faktor an, mit dem die Größe des Fensters im Verhältnis 9:16 bestimmt wird
	 */
	private static final int scale = 50;
	private static final int WINDOW_WIDH = scale * 9;
	private static final int WINDOW_HIGHT = scale * 16;
	private static final Color BG_COLOR = Color.DARK_GRAY; // Eigentliche Backgroundfarbe soll BG_COLOR.brighter()
															// sein!!! Von der Farbe ist es einfacher auszugehen und den
															// Componenten ihre Farben zu zu weisen

	/**
	 * cl ist das CardLayout des Programms. Es ermöglicht ein durchblättern durch die verschiedenen Panels ohne dabei alle komponenten, wie z.b. die untere 
	 * Buttonleiste neu laden zu müssen.
	 */
	private static CardLayout cl = new CardLayout();
	private static JPanel mainPanel = new JPanel(cl);
	
	/**
	 * Die Toolbar ist di obere Leiste, in der variable Button angelegt werden können. Der Button "neue Liste" ist immer vorhanden.
	 */
	private static JToolBar toolbar = new JToolBar();

	private static JPanel listOverviewPanel;
	private static JPanel productPanel;
	private static JPanel listPanel;
	private static JPanel supermarketPanel;

	private NewListFrame newList = new NewListFrame();
	LoggerFrame LogFrame = new LoggerFrame();

	public UI() {
		this.setTitle("easyEK");
		this.setSize(WINDOW_WIDH, WINDOW_HIGHT);
		this.getContentPane().setBackground(BG_COLOR);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int answer = JOptionPane.showConfirmDialog(mainPanel,
						"<html>Möchten Sie das Programm schließen?<br>Alle Änderungen werden automatisch gespeichert!</html>",
						"schließen", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				
				//Nur wenn der User die Warning Massage mit ok bestätigt, wird das  Prgramm geschlossen.
				if (answer == 0) {
					MyLogger.getInstance().getLogger().log(Level.INFO, "Programm wurde beendet");
					System.exit(0);
				}
			}
		});

		addButtonLine();
		addToolbar();
		addMainPanel();
		addRightClick();
		this.add(mainPanel, BorderLayout.CENTER);
		this.setVisible(true);

	}

	/**
	 * Erzeugt ein PopupMenu. Es dient dem Logging und wird erstellt mit einem Klick
	 * auf die rechte Maustaste. 
	 * Auswahl zwischen den Leveln: OFF, INFO, SEVERE (Standard vor Auswahl: INFO)
	 * Mit Auswahl eines Levels öffnet sich der LoggerFrame, welches einfach wieder verborgen werden kann
	 */
	public void addRightClick() {

		JPopupMenu logging = new JPopupMenu();

		JMenuItem off = new JMenuItem("OFF");
		JMenuItem info = new JMenuItem("INFO");
		JMenuItem severe = new JMenuItem("SEVERE");
		logging.add(off);
		logging.add(info);
		logging.add(severe);

		off.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					MyLogger.getInstance().getLogger().info("Level gewechselt zu: OFF");
					MyLogger.getInstance().getLogger().setLevel(Level.OFF);
					LogFrame.initPanel(); // Methode nochmal aufrufen, damit die Logging-File "aktuell" ausgegeben wird
					LogFrame.setVisible(true);
				} catch (SecurityException e1) {
		
					e1.printStackTrace();
				}
				System.out.println(MyLogger.getInstance().getLogger().getLevel());
			}
		});

		info.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					MyLogger.getInstance().getLogger().info("Level gewechselt zu: INFO");
					LogFrame.initPanel();
					MyLogger.getInstance().getLogger().setLevel(Level.INFO);
					LogFrame.setVisible(true);
				} catch (SecurityException e1) {
				
					e1.printStackTrace();
				}
				System.out.println(MyLogger.getInstance().getLogger().getLevel());
			}
		});

		severe.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					MyLogger.getInstance().getLogger().info("Level gewechselt zu: SEVERE");
					MyLogger.getInstance().getLogger().setLevel(Level.SEVERE);
					LogFrame.initPanel();
					LogFrame.setVisible(true);
				} catch (SecurityException e1)
				{
					
					e1.printStackTrace();
				}
				System.out.println(MyLogger.getInstance().getLogger().getLevel());
			}
		});

		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				if (SwingUtilities.isRightMouseButton(e)) {

					logging.show(UI.this, e.getX(), e.getY());
				}
			}
		});
		this.add(logging);

	}
	

	/**
	 * Weist dem Zentrum des Frames das "MainPanel" hinzu. Dies ist durch ein
	 * CardLayout in der Lage, mehrere Panels übereinander zu lagern, wobei nur eins
	 * sichtbar ist. Die auswahl der einzelenen Panels erfolgt über die Buttons.
	 * Siehe: {@code addButtonLine()} Die Auswahl der Panels ist über die Methode
	 * {@code mainPanel.show("PanelName")} möglich.
	 * 
	 * 
	 * @category GUI
	 */
	private void addMainPanel() {
		this.add(mainPanel, BorderLayout.CENTER);
		mainPanel.setBackground(BG_COLOR);

		UI.listOverviewPanel = new ListOverviewPanel();
		mainPanel.add(UI.listOverviewPanel, "ListOverview"); // hinzufügen eines Panel mit Schlüsselwort

		UI.productPanel = new ProductPanel();
		mainPanel.add(UI.productPanel, "Products");

		UI.supermarketPanel = new SupermarketPanel();
		mainPanel.add(UI.supermarketPanel, "Supermarkets");

		UI.listPanel = new ListPanel();
		mainPanel.add(UI.listPanel, "actualList");
	}

	/**
	 * Fügt dem Objekt eine Toolbar mit diversen Buttons hinzu.
	 * 
	 * @author Lukas Wisniewski
	 * @category GUI
	 */
	private void addToolbar() {

		// Toolbar wird erstellt und mit darauffolgenden Methoden designt
		toolbar.setBackground(BG_COLOR.darker());
		toolbar.setPreferredSize(new Dimension(WINDOW_WIDH, WINDOW_HIGHT / 18)); // Setzt die größe aus 1/18 der
																					// Fensterhöhe fest
		toolbar.setBorderPainted(false); // hässliche Grenze die gefärbt ist weg
		toolbar.setFloatable(false); // Toolbar kann nicht verschoben werden
		toolbar.setBorder(new EmptyBorder(0, 30, 0, 30)); // setzt einen Rand von jeweils 30 Pixeln links und rechts

		/*
		 * Buttons aufbauen
		 */
		int buttonSizeW = 40;
		int buttonSizeH = 40;

		// Erstellt die Buttons, die oben in der Toolbar angezeigt werden sollen
		EKButton addList = new EKButton();
		addList.setText("Neue Liste");
		addList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				newList.setVisible(true);
			}

		});

		toolbar.add(addList);
		toolbar.add(Box.createHorizontalGlue()); // sorgt dafür, dass die darauffolgenden Buttons rechtsbündig sind

		this.add(toolbar, BorderLayout.NORTH);

	}

	/**
	 * Fügt dem Objekt unten größere Buttons hinzu. Das Layout ist ein GridLayout
	 * mit lediglich einer Zeile und drei Spalten (bei drei Buttons)
	 */
	private void addButtonLine() {
		JPanel buttonPanel = new JPanel(); // Erstellt Panel, um die MenuButtons aufzunehmen
		buttonPanel.setPreferredSize(new Dimension(WINDOW_WIDH, WINDOW_HIGHT / 15)); // So konfiguriert, hatten diese
																						// untere ButtonLeiste einen
																						// Anteil von 15 % an der zu
																						// beginn erstellten Größe. Die
																						// Leiste bleibt von der
																						// Pixelhöhe her immer gleich!
		buttonPanel.setBackground(BG_COLOR);
		buttonPanel.setLayout(new GridLayout(1, 3, 4, 0));

		// Es werden Buttons der Unteren Leiste erstellt und hinzugefügt
		EKButton listButton = new EKButton();
		listButton.setText("Einkaufslisten"); //
		listButton.addActionListener(e -> {
			cl.show(mainPanel, "ListOverview");
			UI.changeToolbarButton();
		});
		buttonPanel.add(listButton);

		EKButton products = new EKButton();// Erstellt Button um ins Setting Menu zu kommen.
		products.setText("Produkte");
		products.addActionListener(e -> {
			cl.show(mainPanel, "Products");
			UI.changeToolbarButton();
		});
		buttonPanel.add(products);

		EKButton supermarketButton = new EKButton(); // Erstellt Button, um ins Supermarktmanagementmenu zu kommen.
		supermarketButton.setText("Supermärkte");
		supermarketButton.addActionListener(e -> {
			cl.show(mainPanel, "Supermarkets");
		});
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

	public static JPanel getListOverviewPanel() {
		return listOverviewPanel;
	}

	public static void setListOverviewPanel(JPanel listOverviewPanel) {
		UI.listOverviewPanel = listOverviewPanel;
	}

	public static JPanel getProductPanel() {
		return productPanel;
	}

	public static void setProductPanel(JPanel productPanel) {
		UI.productPanel = productPanel;
	}

	public static JPanel getListPanel() {
		return listPanel;
	}

	public static void setListPanel(JPanel listPanel) {
		UI.listPanel = listPanel;
	}

	/**
	 * Beim Aufruf der Methode werden alle Buttons, welche rechts in der Toolbar
	 * liegen gelöscht. Anschließend werden alle übergebenen Buttons der Toolbar
	 * hinzugefügt. Es können beliebig viele Buttons übergeben werden. Wenn keine
	 * Buttons übergeben werden, werden nur die vorhandenen Buttons gelöscht.
	 * 
	 * @param EKbuttons
	 */
	public static void changeToolbarButton(EKButton... EKbuttons) {

		toolbar.getComponentCount();

		for (int i = toolbar.getComponentCount() - 1; i > 1; i--) {
			toolbar.remove(i);
		}

		if (EKbuttons != null) {
			for (int i = 0; i < EKbuttons.length; i++) {
				toolbar.add(Box.createHorizontalStrut(25));
				toolbar.add(EKbuttons[i]);
			}
		}
		toolbar.repaint();
	}
	
}
