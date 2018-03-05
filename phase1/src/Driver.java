/**
 * Entry point to the program.
 * <p>
 * Manages starting up a {@link Restaurant} and {@link Parser}.
 */
public class Driver {
  /**
   * Low-level flags to determine whether or not to read in information from files.
   */
  private static final boolean READ_CONFIGURATION = true;
  private static final boolean READ_EVENTS = true;

  private Restaurant restaurant;
  private Parser parser;


  /**
   * Constructs a driver with a default {@link Restaurant} and {@link Parser}.
   */
  Driver() {
    restaurant = new Restaurant();
    parser = new Parser(restaurant);
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
   * Reads and parses configuration files.
   */
  public void readConfiguration() {
    parser.parseConfiguration();
  }

  /**
   * Reads and parses events.
   */
  public void readEvents() {
    parser.parseEvents();
  }


  public static void main(String[] args) {
    Driver driver = new Driver();

    if (READ_CONFIGURATION) {
      driver.readConfiguration();
    }
    if (READ_EVENTS) {
      driver.readEvents();
    }
  }
}
