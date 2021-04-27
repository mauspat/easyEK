package de.nrw.hspv.test;

import java.awt.Color;

import javax.swing.JButton;

/**
 * Hilfsklasse, die einige Standartparameter für die Buttons in der App konfiguriert. Es muss nicht jedes mal die Farbe bestimmt werden und die "Effektlinien" entfernt werden.
 */
public class EKButton extends JButton {
	
	
	EKButton(){
		setBackground(Ui.getBgColor().darker());
		setForeground(Color.WHITE);
		setBorderPainted(false);
		setFocusPainted(false);
	}
}
