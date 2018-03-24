package GUI;

import controller.Restaurant;
import org.jetbrains.annotations.NotNull;
import parsing.Parser;

/**
 * Manages starting up a {@link Restaurant} and {@link Parser} and coordinating their interaction.
 */
public class Driver {
  @NotNull
  private final Restaurant restaurant;
  @NotNull
  private final Parser parser;

  /** Constructs a driver with a default {@link Restaurant} and {@link Parser}. */
  Driver() {
    restaurant = Restaurant.getInstance();
    parser = new Parser(restaurant);
  }

  /**
   * Returns the {@link Restaurant} that this Driver is managing.
   *
   * @return the managed {@link Restaurant}.
   */
  @NotNull
  public Restaurant getRestaurant() {
    return restaurant;
  }

  /**
   * Returns the {@link Parser} associated with this Driver.
   *
   * @return the associated {@link Parser}.
   */
  @NotNull
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

  /** Reads and parses configuration files from the stored files location. */
  public void readConfiguration() {
    parser.parseConfiguration();
  }
}
