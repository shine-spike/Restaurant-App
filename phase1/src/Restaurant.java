import java.util.ArrayList;


public class Restaurant {
  private final int NUM_TABLES = 100;
  public TableController tableController = new TableController(NUM_TABLES);

  public EmployeeController employeeController = new EmployeeController();
  public Inventory inventory = new Inventory();
  public MenuController menuController = new MenuController();

  private ArrayList<Order> pendingOrders = new ArrayList<>();


  Restaurant() {

  }

  public void readFiles() {
    Parser.parseFiles(this);
  }

  public boolean placeOrder(int employeeNumber, int tableNumber, String menuNameString, String menuItemString,
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

    if (inventory.confirm(order)) {
      return false;
    }

    pendingOrders.add(order);
    return true;
  }

  public boolean orderSeen(int employeeNumber, int orderNumber) {
    Order order = null;
    for (Order pendingOrder : pendingOrders) {
      if (pendingOrder.getOrderNumber() == orderNumber) {
        order = pendingOrder;
        break;
      }
    }

    if (order != null) {
      order.orderSeen();
      return true;
    }
    return false;
  }
}
