package de.nrw.hspv.backend;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Standardmäßig wird auf INFO Level geloggt, es sei denn man wählt SEVERE oder OFF aus dem JPopupMenu siehe @UI 
 */
public class MyLogger {

	private static final MyLogger logger = new MyLogger(); // kann nur ein Ojekt erzeugt werden und weitergehend durch
															// @getInstance genutzt werden
	private final Logger log = Logger.getLogger(MyLogger.class.getName());
	


	private MyLogger() { // private, damit kein neues Objekt in anderen Klassen erzeugt werden kann
		
		try {
			log.addHandler(new FileHandler("resource/log/Logging.txt"));
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		log.setLevel(Level.INFO);
	}
	
	
	
	
	// --------GETTERS---------
	public static MyLogger getInstance() {
		return logger;
	}

	public Logger getLogger() {
		return logger.log;
	}

}
