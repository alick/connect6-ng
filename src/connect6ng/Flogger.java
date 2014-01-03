package connect6ng;

import java.util.logging.Logger;

public class Flogger {

	private static final Logger fLogger = Logger.getLogger(GameController.class
			.getPackage().getName());

	public static Logger getLogger() {
		return fLogger;
	}
}
