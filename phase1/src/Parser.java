import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Parser {
  private static final String CONFIG_LOCATION = "phase1/config/";
  private static final String EMPLOYEE_FILE_NAME = "employees.txt";
  private static final String INGREDIENTS_FILE_NAME = "ingredients.txt";
  private static final String MENUS_FILE_NAME = "menus.txt";
  private static final String EVENTS_FILE_NAME = "events.txt";


  public static void parseFiles(Restaurant restaurant) {
    // Make sure that the configuration file location exists and
    // notify if it was created right now
    Boolean madeNew = new File(CONFIG_LOCATION).mkdir();
    if (madeNew) {
      System.out.println("Created a configuration folder.");
    }

    // Generate the readers for each type of configuration file and the events file
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

    // Parse each type of configuration file and the events file
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

  private static String[] preParse(BufferedReader reader) throws IOException {
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

  private static void parseEmployeeConfiguration(BufferedReader reader, EmployeeController employeeController) throws IOException {
    String[] symbols;
    while ((symbols = preParse(reader)) != null) {
      employeeController.addEmployee(symbols[0] + symbols[1]);
    }
  }

  private static void parseIngredientConfiguration(BufferedReader reader, Inventory inventory) throws IOException {
    String[] symbols;
    while ((symbols = preParse(reader)) != null) {
      inventory.addIngredient(symbols[0], Integer.parseInt(symbols[1]), Integer.parseInt(symbols[2]));
    }
  }

  private static void parseMenuConfiguration(BufferedReader reader, MenuController menuController) throws IOException {
    String currentMenu = null;

    String[] symbols;
    while ((symbols = preParse(reader)) != null) {
      if (currentMenu != null) {
        if (symbols[0].equals("END")) {
          currentMenu = null;
          continue;
        }

        String currentItem = symbols[0];
        menuController.addItemToMenu(currentMenu, currentItem, Integer.parseInt(symbols[1]));

        for (String ingredient : preParse(reader)) {
          menuController.addIngredientToMenuItem(currentMenu, currentItem, ingredient);
        }

        for (String addition : preParse(reader)) {
          menuController.addAdditionToMenuItem(currentMenu, currentItem, addition);
        }
      } else {
        currentMenu = symbols[1];
        menuController.addMenu(currentMenu);
      }
    }
  }

  private static void parseEvents(BufferedReader reader, Restaurant restaurant) throws IOException {
    String[] symbols;
    while ((symbols = preParse(reader)) != null) {
      EventType eventType = EventType.getEventType(symbols[0]);
      if (eventType == null) {
        continue;
      }

      switch (eventType) {
        // An order is placed by a server
        case ORDER:
          parseOrderEvent(restaurant, symbols);
          break;

        // A placed order is seen by a cook
        case SEEN:
          // TODO: set the seen flag on the order
          break;

        // A seen order is ready to be served
        case READY:
          // TODO: remove the order from pending orders and notify server
          break;

        // A ready order is successfully delivered
        case ACCEPT:
          // TODO: add the order to the bill
          break;

        // A ready order is rejected by the table
        case REJECT:
          // TODO: discard the order
          break;

        // A ready order is sent back to be recooked
        case REDO:
          // TODO: copy the order, requeue it, and discard the original
          break;

        // A bill is requested by a server
        case BILL:
          // TODO: display the bill
          break;

        // A table paid off the bill
        case PAID:
          // TODO: clear the table's bill
          break;

        // An ingredient is received by an employee
        case RECEIVE:
          // TODO: add the amount of ingredient to the inventory
          break;
      }
    }
  }

  private static void parseOrderEvent(Restaurant restaurant, String[] symbols) {
    ArrayList<String> subtractions = new ArrayList<>();
    ArrayList<String> additions = new ArrayList<>();

    boolean isAddition = false;
    for (int i = 5; i < symbols.length; i++) {
      if (symbols[i].equals("NO")) {
        isAddition = false;
        continue;
      } else if (symbols[i].equals("ADD")) {
        isAddition = true;
        continue;
      }

      if (isAddition) {
        additions.add(symbols[i]);
      } else {
        subtractions.add(symbols[i]);
      }
    }

    boolean result = restaurant.placeOrder(Integer.parseInt(symbols[1]), Integer.parseInt(symbols[2]),
            symbols[3], symbols[4], subtractions, additions);

    if (!result) {
      System.out.println("Order could not be placed.");
    }
  }
}
