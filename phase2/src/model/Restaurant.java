package model;

import java.io.IOException;
import java.util.ArrayList;
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
  private static final Restaurant INSTANCE = new Restaurant(DEFAULT_NUM_TABLES);

  // Controllers for all aspects of the Restaurant.
  public final TableController tableController;
  public final EmployeeController employeeController = new EmployeeController();
  public final Inventory inventory = new Inventory();
  public final MenuController menuController = new MenuController();

  // Holder for all orders are in the restaurant
  private final ArrayList<Order> orders = new ArrayList<>();


  /**
   * Constructs a Restaurant with the given number of tables.
   *
   * @param numTables the number of tables in this Restaurant.
   */
  private Restaurant(int numTables) {
    tableController = new TableController(numTables);
  }

  public static Restaurant getInstance() {
    return INSTANCE;
  }

  /**
   * Prints the inventory to be viewed.
   */
  public void printInventory() {
    inventory.printInventory();
  }

  /**
   * Gets the order with a given order number from all the orders.
   *
   * @param orderNumber the order number to search for.
   * @return the {@link Order} that has the given order number.
   */
  public Order getOrderFromNumber(int orderNumber) {
    for (Order order : orders) {
      if (order.getOrderNumber() == orderNumber) {
        return order;
      }
    }
    return null;
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
      orders.add(order);
      return true;
    }
    order.setStatus(OrderStatus.UNSATISFIABLE);
    return false;
  }
}
