package model;

import java.util.HashMap;

/**
 * Represent an order of a single menu item with any additions or subtractions with all required
 * information to travel through the restaurant system.
 */
public class Order {
  private static int numOrders = 0;

  private final int orderNumber;
  private final int tableNumber;
  private final int customerIndex;
  private final Menu menu;
  private final MenuItem menuItem;
  private final HashMap<Ingredient, Integer> ingredientChanges;

  private OrderStatus status = OrderStatus.CREATED;

  /**
   * Constructs an order consisting of a given menu item at a given table for a given customer
   * index, with any ingredient changes to the menu item.
   *
   * @param tableNumber the number of the table which placed this order.
   * @param customerIndex the index of the customer for which this order is.
   * @param menu the menu this order was placed on.
   * @param menuItem the menu item ordered.
   * @param ingredientChanges the changes to each ingredient from the menu item default
   */
  public Order(
      int tableNumber,
      int customerIndex,
      Menu menu,
      MenuItem menuItem,
      HashMap<Ingredient, Integer> ingredientChanges) {
    this.orderNumber = numOrders;
    this.tableNumber = tableNumber;
    this.customerIndex = customerIndex;

    this.menu = menu;
    this.menuItem = menuItem;
    this.ingredientChanges = ingredientChanges;

    numOrders++;
  }

  /**
   * Gets the number of the table that placed this order.
   *
   * @return the number of the table that placed this order.
   */
  public int getTableNumber() {
    return tableNumber;
  }

  /**
   * Gets the price of this order.
   *
   * @return the price of this order.
   */
  public int getPrice() {
    return menuItem.getPrice();
  }

  /**
   * Gets the number of this order.
   *
   * @return the number of this order.
   */
  public int getOrderNumber() {
    return orderNumber;
  }

  /**
   * Gets the index of the customer for which this order is.
   *
   * @return the index of the customer.
   */
  public int getCustomerIndex() {
    return customerIndex;
  }

  /**
   * Gets the current status of this order.
   *
   * @return the status of this order.
   */
  public OrderStatus getStatus() {
    return status;
  }

  /**
   * Sets the status of this order.
   *
   * @param status the new status of this order.
   */
  public void setStatus(OrderStatus status) {
    this.status = status;
  }

  /**
   * Binds this order's information into an array and returns it.
   *
   * @return an array containing order information.
   */
  public String[] getOrderInformation() {
    return new String[] {
      Integer.toString(orderNumber),
      status.toString(),
      menu.getName(),
      menuItem.getName(),
      Integer.toString(tableNumber)
    };
  }

  /**
   * Returns the list of ingredients needed to make this order including the additions and excluding
   * the subtractions.
   *
   * @return the list of ingredients to make this order.
   */
  public HashMap<Ingredient, Integer> getIngredients() {
    HashMap<Ingredient, Integer> ingredients = new HashMap<>(menuItem.getIngredients());
    for (Ingredient ingredientChange : ingredientChanges.keySet()) {
      ingredients.put(ingredientChange, ingredientChanges.getOrDefault(ingredientChange, 0) + 1);
    }
    return ingredients;
  }

  /**
   * Returns this order's menuItem
   *
   * @return the menuItem of this Order
   */
  MenuItem getMenuItem() {
    return menuItem;
  }
}
