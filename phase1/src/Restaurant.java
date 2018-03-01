import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class Restaurant {
  private static final int NUM_TABLES = 100;

  private EmployeeController employeeController = new EmployeeController();
  private TableController tableController       = new TableController(NUM_TABLES);
  private Inventory inventory                   = new Inventory();
  private MenuController menuController         = new MenuController();

  private ArrayList<Order> pendingOrders = new ArrayList<>();


  Restaurant() {

  }

  public void parseEmployeeConfiguration(BufferedReader reader) throws IOException {
    String line;
    while ((line = reader.readLine()) != null) {
      if (line.equals("") || line.charAt(0) == '%') {
        continue;
      }

      employeeController.addEmployee(line);
    }
  }

  public void parseIngredientConfiguration(BufferedReader reader) throws IOException {
    String line;
    while ((line = reader.readLine()) != null) {
      if (line.equals("") || line.charAt(0) == '%') {
        continue;
      }

      String[] split = line.split("\\s+");

      inventory.addIngredient(split[0], Integer.parseInt(split[1]), Integer.parseInt(split[2]));
    }
  }

  public void parseMenuConfiguration(BufferedReader reader) throws IOException {
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
          System.out.println("MENU ITEM INGREDIENTS: " + ingredient);
          menuController.addIngredientToMenuItem(currentMenu, currentItem, ingredient);
        }

        for (String ingredient : reader.readLine().split("\\s+")) {
          System.out.println("MENU ITEM ADDITIONS: " + ingredient);
          menuController.addAdditionToMenuItem(currentMenu, currentItem, ingredient);
        }

      } else {
        if (line.equals("\n")) {
          continue;
        }

        currentMenu = line.split("\\s+")[1];
        System.out.println("MENU NAME: " + currentMenu);
        menuController.addMenu(currentMenu);
      }
    }
  }

  public void parseEvents(BufferedReader reader) throws IOException {
    // TODO: parse events
  }
}
