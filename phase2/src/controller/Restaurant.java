package controller;

import model.Ingredient;
import model.MenuItem;
import model.Order;
import model.OrderStatus;

import java.util.HashMap;


/**
 * Restaurant in the system. Manages all interactions.
 * <p>
 * Contains all functional parts of a restaurant and provides an interface for interaction.
 */
public final class Restaurant {
  // Default number of tables in the restaurant
  private static final int DEFAULT_NUM_TABLES = 100;

  // Singleton
  private static final Restaurant INSTANCE = new Restaurant();

  // Controllers for all aspects of the Restaurant.
  private final TableController tableController = new TableController(DEFAULT_NUM_TABLES);
  private final EmployeeController employeeController = new EmployeeController();
  private final MenuController menuController = new MenuController();
  private final OrderController orderController = new OrderController();
  private final Inventory inventory = new Inventory();


  /**
   * Constructs a Restaurant with the given number of tables.
   */
  private Restaurant() {}

  public static Restaurant getInstance() {
    return INSTANCE;
  }

  public TableController getTableController() {
    return tableController;
  }

  public EmployeeController getEmployeeController() {
    return employeeController;
  }

  public MenuController getMenuController() {
    return menuController;
  }

  public OrderController getOrderController() {
    return orderController;
  }

  public Inventory getInventory() {
    return inventory;
  }

  public Order createOrder(int employeeNumber, int tableNumber, int customerIndex,
                           String menuNameString, String menuItemString, HashMap<String, Integer> ingredientStrings) {
    MenuItem menuItem = menuController.getMenuItem(menuNameString, menuItemString);
    HashMap<Ingredient, Integer> ingredients = new HashMap<>();

    // Adds each subtraction and addition to the order
    for (String ingredientString : ingredientStrings.keySet()) {
      ingredients.put(inventory.getIngredient(ingredientString), ingredientStrings.get(ingredientString));
    }

    return orderController.createOrder(employeeNumber, tableNumber, customerIndex, menuItem, ingredients);
  }

  /**
   * Registers a given order to be placed.
   *
   * @param order the order to register.
   * @return whether or not the order has been successfully registered. This can fail if
   * there are not enough ingredients to fulfil it.
   */
  public boolean registerOrder(Order order) {
    if (inventory.confirmOrder(order)) {
      order.setStatus(OrderStatus.PLACED);
      orderController.registerOrder(order);
      return true;
    }
    order.setStatus(OrderStatus.UNSATISFIABLE);
    return false;
  }
}
