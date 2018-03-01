import java.io.*;

/**
 * Entry point for the program.
 *
 * Controls the starting up of the restaurant program. Parses available configuration files
 * or generates them if they have not been created.
 */
public class Driver {
  private static final String CONFIG_LOCATION       = "phase1/config/";
  private static final String EMPLOYEE_FILE_NAME    = "employees.txt";
  private static final String INGREDIENTS_FILE_NAME = "ingredients.txt";
  private static final String MENUS_FILE_NAME       = "menus.txt";
  private static final String EVENTS_FILE_NAME      = "events.txt";

  public static void main(String[] args) {
    Restaurant restaurant = new Restaurant();

    // Make sure that the configuration file location exists
    Boolean madeNew = new File(CONFIG_LOCATION).mkdir();
    if (madeNew) {
      System.out.println("Created a configuration folder.");
    }

    try {
      restaurant.parseEmployeeConfiguration(getConfigurationReader(EMPLOYEE_FILE_NAME));
      restaurant.parseIngredientConfiguration(getConfigurationReader(INGREDIENTS_FILE_NAME));
      restaurant.parseMenuConfiguration(getConfigurationReader(MENUS_FILE_NAME));
      restaurant.parseEvents(getConfigurationReader(EVENTS_FILE_NAME));
    } catch (IOException e) {
      // TODO: be more delicate
      System.out.println("Parsing of configuration files was not successful.");
      System.exit(1);
    }
  }

  private static BufferedReader getConfigurationReader(String fileName) throws IOException {
    File file = new File(CONFIG_LOCATION + fileName);

    Boolean madeNew = file.createNewFile();
    if (madeNew) {
      System.out.println("Created a " + fileName + " configuration file.");
    }
    return new BufferedReader(new FileReader(file));
  }
}
