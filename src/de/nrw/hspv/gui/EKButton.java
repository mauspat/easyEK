package de.nrw.hspv.gui;

import java.awt.Color;

import javax.swing.JButton;



public class EKButton extends JButton {
	
	
	EKButton(){
		setBackground(UI.getBgColor().darker());
		setForeground(Color.WHITE);
		setBorderPainted(false);
		setFocusPainted(false);
	}
	
	EKButton(String name){
		super(name);
		setBackground(UI.getBgColor().darker());
		setForeground(Color.WHITE);
		setBorderPainted(false);
		setFocusPainted(false);
	}
}
