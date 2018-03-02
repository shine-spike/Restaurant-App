import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Parser {
  private static final String CONFIG_LOCATION       = "phase1/config/";
  private static final String EMPLOYEE_FILE_NAME    = "employees.txt";
  private static final String INGREDIENTS_FILE_NAME = "ingredients.txt";
  private static final String MENUS_FILE_NAME       = "menus.txt";
  private static final String EVENTS_FILE_NAME      = "events.txt";


  public static void parse(Restaurant restaurant) {
    // Make sure that the configuration file location exists
    Boolean madeNew = new File(CONFIG_LOCATION).mkdir();
    if (madeNew) {
      System.out.println("Created a configuration folder.");
    }

    BufferedReader employeeReader;
    BufferedReader ingredientReader;
    BufferedReader menuReader;
    BufferedReader eventReader;
    try {
      employeeReader = getConfigurationReader(EMPLOYEE_FILE_NAME);
      ingredientReader = getConfigurationReader(INGREDIENTS_FILE_NAME);
      menuReader = getConfigurationReader(MENUS_FILE_NAME);
      eventReader = getConfigurationReader(EVENTS_FILE_NAME);
    } catch (IOException e) {
      // TODO: be more delicate
      System.out.println("Finding configuration files failed.");
      System.exit(1);
      return;
    }

    try {
      parseEmployeeConfiguration(employeeReader, restaurant.employeeController);
      parseIngredientConfiguration(ingredientReader, restaurant.inventory);
      parseMenuConfiguration(menuReader, restaurant.menuController);
      parseEvents(eventReader, restaurant);
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

  private static void parseEmployeeConfiguration(BufferedReader reader, EmployeeController employeeController) throws IOException {
    String line;
    while ((line = reader.readLine()) != null) {
      if (line.equals("") || line.charAt(0) == '%') {
        continue;
      }

      employeeController.addEmployee(line);
    }
  }

  private static void parseIngredientConfiguration(BufferedReader reader, Inventory inventory) throws IOException {
    String line;
    while ((line = reader.readLine()) != null) {
      if (line.equals("") || line.charAt(0) == '%') {
        continue;
      }

      String[] split = line.split("\\s+");

      inventory.addIngredient(split[0], Integer.parseInt(split[1]), Integer.parseInt(split[2]));
    }
  }

  private static void parseMenuConfiguration(BufferedReader reader, MenuController menuController) throws IOException {
    String line;
    String currentMenu = null;

    while ((line = reader.readLine()) != null) {
      if (line.equals("") || line.charAt(0) == '%') {
        continue;
      }

      if (currentMenu != null) {
        if (line.equals("END")) {
          currentMenu = null;
          continue;
        }

        String[] split = line.split("\\s+");
        String currentItem = split[0];
        menuController.addItemToMenu(currentMenu, currentItem, Integer.parseInt(split[1]));

        for (String ingredient : reader.readLine().split("\\s+")) {
          menuController.addIngredientToMenuItem(currentMenu, currentItem, ingredient);
        }

        for (String ingredient : reader.readLine().split("\\s+")) {
          menuController.addAdditionToMenuItem(currentMenu, currentItem, ingredient);
        }

      } else {
        if (line.equals("\n")) {
          continue;
        }

        currentMenu = line.split("\\s+")[1];
        menuController.addMenu(currentMenu);
      }
    }
  }

  private static void parseEvents(BufferedReader reader, Restaurant restaurant) throws IOException {
    // TODO: parse events
  }
}
