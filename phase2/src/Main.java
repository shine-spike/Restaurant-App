/**
 * Entry point to the program. Manages some low-level aspects of the program.
 */
class Main {
  /**
   * Low-level flags to determine whether or not to read in information from files.
   */
  private static final boolean READ_CONFIGURATION = true;
  private static final boolean READ_EVENTS = true;

  /**
   * Low-level flag to determine whether or not to log to file.
   */
  private static final boolean LOG_TO_FILE = true;


  public static void main(String[] args) {
    Driver driver = new Driver();

    if (LOG_TO_FILE) {
      driver.startLogger();
    }
    if (READ_CONFIGURATION) {
      driver.readConfiguration();
    }
    if (READ_EVENTS) {
      driver.readEvents();
    }
  }
}
