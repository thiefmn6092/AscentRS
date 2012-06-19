package net.thiefmn6092.ascent;

import net.thiefmn6092.ascent.net.Server;
import net.thiefmn6092.ascent.script.ScriptManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The main class.
 * @author thiefmn6092
 *
 */
public class Ascent {
	
	/**
	 * The SLF4J {@link org.slf4j.Logger} instance.
	 */
	private static final Logger logger = LoggerFactory.getLogger(Ascent.class);

	/**
	 * The JVM entry point.
	 * @param args The command-line arguments.
	 */
	public static void main(String[] args) {
		try {
			logger.info("Initializing Ascent RuneScape 2 Server Emulator...");
			ScriptManager.getSingleton().loadScripts("./scripts/");
			new Server().bind(43954);
			Engine.class.newInstance();
			logger.info("Done.");
		} catch(Exception e) {
			logger.error("Error initializing Ascent.", e);
		}
	}

}
