import java.util.ArrayList;


public class Restaurant {
  private final int NUM_TABLES = 100;
  private TableController tableController = new TableController(NUM_TABLES);

  public EmployeeController employeeController = new EmployeeController();
  public Inventory inventory = new Inventory();
  public MenuController menuController = new MenuController();

  private ArrayList<Order> pendingOrders = new ArrayList<>();
  private ArrayList<Order> readyOrders = new ArrayList<>();


  Restaurant() {

  }

  public void readFiles() {
    Parser.parseFiles(this);
  }

  private static Order getOrderFromNumber(ArrayList<Order> orders, int orderNumber) {
    for (Order order : orders) {
      if (order.getOrderNumber() == orderNumber) {
        return order;
      }
    }
    return null;
  }

  private boolean placeOrder(Order order) {
    if (inventory.confirm(order)) {
      pendingOrders.add(order);
      return true;
    }
    return false;
  }

  public boolean orderPlace(int employeeNumber, int tableNumber, String menuNameString, String menuItemString,
                            ArrayList<String> subtractionStrings, ArrayList<String> additionStrings) {

    MenuItem menuItem = menuController.getItemFromMenu(menuNameString, menuItemString);
    Order order = new Order(employeeNumber, tableNumber, menuItem);

    for (String subtraction : subtractionStrings) {
      Ingredient ingredient = inventory.getIngredient(subtraction);
      order.addSubtraction(ingredient);
    }

    for (String addition : additionStrings) {
      Ingredient ingredient = inventory.getIngredient(addition);
      order.addAddition(ingredient);
    }

    return placeOrder(order);
  }

  public boolean orderSee(int employeeNumber, int orderNumber) {
    Order order = getOrderFromNumber(pendingOrders, orderNumber);

    if (order != null) {
      order.orderSeen();
      return true;
    }
    return false;
  }

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

  public boolean orderAccept(int employeeNumber, int orderNumber) {
    Order order = getOrderFromNumber(readyOrders, orderNumber);

    if (order != null) {
      readyOrders.remove(order);
      tableController.addToBill(order);
      return true;
    }
    return false;
  }

  public boolean orderReject(int employeeNumber, int orderNumber, String reason) {
    Order order = getOrderFromNumber(readyOrders, orderNumber);

    if (order != null) {
      readyOrders.remove(order);
      return true;
    }
    return false;
  }

  public boolean orderRedo(int employeeNumber, int orderNumber, String reason) {
    Order order = getOrderFromNumber(readyOrders, orderNumber);

    if (order != null) {
      readyOrders.remove(order);
      Order redoOrder = new Order(order.getEmployeeNumber(), order.getTableNumber(), order.getMenuItem());
      return placeOrder(redoOrder);
    }
    return false;
  }

  public boolean printBill(int employeeNumber, int tableNumber) {
    System.out.println(tableController.printBill(tableNumber));
    return true;
  }

  public boolean payBill(int employeeNumber, int tableNumber) {
    tableController.clearBill(tableNumber);
    return true;
  }
}
