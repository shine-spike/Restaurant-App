import java.util.ArrayList;


public class Restaurant {
  private final int NUM_TABLES = 100;
  public TableController tableController = new TableController(NUM_TABLES);

  public EmployeeController employeeController = new EmployeeController();
  public Inventory inventory = new Inventory();
  public MenuController menuController = new MenuController();

  public ArrayList<Order> pendingOrders = new ArrayList<>();


  Restaurant() {

  }

  public void readFiles() {
    Parser.parseFiles(this);
  }
}
