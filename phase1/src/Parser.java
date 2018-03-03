import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Parser {
  private static final String EMPLOYEE_FILE_NAME = "employees.txt";
  private static final String INGREDIENTS_FILE_NAME = "ingredients.txt";
  private static final String MENUS_FILE_NAME = "menus.txt";
  private static final String EVENTS_FILE_NAME = "events.txt";

  private String filesLocation = "phase1/config/";
  private Restaurant restaurant;


  Parser(Restaurant restaurant) {
    this.restaurant = restaurant;
  }

  public void setFilesLocation(String filesLocation) {
    this.filesLocation = filesLocation;
  }

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

  private BufferedReader getConfigurationReader(String fileName) throws IOException {
    File file = new File(filesLocation + fileName);

    Boolean madeNew = file.createNewFile();
    if (madeNew) {
      System.out.println("Created a " + fileName + " configuration file.");
    }
    return new BufferedReader(new FileReader(file));
  }

  private String[] preParse(BufferedReader reader) throws IOException {
    String[] symbols;

    do {
      String line = reader.readLine();
      if (line != null) {
        if (line.equals("") || line.charAt(0) == '%') {
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

  private void parseEmployeeConfiguration(BufferedReader reader) throws IOException {
    String[] symbols;
    while ((symbols = preParse(reader)) != null) {
      restaurant.employeeController.addEmployee(symbols[0] + symbols[1]);
    }
  }

  private void parseIngredientConfiguration(BufferedReader reader) throws IOException {
    String[] symbols;
    while ((symbols = preParse(reader)) != null) {
      restaurant.inventory.addIngredient(symbols[0], Integer.parseInt(symbols[1]), Integer.parseInt(symbols[2]));
    }
  }

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

  private void parseEventsFile(BufferedReader reader) throws IOException {
    String[] symbols;
    while ((symbols = preParse(reader)) != null) {
      EventParser eventParser = new EventParser(restaurant);
      eventParser.parseEvent(symbols);
    }
  }
}
