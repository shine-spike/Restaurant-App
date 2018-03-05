import java.util.ArrayList;


/**
 * Restaurant in the system. Manages all interactions.
 *
 * Contains all functional parts of a restaurant and provides an interface for interaction.
 */
public class Restaurant {
  /**
   * Default number of tables in a Restaurant.
   */
  private static final int DEFAULT_NUM_TABLES = 100;

  // Controllers for all aspects of the Restaurant.
  public TableController tableController;
  public EmployeeController employeeController = new EmployeeController();
  public Inventory inventory = new Inventory();
  public MenuController menuController = new MenuController();

  // Holder for pending and ready orders
  private ArrayList<Order> pendingOrders = new ArrayList<>();
  private ArrayList<Order> readyOrders = new ArrayList<>();


  /**
   * Constructs a Restaurant with the given number of tables.
   *
   * @param numTables the number of tables in this Restaurant.
   */
  Restaurant(int numTables) {
    tableController = new TableController(numTables);
  }

  /**
   * Constructs a Restaurant with the default number of tables.
   */
  Restaurant() {
    this(DEFAULT_NUM_TABLES);
  }

  /**
   * Places the order an employee with the given employee number takes from the table with given table number.
   * The order consists of a given menu item name from a given menu name with a list of ingredient names
   * to be added or subtracted from the menu item.
   *
   * @param employeeNumber the number of the employee who took this order.
   * @param tableNumber the table that placed this order.
   * @param menuNameString the name of the menu this order's menu item is in.
   * @param menuItemString the name of the menu item.
   * @param subtractionStrings the list of names of ingredients to remove from the menu item.
   * @param additionStrings the list of names of ingredients to add to the menu item.
   *
   * @return whether or not this order has been successfully placed. This can fail if
   * there are not enough ingredients to fulfill it.
   */
  public boolean placeOrder(int employeeNumber, int tableNumber, String menuNameString, String menuItemString,
                            ArrayList<String> subtractionStrings, ArrayList<String> additionStrings) {

    MenuItem menuItem = menuController.getItemFromMenu(menuNameString, menuItemString);
    Order order = new Order(employeeNumber, tableNumber, menuItem);

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
   * @param orderNumber the order number of the order that has been seen.
   *
   * @return whether or not this order has been successfully flagged as seen. This can fail if
   * the order if it is not a pending order.
   */
  public boolean orderSeen(int employeeNumber, int orderNumber) {
    Order order = getOrderFromNumber(pendingOrders, orderNumber);

    if (order != null) {
      order.orderSeen();
      return true;
    }
    return false;
  }

  /**
   * Notifies the respective server of the order with the given order number that the employee with the given
   * employee number has finished preparing the order and it is ready to be delivered to the respective table.
   *
   * @param employeeNumber the number of the employee who finished preparing the order.
   * @param orderNumber the order number of the order that is ready.
   *
   * @return whether or not the respective server of the order has been successfully notified. This can fail if
   * the order is not a pending order.
   */
  public boolean orderReady(int employeeNumber, int orderNumber) {
    Order order = getOrderFromNumber(pendingOrders, orderNumber);

    if (order != null) {
      System.out.println("Order " + orderNumber + " is ready for delivery!");
      pendingOrders.remove(order);
      readyOrders.add(order);
      return true;
    }
    return false;
  }

  /**
   * Acknowledges that the order with the given order number has been delivered and accepted by the respective table.
   * Adds the order to the bill of the table.
   *
   * @param employeeNumber the number of the employee who confirmed acceptance of the order.
   * @param orderNumber the order number of the order that has been accepted.
   *
   * @return whether or not this order has been successfully registered as accepted. This can fail if
   * the order is not a ready order.
   */
  public boolean orderAccepted(int employeeNumber, int orderNumber) {
    Order order = getOrderFromNumber(readyOrders, orderNumber);

    if (order != null) {
      readyOrders.remove(order);
      tableController.addToBill(order);
      return true;
    }
    return false;
  }

  /**
   * Acknowledges that the order with the given order number has been rejected by the respective table.
   *
   * @param employeeNumber the number of the employee who confirmed rejection of the order.
   * @param orderNumber the order number of the order that has been rejected.
   *
   * @return whether or not this order has been successfully registered as rejected. This can fail if
   * the order is not a ready order.
   */
  public boolean orderRejected(int employeeNumber, int orderNumber, String reason) {
    Order order = getOrderFromNumber(readyOrders, orderNumber);

    if (order != null) {
      readyOrders.remove(order);
      return true;
    }
    return false;
  }

  /**
   * Acknowledges that the order with the given order number has been requested to be redone by the respective table.
   * Creates a new, identical order and attempts to add place it.
   *
   * @param employeeNumber the number of the employee who confirmed request of the order.
   * @param orderNumber the order number of the order that has been requested to be redone.
   *
   * @return whether or not this order has been successfully registered as requested to be redone. This can fail if
   * the order is not a ready order or for any {@link #placeOrder} an order can fail.
   */
  public boolean redoOrder(int employeeNumber, int orderNumber, String reason) {
    Order order = getOrderFromNumber(readyOrders, orderNumber);

    if (order != null) {
      readyOrders.remove(order);
      Order redoOrder = new Order(order.getEmployeeNumber(), order.getTableNumber(), order.getMenuItem());
      return registerOrder(redoOrder);
    }
    return false;
  }

  /**
   * Prints the bill of the table with given table number.
   *
   * @param employeeNumber the number of the employee who requested the printing.
   * @param tableNumber the table number of the table to print the bill of.
   *
   * @return whether or not the bill was successfully printed.
   */
  public boolean printBill(int employeeNumber, int tableNumber) {
    System.out.println(tableController.printBill(tableNumber));
    return true;
  }

  /**
   * Acknowledges that the table with the given table number has paid the respective bill.
   *
   * @param employeeNumber the number of the employee who confirmed payment.
   * @param tableNumber the table number of the table that paid the bill.
   *
   * @return whether or not the bill was successfully acknowledged to be paid off.
   */
  public boolean billPaid(int employeeNumber, int tableNumber) {
    tableController.payBill(tableNumber);
    return true;
  }

  /**
   * Registers that a given amount of ingredient with the given ingredient name has been received.
   *
   * @param employeeNumber the number of the employee who received the ingredient.
   * @param ingredientName the name of the ingredient that has been received.
   * @param ingredientAmount the amount of ingredient that has been received.
   *
   * @return whether or not the ingredient was successfully restocked.
   */
  public boolean ingredientsReceived(int employeeNumber, String ingredientName, int ingredientAmount) {
    inventory.restockIngredient(ingredientName, ingredientAmount);
    return true;
  }

  /**
   * Gets the order with a given order number from a given list of orders.
   *
   * @param orders the list of orders to search through.
   * @param orderNumber the order number to search for.
   *
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
   *
   * @return whether or not the order has been successfully registered. This can fail if
   * there are not enough ingredients to fulfil it.
   */
  private boolean registerOrder(Order order) {
    if (inventory.confirmOrder(order)) {
      pendingOrders.add(order);
      return true;
    }
    return false;
  }

}
