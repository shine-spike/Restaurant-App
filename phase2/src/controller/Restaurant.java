package controller;

import model.Employee;
import model.Ingredient;
import model.Menu;
import util.Serializer;

import java.io.Serializable;
import java.util.ArrayList;

/** Main controller of the system, only contains hooks to sub-controllers. */
public final class Restaurant {
  // Singleton instance of the restaurant
  private static final Restaurant INSTANCE = new Restaurant();

  // Default number of tables in the restaurant
  private static final int DEFAULT_NUM_TABLES = 100;

  // Controllers for all aspects of the restaurant
  private final TableController tableController = new TableController(DEFAULT_NUM_TABLES);
  private final EmployeeController employeeController = new EmployeeController();
  private final MenuController menuController = new MenuController();
  private final OrderController orderController = new OrderController();
  private final Inventory inventory = new Inventory();

  /** Overrides default construction of restaurant. */
  private Restaurant() {}

  /**
   * Gets the created instance of the restaurant.
   *
   * @return the instance of the restaurant.
   */
  public static Restaurant getInstance() {
    return INSTANCE;
  }

  /**
   * Gets a controller for all table-related aspects of the restaurant including billing.
   *
   * @return the table controller of the restaurant.
   */
  public TableController getTableController() {
    return tableController;
  }

  /**
   * Gets a controller for all employee-related aspects of the restaurant including login.
   *
   * @return the employee controller of the restaurant.
   */
  public EmployeeController getEmployeeController() {
    return employeeController;
  }

  /**
   * Gets a controller for all menu-related aspects of the restaurant.
   *
   * @return the menu controller of the restaurant.
   */
  public MenuController getMenuController() {
    return menuController;
  }

  /**
   * Gets a controller for all order-related aspects of the restaurant.
   *
   * @return the order controller of the restaurant.
   */
  public OrderController getOrderController() {
    return orderController;
  }

  /**
   * Gets a controller for all inventory aspects of the restaurant including inventory handling.
   *
   * @return the inventory of the restaurant.
   */
  public Inventory getInventory() {
    return inventory;
  }

  /** Stores all the data in the required controllers to file. */
  public void storeModel() {
    Serializer.serialize(employeeController.getContents(), employeeController.getName() + ".ser");
    Serializer.serialize(menuController.getContents(), menuController.getName() + ".ser");
    Serializer.serialize(inventory.getContents(), inventory.getName() + ".ser");
  }

  /** Retrieves found data from file and stores it in the respective controllers. */
  public void retrieveModel() {
    Employee[] employees = Serializer.deserialize(Employee[].class, employeeController.getName() + ".ser");
    if (employees != null) {
      employeeController.setContents(employees);
    }

    Menu[] menus = Serializer.deserialize(Menu[].class, menuController.getName() + ".ser");
    if (menus != null) {
      menuController.setContents(menus);
    }

    Ingredient[] ingredients = Serializer.deserialize(Ingredient[].class, inventory.getName() + ".ser");
    if (ingredients != null) {
      inventory.setContents(ingredients);
    }
  }
}
