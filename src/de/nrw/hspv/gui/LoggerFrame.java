package de.nrw.hspv.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import de.nrw.hspv.backend.MyLogger;

public class LoggerFrame extends JFrame {
	
	/**
	 * eigener Frame, der sich jedes mal öffnet, sobald ein Levels aus dem JPopUpMenu ausgewählt wird
	 * kann einfach verborgen werden, durch 
	 */

	LoggerFrame() {

		this.setTitle("Logging Datei");
		this.setSize(450, 400);
		this.setLocationRelativeTo(UI.getMainPanel());
		this.setResizable(false);
		this.getContentPane().setBackground(UI.getBgColor().brighter().brighter());

		class MyWindowAdapter extends WindowAdapter {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		}
		this.addWindowListener(new MyWindowAdapter());

		initPanel();

	}

	/**
	 * Logger Datei einlesen und in TextArea ausgeben
	 */
	public void initPanel() {

		JTextArea logText = new JTextArea();
		logText.setSize(new Dimension(this.getWidth(), this.getHeight()));

		Scanner scanFile;
		try {
			scanFile = new Scanner(new File("resource/log/Logging.txt"));
			while (scanFile.hasNext()) {

				logText.append(scanFile.nextLine() + "\n");
			}
			scanFile.close();

		} catch (FileNotFoundException e) {

			e.printStackTrace();
			MyLogger.getInstance().getLogger().severe("Logger-File konnt enicht gefunden werden!");
		}
		JScrollPane scroll = new JScrollPane(logText);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.add(scroll);

	}

}
