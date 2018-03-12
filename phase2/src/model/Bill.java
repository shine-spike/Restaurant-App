package model;

import java.util.ArrayList;

/**
 * Represents a bill
 * <p>
 * Stores an ArrayList of bills
 */
public class Bill {
  private final ArrayList<Order> orders = new ArrayList<>();


  /**
   * Adds the given order to the bill.
   *
   * @param order the order to add to the bill.
   */
  public void addOrder(Order order) {
    orders.add(order);
  }

  /**
   * Computes the total price of all orders on this bill.
   *
   * @return the total price on this bill.
   */
  private int getTotalPrice() {
    int total = 0;
    for (Order order : orders) {
      total += order.getPrice();
    }
    return total;
  }

  /**
   * Returns a string representation of the bill in the following format.
   *
   * === BILL ===
   * [ORDER_1]
   * [ORDER_2]
   * ...
   *
   * TOTAL: [TOTAL_PRICE]
   *
   * @return the bill in string format.
   */
  public String toString() {
    StringBuilder str = new StringBuilder();

    str.append("\n=== BILL ===\n");
    for (Order order : orders) {
      str.append(order.toString()).append("\n\n");
    }

    str.append("TOTAL: ").append(getTotalPrice());
    return str.toString();
  }
}
