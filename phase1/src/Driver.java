public class Driver {
  private static final int DEFAULT_NUM_TABLES = 100;

  private static final boolean READ_CONFIGURATION = true;
  private static final boolean READ_EVENTS = true;

  private Restaurant restaurant;
  private Parser parser;


  Driver(int numTables) {
    restaurant = new Restaurant(numTables);
    parser = new Parser(restaurant);
  }

  Driver() {
    this(DEFAULT_NUM_TABLES);
  }

  public Restaurant getRestaurant() {
    return restaurant;
  }

  public Parser getParser() {
    return parser;
  }

  public void setFilesLocation(String location) {
    parser.setFilesLocation(location);
  }

  private void readConfiguration() {
    parser.parseConfiguration();
  }

  private void readEvents() {
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
