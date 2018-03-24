package model;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;


/**
 * Represent an order of a single menu item with any additions or subtractions
 * with all required information to travel through the restaurant system.
 */
public class Order {
  private static int numOrders = 0;

  private final int orderNumber;
  private final int tableNumber;
  private final int customerIndex;
  private final int employeeNumber;
  private final MenuItem menuItem;
  private final HashMap<Ingredient, Integer> ingredients;

  // Whether or not this order has been seen and is being prepared
  private OrderStatus status = OrderStatus.CREATED;


  /**
   * Constructs an order consisting of a given menu item at a given table,
   * taken by the employee with the given number.
   *
   * @param employeeNumber the number of the employee who took this order.
   * @param tableNumber    the number of the table which placed this order.
   * @param menuItem       the MenuItem ordered.
   */
  public Order(int employeeNumber, int tableNumber, int customerIndex,
               MenuItem menuItem, HashMap<Ingredient, Integer> ingredients) {
    this.orderNumber = numOrders;
    this.tableNumber = tableNumber;
    this.customerIndex = customerIndex;

    this.employeeNumber = employeeNumber;
    this.menuItem = menuItem;
    this.ingredients = ingredients;

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

  public int getCustomerIndex() {
    return customerIndex;
  }

  public OrderStatus getStatus() {
    return status;
  }

  public void setStatus(OrderStatus status) {
    this.status = status;
  }

  /**
   * Deeply duplicates this order and returns the copy.
   *
   * @return a deep copy of this order.
   */
  @NotNull
  public Order duplicate() {
    return new Order(employeeNumber, tableNumber, customerIndex, menuItem, ingredients);
  }

  /**
   * Returns the list of ingredients needed to make this order
   * including the additions and excluding the subtractions.
   *
   * @return the list of ingredients to make this order.
   */
  public HashMap<Ingredient, Integer> getIngredients() {
    return ingredients;
  }
}
