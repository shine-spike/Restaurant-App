import java.io.BufferedReader;
import java.io.IOException;
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

  public void parseConfiguration() {
    Parser.parse(this);
  }
}
