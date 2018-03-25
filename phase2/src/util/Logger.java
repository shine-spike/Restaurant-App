package util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

/**
 * Controls and executes all logging for the program. The logger must be started before it will log
 * anything to file.
 */
public class Logger {
  private static final String LOG_FILE_LOCATION = "phase2/log.txt";
  private static final String LOGGER_NAME = "Restaurant Logger";

  private static final String ORDER_LOG_MESSAGE_FORMAT =
      "[ORDER %1$d : %2$s] Order %1$d has been %3$s.";
  private static final String BILL_LOG_MESSAGE_FORMAT =
      "[BILL %1$d : %2$s] Bill for table %1$d has been %3$s.";
  private static final String INVENTORY_LOG_MESSAGE_FORMAT =
      "[INVENTORY : %1$s] %2$d of ingredient %3$s have been %4$s.";
  private static final String REQUEST_LOG_MESSAGE_FORMAT =
      "[REQUEST] Ingredient %1$s has been requested for reorder.";

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
      // TODO: remove print
      System.out.println("Logging system could not be started.");
    }
  }

  /**
   * Log the given message in the info level of the logger.
   *
   * @param message the message to log.
   */
  private static void log(String message) {
    logger.log(Level.INFO, message);
  }

  /**
   * Logs an order event with the given order number, category, and details.
   *
   * @param orderNumber the number of the order in the event.
   * @param category the category of this order event.
   * @param details extended details as to what happened in past tense.
   */
  public static void orderLog(int orderNumber, String category, String details) {
    log(String.format(ORDER_LOG_MESSAGE_FORMAT, orderNumber, category, details));
  }

  /**
   * Logs a bill event with the given table number, category, and details.
   *
   * @param tableNumber the number of the table in the event.
   * @param category the category of this bill event.
   * @param details extended details as to what happened in past tense.
   */
  public static void billLog(int tableNumber, String category, String details) {
    log(String.format(BILL_LOG_MESSAGE_FORMAT, tableNumber, category, details));
  }

  /**
   * Logs an inventory event with the given category, amount of given ingredient name, and details.
   *
   * @param category the category of this inventory event.
   * @param amount the number of ingredient in this inventory event.
   * @param ingredientName the name of the ingredient in the inventory event.
   * @param details extended details as to what happened in past tense.
   */
  public static void inventoryLog(
      String category, int amount, String ingredientName, String details) {
    log(String.format(INVENTORY_LOG_MESSAGE_FORMAT, category, amount, ingredientName, details));
  }

  /**
   * Logs an ingredient reorder event.
   *
   * @param ingredientName the name of the ingredient reordered.
   */
  public static void reorderLog(String ingredientName) {
    log(String.format(REQUEST_LOG_MESSAGE_FORMAT, ingredientName));
  }
}
