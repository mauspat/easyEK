package de.nrw.hspv.backend;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Carla Freitag
 *
 */
public class MyLogger {

	private static final MyLogger logger = new MyLogger(); // kann nur ein Ojekt erzeugt werden und weitergehend durch
															// @getInstance genutzt werden
	private final Logger log = Logger.getLogger(MyLogger.class.getName());

	private MyLogger() { // private, damit kein neues Objekt in anderen Klassen erzeugt werden kann

		try {
			log.addHandler(new FileHandler("resource/LoggerFile.txt", true)); // File Handler schreibt Logs/Einträge in
																				// eine oder mehrer Dateien, hier eine
		} catch (SecurityException e) { // addHandler und File Handler werfen SecurityException

			e.printStackTrace();
		} catch (IOException e) {		//FileHandler wirft IOException

			e.printStackTrace();
		}
		log.setLevel(Level.FINEST); // falls Level.CONFIG : alle Levels unter Config werden nicht aufgenommen oder
									// berücksichtigt
	}

	// --------GETTERS---------
	public static MyLogger getInstance() {
		return logger;
	}

	public Logger getLogger() {
		return logger.log;
	}
	
	
}
