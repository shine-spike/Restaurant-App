package model;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


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
  public final TableController tableController = new TableController(DEFAULT_NUM_TABLES);
  public final EmployeeController employeeController = new EmployeeController();
  public final Inventory inventory = new Inventory();
  public final MenuController menuController = new MenuController();
  public final OrderController orderController = new OrderController();


  /**
   * Constructs a Restaurant with the given number of tables.
   */
  private Restaurant() {}

  public static Restaurant getInstance() {
    return INSTANCE;
  }

  public Order getOrderFromNumber(int orderNumber) {
    return orderController.getOrderFromNumber(orderNumber);
  }

  public void addToBill(Order order) {
    tableController.addToBill(order);
  }

  public Order createOrder(int employeeNumber, int tableNumber, String menuNameString, String menuItemString,
                           HashMap<String, Integer> ingredientStrings) {
    MenuItem menuItem = menuController.getItemFromMenu(menuNameString, menuItemString);
    HashMap<Ingredient, Integer> ingredients = new HashMap<>();

    // Adds each subtraction and addition to the order
    for (String ingredientString : ingredientStrings.keySet()) {
      ingredients.put(inventory.getIngredient(ingredientString), ingredientStrings.get(ingredientString));
    }

    return orderController.createOrder(employeeNumber, tableNumber, menuItem, ingredients);
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
