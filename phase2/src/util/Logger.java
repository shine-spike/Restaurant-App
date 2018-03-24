package util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

public class Logger {
  private static final String LOG_FILE_LOCATION = "phase2/log.txt";
  private static final String LOGGER_NAME = "Restaurant Logger";

  private static final java.util.logging.Logger logger =
      java.util.logging.Logger.getLogger(LOGGER_NAME);

  /** Starts the logger to log information to file. */
  public static void startLogger() {
    // Do not print logs to console and set log format
    logger.setUseParentHandlers(false);
    System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tF %1$tT %5$s%6$s%n");

    try {
      // Configure the logger with formatting
      FileHandler fileHandler = new FileHandler(LOG_FILE_LOCATION);
      logger.addHandler(fileHandler);

      SimpleFormatter formatter = new SimpleFormatter();
      fileHandler.setFormatter(formatter);
    } catch (IOException e) {
      System.out.println("Logging system could not be started.");
    }
  }

  public static void log(String message) {
    logger.log(Level.INFO, message);
  }
}
