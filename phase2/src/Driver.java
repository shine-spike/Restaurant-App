import model.Restaurant;
import parsing.Parser;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Manages starting up a {@link Restaurant} and {@link Parser} and coordinating their interaction.
 */
public class Driver {
  // Logging system
  private static final String LOG_FILE_LOCATION = "phase2/log.txt";
  private final Logger logger = Logger.getLogger("Restaurant");

  private final Restaurant restaurant;
  private final Parser parser;


  /**
   * Constructs a driver with a default {@link Restaurant} and {@link Parser}.
   */
  Driver() {
    restaurant = Restaurant.getInstance();
    parser = new Parser(restaurant);

    // Do print logs to console and set log format
    logger.setUseParentHandlers(false);
    System.setProperty("java.util.logging.SimpleFormatter.format",
            "%1$tF %1$tT %5$s%6$s%n");
  }

  /**
   * Starts the logger to log information to file.
   */
  public void startLogger() {
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

  /**
   * Returns the {@link Restaurant} that this Driver is managing.
   *
   * @return the managed {@link Restaurant}.
   */
  public Restaurant getRestaurant() {
    return restaurant;
  }

  /**
   * Returns the {@link Parser} associated with this Driver.
   *
   * @return the associated {@link Parser}.
   */
  public Parser getParser() {
    return parser;
  }

  /**
   * Changes the location of the configuration and event files.
   *
   * @param location the new location of the files.
   */
  public void setFilesLocation(String location) {
    parser.setFilesLocation(location);
  }

  /**
   * Reads and parses configuration files from the stored files location.
   */
  public void readConfiguration() {
    parser.parseConfiguration();
  }

  /**
   * Reads and parses events from the stored files location.
   */
  public void readEvents() {
    parser.parseEvents();
  }
}
