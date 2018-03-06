import model.Restaurant;
import parsing.Parser;

/**
 * Manages starting up a {@link Restaurant} and {@link Parser} and coordinating their interaction.
 */
public class Driver {
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
}
