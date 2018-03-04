import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


/**
 * Controls all file input parsing from configuration files and provides an interface for parsing event files.
 *
 * Passes on the parsed information to the respective Restaurant.
 */
public class Parser {
  /**
   * Flags that a line in a file should be ignored.
   */
  private static final char COMMENT_SYMBOL = '%';

  // The names of all the files we need to access.
  private static final String EMPLOYEE_FILE_NAME = "employees.txt";
  private static final String INGREDIENTS_FILE_NAME = "ingredients.txt";
  private static final String MENUS_FILE_NAME = "menus.txt";
  private static final String EVENTS_FILE_NAME = "events.txt";

  private String filesLocation = "phase1/config/";
  private Restaurant restaurant;


  /**
   * Constructs a Parser for a given restaurant.
   *
   * @param restaurant the Restaurant to pass in the information to.
   */
  Parser(Restaurant restaurant) {
    this.restaurant = restaurant;
  }

  /**
   * Changes the location of the files that need to be parsed.
   *
   * @param filesLocation the new location of the files.
   */
  public void setFilesLocation(String filesLocation) {
    this.filesLocation = filesLocation;
  }

  /**
   * Parses all the configuration files needed.
   */
  public void parseConfiguration() {
    // Make sure that the configuration file location exists and
    // notify if it was created right now
    Boolean madeNew = new File(filesLocation).mkdir();
    if (madeNew) {
      System.out.println("Created a configuration folder.");
    }

    // Generate the readers for each type of configuration file.
    BufferedReader employeeReader;
    BufferedReader ingredientReader;
    BufferedReader menuReader;

    try {
      employeeReader = getConfigurationReader(EMPLOYEE_FILE_NAME);
      ingredientReader = getConfigurationReader(INGREDIENTS_FILE_NAME);
      menuReader = getConfigurationReader(MENUS_FILE_NAME);
    } catch (IOException e) {
      // TODO: be more delicate
      System.out.println("Finding configuration files failed.");
      System.exit(1);
      return;
    }

    // Parse each type of configuration file and the events file
    try {
      parseEmployeeConfiguration(employeeReader);
      parseIngredientConfiguration(ingredientReader);
      parseMenuConfiguration(menuReader);
    } catch (IOException e) {
      // TODO: be more delicate
      System.out.println("Parsing of configuration files was not successful.");
      System.exit(1);
    }
  }

  /**
   * Parses the event file and passes on the information.
   */
  public void parseEvents() {
    BufferedReader eventReader;
    try {
      eventReader = getConfigurationReader(EVENTS_FILE_NAME);
    } catch (IOException e) {
      // TODO: be more delicate
      System.out.println("Finding configuration files failed.");
      System.exit(1);
      return;
    }

    try {
      parseEventsFile(eventReader);
    } catch (IOException e) {
      // TODO: be more delicate
      System.out.println("Parsing of configuration files was not successful.");
      System.exit(1);
    }
  }

  /**
   * Helper method for generating a reader for each file, and creating it if it does not exist.
   *
   * @param fileName the name of the file to generate a reader for.
   * @return the reader generated.
   * @throws IOException if something went horribly wrong in creating files.
   */
  private BufferedReader getConfigurationReader(String fileName) throws IOException {
    File file = new File(filesLocation + fileName);

    Boolean madeNew = file.createNewFile();
    if (madeNew) {
      System.out.println("Created a " + fileName + " configuration file.");
    }
    return new BufferedReader(new FileReader(file));
  }

  /**
   * Helper method for pre-parsing a given reader, splitting it up into parts and discarding any lines that
   * have no impact such as comments.
   *
   * @param reader the reader to generate the symbols from.
   * @return the array of symbols representing the different parts of the line read.
   * @throws IOException if something went horribly wrong in reading files.
   */
  private String[] preParse(BufferedReader reader) throws IOException {
    String[] symbols;

    do {
      String line = reader.readLine();
      if (line != null) {
        if (line.equals("") || line.charAt(0) == COMMENT_SYMBOL) {
          symbols = null;
        } else {
          symbols = line.split("\\s+");
        }
      } else {
        return new String[]{};
      }
    } while (symbols == null);

    return symbols;
  }

  /**
   * Parses the employee configuration file. Reads in the names of employees.
   *
   * @param reader the reader of the employee configuration file.
   * @throws IOException if something went horribly wrong in reading files.
   */
  private void parseEmployeeConfiguration(BufferedReader reader) throws IOException {
    String[] symbols;
    while ((symbols = preParse(reader)) != null) {
      restaurant.employeeController.addEmployee(symbols[0] + symbols[1]);
    }
  }

  /**
   * Parses the ingredient configuration file. Reads in the names of ingredients.
   *
   * @param reader the reader of the ingredient configuration file.
   * @throws IOException if something went horribly wrong in reading files.
   */
  private void parseIngredientConfiguration(BufferedReader reader) throws IOException {
    String[] symbols;
    while ((symbols = preParse(reader)) != null) {
      restaurant.inventory.addIngredient(symbols[0], Integer.parseInt(symbols[1]), Integer.parseInt(symbols[2]));
    }
  }

  /**
   * Parses the menu configuration file. Reads in the names of menus, their menu items, what they are made of and
   * what additions and subtractions can be added to each.
   *
   * @param reader the reader of the menu configuration file.
   * @throws IOException if something went horribly wrong in reading files.
   */
  private void parseMenuConfiguration(BufferedReader reader) throws IOException {
    String currentMenu = null;

    String[] symbols;
    while ((symbols = preParse(reader)) != null) {
      if (currentMenu != null) {
        if (symbols[0].equals("END")) {
          currentMenu = null;
          continue;
        }

        String currentItem = symbols[0];
        restaurant.menuController.addItemToMenu(currentMenu, currentItem, Integer.parseInt(symbols[1]));

        for (String ingredient : preParse(reader)) {
          restaurant.menuController.addIngredientToMenuItem(currentMenu, currentItem, ingredient);
        }

        for (String addition : preParse(reader)) {
          restaurant.menuController.addAdditionToMenuItem(currentMenu, currentItem, addition);
        }
      } else {
        currentMenu = symbols[1];
        restaurant.menuController.addMenu(currentMenu);
      }
    }
  }

  /**
   * Parses the events file. Offloads work to {@link EventParser}.
   *
   * @param reader the reader of the employee configuration file.
   * @throws IOException if something went horribly wrong in reading files.
   */
  private void parseEventsFile(BufferedReader reader) throws IOException {
    String[] symbols;
    while ((symbols = preParse(reader)) != null) {
      EventParser eventParser = new EventParser(restaurant);
      eventParser.parseEvent(symbols);
    }
  }
}
