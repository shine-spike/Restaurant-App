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
public class Restaurant {
  // Logging system
  private static final String LOG_FILE_LOCATION = "phase1/log.txt";
  private final Logger logger = Logger.getLogger("Restaurant");

  // Default number of tables in the restaurant
  private static final int DEFAULT_NUM_TABLES = 100;

  // Controllers for all aspects of the Restaurant.
  public final TableController tableController;
  public final EmployeeController employeeController = new EmployeeController();
  public final Inventory inventory = new Inventory();
  public final MenuController menuController = new MenuController();

  // Holder for pending, ready, and completed orders
  private final ArrayList<Order> pendingOrders = new ArrayList<>();
  private final ArrayList<Order> readyOrders = new ArrayList<>();
  private final ArrayList<Order> completedOrders = new ArrayList<>();


  /**
   * Constructs a Restaurant with the given number of tables.
   *
   * @param numTables the number of tables in this Restaurant.
   */
  public Restaurant(int numTables) {
    tableController = new TableController(numTables);

    logger.setUseParentHandlers(false);
    System.setProperty("java.util.logging.SimpleFormatter.format",
            "%1$tF %1$tT %5$s%6$s%n");
  }

  /**
   * Constructs a Restaurant with the default number of tables.
   */
  public Restaurant() {
    this(DEFAULT_NUM_TABLES);
  }

  /**
   * Starts the logger to log information to file.
   */
  public void startLogger() {
    try {
      // Configure the logger with formatting
      FileHandler fileHandler = new FileHandler(LOG_FILE_LOCATION);
      logger.addHandler(fileHandler);
      SimpleFormatter formatter = new SimpleFormatter();
      fileHandler.setFormatter(formatter);
    } catch (IOException e) {
      System.out.println("Logging system could not be started.");
    }
  }

  /**
   * Prints the inventory to be viewed.
   */
  public void printInventory() {
    inventory.printInventory();
  }

  /**
   * Places the order an employee with the given employee number takes from the table with given table number.
   * The order consists of a given menu item name from a given menu name with a list of ingredient names
   * to be added or subtracted from the menu item.
   *
   * @param employeeNumber     the number of the employee who took this order.
   * @param tableNumber        the table that placed this order.
   * @param menuNameString     the name of the menu this order's menu item is in.
   * @param menuItemString     the name of the menu item.
   * @param subtractionStrings the list of names of ingredients to remove from the menu item.
   * @param additionStrings    the list of names of ingredients to add to the menu item.
   * @return whether or not this order has been successfully placed. This can fail if
   * there are not enough ingredients to fulfill it.
   */
  public boolean placeOrder(int employeeNumber, int tableNumber, String menuNameString, String menuItemString,
                            ArrayList<String> subtractionStrings, ArrayList<String> additionStrings) {
    MenuItem menuItem = menuController.getItemFromMenu(menuNameString, menuItemString);
    Order order = new Order(employeeNumber, tableNumber, menuItem);
    logger.info("Order number " + order.getOrderNumber() + " has been created. ("
            + employeeController.getEmployeeName(employeeNumber) + ")");

    // Adds each subtraction and addition to the order
    for (String subtraction : subtractionStrings) {
      order.addSubtraction(inventory.getIngredient(subtraction));
    }
    for (String addition : additionStrings) {
      order.addAddition(inventory.getIngredient(addition));
    }

    return registerOrder(order);
  }

  /**
   * Flags the order with the given order number to be seen and beginning to be prepared by the
   * employee with the given employee number.
   *
   * @param employeeNumber the number of the employee who saw this order.
   * @param orderNumber    the order number of the order that has been seen.
   * @return whether or not this order has been successfully flagged as seen. This can fail if
   * the order if it is not a pending order.
   */
  public boolean orderSeen(int employeeNumber, int orderNumber) {
    Order order = getOrderFromNumber(pendingOrders, orderNumber);

    if (order != null) {
      order.orderSeen();
      logger.info("Order number " + orderNumber + " has been seen. ("
              + employeeController.getEmployeeName(employeeNumber) + ")");
      return true;
    }

    logger.info("Order number " + orderNumber + " is not pending. ("
            + employeeController.getEmployeeName(employeeNumber) + ")");
    return false;
  }

  /**
   * Notifies the respective server of the order with the given order number that the employee with the given
   * employee number has finished preparing the order and it is ready to be delivered to the respective table.
   * Then updates the inventory to subtract the ingredients used to make this order.
   *
   * @param employeeNumber the number of the employee who finished preparing the order.
   * @param orderNumber    the order number of the order that is ready.
   * @return whether or not the respective server of the order has been successfully notified. This can fail if
   * the order is not a pending order.
   */
  public boolean orderReady(int employeeNumber, int orderNumber) {
    Order order = getOrderFromNumber(pendingOrders, orderNumber);

    if (order != null) {
      System.out.println("Order " + orderNumber + " is ready for delivery!");
      inventory.consumeIngredients(order);
      pendingOrders.remove(order);
      readyOrders.add(order);

      logger.info("Order number " + orderNumber + " has been prepared. ("
              + employeeController.getEmployeeName(employeeNumber) + ")");
      return true;
    }

    logger.info("Order number " + orderNumber + " is not pending. ("
            + employeeController.getEmployeeName(employeeNumber) + ")");
    return false;
  }

  /**
   * Acknowledges that the order with the given order number has been delivered and accepted by the respective table.
   * Adds the order to the bill of the table.
   *
   * @param employeeNumber the number of the employee who confirmed acceptance of the order.
   * @param orderNumber    the order number of the order that has been accepted.
   * @return whether or not this order has been successfully registered as accepted. This can fail if
   * the order is not a ready order.
   */
  public boolean orderAccepted(int employeeNumber, int orderNumber) {
    Order order = getOrderFromNumber(readyOrders, orderNumber);

    if (order != null) {
      readyOrders.remove(order);
      completedOrders.add(order);
      tableController.addToBill(order);
      logger.info("Order number " + orderNumber + " has been accepted. ("
              + employeeController.getEmployeeName(employeeNumber) + ")");
      return true;
    }

    logger.info("Order number " + orderNumber + " is not ready. ("
            + employeeController.getEmployeeName(employeeNumber) + ")");
    return false;
  }

  /**
   * Acknowledges that the order with the given order number has been rejected by the respective table.
   *
   * @param employeeNumber the number of the employee who confirmed rejection of the order.
   * @param orderNumber    the order number of the order that has been rejected.
   * @param reason         the reason this order has been rejected.
   * @return whether or not this order has been successfully registered as rejected. This can fail if
   * the order is not a ready order.
   */
  public boolean orderRejected(int employeeNumber, int orderNumber, String reason) {
    Order order = getOrderFromNumber(readyOrders, orderNumber);

    if (order != null) {
      readyOrders.remove(order);
      completedOrders.add(order);
      logger.info("Order number " + orderNumber + " has been rejected for reason \"" + reason + "\". ("
              + employeeController.getEmployeeName(employeeNumber) + ")");
      return true;
    }

    logger.info("Order number " + orderNumber + " is not ready. ("
            + employeeController.getEmployeeName(employeeNumber) + ")");
    return false;
  }

  /**
   * Acknowledges that the order with the given order number has been requested to be redone by the respective table.
   * Creates a new, identical order and attempts to add place it.
   *
   * @param employeeNumber the number of the employee who confirmed request of the order.
   * @param orderNumber    the order number of the order that has been requested to be redone.
   * @param reason         the reason this order has been requested to be redone.
   * @return whether or not this order has been successfully registered as requested to be redone. This can fail if
   * the order is not a ready order or for any {@code placeOrder} an order can fail.
   */
  public boolean redoOrder(int employeeNumber, int orderNumber, String reason) {
    Order order = getOrderFromNumber(readyOrders, orderNumber);

    if (order != null) {
      readyOrders.remove(order);
      completedOrders.add(order);
      logger.info("Order number " + orderNumber + " has been requested for redo for reason \"" + reason + "\". ("
              + employeeController.getEmployeeName(employeeNumber) + ")");

      Order redoOrder = order.duplicate();
      logger.info("Order number " + redoOrder.getOrderNumber() +
              " has been created as a redo for order number " + orderNumber + ".");

      return registerOrder(redoOrder);
    }

    logger.info("Order number " + orderNumber + " is not ready. ("
            + employeeController.getEmployeeName(employeeNumber) + ")");
    return false;
  }

  /**
   * Prints the bill of the table with given table number.
   *
   * @param employeeNumber the number of the employee who requested the printing.
   * @param tableNumber    the table number of the table to print the bill of.
   * @return whether or not the bill was successfully printed.
   */
  public boolean printBill(int employeeNumber, int tableNumber) {
    System.out.println(tableController.printBill(tableNumber));
    logger.info("Bill for table number " + tableNumber + " has been printed. ("
            + employeeController.getEmployeeName(employeeNumber) + ")");
    return true;
  }

  /**
   * Acknowledges that the table with the given table number has paid the respective bill.
   *
   * @param employeeNumber the number of the employee who confirmed payment.
   * @param tableNumber    the table number of the table that paid the bill.
   * @return whether or not the bill was successfully acknowledged to be paid off.
   */
  public boolean billPaid(int employeeNumber, int tableNumber) {
    tableController.clearBill(tableNumber);
    logger.info("Bill for table number " + tableNumber + " has been paid. ("
            + employeeController.getEmployeeName(employeeNumber) + ")");
    return true;
  }

  /**
   * Registers that a given amount of ingredient with the given ingredient name has been received.
   *
   * @param employeeNumber   the number of the employee who received the ingredient.
   * @param ingredientName   the name of the ingredient that has been received.
   * @param ingredientAmount the amount of ingredient that has been received.
   * @return whether or not the ingredient was successfully restocked.
   */
  public boolean ingredientsReceived(int employeeNumber, String ingredientName, int ingredientAmount) {
    inventory.restockIngredient(ingredientName, ingredientAmount);
    logger.info("Inventory has received " + ingredientAmount + " of " + ingredientName + ". ("
            + employeeController.getEmployeeName(employeeNumber) + ")");
    return true;
  }

  /**
   * Gets the order with a given order number from a given list of orders.
   *
   * @param orders      the list of orders to search through.
   * @param orderNumber the order number to search for.
   * @return the {@link Order} that has the given order number.
   */
  private static Order getOrderFromNumber(ArrayList<Order> orders, int orderNumber) {
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
  private boolean registerOrder(Order order) {
    if (inventory.confirmOrder(order)) {
      pendingOrders.add(order);
      logger.info("Order " + order.getOrderNumber() + " has been registered.");
      return true;
    }
    logger.info("Order " + order.getOrderNumber() + " cannot be satisfied.");
    return false;
  }
}
